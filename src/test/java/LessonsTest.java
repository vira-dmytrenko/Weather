
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LessonsTest {
    //TC_1_1 - Тест кейс:
    //1. Открыть страницу https://openweathermap.org/
    //2. Набрать в строке поиска город Paris
    //3. Нажать пункт меню Search
    //4. Из выпадающего списка выбрать Paris, FR
    //5. Подтвердить, что заголовок изменился на "Paris, FR"

    @Test
    public void testH2TagText_WhenSearchingCityCountry() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String cityName = "Paris";
        String expectedResult = "Paris, FR";
        String inputField = "//div[@id='weather-widget']/div/div/div//div/div/input[@placeholder='Search city']";

        driver.get(url);

        Thread.sleep(5000);

        WebElement searchCity = driver.findElement(
                By.xpath("//div[@id='weather-widget']//input[@placeholder='Search city']"));

        searchCity.click();
        searchCity.sendKeys(cityName);

        WebElement searchButton = driver.findElement(
                By.xpath("//div[@id='weather-widget']//button[@type='submit']")
        );
        searchButton.click();

        Thread.sleep(1000);
        WebElement firstFRChoiceInDropdownMenu = driver.findElement(
                By.xpath("//span[text()='Paris, FR ']")
        );
        firstFRChoiceInDropdownMenu.click();


        WebElement h2CityNameHeader = driver.findElement(By.xpath("//div[@id='weather-widget']//h2"));
        Thread.sleep(1000);
        String actualResult = h2CityNameHeader.getText();
        assertEquals(actualResult, expectedResult);


        driver.quit();
    }
}
