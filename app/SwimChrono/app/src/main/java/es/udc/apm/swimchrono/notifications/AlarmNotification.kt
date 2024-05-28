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
        val typeMessage = intent?.getIntExtra(AlarmNotificationManager.TYPE_MSG_KEY, 0) ?: 0
        createSimpleNotification(context, typeMessage)
    }

    private fun createSimpleNotification(context: Context, typeMessage: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Este flag impide que se abra dos veces la app
        }

        // Si la API es 31 no pone el FLAG_INMUTABLE
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)


        val titleText = when (typeMessage) {
            1 -> "¡Empieza la carrera!"
            2 -> "¡Solo falta una hora!"
            3 -> "Mañana es el gran día"
            else -> "Tienes una nueva notificación"
        }


        val contentText = when (typeMessage) {
            1 -> "¡Calienta que sales!"
            2 -> "¡Vete calentando!"
            3 -> "¡Estate preparado para mañana!"
            else -> "Accede a la aplicación"
        }

        val bigText = when (typeMessage) {
            1 -> "Está por comenzar la carrera, ten todo listo y preparado"
            2 -> "Vete calentando que dentro de poco tiempo empieza"
            3 -> "¡Estate preparado para mañana! Prepara todo lo necesario y no olvides de cargar el dispositivo"
            else -> "Accede a la aplicación para saber cuándo es tu próxima carrera"
        }

        val notification =
            NotificationCompat.Builder(context, AlarmNotificationManager.MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Icono del mensaje
                .setContentTitle(titleText) // Titulo del mensaje
                .setContentText(contentText) // Texto sin ampliar
                .setStyle(
                    NotificationCompat.BigTextStyle() // Texto tras ampliar
                        .bigText(bigText)
                )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad alta para heads-up notification
                .setDefaults(NotificationCompat.DEFAULT_ALL) // Usar el sonido y vibración predeterminados
                .build() // Lanzar la notificacion

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
        val id = System.currentTimeMillis().toInt()
        manager.notify(id, notification)
    }
}
