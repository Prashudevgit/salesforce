package com.salesforce;

import java.io.File;
import java.io.FileWriter;
import org.json.simple.JSONObject;
import com.salesforce.security.AESEncrypter;

/*
This is a standalone Java file to write the application properties in encrypted manner
This will generate a CPBooksApplicationParameters.json file
To write data in the file, enter the fieldName and fieldValue as shown below
Then run the java file as standalone from eclipse Run or press Ctrl + F11
Once the java file is run a file called PPWalletApplicationParameters.json will be created in the tomcat.home path
Note: The development and production parameters will change for some settings such as Blockchain, Database etc
Hence be careful of the parameters

IMPORTANT: BEST TO REMOVE THIS FILE (JsonWrite.java) WHILE COMPILING PRODUCTION .war file. the .class file can be decompiled and all params can be read
*/
public class JsonWrite {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		File file = null;
		FileWriter filewriter = null;
		JSONObject obj = null;
		System.out.print(System.getProperty("java.home") + "\n"); // put it as catalina.home or the tomcat path

		try {
			// file = new File(StringUtils.replace(System.getProperty("java.home"), "\\",
			// "/")+"/PPWalletApplicationParameters.json");
			file = new File("H:/apache-tomcat-9.0.41/SalesForceApplicationParameters.json"); // TODO
			// file = new
			// File("D:/apache-tomcat-9.0.31/PPWalletApplicationParameters.json"); // for
			// temp test environment
			file.setWritable(true);
			file.setReadable(true);
			filewriter = new FileWriter(file);
			// filewriter = new
			// FileWriter("D:/apache-tomcat-9.0.21/PPWalletApplicationParameters.json");
			obj = new JSONObject();
			System.out.println("\n *** Now Writing the file at " + file.getAbsolutePath());
			obj.put("debugOn", setParam("true"));
			obj.put("LOCAL_DATEFORMAT", setParam("EAT"));

			obj.put("ERROR_LOG_WIN", setParam("/apps/salesforce/logs/salesforceerror.log"));
			obj.put("ERROR_LOG_LIN", setParam("/apps/salesforce/logs/salesforceerror.log"));

			obj.put("UPLOAD_PATH_WIN", setParam("H:/salesforce/fileupload"));                                          //change *********************************
			obj.put("UPLOAD_PATH_LIN", setParam("/home/benji/salesforce/fileupload"));
		
			obj.put("DOWNLOAD_PATH_WIN", setParam("H:/ppwallet/fileupload"));                                         //change *********************************
			obj.put("DOWNLOAD_PATH_LIN", setParam("/home/benji/salesforce/download"));
			obj.put("NOTI_LOGO_PATH_WIN", setParam("H:/salesforce/logo"));
			obj.put("NOTI_LOGO_PATH_LIN", setParam("/home/benji/salesforce/logo"));
		   //
			obj.put("CODE_NOTI_LOGO_NAME", setParam("noti-logo.png"));

			obj.put("DBUSER", setParam("benji"));
			obj.put("DBPWD", setParam("kelong98"));  //SERVER
			//obj.put("DBPWD", setParam("vcuser123"));      // LOCALHOST
			
			obj.put("stmpuserid", setParam("benjaminkelong98@gmail.com"));
			obj.put("smtppwd", setParam("kelong98"));
			obj.put("smtphost", setParam("smtp.gmail.com"));
			obj.put("smtptlsport", setParam("587"));
			obj.put("smtpsslport", setParam("465"));
			obj.put("smtpsendfromemail", setParam("benjaminkelong98@gmail.com"));
			// obj.put("clientsocketsimulation", setParam("true"));
			obj.put("APIKEYPUB", setParam("39EB1CRWUMQFN91Z9BE0U42Y"));
			obj.put("APIKEYPVT", setParam("6y8dU9av6"));
	
			obj.put("debugOn", setParam("true"));
			obj.put("clientIP", setParam("localhost"));
			obj.put("DB_URL", setParam( "jdbc:mysql://localhost:3306/salesforce?characterEncoding=UTF-8&verifyServerCertificate=false&useSSL=true&requireSSL=false&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull"));
			obj.put("MYSQL_DRIVER", setParam("com.mysql.cj.jdbc.Driver"));
			obj.put("ORACLE_DRIVER", setParam("oracle.jdbc.OracleDriver"));
			obj.put("POSTGRESQL_DRIVER", setParam("org.postgresql.Driver"));
			obj.put("SERVLET_PATH", setParam("ws"));
			obj.put("JSON_SERVLET_PATH", setParam("json"));
			obj.put("MULTIPARTSERVLET_PATH", setParam("ms"));
			obj.put("EMAIL_THREADS_COUNT", setParam("5"));
		
		
			// MPESA
			obj.put("MPESA_APP_KEY", setParam("ziGeTRfD2jIfOQuSnUNujWoYiSMaMJsu"));
			obj.put("MPESA_APP_SECRET", setParam("s0dBpHdDnSgFZXUz"));
			obj.put("MPESA_TOKEN_URL", setParam("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials"));  // https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest
			obj.put("MPESA_LIPA_NA_MPESA_PASS_KEY", setParam("bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"));
			obj.put("MPESA_BUSINESS_SHORT_CODE", setParam("174379"));//
			obj.put("MPESA_TRANSACTION_TYPE", setParam("CustomerPayBillOnline"));
			obj.put("MPESA_PARTY_B", setParam("174379"));//
			obj.put("MPESA_CALLBACK_URL", setParam("https://horizonedge.tech/hooks/mpesa"));//
			obj.put("MPESA_ACCOUNT_REFERENCE", setParam("Notipay"));//
			obj.put("MPESA_TRANSACTION_DESC", setParam("test"));
			obj.put("MPESA_PASS_KEY", setParam("bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"));
			obj.put("MPESA_STK_PUSH_URL", setParam("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"));
			obj.put("WEBSOCKET_URL", setParam("wss://horizonedge.tech/mpesa/hook"));
			obj.put("LOGIN_PAGE", setParam("/login.jsp"));
			obj.put("OTP_EXPIRY_TIME", setParam("3"));
			obj.put("ERROR_PAGE", setParam("/error.jsp"));
			obj.put("LOGIN_PAGE", setParam("/login.jsp"));
			obj.put("DASHBOARD_PAGE", setParam("/dasboard.jsp"));


								
			filewriter.write(obj.toJSONString());
			filewriter.flush();
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		} finally {

			if (obj != null)
				obj = null;
			if (file != null)
				file = null;
			if (filewriter != null)
				filewriter = null;
		}
	}

	private static String setParam(String param) throws Exception {
		return AESEncrypter.encryptJson(param);
	}

}
