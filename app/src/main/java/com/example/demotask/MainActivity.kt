package com.example.demotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Đã Vào ĐÂy")

        // Khởi tạo AlarmManager
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Khởi tạo PendingIntent
        val intent = Intent(this, AppStartBroadcastReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Đặt hẹn giờ cụ thể
        setSpecificAlarmTime(11, 45)
        // set time  chuyển vào saveScreens
        setTime_Savescreens(5)
        //
        check_sleep(1)


        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            intent.action = "com.example.demotask.CUSTOM_ACTION"
            // if  nhấn nút button đặt bộ đếm lại
            setTime_Savescreens(1)

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            // time hệ thống +3s sau khi click nút thì sang HomeScreens
            val triggerTime = SystemClock.elapsedRealtime() + 3000 // 3 seconds

            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }


    private fun setSpecificAlarmTime(hour: Int, minute: Int) {

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        // Kiểm tra nếu thời gian đã qua, thì đặt hẹn giờ cho ngày hôm sau
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Đặt hẹn giờ
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
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
    private fun check_sleep(minutes: Int) {
        val triggerTime = System.currentTimeMillis() + (minutes * 60 * 1000)

        // Tạo Intent để gửi tới BroadcastReceiver
        val intent = Intent(this, SleepCheckReceiver::class.java)

        // Tạo PendingIntent cho BroadcastReceiver
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Đặt hẹn giờ kiểm tra trạng thái ngủ
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, triggerTime,
            (minutes * 60 * 1000).toLong(), pendingIntent
        )
    }
}