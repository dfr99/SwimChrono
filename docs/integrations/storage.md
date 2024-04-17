# Almacenamiento

Para el almacenamiento de la aplicación hemos usado [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences). Es un HashMap en memoria que nos permite tener elementos claves-valor con información relevante a la aplicación. En concreto tenemos almacenado el UID que nos devuelve FirebaseAuth y que nos sirve para saber si un usuario está identificado en la aplicación o no.
