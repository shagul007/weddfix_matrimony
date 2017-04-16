/****************************************************************************************************
 * File name             : TNHBException.java
 * Project Name          : TNHB-eGovernance
 * Package Name          : com.tnhb.web.util
 * Interface Implemented : 
 * Class Type            : TNHBException
 * Brief Description     : This is base exception, to handle the TNHB Application   
 * Inherited From        : 
 * Author Name           : 3130333/CMC
 * Copyright		 	 : TNHB 
 * Date                  : September 20 2013
 * Revision History      : 
		
	Author Name		 	Date			     Change Description			        Version 
	 
*****************************************************************************************************/

package com.weddfix.web.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;


public class WeddingException extends Exception implements Serializable{

	 private static final long serialVersionUID = 8416838558642791219L;
	 private static final Logger logger = Logger.getLogger(WeddingException.class); 
	 
	 protected String className;
	 protected List<ErrorMessage> errorMessages;
	 protected String exceptionID;
	 protected String methodName;
	 protected String errMsg;
	 
	 protected ErrorMessage errorMessage;
	

	 private boolean isLogged = false;
	 
	 
	 public WeddingException(){
		 	super(generateErrorID());//helps to identify the exception even if the same exception is logged in multiple times/files.
	 }
	 
	
	 /**
	  * @description exceptionID - Constructor where only messageID is placed,
	  * This messageID should not be changed as there may be corresponding message in property files.
	  * @param exceptionID
	  */
	 public WeddingException(String exceptionID){
			super(exceptionID+generateErrorID());//super class uniquely add messageID internally to exception.
		 	this.exceptionID = exceptionID;
			this.isLogged = false;
			addMessage();
	 }
	 public WeddingException(String exceptionID, String errMsg){
			super(exceptionID+generateErrorID());//super class uniquely add messageID internally to exception.
		 	this.exceptionID = exceptionID;
			this.isLogged = false;
			this.errMsg = errMsg;
			addMessage();
	 }
	 /**
	  * @description exceObj - The actual Throwable exception. 
	  * @param exceptionID
	  * @param className
	  * @param methodName
	  * @param exceObj
	  */
	 public WeddingException(String exceptionID, String className, String methodName, Throwable exceObj){
			 super(exceObj);
			 this.exceptionID = exceptionID;
			 this.className = className;
			 this.methodName = methodName;
			 
			 logger.error("Exception Occured at Class->"+className+"::Method->"+methodName+"::Exception->", exceObj);
	}
	 
	 
	 /**
	  * @description exceObj - The actual Throwable exception.  
	  * @param exceptionID
	  * @param errorMsg
	  * @param className
	  * @param methodName
	  * @param isLogged
	  */
	 public WeddingException(String exceptionID, String errorMsg, String className, String methodName,  boolean isLogged){
			 this.exceptionID = exceptionID;
			 this.className = className;
			 this.methodName = methodName;
			 this.isLogged = isLogged;
			 this.errMsg = errorMsg;
			 
			 if(!isLogged){
				 logger.error("Exception Occured at Class->"+className+"::Method->"+methodName+"::Message->"+errMsg);
			 }
			 
			 addMessage();
	 }
	 
	 
	 /**
	  * @description exceObj - The actual Throwable exception. 
	  * This constructor is helpful when we wanted to validate multiple 
	  * inputs and catch hold of list of validation exceptions. Instead of throwing for each individual validation failure.
	  * @param exceptionID
	  * @param className
	  * @param methodName
	  * @param exceObj
	  * @param isLogged
	  */
	 public WeddingException(String exceptionID, String className, String methodName, Throwable exceObj, boolean isLogged){
			 super(exceObj);
			 this.exceptionID = exceptionID;
			 this.className = className;
			 this.methodName = methodName;
			 this.isLogged = isLogged;
			 
			 if(!isLogged){
				 logger.error("Exception Occured at Class->"+className+"::Method->"+methodName+"::Exception->", exceObj);
			 }
			 
			 addMessage();
	 }
	 
	 
	 /**
	  * @description exceObj - The actual Throwable exception. 
	  * This constructor is helpful when we wanted to validate multiple 
	  * inputs and catch hold of list of validation exceptions. Instead of throwing for each individual validation failure.
	  * @param exceptionID
	  * @param errorMsg
	  * @param className
	  * @param methodName
	  * @param exceObj
	  * @param isLogged
	  */
	 public WeddingException(String exceptionID, String errorMsg, String className, String methodName, Throwable exceObj, boolean isLogged){
			 super(exceObj);
			 this.exceptionID = exceptionID;
			 this.className = className;
			 this.methodName = methodName;
			 this.isLogged = isLogged;
			 this.errMsg = errorMsg;
			 
			 if(!isLogged){
				 logger.error("Exception Occured at Class->"+className+"::Method->"+methodName+"::Message->"+errorMsg+"::Exception->", exceObj);
			 }
			 
			 addMessage();
	 }
	 /**
	  * @description errorMessages - List of message objects can also be added to constructor.
	  * This may be used in case of more filed validations at server end.
	  * @param errorMessages
	  * @param className
	  */
	 public WeddingException(List<ErrorMessage> errorMessages, String className){
			 super(generateErrorID());
			 this.errorMessages = errorMessages;
			 this.className = className;
			 this.isLogged = false;
	 }
	 
	 
	 public WeddingException(List<ErrorMessage> errorMessages, String className, Throwable exceObj){
			 super(exceObj);
			 this.errorMessages = errorMessages;
			 this.className = className;
			 this.isLogged = false;
		 
	 }
	 
	 public WeddingException(Throwable exceObj, boolean isLogged){
			 super(exceObj);
			 this.isLogged = isLogged;
	 }
	 
	 
	 private void addMessage(){
		
			errorMessage = new ErrorMessage(this.exceptionID, this.className, this.methodName, this.errMsg); 
			
			if(errorMessages == null){
				 errorMessages = new ArrayList<ErrorMessage>();
			}
			errorMessages.add(errorMessage);
	 }
	 
	
	 
	private static String generateErrorID(){
			return ("[ErrorID="+Math.abs(new Random().nextInt())+"]\n");
	}
	
	public String getclassName() {
			return className;
	}

	public void setclassName(String className) {
			this.className = className;
	}

	public List<ErrorMessage> getErrorMessages() {
			return errorMessages;
	}

	public void setErrorMessages(List<ErrorMessage> errorMessages) {
			this.errorMessages = errorMessages;
	}

	public String getExceptionID() {
			return exceptionID;
	}

	public void setExceptionID(String exceptionID) {
			this.exceptionID = exceptionID;
	}

	public boolean isLogged() {
			return this.isLogged;
	}
	 
	public void clearAllMessages(){
			this.errorMessages = null;
	}	
	
	public ErrorMessage getErrorMessage() {
		ErrorMessage errorMessage = null;
		if(this.errorMessages != null){
			errorMessage = this.errorMessages.get(0);
		}
		
		return errorMessage;
	}


	public String getErrMsg() {
		return errMsg;
	}


	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	
}
