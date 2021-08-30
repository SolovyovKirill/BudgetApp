package io.teachmeskills.presentation.view.notification

import android.annotation.SuppressLint
import android.app.*

import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.teachmeskills.MainActivity
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentNotificationBinding
import io.teachmeskills.presentation.view.addexpense.AddExpenseFragment
import io.teachmeskills.presentation.view.notification.NotificationReceiver.Companion.NOTIFICATION_CHANNEL
import java.text.SimpleDateFormat
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
            findNavController().navigate(R.id.action_notificationFragment_to_mainFragment)
        }

        binding.etDate.setOnClickListener {
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
            val notificationManager =
                getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    private fun setNotif() {

        calendar = Calendar.getInstance()

        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), NotificationReceiver::class.java)


        val etText = binding.etTextNotification.text.toString()
        val etTitle = binding.etTitleNotification.text.toString()


        intent.putExtra(TEXT, etText)
        intent.putExtra(TITLE, etTitle)


        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(requireContext(), "Notif set", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("Range")
    private fun showTimePicker() {

//        val calendar = Calendar.getInstance()
//        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePivker, hourOfDay, minute ->
//            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//            calendar.set(Calendar.MINUTE, minute)
//
//            binding.etDate.setText(SimpleDateFormat("HH:mm").format(calendar.time).toString())
//        }
//        TimePickerDialog(requireContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()


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
            binding.etDate.setText(SimpleDateFormat("HH:mm").format(calendar.time).toString())
        }

    }

    companion object {
        const val TEXT = "TEXT"
        const val TITLE = "TITLE"
    }


}