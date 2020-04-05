package alexandru.balan.tema3.broadcasters

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log

class TodoNotificationBroadcast : BroadcastReceiver() {
    private lateinit var notificationManager : NotificationManager
    private val NOTIFICATION_CHANNEL_ID : String = "primary-channel"
    var NOTIFICATION_ID : Int = 0
    private val TAG = "Todo-Notification-Broadcast"

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.i(TAG, "Received broadcast")
        notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        NOTIFICATION_ID = intent!!.getIntExtra("NOTIFICATION_ID",0)
        val notification = intent.getParcelableExtra<Notification>("NOTIFICATION")
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}