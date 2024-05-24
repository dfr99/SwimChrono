package es.udc.apm.swimchrono.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import java.util.Calendar

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Log.d("BootReceiver", "Device rebooted, rescheduling notification")
            rescheduleNotification(context)
        }
    }

    private fun rescheduleNotification(context: Context) {
        // Recuperar fecha y hora de SharedPreferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(NotificationManager.PREFS_NAME, Context.MODE_PRIVATE)
        val year = sharedPreferences.getInt(NotificationManager.YEAR_KEY, 0)
        val month = sharedPreferences.getInt(NotificationManager.MONTH_KEY, 0)
        val day = sharedPreferences.getInt(NotificationManager.DAY_KEY, 0)
        val hour = sharedPreferences.getInt(NotificationManager.HOUR_KEY, 0)
        val minute = sharedPreferences.getInt(NotificationManager.MINUTE_KEY, 0)
        val second = sharedPreferences.getInt(NotificationManager.SECOND_KEY, 0)

        if (year == 0 && month == 0 && day == 0 && hour == 0 && minute == 0 && second == 0) {
            // Si no se encuentran valores en SharedPreferences, no hacer nada
            return
        }

        val calendarTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
        }


        // Verificar si la fecha y hora ya pasaron
        val currentTime = Calendar.getInstance()

        if (calendarTime.before(currentTime)) {
            // No reprogramar la notificación si la fecha y hora ya pasaron
            return
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(context, AlarmNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NotificationManager.NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarTime.timeInMillis, pendingIntent)
    }
}
