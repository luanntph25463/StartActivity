package com.example.demotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import java.math.BigDecimal
import java.math.BigInteger
import androidx.core.content.ContextCompat
import com.example.demotask.HomeScreens
import com.example.demotask.MainActivity
import com.example.demotask.RestartAppService
import java.util.Calendar

class AppStartBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "BoardCast"
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    override fun onReceive(context: Context, intent: Intent) {
            // use Action_Screen_OFF check  when screens off
            // Màn hình tắt, khởi chạy ứng dụng
        Log.d("Vào ĐÂy", "s")
        scheduleAlarm(context)
        when (intent.action) {
            "com.example.demotask.CUSTOM_ACTION" -> {
                // Chuyển đến HomeActivity
                val homeIntent = Intent(context, HomeScreens::class.java)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(homeIntent)
                Log.d(TAG, "home")
                // Hiển thị Toast
                Toast.makeText(context, "Home sss!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun scheduleAlarm(context: Context) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MainActivity::class.java)
        pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val hour = 16
        val minute = 1
        setSpecificAlarmTime(hour, minute, context)

    }
    private fun setSpecificAlarmTime(hour: Int, minute: Int, context: Context) {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // sau khi quay về main activity kèm theo gửi  1 action sang servicePendingIntent chạy ForeGround
        val serviceIntent = Intent(context, RestartAppService::class.java)
        serviceIntent.action = "com.example.demotask.SHOW_NOTIFICATION_ACTION"
        val servicePendingIntent = PendingIntent.getService(
            context,
            0,
            serviceIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, servicePendingIntent)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
    // fun check wakeUpAlarm
//    private fun scheduleWakeUpAlarm(context: Context) {
//        Log.d(TAG, "wakeUpTime")
//
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, WakeUpReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            0,
//            intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//        )
//        val wakeUpTime = System.currentTimeMillis() + 5000 // Thời gian "wake up" sau 5 giây
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            wakeUpTime,
//            pendingIntent
//        )
//    }
}



