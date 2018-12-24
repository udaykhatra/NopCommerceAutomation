import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AutomationTests {
    static WebDriver driver;
    public void clickElementBy(By by){
        driver.findElement(by).click();
    }
    public void findElementBy(By by){
        driver.findElement(by);
    }
    public void enterText(By by, String text){
        driver.findElement(by).sendKeys(text);
    }
    public void selectBy(By by){
        driver.findElement(by).isSelected();
    }
    @BeforeMethod
    public void openingBrowser(){
        System.setProperty("webdriver.chrome.driver","src\\BrowserDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://demo.nopcommerce.com/");
    }
    @AfterMethod
    public void closingBrowser(){
        driver.quit();
    }

    @Test(priority=1)
    public void userShouldBeAbleToRegisterSuccessfully(){
        clickElementBy(By.linkText("Register"));
        clickElementBy(By.id("gender-male"));
        enterText(By.id("FirstName"),"MyFirstName");
        enterText(By.id("LastName"),"MySurname");
        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        day.selectByValue("20");
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByIndex(5);
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1950");
        DateFormat dateFormat = new SimpleDateFormat("DDMMYYYHHMMSS");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        driver.findElement(By.id("Email")).sendKeys("Bhavin+"+date1+"@home.com");
        enterText(By.id("Company"),"My Company");
        selectBy(By.id("Newsletter"));
        enterText(By.id("Password"),"mypass");
        enterText(By.id("ConfirmPassword"),"mypass");
        clickElementBy(By.id("register-button"));
        String actualResult = driver.findElement(By.xpath("//div[@class='result']")).getText();
        String expectedResult = "Your registration completed";
        Assert.assertEquals("Test Fail",expectedResult,actualResult);
        findElementBy(By.linkText("Log out"));
    }

    @Test(priority = 2)
    public void registeredUserShouldBeAbleToSendEmailWithProductSuccessfully(){
        clickElementBy(By.linkText("Register"));
        clickElementBy(By.id("gender-male"));
        enterText(By.id("FirstName"),"MyFirstName");
        enterText(By.id("LastName"),"MySurname");
        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        day.selectByValue("20");
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByIndex(5);
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1950");
        DateFormat dateFormat = new SimpleDateFormat("DDMMYYYHHMMSS");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        driver.findElement(By.id("Email")).sendKeys("Bhavin+"+date1+"@home.com");
        enterText(By.id("Company"),"My Company");
        selectBy(By.id("Newsletter"));
        enterText(By.id("Password"),"mypass");
        enterText(By.id("ConfirmPassword"),"mypass");
        clickElementBy(By.id("register-button"));
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        clickElementBy(By.linkText("Computers"));
        clickElementBy(By.linkText("Notebooks"));
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        clickElementBy(By.xpath("//input[@value='Email a friend']"));
        enterText(By.id("FriendEmail"),"rakesh@patel.com");
        //enterText(By.id("YourEmailAddress"),"ramesh@patel.com");
        enterText(By.id("PersonalMessage"),"Best one for you!!!");
        clickElementBy(By.name("send-email"));
        String expectedResult = "Your message has been sent.";
        String actualResult = driver.findElement(By.xpath("//div[@class='page email-a-friend-page']/div[2]/div[contains(text(),'Your message has been sent.')]")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 3)
    public void unRegisteredUserShouldNotBeAbleToSendEmail(){
        clickElementBy(By.linkText("Computers"));
        //now clicking on notebooks
        clickElementBy(By.linkText("Notebooks"));
        //Now selecting the product
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        //clicking on email a friend button
        clickElementBy(By.xpath("//input[@value='Email a friend']"));
        //passing the values in the required fields
        enterText(By.id("FriendEmail"),"abc@abc.com");
        enterText(By.id("YourEmailAddress"),"def@def.com");
        enterText(By.id("PersonalMessage"),"Best one for you");
        //click on send email button
        clickElementBy(By.name("send-email"));
        //comparing the actual message to expected message to user
        String expectedResult = "Only registered customers can use email a friend feature";
        String actualResult = driver.findElement(By.xpath("//div[@class=\"message-error validation-summary-errors\"]/ul/li")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 4)
    public void userNeedToAcceptTermsAndConditionBeforeCheckOut(){
        clickElementBy(By.linkText("Register"));
        clickElementBy(By.id("gender-male"));
        enterText(By.id("FirstName"),"MyFirstName");
        enterText(By.id("LastName"),"MySurname");
        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        day.selectByValue("20");
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByIndex(5);
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1950");
        DateFormat dateFormat = new SimpleDateFormat("DDMMYYYHHMMSS");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        driver.findElement(By.id("Email")).sendKeys("Bhavin+"+date1+"@home.com");
        enterText(By.id("Company"),"My Company");
        selectBy(By.id("Newsletter"));
        enterText(By.id("Password"),"mypass");
        enterText(By.id("ConfirmPassword"),"mypass");
        clickElementBy(By.id("register-button"));
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        clickElementBy(By.linkText("Computers"));
        clickElementBy(By.linkText("Notebooks"));
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        clickElementBy(By.id("add-to-cart-button-4"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bar-notification']/p/a")));
        clickElementBy(By.xpath("//div[@id='bar-notification']/p/a"));
        clickElementBy(By.id("checkout"));
        String expectedResult = "Please accept the terms of service before the next step.";
        String actualResult = driver.findElement(By.id("terms-of-service-warning-box")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 5)
    public void registeredUserShouldBeAbleToBuyAnySingleProductSuccessfully(){
        clickElementBy(By.linkText("Register"));
        clickElementBy(By.id("gender-male"));
        enterText(By.id("FirstName"),"MyFirstName");
        enterText(By.id("LastName"),"MySurname");
        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        day.selectByValue("20");
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByIndex(5);
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1950");
        DateFormat dateFormat = new SimpleDateFormat("DDMMYYYHHMMSS");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        driver.findElement(By.id("Email")).sendKeys("Bhavin+"+date1+"@home.com");
        enterText(By.id("Company"),"My Company");
        selectBy(By.id("Newsletter"));
        enterText(By.id("Password"),"mypass");
        enterText(By.id("ConfirmPassword"),"mypass");
        clickElementBy(By.id("register-button"));
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        clickElementBy(By.linkText("Computers"));
        clickElementBy(By.linkText("Notebooks"));
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        clickElementBy(By.id("add-to-cart-button-4"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bar-notification']/p/a")));
        clickElementBy(By.xpath("//div[@id='bar-notification']/p/a"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("termsofservice")));
        clickElementBy(By.id("termsofservice"));
        clickElementBy(By.id("checkout"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("BillingNewAddress_CountryId")));
        Select country = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
        country.selectByVisibleText("United Kingdom");
        enterText(By.id("BillingNewAddress_City"),"Wembley");
        enterText(By.id("BillingNewAddress_Address1"),"123 First Street");
        enterText(By.id("BillingNewAddress_ZipPostalCode"),"HA1 1NA");
        enterText(By.id("BillingNewAddress_PhoneNumber"),"07866638787");
        clickElementBy(By.id("billing-buttons-container"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("shipping-method-buttons-container")));
        clickElementBy(By.id("shipping-method-buttons-container"));
        clickElementBy(By.id("paymentmethod_1"));
        clickElementBy(By.xpath("//input[@class='button-1 payment-method-next-step-button']"));
        //Select cardType = new Select(driver.findElement(By.id("CreditCardType")));
        //cardType.selectByValue("Visa");
        enterText(By.id("CardholderName"),"Ramesh Patel");
        enterText(By.id("CardNumber"),"4111 1111 1111 1111");
        Select expiryMonth = new Select(driver.findElement(By.id("ExpireMonth")));
        expiryMonth.selectByValue("2");
        Select expiryYear = new Select(driver.findElement(By.id("ExpireYear")));
        expiryYear.selectByValue("2020");
        enterText(By.id("CardCode"),"737");
        clickElementBy(By.xpath("//input[@class='button-1 payment-info-next-step-button']"));
        clickElementBy(By.xpath("//input[@class='button-1 confirm-order-next-step-button']"));
        String expectedResult = "Your order has been successfully processed!";
        String actualResult = driver.findElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 6)
    public void userShouldBeAbleToSortByPriceHighToLow(){
        clickElementBy(By.linkText("Computers"));
        clickElementBy(By.linkText("Notebooks"));
        Select sortBy = new Select(driver.findElement(By.id("products-orderby")));
        sortBy.selectByVisibleText("Price: High to Low");
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-productid='7']")));
        String firstProductPrice = driver.findElement(By.xpath("//div[@data-productid='4']/div[2]/div[3]/div/span")).getText();
        String lastProductPrice = driver.findElement(By.xpath("//div[@data-productid='7']/div[2]/div[3]/div/span")).getText();
        String firstPrice = firstProductPrice.substring(1).replace(",","");
        String lastPrice = lastProductPrice.substring(1).replace(",","");
        Assert.assertTrue("Test case:Prices Loaded correctly",Float.parseFloat(firstPrice)>Float.parseFloat(lastPrice));
    }

}
