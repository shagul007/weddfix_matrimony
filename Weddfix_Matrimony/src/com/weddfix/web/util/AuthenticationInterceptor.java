package com.weddfix.web.util;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

public class AuthenticationInterceptor extends AbstractInterceptor {
	
	private static final String authenticationRequiredResult  = "authentication_required";
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authenticationSessionField  = new String("authenticated");
	@SuppressWarnings("rawtypes")
	private Set excludeActions = Collections.EMPTY_SET;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		@SuppressWarnings("rawtypes")
		Map session = invocation.getInvocationContext().getSession();
		String actionName = invocation.getProxy().getActionName();
		invocation.getAction();
		
		Object authenticationObject  = session.get(this.authenticationSessionField);
		
		if(this.excludeActions.contains(actionName) || authenticationObject != null && authenticationObject.equals(Boolean.TRUE) ){
			System.out.println("..........................AUTHENTICATED USER");
			return invocation.invoke();
		}
		System.out.println("...............................AUTHENTICATION REQUIRED");
		return authenticationRequiredResult;
	}


	public void setAuthenticationSessionField(String authenticationSessionField) {
		this.authenticationSessionField = authenticationSessionField;
	}
	
	public void setExcludeActions(String values){
		if(values != null){
			this.excludeActions = TextParseUtil.commaDelimitedStringToSet(values);
			System.out.println("THIS . EXCLUDE ACTIONS : "  + this.excludeActions);
		}
	}
	
	/*public String intercept(ActionInvocation actionInvocation) throws Exception {
		// TODO Auto-generated method stub
		String userId  = null;
		System.out.println("Interceptor is calling...........");
		Map<String,Object> session = actionInvocation.getInvocationContext().getSession();
		System.out.println("Interceptor1 is calling..........." + session.toString());
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println("Interceptor 2 request is calling..........." + request.getContextPath());
		HttpSession httpSession = request.getSession(true);
		System.out.println("Interceptor 3 httpSession is calling..........." + httpSession.getCreationTime());
		System.out.println("Interceptor 4 httpSession is calling..........." + session.containsKey("loginid"));
		if(session.containsKey("loginid") ){
			System.out.println("UserId != null" + userId);
			httpSession.removeAttribute("reqUrl");
			Action action = (Action)actionInvocation.getAction();
			return actionInvocation.invoke();
		}
		
		else{
		userId = session.get("loginid").toString();
		System.out.println("In Interceptor UserId : " + userId);
		if(userId == null){
			System.out.println("UserId == null" + userId);
			httpSession.setAttribute("reqUrl", request.getRequestURL());
			return Action.LOGIN;
		}
		
	}*/

		
}
