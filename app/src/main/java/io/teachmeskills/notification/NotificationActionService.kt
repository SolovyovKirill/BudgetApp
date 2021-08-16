package io.teachmeskills.notification

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import androidx.core.content.getSystemService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NotificationActionService : Service(), KoinComponent {

    private var expenseId: Long = -1

    override fun onBind(p0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            expenseId = it.getLongExtra(NotificationReceiver.NOTIFICATION_KEY_EXPENSE_ID, -1)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        }
        stopSelf()
        return START_NOT_STICKY
    }
}