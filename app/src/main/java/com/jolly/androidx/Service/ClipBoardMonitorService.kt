package com.jolly.androidx.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.jolly.androidx.MainActivity
import com.jolly.androidx.R
import com.jolly.androidx.Room.CopyRepository
import com.jolly.androidx.Room.CopyRoomDatabase
import com.jolly.androidx.Room.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ClipBoardMonitorService : Service(), ClipboardManager.OnPrimaryClipChangedListener {

    companion object {
        const val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"

        const val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
    }

    private var repository: CopyRepository? = null
    private val scope = CoroutineScope(Dispatchers.IO)


    private lateinit var clipboardManager: ClipboardManager


    private val iBinder: IBinder = LocalBinder()


    class LocalBinder : Binder() {

        fun getService(): ClipBoardMonitorService {
            return getService()
        }
    }

    /** method for clients  */
    val currentStrInClipBrd: String
        get() = clipboardManager.primaryClip!!.getItemAt(0).toString()


    override fun onCreate() {
        super.onCreate()
        Log.i("TAG","onCreate "+javaClass.canonicalName)
        val copyDao = CopyRoomDatabase.getDatabase(applicationContext).copyDao()
        repository = CopyRepository(copyDao)
        clipboardManager= getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
       clipboardManager.addPrimaryClipChangedListener (this)
    }

    override fun onPrimaryClipChanged() {


            val clipData = clipboardManager.primaryClip
            if (clipData != null && clipData.itemCount > 0) {
                val item = clipData.getItemAt(0)
                val clipDescription = clipData.description
                var newStr = ""
                if (clipData.itemCount > 0 && clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    if (item.text != null) {
                        newStr = item.text.toString()
                    }
                } else if (clipData.itemCount > 0 && clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) {
                    newStr = item.text.toString()
                }
                insert(Word(0,newStr))


        }

    }

      fun insert(word: Word) {
          scope.launch {
              repository!!.insert(word)
          }
    }

    override fun onBind(intent: Intent?): IBinder? {

        return iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action

            if (action != null) {
                when (action) {
                    ACTION_START_FOREGROUND_SERVICE -> {
                        startForegroundService()
                        Toast.makeText(applicationContext, "Foreground service is started.", Toast.LENGTH_LONG).show()
                    }
                    ACTION_STOP_FOREGROUND_SERVICE -> {
                        stopForegroundService()
                        Toast.makeText(applicationContext, "Foreground service is stopped.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
            val activityIntent = Intent(this, MainActivity::class.java)
            val pendingActivityIntent = PendingIntent.getActivity(this, 0, activityIntent, 0)
            val serviceIntent = Intent(this, ClipBoardMonitorService::class.java)
            serviceIntent.action = ACTION_STOP_FOREGROUND_SERVICE

            val pStopSelf = PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            val action = NotificationCompat.Action.Builder(0, "STOP", pStopSelf).build()
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Clipboard Monitoring..")
                    //.setContentText("Click on stop to monitor")
                    .setSmallIcon(R.drawable.ic_menu_share)
                    .addAction(action)
                    .setContentIntent(pendingActivityIntent).build()
            startForeground(1, notification)
        }
    }

    private fun stopForegroundService() {
        Log.d("TAG_FOREGROUND_SERVICE", "Stop foreground service.")

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG","onDestroy "+javaClass.canonicalName)
    }
}