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
    //common methods used during the program
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
    public void registration(){
        //click on register button
        clickElementBy(By.linkText("Register"));
        //select the male gender option
        clickElementBy(By.id("gender-male"));
        //enter "MyFirstName" in First name field
        enterText(By.id("FirstName"),"MyFirstName");
        //enter "MySurname" in Last name field
        enterText(By.id("LastName"),"MySurname");
        //select the value "20" for Day field of Date of Birth
        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        day.selectByValue("20");
        //select the value "May" for the Month field of Date of Birth
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByIndex(5);
        //select the value "1950" for the year field of Date of Birth
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1950");
        DateFormat dateFormat = new SimpleDateFormat("DDMMYYYHHMMSS");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        //enter the value "Bhavin@home.com" in email field
        driver.findElement(By.id("Email")).sendKeys("Bhavin+"+date1+"@home.com");
        //enter the value "My Company" in the company field
        enterText(By.id("Company"),"My Company");
        //tick the Newsletter check box
        selectBy(By.id("Newsletter"));
        //enter the value "mypass" in the Password field
        enterText(By.id("Password"),"mypass");
        //enter the value "mypass" in the Confirm Password field
        enterText(By.id("ConfirmPassword"),"mypass");
        //click on register button
        clickElementBy(By.id("register-button"));
    }
    @BeforeMethod //run before every method
    public void openingBrowser(){
        //initializing the chrome driver and passing the url
        //pre conditions
        System.setProperty("webdriver.chrome.driver","src\\BrowserDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://demo.nopcommerce.com/");
    }
    @AfterMethod //run after every method
    public void closingBrowser(){
        driver.quit();
    }

    @Test(priority=1)   // first test to run to check user should be able to register successfully
    public void userShouldBeAbleToRegisterSuccessfully(){
        registration();
        String actualResult = driver.findElement(By.xpath("//div[@class='result']")).getText();
        //user should be able to see the message "Your registration completed" if successful
        String expectedResult = "Your registration completed";
        Assert.assertEquals("Test Fail",expectedResult,actualResult);
        //click on Log out button
        findElementBy(By.linkText("Log out"));
    }

    @Test(priority = 2) //registered user should be able to send email with product successfully
    public void registeredUserShouldBeAbleToSendEmailWithProductSuccessfully(){
        registration();
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        //click on Computers
        clickElementBy(By.linkText("Computers"));
        //click on Notebooks
        clickElementBy(By.linkText("Notebooks"));
        //click on the first product in the list
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        //click on the Email a Friend button
        clickElementBy(By.xpath("//input[@value='Email a friend']"));
        //enter the value "rakesh@patel.com" in Friend's Email field
        enterText(By.id("FriendEmail"),"rakesh@patel.com");
        //registered email address should be entered in Your email address field
        //Enter the value "Best one for you!!!! in the Personal message field
        enterText(By.id("PersonalMessage"),"Best one for you!!!");
        //click on send email button
        clickElementBy(By.name("send-email"));
        //registered user should be able to see the message "Your message has bee sent."
        String expectedResult = "Your message has been sent.";
        String actualResult = driver.findElement(By.xpath("//div[@class='page email-a-friend-page']/div[2]/div[contains(text(),'Your message has been sent.')]")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 3) // unregistered user should not be able to send email with product
    public void unRegisteredUserShouldNotBeAbleToSendEmail(){
        //click on Computers
        clickElementBy(By.linkText("Computers"));
        //click on Notebooks
        clickElementBy(By.linkText("Notebooks"));
        //click on the first product in the list
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        //click on the Email a Friend button
        clickElementBy(By.xpath("//input[@value='Email a friend']"));
        //enter the value "abc@abc.com" in Friend's Email field
        enterText(By.id("FriendEmail"),"abc@abc.com");
        //enter value "def@def.com" in Your Email Address field
        enterText(By.id("YourEmailAddress"),"def@def.com");
        //Enter the value "Best one for you!!!! in the Personal message field
        enterText(By.id("PersonalMessage"),"Best one for you!!!!");
        //click on send email button
        clickElementBy(By.name("send-email"));
        //"Only registered customers can use email a friend feature" unsuccessful message should be display
        String expectedResult = "Only registered customers can use email a friend feature";
        String actualResult = driver.findElement(By.xpath("//div[@class=\"message-error validation-summary-errors\"]/ul/li")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 4) //user need to accept the T&C before checkout pop should be displayed
    public void userNeedToAcceptTermsAndConditionBeforeCheckOut(){
        registration();
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        //click on Computers
        clickElementBy(By.linkText("Computers"));
        //click on Notebooks
        clickElementBy(By.linkText("Notebooks"));
        //click on the first product in the list
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        //click on ADD TO CART button
        clickElementBy(By.id("add-to-cart-button-4"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bar-notification']/p/a")));
        //click on shopping cart on the notification bar
        clickElementBy(By.xpath("//div[@id='bar-notification']/p/a"));
        //click on the CHECKOUT button
        clickElementBy(By.id("checkout"));
        //Pop up display should be display to accept T&C to user
        String expectedResult = "Please accept the terms of service before the next step.";
        String actualResult = driver.findElement(By.id("terms-of-service-warning-box")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 5)  // registered user should be able to buy single product successfully
    public void registeredUserShouldBeAbleToBuyAnySingleProductSuccessfully(){
        registration();
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Computers")));
        //click on Computers
        clickElementBy(By.linkText("Computers"));
        //click on Notebooks
        clickElementBy(By.linkText("Notebooks"));
        //click on the first product in the list
        clickElementBy(By.xpath("//div[@data-productid='4']/div[@class='picture']"));
        //click on ADD TO CART button
        clickElementBy(By.id("add-to-cart-button-4"));
        //click on shopping cart on the notification bar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='bar-notification']/p/a")));
        clickElementBy(By.xpath("//div[@id='bar-notification']/p/a"));
        //select the T&C check box
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("termsofservice")));
        clickElementBy(By.id("termsofservice"));
        //click on CHECK OUT button
        clickElementBy(By.id("checkout"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("BillingNewAddress_CountryId")));
        Select country = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
        //registered user details will be autofilled
        //select the value "United Kingdom" in the country field
        country.selectByVisibleText("United Kingdom");
        //enter "Wembley" in the city field
        enterText(By.id("BillingNewAddress_City"),"Wembley");
        //enter the value "123 First Street" in Address 1 field
        enterText(By.id("BillingNewAddress_Address1"),"123 First Street");
        //enter the value "HA1 1NA" in the zip postal code field
        enterText(By.id("BillingNewAddress_ZipPostalCode"),"HA1 1NA");
        //enter the value "07866638787" in the phone number field
        enterText(By.id("BillingNewAddress_PhoneNumber"),"07866638787");
        //click on continue button
        clickElementBy(By.id("billing-buttons-container"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("shipping-method-buttons-container")));
        //accept default value and click on continue button
        clickElementBy(By.id("shipping-method-buttons-container"));
        //click on credit card radio button
        clickElementBy(By.id("paymentmethod_1"));
        //click on continue button
        clickElementBy(By.xpath("//input[@class='button-1 payment-method-next-step-button']"));
        //enter the value "Ramesh Patel" in the card holder name field
        enterText(By.id("CardholderName"),"Ramesh Patel");
        //enter the value "4111 1111 1111 1111" in the card number field
        enterText(By.id("CardNumber"),"4111 1111 1111 1111");
        //select the value "02" in the expiry month field
        Select expiryMonth = new Select(driver.findElement(By.id("ExpireMonth")));
        expiryMonth.selectByValue("2");
        //select the value "2020" in the expiry year value
        Select expiryYear = new Select(driver.findElement(By.id("ExpireYear")));
        expiryYear.selectByValue("2020");
        //enter the value "737" in the card code field
        enterText(By.id("CardCode"),"737");
        //click on continue button
        clickElementBy(By.xpath("//input[@class='button-1 payment-info-next-step-button']"));
        //click on confirm button
        clickElementBy(By.xpath("//input[@class='button-1 confirm-order-next-step-button']"));
        //"Your order has been successfully processed!" message should be displayed
        String expectedResult = "Your order has been successfully processed!";
        String actualResult = driver.findElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]")).getText();
        Assert.assertEquals("Test case : Test Fail",expectedResult,actualResult);
    }

    @Test (priority = 6) // user should be able to sort the price by high to low
    public void userShouldBeAbleToSortByPriceHighToLow(){
        //click on Computers
        clickElementBy(By.linkText("Computers"));
        //click on Notebooks
        clickElementBy(By.linkText("Notebooks"));
        //select the value "Price: High to Low" from sort by field
        Select sortBy = new Select(driver.findElement(By.id("products-orderby")));
        sortBy.selectByVisibleText("Price: High to Low");

        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-productid='7']")));
        //verify the products loaded on the page high to low in price by checking first price and last price
        String firstProductPrice = driver.findElement(By.xpath("//div[@data-productid='4']/div[2]/div[3]/div/span")).getText();
        String lastProductPrice = driver.findElement(By.xpath("//div[@data-productid='7']/div[2]/div[3]/div/span")).getText();
        String firstPrice = firstProductPrice.substring(1).replace(",","");
        String lastPrice = lastProductPrice.substring(1).replace(",","");
        Assert.assertTrue("Test case:Prices Loaded correctly",Float.parseFloat(firstPrice)>Float.parseFloat(lastPrice));
    }

}
