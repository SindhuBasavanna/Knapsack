package com.mobiquityinc.exception;

import java.io.Serializable;
/**
 * CustomException as requested
 * @author sindhu
 *
 */
public class APIException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  public APIException(String s) {
	  super(s);
  }

  public APIException(Throwable cause) {
    super(cause);
  }
}
