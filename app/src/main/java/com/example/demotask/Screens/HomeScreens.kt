package com.example.demotask.Screens

import android.app.AlarmManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demotask.R

class HomeScreens : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screens)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    }

}