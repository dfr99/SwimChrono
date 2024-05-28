# Firebase
En la aplicación se usan varias integraciones que ofrece Firebase. En concreto son [FirebaseAuth](https://firebase.google.com/docs/auth) y [Firebase Realtime Database](https://firebase.google.com/docs/database)

## FirebaseAuth

Gracias a esta integración podemos tener un gestor de cuentas de usuario, que nos permite tener un mayor control sobre quien se puede loguear sobre la aplicación. También se podrá loguear uno mediante el sistema Sign-In de Google

## Firebase Realtime Database

En esta base de datos tendremos todos la información relativa a torneos, nadadores, carreras, tiempos y permisos. Aquí con el UID que nos ofrece FirebaseAuth podremos leer el rol del usuario y en función de ese rol mostraremos más o menos funcionalidades.
