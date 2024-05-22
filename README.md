#Добавлен MagazineControllerAdapter 
Обращение по ссылке http://localhost:8765/MS (http://localhost:8080/MS). Имеет дополнительные функции для магазина такие как: получить продукт по id и работа с корщиной

# Метрики
## Общие
![](MetricsScreenshoots/Basic1.png)
![](MetricsScreenshoots/Basic2.png)
## Количесво успешных PUT запросов
![](MetricsScreenshoots/put.png)
## Количесво PUT запросов (включая неудачные)
![](MetricsScreenshoots/put_try.png)
## Максимальное время выполнения PUT запросов
![](MetricsScreenshoots/time_max.png)
## Суммарное время выполнения PUT запросов
![](MetricsScreenshoots/time_sum.png)
## Важные для проверки классы (свои метрики): MetricVariables.java, MetricsController.java, MagazineController.java, WarehouseController.java

# Аспекты логгируют время и результат работы методов в MarkDown файлы в папку LOG
# В ходе проведения нагрузочного тестирования были выявлены деффекты в процессе оплаты, поэтому метод был изменен. 
![](Screenshot_2.png)
![](Screenshot_1.png)
![](Screenshot_3.png)
![](Screenshot_4.png)
