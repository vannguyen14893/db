package com.cmc.dashboard.util;

import org.springframework.http.HttpStatus;

/**
 * This class is message of api .
 * 
 * @author: NNDuy
 * @create_date: Feb 23, 2018
 * @version: 1.0
 * @modifer: NNDuy
 * @modifer_date: Feb 23, 2018
 */
public class ApiMessage {

  private HttpStatus status;
  private String message;

  public ApiMessage(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
