# Watch-dog with email sending

Назначение программы постоянно контролировать состояние ключевых параметров работоспособности 
текущей Ноды данных и проверять ее связь с Мастер нодами.

Программа выполняет следующие проверки: 
1. Процент свободного места HEAP, как разница между Xmx (граница максимального расширения 
памяти приложений работающих в JVM) и текущего размера используемой памяти

2. Проверяет TCP соединение с нодами из prod-request-host используя порт 7 "Echo" (соблюдая безопасность 
и не мешая реальной работе) 

Рассылка сообщений о срабатывании проверки выполняется по адресам электронной почты из prod-email-to 
(адреса разделяются ";" )

### Параметры настройки watch-dog.properties:

check-interval - интервал проверок. измеряется в миллисекундах. Значение начальное 5000 = 5 секунд
heap-size-in - отображать количественные значения памяти в Mb, Gb, Tb, Pb (мегабайты, Гигабайты, Терабайты, Петабайты)
heap-free-size-percent-remains - размер остатка свободной памяти в HEAP при котором отправлять сообщение
heap-remains-percent-delta - процент изменения размера остатка HEAP при котором отправлять повторное сообщение
round-precision-template - шаблон точности округления для сообщения. Начальное значение -  0.000

prod-email-to - список адресов электронной почты, для рассылки сообщений
prod-email-from - электронный адрес, от чьего имени рассылаются сообщения. Начальное значение - <...>@<....>.ru
prod-email-host - адрес SMTP через который выполняется рассылка. Начальное значение - smtp.<...>.ru

prod-request-host - список имен нод с которыми выполняется проверка доступности соединения

### Сборка проекта
mvn clean compile assembly:single