package com.example.serviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyBackgroundService : Service() {
    private var isRunning = false
    private var count = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            isRunning = true
            // Naye thread mein counter start karna taaki UI hang na ho
            Thread {
                while (isRunning) {
                    try {
                        Thread.sleep(1000)
                        count++

                        // Activity ko naya data bhejna
                        val updateIntent = Intent("COUNTER_UPDATED")
                        updateIntent.putExtra("value", count)
                        sendBroadcast(updateIntent)
                    } catch (e: InterruptedException) {
                        break
                    }
                }
            }.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false // Isse while loop ruk jayega
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}