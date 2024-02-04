package ru.aydar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aydar.pages.GitHubStepsPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

@DisplayName("Поиск issue в репозитории по номеру и проверка его названия")
public class GitHubIssueNameTest {
    public static final String REPOSITORY = "ThirteenAG/WidescreenFixesPack";
    public static final String ISSUENUMBER = "1510";
    public static final String ISSUENAME = "A message to the author";

    @Test
    @DisplayName("Тест с использованием Selenide listener")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("aydarium")
    @Link(value = "Репозиторий тестов", url = "https://github.com/aydarium/homework12")
    public void selenideListenerTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com");
        $(".search-input").click();
        $("#query-builder-test").setValue(REPOSITORY).submit();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $("#issue_" + ISSUENUMBER + "_link").should(Condition.text(ISSUENAME));
    }

    @Test
    @DisplayName("Тест с использованием лямбд")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("aydarium")
    @Link(value = "Репозиторий тестов", url = "https://github.com/aydarium/homework12")
    public void lambdaTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем главную страницу Гитхаба", () -> open("https://github.com"));
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").setValue(REPOSITORY).submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> $(linkText(REPOSITORY)).click());
        step("Открываем вкладку Issues", () -> $("#issues-tab").click());
        step("Проверяем, что у Issue с номером " + ISSUENUMBER + " название " + ISSUENAME, () -> {
            $("#issue_" + ISSUENUMBER + "_link").should(Condition.text(ISSUENAME));
        });
    }

    @Test
    @DisplayName("Тест с использованием аннотированных шагов")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("aydarium")
    @Link(value = "Репозиторий тестов", url = "https://github.com/aydarium/homework12")
    public void allureStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        GitHubStepsPage steps = new GitHubStepsPage();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.checkIssueName(ISSUENUMBER,ISSUENAME);
    }
}
