package io.teachmeskills.presentation.view.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkBuilder
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentNotificationBinding
import io.teachmeskills.presentation.view.notification.NotificationFragment.Companion.TEXT
import io.teachmeskills.presentation.view.notification.NotificationFragment.Companion.TITLE

class NotificationReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, arg1: Intent) {
        showNotification(context, arg1)
    }

    private fun showNotification(context: Context, intent: Intent) {
        try {
            val contentIntent = PendingIntent.getActivity(
                context, 0,
                Intent(context, NotificationReceiver::class.java), 0
            )

            val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.addExpenseFragment)
                .createPendingIntent()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel(notificationManager)
            }

            val text = intent.getStringExtra(TEXT)
            val title = intent.getStringExtra(TITLE)


            val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_free_icon_finances_2167853)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(contentIntent)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
            notificationManager.notify(0, builder.build())

        } catch (e: Exception) {
            println("===============================")
            e.printStackTrace()
            println("===============================")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationManager: NotificationManager) {
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL) == null) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL = "EXPENSE_NOTIFICATION_CHANNEL"
        const val NOTIFICATION_KEY_EXPENSE_ID = "EXPENSE_APP_NOTIFICATION_KEY_NOTE_ID"
    }
}