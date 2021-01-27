package com.salesforce.rules;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.salesforce.SalesForceEnvironment;
import com.salesforce.dao.UserProfileDao;
import com.salesforce.model.User;
import com.salesforce.rules.UserProfileRulesImpl;

public class UserProfileRulesImpl implements Rules {
	private static String className = UserProfileRulesImpl.class.getSimpleName();
	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session =request.getSession(false);
		
		switch (rules) {
		
				case Rules.PROFILE_USER_DASHBOARD_PAGE:
					try {
					String userName=null; User user=null;
					 if(request.getParameter("username")!=null)	userName = request.getParameter("username").trim();
					 
					 user=(User)UserProfileDao.class.getConstructor().newInstance().getUserDetails(userName);
					 
					 	if(user!=null) {			
							session.setAttribute("SESS_USER", user);
						}else {
							SalesForceEnvironment.setComment(3, className, "Error in Login: Try again ");
							request.setAttribute("userbroadcastederror", "Error in Login: Try again");
							throw new Exception ("Error occured in login");	
						}
					 
						request.setAttribute("lastaction", "dash"); // going to dash
						request.setAttribute("lastrule", "Dashboard"); //  set the last rule for left menu selection
						response.setContentType("text/html");
						ctx.getRequestDispatcher(SalesForceEnvironment.getDashBoardPage()).forward(request, response); 
					 try {
						
					 }finally {
						 if (userName!=null)userName=null; if (user!=null) user=null;
					 }
					
					}catch (Exception e) {
						callException(request, response, ctx, session,e, e.getMessage());
					}
				break;
				
				
				
			case Rules.LOGOUT_MODULE:
				
				try {
					if(session.getAttribute("SESS_USER")!=null) 				session.invalidate();
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(SalesForceEnvironment.getLoginPage()).forward(request, response);
					} finally {
						// TODO: handle finally clause
					}
				} catch (Exception e) {
					callException(request, response, ctx, session,e, e.getMessage());
				}
			break;		
		}

	}

	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext context, JsonObject jsonObj) throws Exception {
		// TODO Auto-generated method stub
		
		switch ( rulesaction) {
		
			case Rules.JSON_LOGIN_VALIDATE:
				try {
					String userEmail=null; String password=null; String privateKey=null; boolean allow=true;
					PrintWriter jsonOutput = null; String message="";
					
					if(jsonObj.get("useremail")!=null) userEmail = jsonObj.get("useremail").toString().replaceAll("\"", "");
					if(jsonObj.get("password")!=null) password = jsonObj.get("password").toString().replaceAll("\"", "");
					if(jsonObj.get("pvtkey")!=null) privateKey = jsonObj.get("pvtkey").toString().replaceAll("\"", "");
					
					if(!privateKey.equals(SalesForceEnvironment.getAPIKeyPrivate())) {
		 				allow = false;
		 				SalesForceEnvironment.setComment(1, className, "Error from JSON_LOGIN_VALIDATE: pvt key is incorrect "+privateKey);
		 			}
					jsonOutput = response.getWriter();
		 			Gson gson = new Gson();
		 			JsonObject obj = new JsonObject(); //Json Object
					
		 			if( allow) {
		 			// Validate User Details
		 				message =(String)UserProfileDao.class.getConstructor().newInstance().validateUser(userEmail,password);
		 				
		 				if (message.equals("success")) {
		 					
		 					obj.add("error", gson.toJsonTree("false"));
		 					obj.add("useremail", gson.toJsonTree(userEmail));
		 					// TODO:- Update Audit Trail there was a successful login by User at what time 
		 				}else {
		 					
		 					obj.add("error", gson.toJsonTree("incorrect"));
		 				}
		 				
		 			}else {
		 				obj.add("error", gson.toJsonTree("true"));
		 			}
					try {
						jsonOutput.print(gson.toJson(obj));
						SalesForceEnvironment.setComment(3, className, " JSON_LOGIN_VALIDATE String is "+gson.toJson(obj));
						
					}finally {
						if(jsonOutput!=null) jsonOutput.close(); if (gson!=null) gson=null; if (password!=null) password=null; 
						if (privateKey!=null) privateKey=null; if (userEmail!=null) userEmail=null; if (message!=null) message=null; 
					}
				}catch (Exception e) {
					SalesForceEnvironment.setComment(1, className, "Error from JSON_LOGIN_VALIDATE "+e.getMessage());
				}
			break;
		
		
		
		}

	}

	@Override
	public void callException(HttpServletRequest request, HttpServletResponse response, ServletContext ctx,
			HttpSession session, Exception e, String msg) throws Exception {
		// TODO Auto-generated method stub
		try {
		if(session!=null) 	session.invalidate();
		SalesForceEnvironment.setComment(1, className, "Error is "+msg);
		request.setAttribute("errormsg", msg);
		response.setContentType("text/html");
		ctx.getRequestDispatcher(SalesForceEnvironment.getErrorPage()).forward(request, response);
		} catch (Exception e1) {
		SalesForceEnvironment.setComment(1, className, "Problem in forwarding to Error Page, error : "+e1.getMessage());
		}
	}

	@Override
	public void performUploadOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			List<FileItem> multiparts, ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void performExternalOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext context, JsonObject jsonObj) throws Exception {
		// TODO Auto-generated method stub

	}

}
