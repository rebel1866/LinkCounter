1) В поле ввода приложение принимает url-адреса в любом из форматов: <br>
isida.by<br>
www.isida.by<br>
https://isida.by<br>
https://www.isida.by<br>

2) Веб-приложение учитывает только абсолютные ссылки, относительные ссылки (например, /about/careers), 
а также ссылки не содержащие текста игнорируются.

3) Испольняемый архив программы собирается командой mvn package (тем не менее, к исходному коду уже прилагается исполняемый 
модуль, см. ниже).

4) Проект содержит файл links.war, который может быть запущен в окружении Apache Tomcat одним из способов:<br>
-Переместить указанный файл в директорию wepapps, затем запустить tomcat (приложение будет доступно по адресу 
http://localhost:8080/links)<br>
-либо запустить томкат, перейти в окно админа (/manager), и вручную произвести деплой (указать путь к файлу war и deploy path - /links)

5) Программа тестирована в браузерах на ОС Linux Ubuntu 22.04

