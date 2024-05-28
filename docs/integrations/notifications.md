# Notificaciones
En la aplicación se implementa un sistema de notificaciones para informar del usuario sobre cuando inicia la carrera en donde se apunta.

**(*Falta escribir si la notificación se hace en el momento de la carrera, 24 horas antes, o ambas,...*)**

## AlarmNotification

NO SE SI ESCRIBIR ALGO O NO


## BootReceiver

En esta clase se implementa la capacidad de la aplicación para conservar la programación de la notificación de la carrera y volver a programarlo tras el reinicio. Para ello se usa un componente *BootReceiver*, que se utiliza para recibir una notificación cuando el dispositivo ha terminado de arrancar (boot). Es decir, es un receptor de broadcast (broadcast receiver) que escucha un evento el cual se envía una vez que el sistema ha terminado de iniciar.