package com.linkstesting.restMethods;

import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class GetStatusCodes {



  public static int getStatus(String url) {

    int status;
    Response response = RestAssured.given().when().redirects().follow(false).get(url);
    status = response.getStatusCode();
      return status;
    

  }
  
  public static String getRedirectedLink(String url)
  {
    Response response = RestAssured.given().when().redirects().follow(false).get(url);
    return response.header("Location");
  }
  
}
