package com.example.demotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demotask.BoardCast.AppStartBroadcastReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intent: Intent

    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Đã Vào ĐÂy")
        // Khởi tạo AlarmManager
         alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        // Khởi tạo Intent với action "com.example.demotask.CUSTOM_ACTION"
         intent = Intent(this, AppStartBroadcastReceiver::class.java)
        // Khởi tạo PendingIntent
         pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Đặt alarm và gửi broadcast
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            pendingIntent
        )

        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            intent.action = "com.example.demotask.CUSTOM_ACTION"

            // tạo intent tới boardcast
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            //tạo trigger time hệ thống +3s sau khi click nút thì sang HomeScreens
            val triggerTime = SystemClock.elapsedRealtime() + 3000 // 3 seconds

            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
    override fun onResume() {
        super.onResume()
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "MyApp:WakeLockTag"
        )
        wakeLock.acquire()
        //
        Handler(Looper.getMainLooper()).postDelayed({
            wakeLock.release()
        }, 2000) // Đặt độ trễ tùy ý (ví dụ: 2 giây)
        Toast.makeText(applicationContext, "Chào Mừng Quay Trở lại main Activity!", Toast.LENGTH_SHORT).show()

    }
}