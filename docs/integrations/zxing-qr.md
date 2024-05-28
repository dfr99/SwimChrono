# Generación de QR (ZXing)

## Introducción
En nuestra aplicación para dispositivos Android, hemos implementado la funcionalidad de escaneo de códigos QR utilizando la biblioteca [ZXing](https://github.com/journeyapps/zxing-android-embedded) (pronunciada "Zebra Crossing"). Esta implementación nos permite crear una experiencia fluida para que los árbitros puedan identificar y cronometrar a los nadadores durante sus carreras.

## Funcionalidad
1. **Generación de QR único para cada nadador:** Utilizamos el identificador único de cada nadador en nuestra base de datos para generar un código QR exclusivo. Este QR se genera dinámicamente en la aplicación utilizando la biblioteca ZXing.
2. **Escaneo de QR por parte del árbitro:** En el momento de la carrera, el árbitro utiliza la función de escaneo de códigos QR de nuestra aplicación para leer el código QR del nadador. Esto se realiza utilizando la cámara integrada en el dispositivo móvil.
3. **Identificación y cronometraje:** Una vez que el código QR del nadador es escaneado, la aplicación identifica al nadador asociado con ese código y permite al árbitro iniciar el cronómetro para registrar el tiempo de la carrera.


## Ventajas
La implementación del escaneo de códigos QR en nuestra aplicación Android facilita la identificación y el cronometraje preciso de los nadadores durante las carreras. Gracias a la integración de la biblioteca ZXing, podemos ofrecer una experiencia de usuario fluida y eficiente tanto para los árbitros como para los nadadores.

## Ejemplo QR (Contenido: "1234")

<p align="center"><img src="../../images/qr-example/QR_example_1234.jpeg" width="350" alt="QR - 1234"></p>
