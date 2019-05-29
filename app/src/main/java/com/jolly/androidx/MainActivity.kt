package com.jolly.androidx

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jolly.androidx.Service.ClipBoardMonitorService


class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.data_nav_fragment) as NavHostFragment?
        val bottomNav=findViewById<BottomNavigationView>(R.id.btm_nav_view)
        bottomNav.setOnNavigationItemSelectedListener(bottomNavigationListener)

        NavigationUI.setupWithNavController(bottomNav, navHostFragment!!.navController)
    }




    private val bottomNavigationListener=BottomNavigationView.OnNavigationItemSelectedListener{
        when(it.itemId){
          /*  R.id.allFragment->{
               findNavController(R.id.data_nav_fragment).navigate(R.id.allFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.settingFragment->{
               // findNavController(R.id.data_nav_fragment).navigate(R.id.action_allFragment_to_settingFragment)
                return@OnNavigationItemSelectedListener true
            }*/


        }
        false
    }



    override fun onStart() {
        super.onStart()
        Log.i("TAG","onStart "+javaClass.canonicalName)
        val intent = Intent(this, ClipBoardMonitorService::class.java)
        intent.action = ClipBoardMonitorService.ACTION_START_FOREGROUND_SERVICE
        startService(intent)

    }



}
