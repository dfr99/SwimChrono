package es.udc.apm.swimchrono.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import java.util.Calendar
import android.app.NotificationManager as AndroidNotificationManager


class AlarmNotificationManager(val context: Context) {
    companion object {
        const val MY_CHANNEL_ID = "myChannelID"
        const val NAME_CHANNEL = "Canal SwimChrono"
        const val PREFS_NAME = "NotificationPrefs"
        const val YEAR_KEY = "year"
        const val MONTH_KEY = "month"
        const val DAY_KEY = "day"
        const val HOUR_KEY = "hour"
        const val MINUTE_KEY = "minute"
        const val SECOND_KEY = "second"
        const val TYPE_MSG_KEY = "type_message"
    }

    init {
        createChannel()
    }

    // Método para crear una notificación
    @SuppressLint("ScheduleExactAlarm")
    fun createNotification(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, type_message: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putInt(YEAR_KEY, year)
            putInt(MONTH_KEY, month)
            putInt(DAY_KEY, day)
            putInt(HOUR_KEY, hour)
            putInt(MINUTE_KEY, minute)
            putInt(SECOND_KEY, second)
            apply()
        }

        // Añadir al Intent el tipo de mensaje
        val intent = Intent(context, AlarmNotification::class.java).apply {
            putExtra(TYPE_MSG_KEY, type_message)
        }

        val id = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendarTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarTime.timeInMillis, pendingIntent)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Si la API es mayor o igual a API 26 o Android 6
            val channel = NotificationChannel( // Crear canal
                MY_CHANNEL_ID, // ID
                NAME_CHANNEL,// Nombre del canal
                AndroidNotificationManager.IMPORTANCE_HIGH // Importancia alta para heads-up notification
            ).apply {
                description = "SwimChrono" // (Creo que es opcional) Descripción del canal
            }

            val notificationManager: AndroidNotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}
