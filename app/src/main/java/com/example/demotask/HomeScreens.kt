package com.example.demotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeScreens : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screens)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        setTime_Savescreens(5)
    }

    private fun setTime_Savescreens(minutes: Int) {
        val triggerTime = System.currentTimeMillis() + (minutes * 60 * 1000)

        // Tạo Intent để chuyển trang tới SaveActivity
        val intent = Intent(this, SaverScreens::class.java)

        // Tạo PendingIntent cho chuyển trang
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Đặt hẹn giờ
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }
}