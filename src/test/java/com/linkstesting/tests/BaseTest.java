package com.linkstesting.tests;



import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class BaseTest {

  ExtentReports extent;
  ExtentTest logger;
  protected WebDriver driver = new ChromeDriver();;
  private static Properties prop = new Properties();
  Test test;
  String testName;
  String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(Calendar.getInstance().getTime());



  @BeforeSuite
  public void beforeSuite() {

    try {
      prop.load(ClassLoader.getSystemResource("reporting.properties").openStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    String filepath =
        "/" + prop.getProperty("reportFolder") +  "/" + prop.getProperty("repoertName")+"_"+timeStamp+".html";
    extent = new ExtentReports(System.getProperty("user.dir") + filepath, true);
    extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

  }

  @BeforeMethod(alwaysRun = true)
  public void nameBefore(Method method) {
    test = method.getAnnotation(Test.class);
    testName = test.testName();
    logger = extent.startTest(testName);

  }

  @AfterMethod
  public void getResult(ITestResult result) {

    String screenshotPath = null;
    if (result.getStatus() == ITestResult.FAILURE) {
      try {
        screenshotPath = capture(driver, testName);
      } catch (IOException e) {
        e.printStackTrace();
      }
      logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
      logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
      logger.log(LogStatus.FAIL, "Snapshot below: " + logger.addScreenCapture(screenshotPath));
    } else if (result.getStatus() == ITestResult.SKIP) {
      logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
    } else if (result.getStatus() == ITestResult.SUCCESS) {
      logger.log(LogStatus.PASS, "Test case passed is " + result.getName());
    }
    // ending test
    extent.endTest(logger);

  }

  @AfterTest
  public void endReport() {

    extent.flush();
    extent.close();
    driver.quit();
  }

  

  public static String capture(WebDriver driver, String screenShotName) throws IOException {

    File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    String dest = System.getProperty("user.dir") + prop.getProperty("screenshotfolder") + screenShotName + ".png";
    TakesScreenshot ts = (TakesScreenshot) driver;
    FileUtils.copyFile(source, new File(dest));
    return dest;
  }

}
