# Laboratorio 2.2 - Formulario Resiliente

## Diferencia entre SavedStateHandle y DataStore

En este laboratorio se usaron dos formas de persistencia: SavedStateHandle y DataStore.

- SavedStateHandle se utilizó para guardar el campo de Email. Este mecanismo permite conservar datos pequeños del estado de la interfaz cuando ocurre una rotación de pantalla o cuando Android destruye temporalmente la actividad, esto se considera útil para datos momentáneos que pertenecen a la pantalla actual.

- DataStore se utilizó para guardar el campo de Nombre. A diferencia de SavedStateHandle, DataStore guarda la información en el almacenamiento del dispositivo, por lo que los datos pueden mantenerse incluso si la aplicación se cierra completamente y se vuelve a abrir.

En resumen, SavedStateHandle es adecuado para estados temporales de la UI, mientras que DataStore es mejor para guardar datos de forma permanente en el dispositivo.
