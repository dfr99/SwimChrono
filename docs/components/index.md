# Elección de los componentes

## Actividades

<!--  Actividad principal -->
<!-- Cada unha das opcións de perfil -->
<!-- Cronómetro -->

## Fragmentos

En la aplicación _SwimChrono_ se han decidido implementar las pantallas
principales de la aplicación como fragmentos, siendo cada pantalla un fragmento:

- Pantalla de _Próximos Torneos_
- Pantalla de _Mis Torneos_
- Pantalla de _Mi Club_
- Pantalla de _Mi Perfil_

Estos fragmentos son utilizados por la actividad principal, que dispone de un
_navController_ para gestionar la navegación entre y la disposición de las
pantallas en el menú principal de la aplicación.

![Fragmentos usados en la configuración de la navegación](../images/components/fragments.png)

El equipo ha optado por esta solución por los siguientes motivos:

- El uso de fragmentos favorece la **reutilización** y **modularidad** de los
recursos.
- Con la utilización de fragmentos, es posible comunicarse con la actividad
anfitriona sin necesidad de crear componentes adicionales, como los _Intents_.
Además, estos fragmentos, al estar contenidos dentro de la misma actividad, se
podrían llegar a comunicar entre ellos.
- La gestión de los estados de los fragmentos es más precisa y controlada en
comparación con la gestión de los estados de las actividades

Esta solución también presenta inconvenientes, que se detallan a continuación:

- La gestión de los estados, a pesar de ser más precisa que con el uso de
actividades, se vuelve más compleja al tener que ser manejada explícitamente
por los desarrolladores, con el uso de la función [_addToBackStack()_][backstack].
- El ciclo de vida de los fragmentos depende de la actividad anfitriona, mientras
que las actividades son independientes y tienen sus propio ciclo de vida.

## Servicios

<!-- Acceso a API como servicio-->

<!-- Variables -->
[backstack]: https://developer.android.com/reference/androidx/fragment/app/FragmentTransaction#addToBackStack(java.lang.String)
