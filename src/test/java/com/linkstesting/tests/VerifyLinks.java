package com.linkstesting.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.linkstesting.restMethods.GetStatusCodes;
import com.linkstesting.testData.TestData;
import com.mongodb.diagnostics.logging.Logger;
import com.relevantcodes.extentreports.LogStatus;


public class VerifyLinks extends BaseTest {

  @Test(testName="Verify links",dataProvider = "Links", dataProviderClass = TestData.class)
  public void verifyLinks(String url) {

    String urlToTest = "";
   
    int status = 0;
    driver.get(url);
    List<WebElement> links = driver.findElements(By.tagName("a"));
    System.out.println(links.size());
    for (WebElement webElement : links) {

      urlToTest = webElement.getAttribute("href");
      if (urlToTest != null) {
        status=GetStatusCodes.getStatus(urlToTest);
       
          
        if(status==200|status==301)
        {
          logger.log(LogStatus.PASS, "url is " +urlToTest+ " "+"status code is "+status);
          if(status==301)
          {
            logger.log(LogStatus.INFO, "Redirected url is "+GetStatusCodes.getRedirectedLink(urlToTest));
          }
          
        }
        else
        {
          logger.log(LogStatus.FAIL,"url is " +urlToTest+ " "+"status code is "+status);
        }
        
       
      }


    }
    
  }
}
