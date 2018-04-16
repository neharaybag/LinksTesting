package com.linkstesting.testData;

import org.testng.annotations.DataProvider;

public class TestData {
  
  @DataProvider(name="Links")
  public static Object[][] Links() {

    return new Object[][] {{"http://www.walkthroughindia.com/"}};

  }

}
