package es.udc.apm.swimchrono.notifications

import android.app.NotificationManager as AndroidNotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import es.udc.apm.swimchrono.MainActivity
import es.udc.apm.swimchrono.R

class AlarmNotification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        createSimpleNotification(context)
    }

    private fun createSimpleNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Este flag impide que se abra dos veces la app
        }

        // Si la API es 31 no pone el dichoso FLAG_INMUTABLE
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notification = NotificationCompat.Builder(context, NotificationManager.MY_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // Icono del mensaje
            .setContentTitle("My title") // Titulo del mensaje
            .setContentText("Esto es un ejemplo <3") // Texto sin ampliar
            .setStyle(
                NotificationCompat.BigTextStyle() // Texto tras ampliar
                    .bigText("Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi Holita holi ")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad alta para heads-up notification
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Usar el sonido y vibración predeterminados
            .build() // Lanzar la notificacion

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
        manager.notify(NotificationManager.NOTIFICATION_ID, notification)
    }
}
