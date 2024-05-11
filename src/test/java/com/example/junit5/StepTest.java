package com.example.junit5;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class StepTest {

    @Test
    public void annotatedStepTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://app-test11-2.myhrlink.ru/login");

//        Задаём неявное ожидание, т.к. некоторые элементы прогружаются не сразу (1 раз из 10 тест может падать, т.к. иногда и этого времени не достаточно)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        //Выполняем логин за кадровика
        WebElement login = (new WebDriverWait(driver, Duration.ofSeconds(10)) //здесь мы задали явное ожидание, т.к. стартовая страница грузится 100500 лет
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-qa=\"credential-form-login-input\"]"))));
        login.sendKeys("aluchkin753+1@gmail.com");
        WebElement password = driver.findElement(By.xpath("//*[@data-qa=\"credential-form-password-input\"]"));
        password.sendKeys("Qq_123321");
        WebElement button = driver.findElement(By.xpath("//*[@data-qa=\"credential-form-submit-button\"]"));
        button.click();
        Allure.step("Выполнен логин за кодровика");

        //Закрываем модульное окно с ТГ
        WebElement telegram = (new WebDriverWait(driver, Duration.ofSeconds(5)) //здесь мы задали явное ожидание, т.к. модалка ТГ тоже иногда грузится не сразу
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-qa=\"telegram-invitation-dlg-cancel-button\"]"))));
        telegram.click();

//        Выполняем фильтр по сотруднику в реестре документов
        WebElement filterEmployeesDocumentsRegistry = (new WebDriverWait(driver, Duration.ofSeconds(5)) //задали явное ожидание, т.к. после закрытия модального окна UI подтупливает
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-qa=\"documents-registry-table-filter-row-employees-select\"]"))));
        filterEmployeesDocumentsRegistry.click();
        WebElement inputEmployee = driver.findElement(By.xpath("(//documents-registry-table-filter-row//div[@role='combobox']/input)[4]"));
        inputEmployee.sendKeys("Орлов Д");
//        Проверяем, что в списке сотрудников нет Орлов Д.
//        Получаем список сотрудников, которые есть в выпдающем списке и проверяем, что нет Орлова
        List<WebElement> list = driver.findElements(By.xpath("//*[@class=\"ng-option ng-option-marked ng-star-inserted\"]"));
        for (WebElement e : list) {
            boolean filterOrlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д')]")).isEmpty();
            if (filterOrlov == true) {
                WebElement selectOrlov = driver.findElement(By.xpath("//*[@class=\"mat-tooltip-trigger\"]").xpath("//*[contains(text(), 'Орлов Д')]"));
                selectOrlov.click();
            } else {
                Allure.step("В реестре документов в фильтре по сотруднику нет Орлов Д.");
                System.out.println("В реестре документов в фильтре по сотруднику нет Орлов Д.");
            }
        }
        //Проверяем, что в реестре документов нет документа, в котором в качестве сотрудника указан Орлов Д.
        boolean orlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д.Е.')]")).isEmpty();
        if (orlov == true) {
            Allure.step("В реестре документов есть документ, в котором в качестве сотрудника указан Орлов Д.");
            System.out.println("В реестре документов есть документ, в котором в качестве сотрудника указан Орлов Д.");
        } else {
            Allure.step("В реестре документов нет документа, в котором в качестве сотрудника указан Орлов Д.");
            System.out.println("В реестре документов нет документа, в котором в качестве сотрудника указан Орлов Д.");
        }

//        В верхнем фильтре нажать на фильтр "Юрлицо".
        WebElement filter = driver.findElement(By.xpath("//*[@data-qa=\"documents-registry-table-filter-row-legal-entities-select\"]"));
        filter.click();
//        Проверяем, что в списке юрлиц нет ООО "Кот".
//        Получаем список ЮЛ, которые есть в выпдающем списке и проверяем, что нет нашего Кота
        List<WebElement> elements = driver.findElements(By.xpath("//*[@class=\"ng-dropdown-panel-items scroll-host\"]"));
        for (WebElement e : elements) {
            boolean cat = !driver.findElements(By.xpath("//*[contains(text(), 'ООО \"Кот\"')]")).isEmpty();
            if (cat == true) {
                Allure.step("В списке юрлиц есть ООО \"Кот\"");
                System.out.println("В списке юрлиц есть ООО \"Кот\"");
            } else {
                Allure.step("В списке юрлиц нет ООО \"Кот\"");
                System.out.println("В списке юрлиц нет ООО \"Кот\"");
            }
        }

        //Открываем реестр заявлений
        WebElement applications = driver.findElement(By.xpath("//*[@data-qa=\"hr-app-nav-applications-registry-link\"]"));
        applications.click();
//        Выполняем фильтр по сотруднику в реестре заявлений
        WebElement filterEmployeesApplicationsRegistry = (new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-qa=\"applications-registry-table-filter-row-employees-select\"]"))));
        filterEmployeesApplicationsRegistry.click();
        WebElement inputEmployeeApplicationsRegistry = driver.findElement(By.xpath("//applications-registry-table-filter-row/form/div[6]/ng-select/div/div/div[2]/input"));
        inputEmployeeApplicationsRegistry.sendKeys("Орлов Д");
//        Проверяем, что в списке сотрудников нет Орлов Д. в фильтре по сотруднику
//        Получаем список сотрудников, которые есть в выпдающем списке и проверяем, что нет Орлова
        List<WebElement> listEmployeesApplicationsRegistry = driver.findElements(By.xpath("//*[@class=\"ng-dropdown-panel-items scroll-host\"]"));
        for (WebElement e : listEmployeesApplicationsRegistry) {
            boolean employeesApplicationsOrlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д')]")).isEmpty();
            if (employeesApplicationsOrlov == true) {
                WebElement orlovEmployeesSelect = driver.findElement(By.xpath("//*[@class=\"ng-star-inserted\"]").xpath("//*[contains(text(), 'Орлов Д')]"));
                orlovEmployeesSelect.click();
            } else {
                Allure.step("В реестре документов в фильтре по сотруднику нет Орлов Д.");
                System.out.println("В реестре документов в фильтре по сотруднику нет Орлов Д.");
            }
        }
        //Проверяем, что в реестре заявлений не отображается заявление от Орлова Д.
        boolean applicationsOrlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д.Е.')]")).isEmpty();
        if (applicationsOrlov == true) {
            Allure.step("В реестре заявлений есть заявление, в котором в качестве сотрудника указан Орлов Д.");
            System.out.println("В реестре заявлений есть заявление, в котором в качестве сотрудника указан Орлов Д.");
        } else {
            Allure.step("В реестре заявлений нет заявления, в котором в качестве сотрудника указан Орлов Д.");
            System.out.println("В реестре заявлений нет заявления, в котором в качестве сотрудника указан Орлов Д.");
        }

        //Открываем реестр сотрудников
        WebElement employeesRegistry = driver.findElement(By.xpath("//*[@data-qa=\"hr-app-nav-employees-registry-link\"]"));
        employeesRegistry.click();
        //Выполняем фильтр по Орлов Д
        WebElement inputEmployeeName = driver.findElement(By.xpath("//*[@data-qa=\"registry-header-name-input\"]"));
        inputEmployeeName.sendKeys("Орлов Д");
        //Проверяем, что в реестре сотрудников не отображается Орлов Д.
        boolean employeesRegistryOrlov = !driver.findElements(By.xpath("//*[@data-qa=\"registry-row-employee-link\"]").xpath("//*[contains(text(), 'Орлов Дмитрий Евгеньевич')]")).isEmpty();
        if (employeesRegistryOrlov == true) {
            Allure.step("В реестре сотрудников есть Орлов Д.");
            System.out.println("В реестре сотрудников есть Орлов Д.");
        } else {
            Allure.step("В реестре сотрудников нет Орлова Д.");
            System.out.println("В реестре сотрудников нет Орлова Д.");
        }

        //Открываем Справочники
        WebElement catalogs = driver.findElement(By.xpath("//*[@data-qa=\"hr-app-nav-catalogs-link\"]"));
        catalogs.click();
        //Проверяем, что в Справочнике ЮЛ нет ООО "Кот"
        boolean catalogsCat = !driver.findElements(By.xpath("//*[contains(text(), 'ООО \"Кот\"')]")).isEmpty();
        if (catalogsCat == true) {
            Allure.step("В Справочнике ЮЛ есть ООО \"Кот\"");
            System.out.println("В Справочнике ЮЛ есть ООО \"Кот\"");
        } else {
            Allure.step("В Справочнике ЮЛ нет ООО \"Кот\"");
            System.out.println("В Справочнике ЮЛ нет ООО \"Кот\"");
        }
        driver.quit();
    }
}
