package com.example.demotask.BoardCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TimePicker
import java.util.Calendar

class PowerButtonBroadcastReceiver(private val timePicker: TimePicker) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF) {
            // Thiết bị đã tắt màn hình, bạn có thể thực hiện các xử lý ở đây
        } else if (intent.action == Intent.ACTION_SCREEN_ON) {
            // Thiết bị đã bật màn hình, bạn có thể thực hiện các xử lý ở đây
            // Kiểm tra nếu đã đến thời gian được đặt trong TimePicker, sau đó mở ứng dụng
            val currentTimeMillis = System.currentTimeMillis()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = currentTimeMillis
            val selectedHour = timePicker.currentHour
            val selectedMinute = timePicker.currentMinute
            if (calendar.get(Calendar.HOUR_OF_DAY) == selectedHour && calendar.get(Calendar.MINUTE) == selectedMinute) {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                context.startActivity(launchIntent)
            }
        }
    }
}