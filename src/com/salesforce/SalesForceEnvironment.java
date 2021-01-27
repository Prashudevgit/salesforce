package com.salesforce;

import java.io.FileReader;


import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;

import com.salesforce.security.AESEncrypter;
import com.salesforce.utilities.Utilities;
//comms
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.json.simple.parser.JSONParser;


public class SalesForceEnvironment {
	private static String className = SalesForceEnvironment.class.getSimpleName();
	//private static ResourceBundle RB_LOCALE = null;
	private static JSONObject JSON_LOCALE = null;
    private static  String KEYVALUE = null;
    private static  String FILE_UPLOAD_PATH = null; 
  	private static  String FILE_DOWNLOAD_PATH = null; 
  	private static  String PERSO_UPLOAD_PATH = null; 
  	private static  String LOGBACK_CONFIG_FILE_PATH = null; 
    private static  String FILE_NOTI_LOGO_PATH = null; 
    private static  String BIOFR_URL = null;
    // goo

  	//private static  String ISO8583_VM = null;
  	
    private static String OS = System.getProperty("os.name").toLowerCase();    
    private static boolean debugOn = false;									    
    private static  Logger logger = null;

	public static  String getFileUploadPath() throws Exception{ 			return FILE_UPLOAD_PATH; }													
	public static  String getFileDownloadPath() throws Exception{ 			return FILE_DOWNLOAD_PATH; }
	public static  String getNotiLogoPath() throws Exception{ 		      	return FILE_NOTI_LOGO_PATH; }
	public static  String getBioFrUrl() throws Exception{ 			        return BIOFR_URL; }	
	public static  String getPersoFileUploadPath() throws Exception{ 		return PERSO_UPLOAD_PATH; }	
	public static  String getDBUser() throws Exception{ 					return getParameters("DBUSER"); }														
	public static  String getDBPwd() throws Exception{ 					    return getParameters("DBPWD"); }
	public static  String getMYSQLDriver() throws Exception{ 				return getParameters("MYSQL_DRIVER"); }						
	public static  String getPostGreSQLDriver() throws Exception{ 			return getParameters("POSTGRESQL_DRIVER"); }
	public static  String getKeyValue() throws Exception{					 	return KEYVALUE; }															
	public static  String getDBURL() throws Exception{ 					    return getParameters("DB_URL"); }
	//Bio Rules
	public static  String getBioFrAction() throws Exception{ 			        return getParameters("BIOFR_ACTION"); }		
	public static  String getBioFrRegRule() throws Exception{ 			        return getParameters("BIOFR_REG_RULE"); }		
	public static  String getBioFrAuthRule() throws Exception{ 			        return getParameters("BIOFR_AUTH_RULE"); }		

	public static  String getLocalDateFormat() throws Exception{ 			return getParameters("LOCAL_DATEFORMAT"); }	


    public  static  String getServletPath() throws Exception{     			return getParameters("SERVLET_PATH"); }	
    public  static  String getMutipartServletPath() throws Exception{     	return getParameters("MULTIPARTSERVLET_PATH"); }
    public  static  String getJSONServletPath() throws Exception{     		return getParameters("JSON_SERVLET_PATH"); }
    //wbs
  
	// Mpesa Settings
	public static  String getMpesaAppKey() throws Exception{ 				return getParameters("MPESA_APP_KEY"); }						
	public static  String getMpesaAppSecret() throws Exception{ 				return getParameters("MPESA_APP_SECRET"); }						
	public static  String getMpesaTokenURL() throws Exception{ 					return getParameters("MPESA_TOKEN_URL"); }	
	public static  String getMpesaLipaNaMpesaPassKey() throws Exception{ 					return getParameters("MPESA_LIPA_NA_MPESA_PASS_KEY"); }	
	public static  String getMpesaBusinessShortCode() throws Exception{ 					return getParameters("MPESA_BUSINESS_SHORT_CODE"); }	
	public static  String getMpesaTransactionType() throws Exception{ 					return getParameters("MPESA_TRANSACTION_TYPE"); }	
	public static  String getMpesaPartyB() throws Exception{ 					return getParameters("MPESA_PARTY_B"); }	
	public static  String getMpesaCallBackURL() throws Exception{ 					return getParameters("MPESA_CALLBACK_URL"); }	
	public static  String getMpesaAccountReference() throws Exception{ 					return getParameters("MPESA_ACCOUNT_REFERENCE"); }	
	public static  String getMpesaTransactionDescription() throws Exception{ 					return getParameters("MPESA_TRANSACTION_DESC"); }	
	public static  String getMpesaPassKey() throws Exception{ 					return getParameters("MPESA_PASS_KEY"); }	
	public static  String getMpesaSTKPushURL() throws Exception{ 					return getParameters("MPESA_STK_PUSH_URL"); }	
	public static  String getMpesatTRANS_TYPE() throws Exception{ 					return getParameters("TRANS_TYPE"); }	
	public static  String getWebsocketURL() throws Exception{ 					return getParameters("WEBSOCKET_URL"); }	

	public static  String getAPIKeyPublic() throws Exception{ 				return getParameters("APIKEYPUB"); }	
	public static  String getAPIKeyPrivate() throws Exception{ 			return getParameters("APIKEYPVT"); }

	
	// Email Settings
		public static  String getSMTPUserId() throws Exception{ 				return getParameters("stmpuserid"); }						
		public static  String getSMTPUserPwd() throws Exception{ 				return getParameters("smtppwd"); }						
		public static  String getSMTPHost() throws Exception{ 					return getParameters("smtphost"); }			
		public static  String getSMTPTLSPort() throws Exception{ 				return getParameters("smtptlsport"); }
		public static  String getSMTPSSLPort() throws Exception{ 				return getParameters("smtpsslport"); }				
		public static  String getSendFromEmailId() throws Exception{ 			return getParameters("smtpsendfromemail"); }						
		public static  String getEmailThreadCount() throws Exception{ 			return getParameters("EMAIL_THREADS_COUNT"); }
		public static String getOTPExpiryTime() throws Exception {	return getParameters("OTP_EXPIRY_TIME");	}
		public static  String getErrorPage() throws Exception{     				return getParameters("ERROR_PAGE"); }
		public static  String getLoginPage() throws Exception{     				return getParameters("LOGIN_PAGE"); }
		public static String getDashBoardPage()throws Exception {                        		return getParameters("DASHBOARD_PAGE");}
	

		
    public static synchronized JSONObject getInstance(){ 
    	// calling from multiple EventListerers and Servlets to check whether the environment is formed.
    	return JSON_LOCALE; 
    	}
    public static synchronized void setInstance(JSONObject JLocale){ 
    	// calling from multiple EventListerers and Servlets to check whether the environment is formed.
    	JSON_LOCALE = JLocale;
    	}
    

   public static synchronized void init() throws Exception {
    	try {
    		 JSONParser parser = new JSONParser();
    		if(JSON_LOCALE==null) {
    		       //Properties jvm = System.getProperties();
    		       // jvm.list(System.out);
	 					System.out.print("\n *** Now catalina.home is**** "+System.getProperty("catalina.home")+"\n");
	 					try {
	 			         System.out.println("\n *** Now Reading Properties file**** ");	 			         	 			         
	 			         Object objRead = parser.parse(new FileReader(StringUtils.replace(System.getProperty("catalina.home"), "\\", "/")+"/SalesForceApplicationParameters.json"));
	 			        //Object objRead = parser.parse(new FileReader("D:/apache-tomcat-9.0.7-2/CPBooksApplicationParameters.json"));
	 			         JSON_LOCALE = (JSONObject) objRead;
	 			        //System.out.println("\n "+JSON_LOCALE.toString());
		 				} catch (Exception e) {
		 					System.out.println(className+ "  Exception in reading Resourcebundle "+e.getMessage());
		 				}
			     			debugOn = Boolean.parseBoolean(StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("debugOn").toString()))); 
		     			if(isWindows()){
		     				//FILE_UPLOAD_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("UPLOAD_PATH_WIN").toString()));
		     				FILE_UPLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("UPLOAD_PATH_WIN").toString()));
		     				FILE_NOTI_LOGO_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("NOTI_LOGO_PATH_WIN").toString()));
		     				FILE_DOWNLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_WIN").toString()));
		     				BIOFR_URL = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("BIOFR_URL_WIN").toString()));
		     				PERSO_UPLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("PERSO_UPLOAD_PATH_WIN").toString()));
		     				LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") +	StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("ERROR_LOG_WIN").toString()));

		     				//FILE_DOWNLOAD_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_WIN").toString()));
		     				//FILE_DOWNLOAD_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_WIN").toString()));
		     				//FILE_DOWNLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_WIN").toString()));

		     				//LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") +	StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("ERROR_LOG_WIN").toString()));
		     			}else if(isUnix()){
		     				FILE_UPLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("UPLOAD_PATH_LIN").toString()));
		     				FILE_DOWNLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_LIN").toString()));
		     				FILE_NOTI_LOGO_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("NOTI_LOGO_PATH_LIN").toString()));
		     				BIOFR_URL = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("BIOFR_URL_LIN").toString()));
		     				PERSO_UPLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("PERSO_UPLOAD_PATH_LIN").toString()));
		     	 			LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("ERROR_LOG_LIN").toString()));

		     				//FILE_UPLOAD_PATH = System.getProperty("catalina.home") +	StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("UPLOAD_PATH_LIN").toString()));
		     				//FILE_DOWNLOAD_PATH = System.getProperty("catalina.home") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_LIN").toString()));
		     	 			//LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("ERROR_LOG_LIN").toString()));
		     	 			//FILE_NOTI_LOGO_PATH = System.getProperty("catalina.home") +	StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("NOTI_LOGO_PATH_LIN").toString()));
		
		     			}else if(isSolaris()){
		     				FILE_UPLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("UPLOAD_PATH_LIN").toString()));
		     				FILE_DOWNLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_LIN").toString()));
		     				FILE_NOTI_LOGO_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("NOTI_LOGO_PATH_LIN").toString()));
		     				BIOFR_URL = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("BIOFR_URL_LIN").toString()));
		     				PERSO_UPLOAD_PATH = StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("PERSO_UPLOAD_PATH_LIN").toString()));
		     	 			LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("ERROR_LOG_LIN").toString()));


		     				//FILE_UPLOAD_PATH = System.getProperty("catalina.home") +	StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("UPLOAD_PATH_LIN").toString()));
		     				//FILE_DOWNLOAD_PATH = System.getProperty("catalina.home") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("DOWNLOAD_PATH_LIN").toString()));
		     	 			//LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("ERROR_LOG_LIN").toString()));
		     	 			//FILE_NOTI_LOGO_PATH = System.getProperty("catalina.home") +	StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get("NOTI_LOGO_PATH_LIN").toString()));

		     			}
		     				//**** Now forming the logger file
		     	 			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		     	 			PatternLayoutEncoder ple = new PatternLayoutEncoder();
		     	 			//ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
		     	 			ple.setPattern("%-12date{dd-MM-YYYY HH:mm:ss.SSS} - %msg%n");
		     	 			ple.setContext(lc);
		     	 			ple.start();
		     	 			FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		     	 			fileAppender.setFile(LOGBACK_CONFIG_FILE_PATH);
		     	 			fileAppender.setEncoder(ple);
		     	 			fileAppender.setContext(lc);
		     	 			fileAppender.start();
		
		     	 			logger = (Logger) LoggerFactory.getLogger("");
		     	 			logger.addAppender(fileAppender);
		     	 			logger.setLevel(Level.ALL);
		     	 			logger.setAdditive(false); 
					
		      			//if(DBPASS==null){ DBPASS= getDBpass1().trim(); }
		     	 			if(KEYVALUE==null){ KEYVALUE= getKey(); }
		 			
    		}else {
    			setComment(3, className, "Environment already formed...");
    		}
    		
    	}catch(Exception e) {
    		System.out.println(className+"  ==> Exception in the init() method -- > "+e.getMessage());
    	}
    	
    }

    public static void setComment(int level,String className, String msg) { 
		  try{
			  if(debugOn) {
				  switch(level) {
				  case 1:   logger.error("SEVERE: "+className+" --- "+msg);				  break;
				  case 2:   logger.debug("DEBUG: "+className+" --- "+msg);				  break;
				  case 3:   logger.info("INFO: "+className+" --- "+msg);				  break;
				  }
				  }else {
					  if(level==1)
						  logger.error("SEVERE: "+className+" --- "+msg);	
				  }
				  
	      	} catch (Exception e){
	      		e.printStackTrace();
	      	}
	}
   
	//private static String getDBpass1()  throws Exception{return Utilities.getPass(DBPASS1.trim());	}
 	public static boolean isWindows() { return (OS.indexOf("win") >= 0); }
 	public static boolean isMac() { return (OS.indexOf("mac") >= 0); 	}
 	public static boolean isUnix() { return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ); 	}
 	public static boolean isSolaris() { return (OS.indexOf("sunos") >= 0); 	}
	private static String getKey() throws Exception{return getKey_01().trim();}
	private static String getKey_01()  throws Exception{return   Utilities.getKey_02(StringUtils.reverse(StringUtils.substring(className, 0,4)));	}
	public static String tempKey() throws Exception {return getKey_01().trim();}
	private static synchronized String getParameters(String paramName) throws Exception{		return StringUtils.trim(AESEncrypter.decryptJson(JSON_LOCALE.get(paramName).toString()));	}
	

	
	

}
