# ~~Bluetooth~~

## ~~Introducción~~

~~La plataforma Android incluye soporte para la [pila de red Bluetooth](https://es.wikipedia.org/wiki/Bluetooth_(especificaci%C3%B3n)),
que permite a un dispositivo intercambiar datos de forma inalámbrica con
otros dispositivos Bluetooth. El _framework_ proporciona acceso a la
funcionalidad Bluetooth a través de la API Bluetooth de Android, que permiten a
las aplicaciones conectarse de forma inalámbrica a otros dispositivos Bluetooth,
habilitando funciones inalámbricas punto a punto y multipunto.~~

~~Utilizando la API Bluetooth, una aplicación Android puede realizar lo siguiente:~~

~~- Buscar otros dispositivos Bluetooth~~
~~- Consultar el adaptador Bluetooth local en busca de dispositivos Bluetooth
  emparejados~~
~~- Establecer canales RFCOMM~~
~~- Conectarse a otros dispositivos mediante la detección de servicios~~
~~- Transferir datos a y desde otros dispositivos~~
~~- Gestionar múltiples conexiones~~

~~Se distinguen entre Bluetooth clásico, opción adecuada para operaciones que
consumen más batería del dispositivo (véase _streaming_ y la comunicación entre
dispositivos), y [_Bluetooth Low Energy_](https://developer.android.com/develop/connectivity/bluetooth/ble/ble-overview), orientada a dispositivos Bluetooth con
requisitos de bajo consumo. Esta última alternativa es introducida en la versión
4.3 de Android (API level 18)~~

## ~~Procedimiento de conexión~~

~~Para que los dispositivos con Bluetooth puedan transmitir datos entre sí,
primero deben formar un canal de comunicación mediante un proceso de
emparejamiento. Un dispositivo, el dispositivo detectable, se pone a disposición
de las solicitudes de conexión entrantes. Otro dispositivo lo encuentra mediante
un proceso de descubrimiento de servicios. Después de que el dispositivo
detectable acepte la solicitud de emparejamiento, los dos dispositivos completan
un proceso de vinculación en el que intercambian claves de seguridad. Los
dispositivos almacenan en caché estas claves para su uso posterior. Una vez
finalizados los procesos de emparejamiento y vinculación, los dos dispositivos
intercambian información.~~

~~Cuando finaliza la sesión, el dispositivo que inició la solicitud de
emparejamiento libera el canal que lo había vinculado al dispositivo detectable.
Sin embargo, los dos dispositivos permanecen vinculados, por lo que pueden
volver a conectarse automáticamente durante una sesión futura siempre que estén
dentro del alcance del otro y ninguno de los dispositivos haya eliminado el
vínculo.~~

## ~~Ejemplo~~

~~Para establecer la conexión Bluetooth, se puede seguir esta documentación de
Android Studio sobre [cómo establecer la conexión Bluetooth](https://developer.android.com/guide/topics/connectivity/bluetooth#SettingUp), que se resume
en los siguientes puntos:~~

~~- Comprobación del soporte del dispositivo para utilizar Bluetooth~~
~~- Comprobación de los permisos para establecer la conexión~~
~~- Habilitar Bluetooth~~
~~- Buscar dispositivos con los que establecer una conexión~~
~~- Conexión de los dispositivos~~
  ~~- Como servidor~~
  ~~- O como cliente~~
~~- Mantener y gestionar la conexión~~

# Justificación para no implementar la funcionalidad Bluetooth

## Limitaciones de Tiempo

Dado el plazo limitado para completar esta práctica junto a las demás y los exámenes, hemos tenido que tomar decisiones difíciles sobre qué funcionalidades implementar. La integración de Bluetooth, requiere una cantidad significativa de tiempo y esfuerzo. Nos hemos enfocado en asegurar que todas las demás funcionalidades críticas estén completamente implementadas y funcionando correctamente.

## Reducción del Equipo

Originalmente, nuestro equipo constaba de cuatro miembros, pero debido a circunstancias imprevistas, ahora somos solo tres. Esta reducción en el equipo ha afectado nuestra capacidad para distribuir la carga de trabajo y ha incrementado la presión sobre cada miembro restante.

## Complejidad Técnica

La implementación de Bluetooth no es trivial. Requiere un conocimiento profundo de la API de Bluetooth de Android, y el manejo de múltiples escenarios como:

- **Emparejamiento de Dispositivos**: Proceso complejo que involucra la detección y conexión segura entre dispositivos.
- **Gestión de Conexiones**: Mantener conexiones estables y seguras a lo largo del tiempo.

La complejidad de estas tareas habría requerido un tiempo significativo de desarrollo y pruebas, lo que no era factible dentro de los límites del proyecto.
