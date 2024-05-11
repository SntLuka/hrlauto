**Как всё работает:**
1. Для просмотра автоматического отчета по проходимым шагам и проверкам нам понадобится плагин  Allure TestOps Support, устанавливаем его в File | Settings | Plugins, выполнив поиск
2. После установки плагина настраиваем его: 
2.1. File | Settings | Tools | Allure TestOps нужно внести url нашего Аллюра (https://hrlink.qatools.cloud/) + token
тамже выбираем путь, куда будет сохраняться репорт ~/junit5-java-maven/target/allure-results
сохранили
2.2 File | Settings | Tools | Allure TestOps | Project Settings - выбираем Project HR-Link
3. Запускаем наш автотест, который находится: ~/junit5-java-maven/src/test/java/com/example/junit5/StepTest.java
4. После выполнения автотеста создаётся дирректория с репортом ~/junit5-java-maven/allure-results
   4.1 Кликаем по ней правой км и выполняем Allure TestOps: Upload Results
5. Переходим в наш [Аллюр](https://hrlink.qatools.cloud/project/1/launches) / просматриваем отчёт

**Примечания:**
1. Автотест написан так, что мы можем проверить не только отсуствтие ЮЛ/сотрудник/документа/заявления, выполнив вход за кадровика (aluchkin753+1@gmail.com), но и также можем проверить их наличие на тенанте, выполнив логин за Админа (aluchkin753@gmail.com)
2. Для удобства отслеживания выполнения автотеста добавил принты, которые выводят результат выполнение шагов в консоли.