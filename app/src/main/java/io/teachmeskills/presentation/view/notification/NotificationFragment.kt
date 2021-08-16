package io.teachmeskills.presentation.view.notification

import android.annotation.SuppressLint

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentNotificationBinding
import io.teachmeskills.notification.NotificationReceiver
import io.teachmeskills.notification.NotificationReceiver.Companion.NOTIFICATION_CHANNEL
import java.util.*


class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        binding.btnSetNot.setOnClickListener {
            setNotif()
        }

        binding.btnSetTime.setOnClickListener {
            showTimePicker()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name: CharSequence = "name"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance)
            channel.description = description
            val notificationManager = getSystemService(requireContext(),NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }

    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createChannel(notificationManager: NotificationManager) {
//        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL) == null) {
//            val channel = NotificationChannel(
//                NOTIFICATION_CHANNEL,
//                NOTIFICATION_CHANNEL,
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

    private fun setNotif() {

        calendar = Calendar.getInstance()

        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), NotificationReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(requireContext(), "Notif set", Toast.LENGTH_LONG).show()

//        calendar = Calendar.getInstance()
//
//        if (calendar.getTime().compareTo(Date()) < 0)
//            calendar.add(Calendar.DAY_OF_MONTH, 1)
//        val intent = Intent(requireContext(), NotificationReceiver::class.java)
//        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
//        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//
//        if (alarmManager != null) {
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
//                AlarmManager.INTERVAL_DAY, pendingIntent)
//        }
    }

    @SuppressLint("Range")
    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(24)
            .setMinute(0)
            .setTitleText("Selected Alarm Time")
            .build()
        picker.show(requireFragmentManager(), "SHOW")


        picker.addOnPositiveButtonClickListener {

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }
    }
}