# Usabilidad y UX

## Detección de mejoras

A día 2 de mayo de 2024, se han detectado las siguientes mejoras de usabilidad para la aplicación SwimChrono:

- El _login_ de nuestra aplicación. Algo deseable sería añadir opciones de _login_ con otros servicios, como por ejemplo, Google.

![Login de la aplicación](./images/login.png){ width="300" height="150" style="display: block; margin: 0 auto" }


- Sería interesante implementar gestos en la aplicación. Por ejemplo, a la hora de deslizar para navegar entre las diferentes pantallas o volver atrás.
- Actualmente, se definen los estilos y los colores de forma manual. Una mejora sería utilizar _Material Design_ para estos dos apartados.

## Utilización del dispositivos

Con respecto a la sujección del dispositivo por parte del usuario cuando este está utilizando la aplicación, se asume lo siguiente:

- En disposición vertical, el dispositivo se sostiene con una o dos manos, operando con la aplicación con el dedo pulgar, como se muestra en estas imágenes.

![Sujección móvil vertical 1](./images/disposiciones/vertical_1.png){ width="200" height="150" style="display: block; margin: 0 auto" } 

![Sujección móvil vertical 2](./images/disposiciones/vertical_2.png){ width="400" height="300" style="display: block; margin: 0 auto" }

- En disposición horizontal, el dispositivo se sostiene con las dos manos, operando también con los dos pulgares, como se muestra en la siguiente imagen

![Sujección móvil horizontal 1](./images/disposiciones/horizontal_1.png){ width="400" height="300" style="display: block; margin: 0 auto" }

## Material Design

Durante el desarrollo de la aplicación SwimChrono se ha aplicado _Material Design_ en los siguientes apartados:

- Todas las tarjetas de club, competiciones y listas de los deportistas se implementan con el elemento [_MaterialCardView_](https://m2.material.io/components/cards). Las tarjetas cumplen los siguientes principios:
	- Una tarjeta es identificable como un unidad única contenida.
	- Una tarjeta no puede ser unida con otra tarjeta ni separada en múltiples tarjetas.
	- Una tarjeta es independiente del contexto que la rodea.

- Por defecto, los elementos _Button_ que se utilizan en la aplicación utilizan a bajo nivel el elemento [_MaterialButton_](https://m2.material.io/components/buttons). Estos botones cumplen, por diseño, los siguientes principios:
	- Son identificables e indican de forma clara que pueden iniciar acciones.
	- Son fácilmente localizables entre los demás elementos de la aplicación.
	- La acción de un botón y su estado son claros.

- Se utiliza el elemento [_BottomNavigation_](https://m2.material.io/components/bottom-navigation) . Por diseño, este elemento cumple los siguientes principios:
	- Es ergónomico: sus secciones son fácilmente alcanzables para el usuario.
	- Cuando es utilizado, _BottomNavigation_ aparece en la parte inferior de todas las pantallas de forma consistente.
	- Las secciones de _BottomNavigation_ son igual de importantes. En nuestro caso, a través de este elemento se accede a la pantalla principal de cada una de las funcionalidades disponibles en la aplicación salvo el cronómetro. Esta última depende de tener carreras registradas en ese momento, por lo que se considera en otro orden.
