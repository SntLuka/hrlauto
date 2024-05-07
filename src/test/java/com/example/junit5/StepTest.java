package com.example.junit5;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
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

    private static final String GLOBAL_PARAMETER = "global value";

    @Test
    public void annotatedStepTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://app-test11-2.myhrlink.ru/login");

//        Задаём неявное ожидание, т.к. некоторые элементы прогружаются не сразу
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        //Выполняем логин за кадровика
        WebElement login = (new WebDriverWait(driver, Duration.ofSeconds(10)) //здесь мы задали явное ожидание, т.к. стартовая страница грузится 100500 лет
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-qa=\"credential-form-login-input\"]"))));
        login.sendKeys("aluchkin753+1@gmail.com");
        WebElement password = driver.findElement(By.xpath("//*[@data-qa=\"credential-form-password-input\"]"));
        password.sendKeys("Qq_123321");
        WebElement button = driver.findElement(By.xpath("//*[@data-qa=\"credential-form-submit-button\"]"));
        button.click();
        annotatedStep("Выполнен логин за кодровика");

        //Закрываем модульное окно с ТГ
        WebElement telegram = (new WebDriverWait(driver, Duration.ofSeconds(5)) //здесь мы задали явное ожидание, т.к. модалка ТГ тоже иногда грузится не сразу
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-qa=\"telegram-invitation-dlg-cancel-button\"]"))));
        telegram.click();

//        Выполняем фильтр по сотруднику
        WebElement filterEmployees = driver.findElement(By.xpath("//*[@data-qa=\"documents-registry-table-filter-row-employees-select\"]"));
        filterEmployees.click();
        WebElement inputEmployee = driver.findElement(By.xpath("/html/body/root/mat-sidenav-container/mat-sidenav-content/div[1]/ng-component/div/documents-registry-table/div/documents-registry-table-filter-row/div[6]/div[2]/employee-select/ng-select/div/div/div[2]/input"));
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
                System.out.println("В списке сотрудников в фильтре нет Орлов Д.");
            }
        }
        //Проверяем, что в реестре документов нет документа, в котором в качестве сотрудника указан Орлов Д.
//        driver.findElement(By.xpath("//*[contains(text(), 'Орлов Д.Е.')]")).isDisplayed();
        boolean orlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д.Е.')]")).isEmpty();
//        System.out.print(orlov);
        if (orlov == true) {
            System.out.println("В реестре документов есть документ, в котором в качестве сотрудника указан Орлов Д.");
        } else {
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
                System.out.println("В списке юрлиц есть ООО \"Кот\"");
            } else {
                System.out.println("В списке юрлиц нет ООО \"Кот\"");
            }
        }

        //Открываем реестр заявлений
        WebElement applications = driver.findElement(By.xpath("//*[@data-qa=\"hr-app-nav-applications-registry-link\"]"));
        applications.click();

        //Проверяем, что в реестре заявлений не отображается заявление от Орлова Д.
        boolean applicationsOrlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д.Е.')]")).isEmpty();
        if (applicationsOrlov == true) {
            System.out.println("В реестре заявлений есть заявление, в котором в качестве сотрудника указан Орлов Д.");
        } else {
            System.out.println("В реестре заявлений нет заявления, в котором в качестве сотрудника указан Орлов Д.");
        }

        //Открываем реестр сотрудников
        WebElement employeesRegistry = driver.findElement(By.xpath("//*[@data-qa=\"hr-app-nav-employees-registry-link\"]"));
        employeesRegistry.click();
        //Проверяем, что в реестре сотрудников не отображается Орлов Д.
        boolean employeesRegistryOrlov = !driver.findElements(By.xpath("//*[contains(text(), 'Орлов Д.Е.')]")).isEmpty();
        if (employeesRegistryOrlov == true) {
            System.out.println("В реестре сотрудников есть Орлов Д.");
        } else {
            System.out.println("В реестре сотрудников нет Орлова Д.");
        }

        //Открываем Справочники
        WebElement catalogs = driver.findElement(By.xpath("//*[@data-qa=\"hr-app-nav-catalogs-link\"]"));
        catalogs.click();
        //Проверяем, что в Справочнике ЮЛ нет ООО "Кот"
        boolean catalogsCat = !driver.findElements(By.xpath("//*[contains(text(), 'ООО \"Кот\"')]")).isEmpty();
        if (catalogsCat == true) {
            System.out.println("В Справочнике ЮЛ есть ООО \"Кот\"");
        } else {
            System.out.println("В Справочнике ЮЛ нет ООО \"Кот\"");
        }
        driver.quit();
    }

    @Test
    public void lambdaStepTest() {
        final String localParameter = "parameter value";
        Allure.step(String.format("Parent lambda step with parameter [%s]", localParameter), (step) -> {
            step.parameter("parameter", localParameter);
            Allure.step(String.format("Nested lambda step with global parameter [%s]", GLOBAL_PARAMETER));
        });
    }

    @Step("Parent annotated step with parameter [{parameter}]")
    public void annotatedStep(final String parameter) {
        nestedAnnotatedStep();
    }

    @Step("Nested annotated step with global parameter [{this.GLOBAL_PARAMETER}]")
    public void nestedAnnotatedStep() {

    }

}
