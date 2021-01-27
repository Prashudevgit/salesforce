package com.salesforce.rules;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import com.google.gson.JsonObject;


public interface Rules {
	public static final String DEFAULT_LOGIN_MODULE="defaultloginpage";	
	public static final String JSON_LOGIN_VALIDATE="jloginvalidate";
	public static final String LOGOUT_MODULE="lgtdefault";
	public static final String PASSWORD_RESET="pswdreset";
	public static final String PASSWORD_RESET_PAGE="pswdresetpage"; 
	public static final String JSON_PASSWORD_RESET="jsonpswdreset";
	public static final String PROFILE_USER_DASHBOARD_PAGE="Dashboard";


	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response,ServletContext ctx) throws Exception;
	
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,ServletContext context, JsonObject jsonObj) throws Exception;;
	
	public void callException(HttpServletRequest request, HttpServletResponse response,ServletContext ctx,HttpSession session, Exception e, String msg) throws Exception;;
	
	public void performUploadOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,List<FileItem> multiparts, ServletContext ctx) throws Exception;
	public void performExternalOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,ServletContext context, JsonObject jsonObj) throws Exception;


	public static void main(String[] args) {
		
	};
}
