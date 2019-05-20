package com.jolly.androidx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jolly.androidx.Adapter.RecycleViewAdapter
import com.jolly.androidx.Room.CopyViewModel
import com.jolly.androidx.Room.Word

class MainActivity : AppCompatActivity() {
    private lateinit var copyViewModel: CopyViewModel
    companion object {
        const val newWordActivityRequestCode = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_main)

        copyViewModel = ViewModelProviders.of(this).get(CopyViewModel::class.java)


        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, AddNewCopy::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        val recyclerView = findViewById<View>(R.id.recyclerview) as androidx.recyclerview.widget.RecyclerView
        recyclerView.layoutManager= androidx.recyclerview.widget.LinearLayoutManager(this)
        val adapter = RecycleViewAdapter(this)
        recyclerView.adapter=adapter

        copyViewModel.allData.observe(this, Observer {t -> t.let { adapter.updateLsit(t) } })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = it.getStringExtra(AddNewCopy.EXTRA_REPLY)
                copyViewModel.insert(Word(0,word))
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

}
