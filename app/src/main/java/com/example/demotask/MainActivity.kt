package com.example.demotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
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
        // set time  chuyển vào saveScreens
//        setTime_Savescreens(1)
        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            intent.action = "com.example.demotask.CUSTOM_ACTION"
//            // if  nhấn nút button đặt bộ đếm lại
//            setTime_Savescreens(1)
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
        val wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "MyApp:WakeLockTag")
        wakeLock.acquire()

        // Đặt một độ trễ nhỏ và sau đó giải phóng wakeLock
        Handler(Looper.getMainLooper()).postDelayed({
            wakeLock.release()
        }, 2000) // Đặt độ trễ tùy ý (ví dụ: 2 giây)
        Toast.makeText(this, "Quay Trở Lại Màn hình Main Activity!", Toast.LENGTH_SHORT).show()
    }
//    private fun setTime_Savescreens(minutes: Int) {
//        //  tạo trigger minitus
////        val triggerTime = System.currentTimeMillis() + (minutes * 60 * 1000)
////        // Tạo Intent để chuyển trang tới SaveActivity
////        val intent = Intent(this, SaverScreens::class.java)
////        // Tạo PendingIntent cho chuyển trang
////        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
////        // Đặt hẹn giờ
////        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
//    }

}