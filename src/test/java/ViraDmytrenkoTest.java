import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ViraDmytrenkoTest {
    WebDriver driver;
    static String baseUrl = "https://openweathermap.org/";

    @BeforeMethod
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(baseUrl);
        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testTC_11_01() {
        String expectedResult1 = "https://openweathermap.org/guide";
        String expectedResult2 = "OpenWeatherMap API guide - OpenWeatherMap";

        // Нажать на пункт меню Guide
        driver.findElement(By.xpath("//div[@id='desktop-menu']//a[text()='Guide']")).click();

        String actualResult1 = driver.getCurrentUrl();
        String actualResult2 = driver.getTitle();

        assertEquals(actualResult1, expectedResult1);
        assertEquals(actualResult2, expectedResult2);
    }

    @Test
    public void testTC_11_02() {
        String expectedResult = "F";

        WebElement imperialFButton = driver.findElement(By.xpath("//div[text()='Imperial: °F, mph']"));
        imperialFButton.click();

        WebElement currentTemperature = driver.findElement(By.xpath("//div[@class='current-temp']//span[@class='heading']"));

        // first version
        assertTrue(currentTemperature.getText().endsWith(expectedResult));

        // second version
        String currentTempText = currentTemperature.getText();
        String actual = currentTempText.substring(currentTempText.length() - 1);
        assertEquals(actual, "F");
    }

    @Test
    public void testTC_11_03() {
        String expectedResult1 = "We use cookies which are essential for the site to work. We also use non-essential"
                + " cookies to help us improve our services. Any data collected is anonymised. "
                + "You can allow all cookies or manage them individually.";
        String expectedButton1Text = "Allow all";
        String expectedButton2Text = "Manage cookies";

        // panel text
        WebElement cookiesMessagePanel = driver.findElement(By.className("stick-footer-panel__description"));
        String actualResult1 = cookiesMessagePanel.getText();

        // button Allow All
        WebElement buttonAllowAll = driver.findElement(By.xpath("//button[@class='stick-footer-panel__link']"));
        String actualButton1Text = buttonAllowAll.getText();

        // button Manage Cookies
        WebElement buttonManageCookies = driver.findElement(By.xpath("//a[@class='stick-footer-panel__link']"));
        String actualButton2Text = buttonManageCookies.getText();

        assertTrue(driver.findElement(By.id("stick-footer-panel")).isDisplayed());
        assertEquals(actualResult1, expectedResult1);
        assertEquals(actualButton1Text, expectedButton1Text);
        assertEquals(actualButton2Text, expectedButton2Text);
    }

    @Test
    public void testTC_11_04() {
        driver.findElement(By.id("support-dropdown")).click();
        assertEquals(driver.findElements(By.xpath("//ul[@id='support-dropdown-menu']/li")).size(), 3);
//        assertEquals(driver.findElement(By.xpath("//ul[@id='support-dropdown-menu']//a[@href='/faq'])")).getText(), "FAQ");


    }

    @Test
    public void testTC_11_05() throws InterruptedException {
        driver.findElement(By.id("support-dropdown")).click();

        driver.findElement(By.xpath("//ul[@id='support-dropdown-menu']/li/a[text()='Ask a question']")).click();

        Thread.sleep(5000);

        String originalWindow = driver.getWindowHandle();    //Store the ID of the original window

        //Loop through until we find a new window handle
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }


        Thread.sleep(3000);
        driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@name='commit']")).click();
        Thread.sleep(2000);
    }

//    @Test
//    public void testTC_11_05() throws InterruptedException {
//
//    }

    @Test
    public void testTC_11_10() throws InterruptedException {
        int expectedResult = 30;

        WebElement api_LinkOnDesktopMenu = driver.findElement(By.xpath("//div[@id='desktop-menu']//a[text()='API']"));
        Thread.sleep(5000);
        api_LinkOnDesktopMenu.click();
        Thread.sleep(2000);

        int actualResult = driver.findElements(By.xpath("//a[contains(@class, 'btn_block orange round') or contains(@class, 'ow-btn round btn-orange')]")).size();

        assertEquals(actualResult, expectedResult);
    }


}
