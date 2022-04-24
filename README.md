# Приложение погоды
### Приложение в котором я использовал:
## MVP и Moxy, Room, Retrofit2, RxJava3, RecyclerView, BottomSheets, SharedPreferences

  При первом запуске приложения запускается Initial activity, которое в runtime запращивает разрешение на получение данных о геолокации.
В самом приложении есть главный экран, с ресайклер вью который показывает почасовой прогноз, а также с выдвижным ресайклер вью, 
который представляет собой фрагмент с подневным прогнозом, если нажать на него то фрагмент заменится на фрагмент с подробной информацией о погоде конкретного дня. 
Так же реализованы 2 дополнительных экрана: экран с геолокацией, где можно найти город по его названию, а также добавить его в избранное и экран с настройками, 
где можно поменять единицы измерения с Цельсия на Фаренгейты и тд. 

![alt text](screenshots/initial_activity.jpg?raw=true) 
![alt text](screenshots/dialog_fragment.jpg?raw=true) 
![alt text](screenshots/main_activity.jpg?raw=true) 
![alt text](screenshots/daily_recucler_view.jpg?raw=true) 
![alt text](screenshots/detail_info_fragment.jpg?raw=true) 
![alt text](screenshots/search_activity.jpg?raw=true) 
![alt text](screenshots/new_york.jpg?raw=true) 
![alt text](screenshots/settings_activity.jpg?raw=true) 
