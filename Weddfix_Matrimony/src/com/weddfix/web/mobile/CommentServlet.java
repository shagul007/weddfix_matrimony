package com.weddfix.web.mobile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weddfix.web.formbean.CommentsFormBean;
import com.weddfix.web.services.CommonMasterService;
import com.weddfix.web.util.MailMessage;

public class CommentServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CommentsFormBean commentsFormBean = new CommentsFormBean();
	CommonMasterService commonMasterService = new CommonMasterService();
	LinkedHashMap<String, Object> rootMap = new LinkedHashMap<String, Object>();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedHashMap<String, Object> main = new LinkedHashMap<String, Object>();
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
		String name = null;
		String email = null;
		String subject = null;
		String comment = null;
		
		if(request.getParameter("name") != null) {
			name = request.getParameter("name").toString();
		}
		
		if(request.getParameter("email") != null) {
			email = request.getParameter("email").toString();
		}
		
		if(request.getParameter("subject") != null) {
			subject = request.getParameter("subject").toString();
		}
		
		if(request.getParameter("comment") != null) {
			comment = request.getParameter("comment").toString();
		}
		
		String save = request.getParameter("save");
		
		if(save != null) {
			if(save.equals("comment") && name != null && email != null && subject != null && comment != null ) {
		
				commentsFormBean.setName(name);
				commentsFormBean.setEmail(email);
				commentsFormBean.setSubject(subject);
				commentsFormBean.setComment(comment);
				
		Long status = commonMasterService
				.saveCommentDetails(commentsFormBean);
		
		Properties props = new Properties();
		props.put("subject", commentsFormBean.getSubject());
		props.put("fullName", commentsFormBean.getName());
		props.put("email", commentsFormBean.getEmail());
		props.put("comments", commentsFormBean.getComment());
		MailMessage msg = new MailMessage(props, "feedback.vm",
				rb.getString("contact.admin.email"), "Feedback of Weddfix Matrimony");
		try {
			msg.send();
		} catch (Exception e) {
		
		}
		
		if (status != null) {
			rootMap.put("status", "success");
			rootMap.put("message", "Comment Sent Successfully.");
			main.put("comment", rootMap);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
		} else {
			rootMap.put("status", "failure");
			rootMap.put("message", "Something went wrong. Please try again later.");
			main.put("comment", rootMap);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
		}
			}
		} else {
			rootMap.put("status", "failure");
			rootMap.put("message", "Missing Parameters.");
			main.put("comment", rootMap);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(main);
            
            response.setContentType("application/json;charset=utf-8");
            byte[] out = json.getBytes("UTF-8");
            response.setContentLength(out.length);
            response.getOutputStream().write(out);
			return;
		}
	}
}
	
