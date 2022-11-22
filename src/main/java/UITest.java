import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


public class UITest {
    static ChromeOptions options = new ChromeOptions();
   public static WebDriver driver=new ChromeDriver(options);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


    @Before
    public  void openBrowser(){
        WebDriverManager.chromedriver().setup();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }

    @Test
    public void validate_balance_afterDepositAndWithdraw() {

        WebElement loginElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[ng-click='customer()']")));
        loginElement.click();
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("userSelect")));
        selectElement.click();
        Select drpCountry = new Select(driver.findElement(By.id("userSelect")));
        drpCountry.selectByVisibleText("Albus Dumbledore");
        driver.findElement(By.cssSelector("[type='submit']")).click();

        // Deposit
        WebElement depositElement = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("[ng-click='deposit()']"))));
        depositElement.click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("[ng-model='amount'")).sendKeys("1000");
        driver.findElement(By.cssSelector("[type='submit']")).click();

        //Withdraw
        WebElement withdrawElement = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("[ng-click='withdrawl()']"))));
        withdrawElement.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement txtWithdrawElement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("[ng-submit='withdrawl()']")));
        txtWithdrawElement.findElement(By.cssSelector("[ng-model='amount']")).sendKeys("400");
        driver.findElement(By.cssSelector("[type='submit']")).click();

        WebElement balanceTxtValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]"));
        Assert.assertEquals(balanceTxtValue.getText(),"600");
        driver.findElement(By.cssSelector("[ng-click='transactions()']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"anchor1\"]/td[3]")).getText()
                ,"Debit");
    }

    @After
    public void quit()
    {
        driver.quit();
    }

}


