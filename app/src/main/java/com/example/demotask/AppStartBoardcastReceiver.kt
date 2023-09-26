package com.example.demotask

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.demotask.HomeScreens
import com.example.demotask.MainActivity
import com.example.demotask.RestartAppService
import java.util.Calendar

class AppStartBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF) {
            // Khởi tạo và lên lịch AlarmManager để "wake up" ứng dụng sau 5 giây
            scheduleWakeUpAlarm(context)
        }
        when (intent.action) {
            "com.example.demotask.CUSTOM_ACTION" -> {
                // Chuyển đến HomeActivity
                val homeIntent = Intent(context, HomeScreens::class.java)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(homeIntent)
                Log.d("TAg", "home")
                // Hiển thị Toast
                Toast.makeText(context, "Home Screens!", Toast.LENGTH_SHORT).show()
            }

            else -> {

//                val serviceIntent = Intent(context, RestartAppService::class.java)
                // Chạy Foreground Service
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    context.startForegroundService(serviceIntent)
//                } else {
//                    context.startService(serviceIntent)
//                }
//                // Mở ứng dụng khi hẹn giờ kích hoạt
//                val launchIntent =
//                    context.packageManager.getLaunchIntentForPackage(context.packageName)
//                Log.d("this", "$launchIntent")
//                launchIntent?.let {
//                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    Log.d("this", "$it")
//                    context.startActivity(launchIntent)
//                    Toast.makeText(
//                        context,
//                        "Quay Trở Lại Màn Hình Main Activity",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }

        }

    }
    private fun scheduleWakeUpAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WakeUpReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val wakeUpTime = System.currentTimeMillis() + 5000 // Thời gian "wake up" sau 5 giây

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            wakeUpTime,
            pendingIntent
        )
    }
}

/// khi không kill


// boadcastreceived không hỗ trợ từ android 10 trở lên
// Hiển thị Toast khi hẹn giờ kích hoạt
//        if (intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
//            // Khởi chạy Activity mong muốn tại đây
//            val launchIntent = Intent(context, MainActivity::class.java)
//            launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(launchIntent)
//            Toast.makeText(context, "Ứng dụng đã được khởi chạy.", Toast.LENGTH_SHORT).show()
//            Log.d("this","vao day  roisss")
//        }




