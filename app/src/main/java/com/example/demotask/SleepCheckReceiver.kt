package com.example.demotask

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.widget.Toast

class SleepCheckReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Kiểm tra trạng thái ngủ của thiết bị
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val isSleeping = !powerManager.isInteractive

        if (isSleeping) {
            // Thiết bị vào chế độ sleep
            Toast.makeText(context, "Ứng dụng đã vào chế độ sleep", Toast.LENGTH_SHORT).show()
            Log.d("this","Đã Vào chế độn gủ")
        }
    }
}