La arquitectura de nuestra aplicación SwimChrono se organiza en tres capas fundamentales, lo que proporciona claridad en la estructura y separación de responsabilidades.

1. **Capa de presentación (Frontend):** Esta capa representa la interfaz de usuario con la que los usuarios interactúan directamente. Aquí se encuentra la lógica de presentación y la representación visual de la aplicación en dispositivos Android y tablets. Todo lo relacionado con la experiencia del usuario, como la navegación, la entrada de datos y la presentación de resultados, se gestiona en esta capa. Está capa contará con una arquitectura Modelo-Vista-Presentador (MVP)

2. **Capa de lógica de aplicación (Backend):** En esta capa reside la lógica principal de la aplicación. Aquí se encuentran los microservicios que manejan las diferentes funcionalidades de SwimChrono, como la gestión de entidades, la autenticación de usuarios, el cronometraje de competiciones y la integración con tecnologías externas como NFC y Bluetooth. Esta capa se comunica con el frontend a través de una API RESTful, proporcionando así una separación clara entre la presentación y la lógica de la aplicación.

3. **Capa de almacenamiento de datos (Base de datos):** La capa de almacenamiento de datos es donde se almacena y gestiona toda la información necesaria para el funcionamiento de la aplicación. Esta capa proporciona una forma segura y eficiente de almacenar y recuperar datos para su uso en la lógica de la aplicación.

4. **Integraciones de liberías:** Este paso intermedio nos permite hacer uso de librerías de terceros para añadir funcionalidades ya construidas en nuestra aplicación. Con el uso de librerías podremos visualizar QR's, exportar e importar CSV's y hacer uso de el login de Google.


    ![Diagrama de Arquitectura](../images/Android%20Architecture.png)
