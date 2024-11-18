package com.example.tugasnotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.tugasnotif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 90

    companion object {
        var likeCount = 0
        var dislikeCount = 0

        lateinit var binding: ActivityMainBinding

        fun updateUI() {
            binding.likeText.text = likeCount.toString()
            binding.dislikeText.text = dislikeCount.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MainActivity.binding = binding // Simpan binding ke companion object

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.btnNotif.setOnClickListener {
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                0
            }

            val likeIntent = Intent(this, NotifReceiver::class.java).apply {
                action = "ACTION_LIKE"
            }
            val likePendingIntent = PendingIntent.getBroadcast(this, 1, likeIntent, flag)

            val dislikeIntent = Intent(this, NotifReceiver::class.java).apply {
                action = "ACTION_DISLIKE"
            }
            val dislikePendingIntent = PendingIntent.getBroadcast(this, 2, dislikeIntent, flag)

            val gambar = BitmapFactory.decodeResource(resources, R.drawable.mayo)
            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Mayo Nakal")
                .setContentText("Ini Mayo kecil sedang tidur")
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(gambar)
                )
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(0, "like", likePendingIntent)
                .addAction(0, "dislike", dislikePendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    channelId,
                    "Notifku",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                with(notifManager) {
                    createNotificationChannel(notifChannel)
                    notify(notifId, builder.build())
                }
            } else {
                notifManager.notify(notifId, builder.build())
            }
        }
    }
}
