/****************************************************************************************************
 * File name             : ErrorMessage.java
 * Project Name          : Common
 * Package Name          : com.tnhb.web.util
 * Interface Implemented : Serializable
 * Class Type            : ErrorMessage
 * Brief Description     : 
 * Inherited From        : 
 * Author Name           : 3130333/CMC
 * Copyright		 	 : TNHB 
 * Date                  : September 23, 2013
 * Revision History      : 
		
	Author Name		 	Date			     Change Description			        Version 
	 
*****************************************************************************************************/
package com.weddfix.web.util;

import java.io.Serializable;


public class ErrorMessage implements Serializable{

	   private static final long serialVersionUID = 21234213L;
	   
	   private String errorCode;
	   private String className;
	   private String errorMessage;
	   private String methodName;
	   //further if any additional error message parameters required can be added 
	 
	 
	public ErrorMessage(){
		   
	   }
	   
	   /**
	    * @description errCode - exception messageCode
	    * @param errCode
	    */
	   public ErrorMessage(String errCode){
		   this.errorCode = errCode;
	   }
	 
	   /**
	    * @description errMessage - exception message, added while raising the exceptions.
	    * @param errCode - 
	    * @param errMessage
	    */
	   public ErrorMessage(String errCode, String errMessage){
		   this.errorCode = errCode;
		   this.errorMessage = errMessage;
	   }
	  
	   /**
	    * @description  className - Actual class (component) where the exception is raised.
	    * @param errCode
	    * @param className
	    * @param errMessage
	    */
	   public ErrorMessage(String errCode, String className, String methodName, String errMessage){
		   this.errorCode = errCode;
		   this.className = className;
		   this.methodName = methodName;
		   this.errorMessage = errMessage;
	   }

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	   
	  
		
}
