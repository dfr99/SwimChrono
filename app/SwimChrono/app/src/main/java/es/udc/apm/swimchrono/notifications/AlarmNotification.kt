package es.udc.apm.swimchrono.notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import es.udc.apm.swimchrono.MainActivity
import es.udc.apm.swimchrono.R
import android.app.NotificationManager as AndroidNotificationManager

class AlarmNotification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        createSimpleNotification(context)
    }

    private fun createSimpleNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Este flag impide que se abra dos veces la app
        }

        // Si la API es 31 no pone el dichoso FLAG_INMUTABLE
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notification =
            NotificationCompat.Builder(context, AlarmNotificationManager.MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Icono del mensaje
                .setContentTitle("¡Empieza la carrera!") // Titulo del mensaje
                .setContentText("¡Calienta que sales!") // Texto sin ampliar
                .setStyle(
                    NotificationCompat.BigTextStyle() // Texto tras ampliar
                        .bigText("Está por comenzar la carrera, ten todo listo y preparado")
                )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad alta para heads-up notification
                .setDefaults(NotificationCompat.DEFAULT_ALL) // Usar el sonido y vibración predeterminados
                .build() // Lanzar la notificacion

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
        manager.notify(AlarmNotificationManager.NOTIFICATION_ID, notification)
    }
}
