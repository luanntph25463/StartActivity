package com.example.demotask.BoardCast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.demotask.Screens.HomeScreens
import com.example.demotask.MainActivity
import com.example.demotask.Services.RestartAppService
import java.util.Calendar

class AppStartBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "BoardCast"
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Vào ĐÂy", "s")
        val hour = intent.getIntExtra("EXTRA_HOUR", 0)
        val minute = intent.getIntExtra("EXTRA_MINUTE", 0)

        Log.d(TAG, "Hour: $hour, Minute: $minute")

        scheduleAlarm(context, hour, minute)
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

    private fun scheduleAlarm(context: Context, hour: Int, minute: Int) {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MainActivity::class.java)
        pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        setSpecificAlarmTime(hour, minute, context)
    }

    private fun setSpecificAlarmTime(hour: Int, minute: Int, context: Context) {

        Toast.makeText(context, "$hour", Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "$minute", Toast.LENGTH_SHORT).show()

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
}



