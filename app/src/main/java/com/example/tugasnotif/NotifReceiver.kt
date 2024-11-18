package com.example.tugasnotif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotifReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "ACTION_LIKE" -> {
                MainActivity.likeCount++
                MainActivity.updateUI()
            }
            "ACTION_DISLIKE" -> {
                MainActivity.dislikeCount++
                MainActivity.updateUI()
            }
        }
    }
}
