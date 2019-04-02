package cn.tjucic.selenium;

import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class lab2 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  File file;
  String[][] result;
  //加载表格内容
  @Before
  public void setUp() throws Exception {
	  String driverPath = System.getProperty("user.dir") + "/src/resources/driver/geckodriver.exe";
	  System.setProperty("webdriver.gecko.driver", driverPath);
	  driver = new FirefoxDriver();
	  baseUrl = "http://121.193.130.195:8800";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  file = new File("D:\\java-neon\\lab2\\src\\software.xls");
	  result = new read().getData(file, 1);
  }
  //加载driver，逐一进行测试
  @Test
  public void testBaidu() throws Exception {
	  for(int i = 1; i <= 143; i++){
		  driver.get(baseUrl + "/");
		  WebElement we = driver.findElement(By.name("id"));
		  we.sendKeys(result[i][1]);    
	      we = driver.findElement(By.name("password"));
	      we.sendKeys(result[i][1].substring(4));
	      we = driver.findElement(By.id("btn_login"));
	      we.click();
	      assertEquals(result[i][1], driver.findElement(By.id("student-id")).getText());
	      assertEquals(result[i][2], driver.findElement(By.id("student-name")).getText());
	      assertEquals(result[i][3], driver.findElement(By.id("student-git")).getText());
	      we = driver.findElement(By.id("btn_logout"));
	      we.click();
	      we = driver.findElement(By.id("btn_return"));
	      we.click(); 
	  }
    
  }
  //关闭driver
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
	}
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

