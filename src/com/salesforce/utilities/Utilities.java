package com.salesforce.utilities;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.salesforce.SalesForceEnvironment;

import com.salesforce.security.AESEncrypter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class Utilities {
	private static String className = Utilities.class.getSimpleName();
	private  final static byte[] ASCII2EBCDIC = new byte[] { (byte) 0x00, (byte) 0x01,
	(byte) 0x02, (byte) 0x03, (byte) 0x37, (byte) 0x2D, (byte) 0x2E,(byte) 0x2F, (byte) 0x16, (byte) 0x05, (byte) 0x25, (byte) 0x0B,(byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F, (byte) 0x10,(byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x3C, (byte) 0x3D,
	(byte) 0x32, (byte) 0x26, (byte) 0x18, (byte) 0x19, (byte) 0x3F,(byte) 0x27, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,(byte) 0x40, (byte) 0x5A, (byte) 0x7F, (byte) 0x7B, (byte) 0x5B,(byte) 0x6C, (byte) 0x50, (byte) 0x7D, (byte) 0x4D, (byte) 0x5D,
	(byte) 0x5C, (byte) 0x4E, (byte) 0x6B, (byte) 0x60, (byte) 0x4B,(byte) 0x61, (byte) 0xF0, (byte) 0xF1, (byte) 0xF2, (byte) 0xF3,(byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8,(byte) 0xF9, (byte) 0x7A, (byte) 0x5E, (byte) 0x4C, (byte) 0x7E,
	(byte) 0x6E, (byte) 0x6F, (byte) 0x7C, (byte) 0xC1, (byte) 0xC2,(byte) 0xC3, (byte) 0xC4, (byte) 0xC5, (byte) 0xC6, (byte) 0xC7,(byte) 0xC8, (byte) 0xC9, (byte) 0xD1, (byte) 0xD2, (byte) 0xD3,(byte) 0xD4, (byte) 0xD5, (byte) 0xD6, (byte) 0xD7, (byte) 0xD8,
	(byte) 0xD9, (byte) 0xE2, (byte) 0xE3, (byte) 0xE4, (byte) 0xE5,(byte) 0xE6, (byte) 0xE7, (byte) 0xE8, (byte) 0xE9, (byte) 0xBA,(byte) 0xE0, (byte) 0xBB, (byte) 0xB0, (byte) 0x6D, (byte) 0x79,(byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85,
	(byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x91,(byte) 0x92, (byte) 0x93, (byte) 0x94, (byte) 0x95, (byte) 0x96,(byte) 0x97, (byte) 0x98, (byte) 0x99, (byte) 0xA2, (byte) 0xA3,(byte) 0xA4, (byte) 0xA5, (byte) 0xA6, (byte) 0xA7, (byte) 0xA8,
	(byte) 0xA9, (byte) 0xC0, (byte) 0x4F, (byte) 0xD0, (byte) 0xA1,(byte) 0x07, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,
	(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F,(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x41, (byte) 0xAA,
	(byte) 0x4A, (byte) 0xB1, (byte) 0x9F, (byte) 0xB2, (byte) 0x6A,(byte) 0xB5, (byte) 0xBD, (byte) 0xB4, (byte) 0x9A, (byte) 0x8A,(byte) 0x5F, (byte) 0xCA, (byte) 0xAF, (byte) 0xBC, (byte) 0x90,(byte) 0x8F, (byte) 0xEA, (byte) 0xFA, (byte) 0xBE, (byte) 0xA0,
	(byte) 0xB6, (byte) 0xB3, (byte) 0x9D, (byte) 0xDA, (byte) 0x9B,(byte) 0x8B, (byte) 0xB7, (byte) 0xB8, (byte) 0xB9, (byte) 0xAB,(byte) 0x64, (byte) 0x65, (byte) 0x62, (byte) 0x66, (byte) 0x63,(byte) 0x67, (byte) 0x9E, (byte) 0x68, (byte) 0x74, (byte) 0x71,
	(byte) 0x72, (byte) 0x73, (byte) 0x78, (byte) 0x75, (byte) 0x76,(byte) 0x77, (byte) 0xAC, (byte) 0x69, (byte) 0xED, (byte) 0xEE,(byte) 0xEB, (byte) 0xEF, (byte) 0xEC, (byte) 0xBF, (byte) 0x80,(byte) 0xFD, (byte) 0xFE, (byte) 0xFB, (byte) 0xFC, (byte) 0xAD,
	(byte) 0xAE, (byte) 0x59, (byte) 0x44, (byte) 0x45, (byte) 0x42,(byte) 0x46, (byte) 0x43, (byte) 0x47, (byte) 0x9C, (byte) 0x48,(byte) 0x54, (byte) 0x51, (byte) 0x52, (byte) 0x53, (byte) 0x58,(byte) 0x55, (byte) 0x56, (byte) 0x57, (byte) 0x8C, (byte) 0x49,
	(byte) 0xCD, (byte) 0xCE, (byte) 0xCB, (byte) 0xCF, (byte) 0xCC,(byte) 0xE1, (byte) 0x70, (byte) 0xDD, (byte) 0xDE, (byte) 0xDB,(byte) 0xDC, (byte) 0x8D, (byte) 0x8E, (byte) 0xDF };

	
	static final String RNDSTRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();
	private static final String NUMBERS = "0123456789";
	private static final String UPPER_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
	private static final String SPECIALCHARACTERS = "@#$%&*";
	private static final int MINLENGTHOFPASSWORD = 8;

 public static String replaceString(String text, String find, String replace) {
     int findLength = find.length();
     StringBuffer buffer = new StringBuffer();
     int i;
     for (i = 0; i < text.length() - find.length() + 1; i++) {
         String substring = text.substring(i, i + findLength);
         if (substring.equals(find)) {
             buffer.append(replace);
             i += find.length() - 1;
         } else {
             buffer.append(text.charAt(i));
         }
     }
     buffer.append(text.substring(text.length() - (text.length() - i)));
     return buffer.toString();
 }
//go
 /**
  * Returns any trailing . , ; : characters on the given string
  * @param text
  * @return empty string if none are found
  */
 public static String extractTrailingPunctuation(String text) {
     StringBuffer buffer = new StringBuffer();
     for (int i = text.length() - 1; i >= 0; i--) {
         char c = text.charAt(i);
         if (c == '.' || c == ';' || c == ',' || c == ':') {
             buffer.append(c);
         } else {
             break;
         }
     }//
     if (buffer.length() == 0) return "";
     buffer = buffer.reverse();
     return buffer.toString();
 }

 	public static String  encryptJSString(String string){
 	//string=replaceIgnoreCase(string," ","%20");
 	string=replaceString(string,"&","%26");
 	string=replaceString(string,"+","%2B");
 	string=replaceString(string,"?","%3F");
 	string=replaceString(string,"\"","%34");
 	string=replaceString(string,"'","%39");

 	return string;
 	}//
 	public static String  decryptJSString(String string){
 	//string=replaceIgnoreCase(string,"%20"," ");
 	string=replaceString(string,"%26","&");
 	string=replaceString(string,"%2B","+");
 	string=replaceString(string,"%3F","?");
 	string=replaceString(string,"%34","\"");
 	string=replaceString(string,"%39","\\'");
 	return string;
 	}

    	public static String  sqlEncode(String string){
     	string=replaceString(string,"'","/'");
     	string=replaceString(string,"//","///");
     	return string;
     	}
    	
       public static String getMYSQLCurrentTimeStampForInsert() throws Exception{
          	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
          	 java.util.Date date = new Date();      	             	        
          	return formatter1.format(date);
          	}
    	public static String getMySQLDateTimeConvertor(String datetimestring) throws Exception{
    	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
    	 java.util.Date date = formatter1.parse(datetimestring);      	        
    	 SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMM-yyyy HH:mm:ss");      	        
    	return formatter2.format(date);

    	}
    	
    	public static String getMySQLDateTimeConvertorWithoutSeconds(String datetimestring) throws Exception{
         	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
         	 java.util.Date date = formatter1.parse(datetimestring);      	        
         	 SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMM-yyyy HH:mm");      	        
         	return formatter2.format(date);

         }
    	public static String getMYSQLCurrentTimeStampForOpsDashGraphs() throws Exception{
       	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");     	        
       	 java.util.Date date = new Date();      	             	        
       	return formatter1.format(date);
       	}
    	
 	public static String getUTCMySQLDateTime(String datetimestring) throws Exception{
       	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));  	        
       	 java.util.Date date = formatter1.parse(datetimestring);      	        
       	return formatter1.format(date);
       	}
 	
 	public static String getUTCMySQLDateTime(Date date) throws Exception{
      	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));  	        
      	return formatter1.format(date);
      	}


    	public static String getMySQLDateConvertor(String datestring) throws Exception{
    	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");    
    	 java.util.Date date = formatter1.parse(datestring);
    	 SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMM-yyyy");
    	 return formatter2.format(date);

    	}
    	public static String getMySQLDateConvertorForCardDoe(String datestring) throws Exception{
       	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");    
       	 java.util.Date date = formatter1.parse(datestring);
       	 SimpleDateFormat formatter2 = new SimpleDateFormat ("MM-yy");
       	 return formatter2.format(date);

       	}

  	public static String convertDatetoMySQLDateFormat(java.util.Date date) throws Exception{
       	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");    
       	 return formatter1.format(date);

       	}
  	public static String getMoneyinDecimalFormat(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("#,###,##0.00");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
        	}
  	public static String getMoneyinSimpleDecimalFormat(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("######0.00");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
        	}

  	public static String getMoneyinNoDecimalFormat(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("#,###,##0");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
        	}	
  	public static String getUTCtoISTDateTimeConvertor(String dateTimeInUTCFormat) throws Exception{

  		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

  		Date date = utcFormat.parse(dateTimeInUTCFormat);

  		DateFormat pstFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  		pstFormat.setTimeZone(TimeZone.getTimeZone("IST"));

  		return (pstFormat.format(date));

       	} 	
  	public static String getUTCtoYourTimeZoneConvertor(String dateTimeInUTCFormat, String yourTimeZone) throws Exception{

  		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

  		Date date = utcFormat.parse(dateTimeInUTCFormat);

  		DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		pstFormat.setTimeZone(TimeZone.getTimeZone(yourTimeZone));

  		return (pstFormat.format(date));

       	}  	
  	
  	public static String getUTCDateTimeConvertor(String datetimestring, String formatting) throws Exception{
			if(formatting.equals("")){
				formatting = "MMddHHmmss";
			}
			
      	 SimpleDateFormat formatter1 = new SimpleDateFormat (formatting);  // Assuming this is in UTC
      	 formatter1.setTimeZone(TimeZone.getTimeZone("IST"));
      	 java.util.Date date = formatter1.parse(datetimestring);  
      	 SimpleDateFormat formatter2 = new SimpleDateFormat (formatting); 
      	 formatter2.setTimeZone(TimeZone.getTimeZone("UTC"));
      	
      	return formatter2.format(date);

       	}
  	
  	public static String covertAnyDateFormatToMysqlDateFormat(String datestring, String userFormat, String mysqldateFormat, String systemTimeZone) throws Exception{
  		DateFormat inputFormat = null; 		// e.g. dd/MM/yyyy
  		Date date1 = null;	
  		DateFormat mysqlFormat = null;	// e.g. yyyy-MM-dd
  		String coversionFormat = null;
  		try {
		  		inputFormat = new SimpleDateFormat(userFormat);
		  		inputFormat.setTimeZone(TimeZone.getTimeZone(systemTimeZone));
		  		date1 = inputFormat.parse(datestring);
		  		mysqlFormat = new SimpleDateFormat(mysqldateFormat);
		  		mysqlFormat.setTimeZone(TimeZone.getTimeZone(systemTimeZone));
		  		coversionFormat =  mysqlFormat.format(date1);
		  }catch (Exception e) {
  			throw new Exception ("Exception is "+e.getMessage());
  		}
  		return coversionFormat;
       	} 	
  	
	public static String getPass(String trim) {	return  StringUtils.reverse(StringUtils.replace(AESEncrypter.unRavel(trim.trim()),"&",""));}
	public static  String getKey_02(String input_1) {return input_1+Integer.toString(10-1)+(AESEncrypter.getKey_03((StringUtils.reverse(StringUtils.substring(className, 0,4)))));}

	public static synchronized int getRandomNumber(int min, int max) throws Exception{
		return (ThreadLocalRandom.current().nextInt(min, max + 1));	
	}

	public static synchronized boolean isValidCreditCardNumber(String creditCardNumber) {
		boolean isValid = false;
		try {
			//String reversedNumber = new StringBuffer(creditCardNumber).reverse().toString();
			String reversedNumber = StringUtils.reverse(creditCardNumber);
			int mod10Count = 0;
			for (int i = 0; i < reversedNumber.length(); i++) {
				int augend = Integer.parseInt(String.valueOf(reversedNumber.charAt(i)));
				if (((i + 1) % 2) == 0) {
					String productString = String.valueOf(augend * 2);
					augend = 0;
					for (int j = 0; j < productString.length(); j++) {
						augend += Integer.parseInt(String.valueOf(productString.charAt(j)));
					}
				}
				mod10Count += augend;
			}
			if ((mod10Count % 10) == 0) {
				isValid = true;
			}
		} catch (NumberFormatException e) {
		}
		return isValid;
	}

   	/**
 	 * Applies the specified mask to the card number.
 	 *
 	 * @param cardNumber The card number in plain format
 	 * @param mask The number mask pattern. Use # to include a digit from the
 	 * card number at that position, use x to skip the digit at that position
 	 *
 	 * @return The masked card number
 	 *  for card number "1234123412341234" > 1234-xxxx-xxxx-xx34
 	 */
// 	public static String maskCardNumber(String cardNumber) {
// 	    // format the number	SYTXkq38KSVQS6rN6wzRFrLyeXSCs7XsHq2Aht9E1Po=
// 		String mask="##xx-xxxx-xxxx-xx##";
// 	    int index = 0;
// 	    StringBuilder maskedNumber = new StringBuilder();
// 	    for (int i = 0; i < mask.length(); i++) {
// 	        char c = mask.charAt(i);
// 	        if (c == '#') {
// 	            maskedNumber.append(cardNumber.charAt(index));
// 	            index++;
// 	        } else if (c == 'x') {
// 	            maskedNumber.append(c);
// 	            index++;
// 	        } else {
// 	            maskedNumber.append(c);
// 	        }
// 	    }
// 	    // return the masked number
// 	    return maskedNumber.toString();
// 	}

 	
 	public static String maskCardNumber(String cardNumber) {
 	// format the number SYTXkq38KSVQS6rN6wzRFrLyeXSCs7XsHq2Aht9E1Po=
 	//String mask="##xx-xxxx-xxxx-xx##";
 	String mask="#xxx-xxxx-xxxx-####";
	
	 	int index = 0;
	 	StringBuilder maskedNumber = new StringBuilder();
	 	for (int i = 0; i < mask.length(); i++) {
	 	char c = mask.charAt(i);
		 	if (c == '#') {
		    	maskedNumber.append(cardNumber.charAt(index));
		 	    index++;
		 	} else if (c == 'x') {
		 	maskedNumber.append(c);
		 	   index++;
		 	} else {
		 	maskedNumber.append(c);
		 	}
	 	}
 	// return the masked number
 	return maskedNumber.toString();
 	}
 	
 	
 	

 	/*
 	 * It is done in following steps:
    Convert String to char array
    Cast it to Integer
    Use Integer.toHexString() to convert it to Hex
 	 */   			
 	public static String asciiToHex(String asciiValue)
 	{
 	    char[] chars = asciiValue.toCharArray();
 	    StringBuffer hex = new StringBuffer();
 	    for (int i = 0; i < chars.length; i++)
 	    {
 	        hex.append(Integer.toHexString((int) chars[i]));
 	    }
 	    return hex.toString();
 	}
 	/*
 	 * It is done in following steps:
    Cut the Hex value in 2 chars groups
    Convert it to base 16 Integer using Integer.parseInt(hex, 16) and cast to char
    Append all chars in StringBuilder     	 */   			
 	public static String hexToASCII(String hexValue)
 	{
 	    StringBuilder output = new StringBuilder("");
 	    for (int i = 0; i < hexValue.length(); i += 2)
 	    {
 	        String str = hexValue.substring(i, i + 2);
 	        output.append((char) Integer.parseInt(str, 16));
 	    }
 	    return output.toString();
 	}
 	
 	public static String ebcdicToASCII(String edata) throws Exception{
 	    String ebcdic_encoding = "IBM-1047"; //Setting the encoding in which the source was encoded
 	    byte[] result = edata.getBytes(ebcdic_encoding); //Getting the raw bytes of the EBCDIC string by mentioning its encoding
 	    String output = asHex(result); //Converting the raw bytes into hexadecimal format
 	    byte[] b = new BigInteger(output, 16).toByteArray(); //Now its easy to convert it into another byte array (mentioning that this is of base16 since it is hexadecimal)
 	    String ascii = new String(b, "ISO-8859-1"); //Now convert the modified byte array to normal ASCII string using its encoding "ISO-8859-1"
 		return ascii;
 	}
 	public static String asciiToEBCDIC(String adata) throws Exception{
 	    String ascii_encoding = "ISO-8859-1";
 	    byte[] res = adata.getBytes(ascii_encoding);
 	    String out = asHex(res);
 	    byte[] bytebuff = new BigInteger(out, 16).toByteArray();
 	    String ebcdic = new String(bytebuff, "IBM-1047");
 	    return ebcdic;     		
 	}
	/** 
	 * This is used to convert ASCII to EBCDIC format 	
	 */
	public static String asciiToEBCDIC(byte[] a) {
		byte[] e = new byte[a.length];
		for (int i = 0; i < a.length; i++)
			e[i] = ASCII2EBCDIC[a[i] & 0xFF];
		return new String(e);
	}     	
 	
 	//This asHex method converts the given byte array to a String of Hexadecimal equivalent
 	public static String asHex(byte[] buf) {
 	    char[] HEX_CHARS = "0123456789abcdef".toCharArray();
 	    char[] chars = new char[2 * buf.length];
 	    for (int i = 0; i < buf.length; ++i) {
 	        chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
 	        chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
 	    }
 	    return new String(chars);
 	}
 	public static char[] hexStringToByte(String s) throws Exception {

		int i = s.length();
		int j = i / 2;
		char abyte0[] = new char[j];
		int k = 0;	
		int l = 0;
			for (k = 0; k < i; k += 2) {
				int byte0 = Integer.parseInt(s.substring(k, k + 2), 16);
				abyte0[l] = (char) byte0;
				l++;
			}
		
		return abyte0;

	}
 	public static String createSingleCard (String strbin) throws Exception{
		StringBuilder builder = null;
		int  totalcardlength = 16;
		Random random = new Random(System.currentTimeMillis());
	        try {
						builder = null;
							 int randomNumberLength = totalcardlength - (strbin.length() + 1);
							 builder = new StringBuilder(strbin);
								for (int i = 0; i < randomNumberLength; i++) {
									int digit = random.nextInt(10);
									builder.append(digit);
								}
							int checkDigit = getCheckDigit(builder.toString());
				            builder.append(checkDigit);
							 
	        } catch (Exception e) {
	            throw new Exception(" Method createSingleCard: Error in creating card number",e);
	        }

 		return builder.toString();
 	}
 	public static ArrayList<String> createMultipleCards (String strbin,String totalcards) throws Exception{
 		ArrayList<String> arrCards = null;
		StringBuilder builder = null;
		int  totalcardlength = 16;
		int totalcardsgenerated  = Integer.parseInt(totalcards);
		Random random = new Random(System.currentTimeMillis());
	        try {
	        	arrCards = new ArrayList<String>();
					for (int j=0;j<totalcardsgenerated;j++){
						builder = null;
							 int randomNumberLength = totalcardlength - (strbin.length() + 1);
							 builder = new StringBuilder(strbin);
								for (int i = 0; i < randomNumberLength; i++) {
									int digit = random.nextInt(10);
									builder.append(digit);
								}
							int checkDigit = getCheckDigit(builder.toString());
				            builder.append(checkDigit);
				            arrCards.add(builder.toString());
					}		 
	        } catch (Exception e) {
	            throw new Exception(" Method createMultipleCards:  Error in creating card number",e);
	        }

 		return arrCards;
 	}
	  	 private static int getCheckDigit(String number) {
      int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
 }

	  	public static void wipeString(String stringToWipe) throws Exception {
	  		try {
	  		Field stringValue = String.class.getDeclaredField("value");
	  		stringValue.setAccessible(true);
	  		Arrays.fill((char[]) stringValue.get(stringToWipe), '*');
	  		} catch (IllegalAccessException e) {
	  		throw new Exception("Can't wipe string data");
	  		}
	  	}
	  	public static String encryptString(String stringToEncrypt) throws Exception {
	  		return AESEncrypter.encrypt(stringToEncrypt);
	  	}
	  	public static String decryptString(String stringToDecrypt) throws Exception {
	  		return AESEncrypter.decrypt(stringToDecrypt);

	  	}
	  	public static synchronized String generateCVV2(int length) throws Exception {
	  		int randomNumberLength = length ;	Random random = null;	StringBuilder builder = null;
	  		random = new Random(System.currentTimeMillis());
			 builder = new StringBuilder("");
				try {
					for (int i = 0; i < randomNumberLength; i++) {
						int digit = random.nextInt(10);
						if(digit==0)
						builder.append(digit+1);
						else
							builder.append(digit);
					}
				} catch (Exception e) {
					throw new Exception("Can't generate CVV");
				}finally{
					random=null;
				}
	  		return builder.toString();  		
	  	}
	  	
		public static synchronized String generateBillerCode(int length) throws Exception {
	  		int randomNumberLength = length ;	Random random = null;	StringBuilder builder = null;
	  		random = new Random(System.currentTimeMillis());
			 builder = new StringBuilder("");
				try {
					for (int i = 0; i < randomNumberLength; i++) {
						int digit = random.nextInt(10);
						if(digit==0)
						builder.append(digit+1);
						else
							builder.append(digit);
					}
				} catch (Exception e) {
					throw new Exception("Can't generate BillerCode");
				}finally{
					random=null;
				}
	  		return builder.toString();  		
	  	}

	  	public static synchronized Vector<String> createCVV2(int totalcards, int length) throws Exception{
			Vector<String> vectoralCVV2 =new Vector<String>();	Random random = null;StringBuilder builder = null;
			random = new Random(System.currentTimeMillis());
			try{
			for(int count=0;count<totalcards;count++){
	   	  		int randomNumberLength = length ;
    	  		
				 builder = new StringBuilder("");						
						for (int i = 0; i < randomNumberLength; i++) {
							int digit = random.nextInt(10);
							if(digit==0)
							builder.append(digit+1);
							else
								builder.append(digit);
						}
						vectoralCVV2.add(builder.toString());
				}
			} catch (Exception e) {
				throw new Exception("Can't generate CVV");
			}
			return vectoralCVV2;
		}
	  	
	  	public static synchronized String genAlphaNumRandom(int len) throws Exception {
	  		StringBuilder sb = null;
				try {
					sb = new StringBuilder( len );
					for( int i = 0; i < len; i++ ) {
					      sb.append( RNDSTRING.charAt( rnd.nextInt(RNDSTRING.length()) ) );
					}

				} catch (Exception e) {
					throw new Exception("Can't generate Random Number");
				}
	  		return sb.toString();  		
	  	}	  	
	  	
	    public static int getBusDaysBetweenDuration(String startDateString, String endDateString) throws Exception{
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //String dateInString = "01-07-2016";
	        Date startDate = sdf.parse(startDateString);
	        //String dateInString2 = "31-07-2016";
	        Date endDate = sdf.parse(endDateString);
	       // calculateDuration(startDate,endDate);
	    	
	    	
	      Calendar startCal = Calendar.getInstance();
	      startCal.setTime(startDate);

	      Calendar endCal = Calendar.getInstance();
	      endCal.setTime(endDate);

	      int workDays = 0;

	      if (startCal.getTimeInMillis() > endCal.getTimeInMillis()){
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	      }

	      while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
	    	    if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
	    	        startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	    	        workDays++;
	    	    }
	    	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	    	}

	      return workDays;
	    }
/*
		public static String generateToken(String acode) throws Exception {
			return asciiToHex( StringUtils.reverse(encryptString(acode))).substring(0, 24);
			
			//return asciiToHex(AESEncrypter.encrypt(acode)).substring(0, 24);
		}
		*/
		
		
		public static String generateToken(String acode) throws Exception {
			//TODO Later put it into the Parameters json file.
				final String internalTokenBIN = "2123";
				final String externalTokenBIN = "2345";
				
				SimpleDateFormat formatter1 = new SimpleDateFormat ("yy");
				String finalToken = null;
				// Token is BIN+YY+Random(10)
				if(acode.startsWith("4", 0) || (acode.startsWith("5", 0))) {
					finalToken = externalTokenBIN + formatter1.format(new java.util.Date()) + RandomStringUtils.randomNumeric(10);
				}else {
					finalToken = internalTokenBIN + formatter1.format(new java.util.Date()) + RandomStringUtils.randomNumeric(10);
				}
					return finalToken;
			}


/*		To resize the image call this method from the class as follows:
 * 		File input = new File("/tmp/duke.png");
        BufferedImage image = ImageIO.read(input);
        BufferedImage resized = resize(image, 500, 500);
        File output = new File("/tmp/duke-resized-500x500.png");
        ImageIO.write(resized, "png", output);	
*/	  
		
		public static synchronized String getRandomPassword() {
		    StringBuilder password = new StringBuilder();
		    int j = 0;
		    for (int i = 0; i < MINLENGTHOFPASSWORD; i++) {
		    	//System.out.println("J is "+j);
		        password.append(getRandomPasswordCharacters(j));
		        j++;
		        
		        if (j == 4) {
		            j = 0;
		        }
		    }
		    return password.toString();
		}

		private static synchronized String getRandomPasswordCharacters(int pos) {
		    Random randomNum = new Random();
		    StringBuilder randomChar = new StringBuilder();
		    switch (pos) {
		        case 0:
		            randomChar.append(NUMBERS.charAt(randomNum.nextInt(NUMBERS.length() - 1)));
		            break;
		        case 1:
		            randomChar.append(UPPER_ALPHABETS.charAt(randomNum.nextInt(UPPER_ALPHABETS.length() - 1)));
		            break;
		        case 2:
		            randomChar.append(SPECIALCHARACTERS.charAt(randomNum.nextInt(SPECIALCHARACTERS.length() - 1)));
		            break;
		        case 3:
		            randomChar.append(LOWER_ALPHABETS.charAt(randomNum.nextInt(LOWER_ALPHABETS.length() - 1)));
		            break;
		    }
		    return randomChar.toString();

		}

		//New addition Ben
		//Convert dates from MMDDYYY to YYYYMMDD
		
		public static String formartDate(String mDate) {
			// TODO Auto-generated method stub
			
			SimpleDateFormat inSDF = new SimpleDateFormat("mm/dd/yyyy");
			  SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");

			 
			  String outDate = "";
			  
			    if (mDate != null) {
			        try {
			            Date date = inSDF.parse(mDate);
			            outDate = outSDF.format(date);
			            
			            
			        } catch (Exception  ex){ 
			        	ex.printStackTrace();
			        }
			    }
			    return outDate;
		}
	  	
		
		//New Addition
		public static String mobileformartDate(String mDate) {
			// TODO Auto-generated method stub
			SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outSDF = new SimpleDateFormat("dd-MM-yyyy");

			 String outDate = "";
			 if (mDate != null) {
			    try {
			        Date date = inSDF.parse(mDate);
			        outDate = outSDF.format(date);
			        } catch (Exception  ex){ 
			        	ex.printStackTrace();
			        }
			    }
			    return outDate;
		}
		
		//Date Calculation
	
		public static String getDateCalculate(String dateString, int days, String dateFormat) {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		    try {
		        cal.setTime(s.parse(dateString));
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		    cal.add(Calendar.DATE, days);
		    return s.format(cal.getTime());
		}
		
		public static String formartDateMpesa(String mDate) {
			// TODO Auto-generated method stub
			SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outSDF = new SimpleDateFormat("dd-MM-yyyy");

			 String outDate = "";
			 if (mDate != null) {
			    try {
			        Date date = inSDF.parse(mDate);
			        outDate = outSDF.format(date);
			        } catch (Exception  ex){ 
			        	ex.printStackTrace();
			        }
			    }
			    return outDate;
		}
		
		
		

		
		//New addition Ben
		//Convert dates from MMDDYYY to YYYYMMDD
		
		public static String formartDateforGraph(String mDate) {
			// TODO Auto-generated method stub
			
			SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");

			 
			  String outDate = "";
			  
			    if (mDate != null) {
			        try {
			            Date date = inSDF.parse(mDate);
			            outDate = outSDF.format(date);
			            
			            
			        } catch (Exception  ex){ 
			        	ex.printStackTrace();
			        }
			    }
			    return outDate;
		}
		
		public static String getCurrentDate() throws Exception{
	     	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");     	        
	     	 java.util.Date date = new Date();      	             	        
	     	return formatter1.format(date);
	     	}
		//ADDED
		 public static String generateOTP(int size) {

		        StringBuilder generatedToken = new StringBuilder();
		        try {
		            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
		            // Generate 20 integers 0..20
		            for (int i = 0; i < size; i++) {
		                generatedToken.append(number.nextInt(9));
		            }
		        } catch (NoSuchAlgorithmException e) {
		            e.printStackTrace();
		        }

		        return generatedToken.toString();
		 }
		 
		 public static String getMYSQLCurrentTimeStampForOTPValidTo() throws Exception{
	      	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
	      	 java.util.Date validFrom = new Date(); 
				int addMinuteTime = Integer.parseInt((SalesForceEnvironment.getOTPExpiryTime()));
				java.util.Date validTo =null;
				validTo = DateUtils.addMinutes(validFrom, addMinuteTime); 				

	      	return formatter1.format(validTo );
	      	}
			public static String formartDateYOB(String mDate) {
				// TODO Auto-generated method stub
				
				SimpleDateFormat inSDF = new SimpleDateFormat("dd/mm/yyyy");
				  SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");

				 
				  String outDate = "";
				  
				    if (mDate != null) {
				        try {
				            Date date = inSDF.parse(mDate);
				            outDate = outSDF.format(date);
				            
				            
				        } catch (Exception  ex){ 
				        	ex.printStackTrace();
				        }
				    }
				    return outDate;
			}
			
			  
			public static BufferedImage resize(BufferedImage img, int height, int width) throws Exception{
				BufferedImage resized = null;
				try{
					Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				
		         resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D g2d = resized.createGraphics();
		        g2d.drawImage(tmp, 0, 0, null);
		        g2d.dispose();
			}catch (Exception e) {
				throw new Exception (e.getMessage());
			}
		        return resized;
		    }
			
			public static BufferedImage resized(BufferedImage img, int scaledWidth, int scaledHeight)
		            throws Exception {

		        // creates output image
		        BufferedImage outputImage = new BufferedImage(scaledWidth,
		                scaledHeight, BufferedImage.TYPE_INT_ARGB);
				try {
					 Image tmp = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

					    Graphics2D g2d = outputImage.createGraphics();
				        g2d.drawImage(tmp, 0, 0, scaledWidth, scaledHeight, null);
				        g2d.dispose();
						//System.out.println("Good");

				}catch(Exception e) {
					//System.out.println("Exception is "+e.getMessage());
				}
		        return outputImage;
		    }
		  	
		
					// Get current date at midnight
			public static String getCurrentDateAtMidnight() throws Exception{
		     	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");     	        
		     	 java.util.Date date = new Date();      	             	        
		     	return formatter1.format(date);
		     	}

			@SuppressWarnings("unchecked")
		//	public static void jsonResponse(String message, int errorCode, String errorBoolean,	 HttpServletResponse response) throws Exception{
			public static void jsonResponse(String message, int errorCode,	 HttpServletResponse response) throws Exception{

				try {
					PrintWriter jsonOutput_1 = null; jsonOutput_1 = response.getWriter();  JSONObject jsonResponse = new JSONObject();
					
					//jsonResponse.put("error",errorBoolean );
					jsonResponse.put("code", errorCode);
					jsonResponse.put("message", message);
					try {
						jsonOutput_1.print(jsonResponse);
					}finally {
						if(jsonOutput_1!=null) {jsonOutput_1.flush(); jsonOutput_1.close();} 
						 if(message!=null)message = null; if(jsonResponse!=null) jsonResponse =null;
					}
				}catch (Exception e1) { 
					SalesForceEnvironment.setComment(1, className, "Problem in forwarding jsonresponse from method jsonResponse : "+e1.getMessage());
				}
			}
	@SuppressWarnings("unchecked")
			public static void minimumTxnAmountAttainedResponse(String minimumAmount, HttpServletResponse response) throws Exception{
				
				try {
					PrintWriter jsonOutput_1 = null; jsonOutput_1 = response.getWriter();  JSONObject jsonResponse = new JSONObject();
					//jsonResponse.put("error",errorBoolean );
					jsonResponse.put("error", "less");
					jsonResponse.put("minimumAmount", minimumAmount);
					try {
						jsonOutput_1.print(jsonResponse);
					}finally {
						if(jsonOutput_1!=null) {jsonOutput_1.flush(); jsonOutput_1.close();} 
						if(minimumAmount!=null)minimumAmount = null; if(jsonResponse!=null) jsonResponse =null;
					}
				}catch (Exception e1) { 
					SalesForceEnvironment.setComment(1, className, "Problem in forwarding jsonresponse from method jsonResponse : "+e1.getMessage());
				}
			}
			
			@SuppressWarnings("unchecked")
			public static void pinIncorrectResponse(String flag,int txnTries, HttpServletResponse response) throws Exception{
				
				try {
					PrintWriter jsonOutput_1 = null; jsonOutput_1 = response.getWriter();  JSONObject jsonResponse = new JSONObject();

					
					if (flag.equals("NB")) {
						//Account not blocked
					jsonResponse.put("error", "wrongpin");
					}else if (flag.equals("AB")){
						//Account blocked
						jsonResponse.put("error", "pinblocked");
					}
					jsonResponse.put("trials", txnTries);
					try {
						jsonOutput_1.print(jsonResponse);
					}finally {
						if(jsonOutput_1!=null) {jsonOutput_1.flush(); jsonOutput_1.close();} 
						if(txnTries!=0)txnTries=0; if(jsonResponse!=null) jsonResponse =null;
					}
				}catch (Exception e1) { 
					SalesForceEnvironment.setComment(1, className, "Problem in forwarding jsonresponse from method jsonResponse : "+e1.getMessage());
				}
			}
			
			private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			public static String randomAlphaNumeric(int count) {
			StringBuilder builder = new StringBuilder();

			while (count-- != 0) {

			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

			builder.append(ALPHA_NUMERIC_STRING.charAt(character));

			}

			return builder.toString();

		}
			//Genarate Transaction code fro user
			public static String generateTransactionCode(int count) {
				int count2 = 9;
				String txnCode = RandomStringUtils.randomAlphanumeric(count2).toUpperCase();

			return txnCode;

		}

			private static final String NUMERIC_STRING = "0123456789";
			public static String generatePIN(int count) {
			StringBuilder builder = new StringBuilder();

			while (count-- != 0) {

			int character = (int)(Math.random()*NUMERIC_STRING.length());

			builder.append(NUMERIC_STRING.charAt(character));

			}

			return builder.toString();

			}
			

			public static final String getMpesaToken() {
				String token = null;
				byte[] bytes;
				try {
				    String getTokenUrl = SalesForceEnvironment.getMpesaTokenURL();
					String appKeySecret = SalesForceEnvironment.getMpesaAppKey()+ ":" + SalesForceEnvironment.getMpesaAppSecret();

					bytes = appKeySecret.getBytes("ISO-8859-1");
					String encoded = Base64.getEncoder().encodeToString(bytes);
					HttpRequest request = HttpRequest.newBuilder().uri(URI.create(getTokenUrl))
							.header("Authorization", "Basic " + encoded).header("Accept", "application/json").build();
					HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
					
					JSONObject object = null;  JSONParser parser = new JSONParser();
					object = (JSONObject) parser.parse(response.body().toString());
					token= (String) object.get("access_token");
					return token;
				} catch (Exception e) {
					SalesForceEnvironment.setComment(1,className,"Exception in getMpesaToken is "+e.getMessage());
				}
				return token;
			}
			
			public static final String getMpesaToken2() throws Exception{
				String token = null;
				byte[] bytes=null;
				try {
				    String getTokenUrl =  SalesForceEnvironment.getMpesaTokenURL();
					String appKeySecret =SalesForceEnvironment.getMpesaAppKey()+ ":" + SalesForceEnvironment.getMpesaAppSecret();
					bytes = appKeySecret.getBytes("ISO-8859-1");
					String encoded = Base64.getEncoder().encodeToString(bytes);
					
					OkHttpClient client = new OkHttpClient().newBuilder() .build();
								//MediaType mediaType = MediaType.parse("application/json");
								Request request1 = new Request.Builder().url(getTokenUrl) 
										.addHeader("Authorization",  "Basic " + encoded  ).addHeader("Accept", "application/json").build();
								Response response1 = client.newCall(request1).execute();
								String jsonResponse = response1.body().string();
								JsonObject jsonObj = null;
								jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
								token =  jsonObj.get("access_token").toString().replaceAll("\"", ""); 
					try {
						
					}finally {
						if(client != null) { client.dispatcher().executorService().shutdown(); client.connectionPool().evictAll();}
						if(request1 !=null) request1 = null; 
						if(jsonObj !=null) jsonObj = null;
						if(encoded !=null) encoded = null; if(bytes !=null) bytes = null;
					}
				} catch (Exception e) {
					SalesForceEnvironment.setComment(1,className,"Exception in getMpesaToken is "+e.getMessage());
				}
				
				return token;
			}
			public static  String getMpesaToken3() throws Exception{
				String token = null;
				byte[] bytes;
				try {
					System.out.println("start");
				    String getTokenUrl = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";
				    System.out.println("step 1");
					String appKeySecret ="qeyGVOjoH4UAvmptXMYl0QDGiPFQKFkm"+ ":" + "rGGMBv5WATigpzWL";
					System.out.println("step 3");
					bytes = appKeySecret.getBytes("ISO-8859-1");
					String encoded = Base64.getEncoder().encodeToString(bytes);
					
					OkHttpClient client = new OkHttpClient().newBuilder()
							  .build();
								Request request1 = new Request.Builder().url(getTokenUrl)
										.addHeader("Authorization",  "Basic " + encoded  )
										.addHeader("Accept", "application/json").build();
					
								Response response1 = client.newCall(request1).execute();
								//System.out.println("Response is "+response1.body().string());
								String jsonResponse = response1.body().string();
								System.out.println("jsonResponse "+jsonResponse);
								
								JsonObject jsonObj = null;
								jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
								token =  jsonObj.get("access_token").toString().replaceAll("\"", ""); 
					System.out.println("token "+token);
					System.out.println("stop ");
				
				} catch (Exception e) {
					System.out.println("Exception in getMpesaToken is "+e.getMessage());
				}
				
				return token;
			}
			public static String mpesaLocalTimeStamp() throws Exception{
		  		return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
		      } 

			public static synchronized ArrayList<String> uploadFileSAN(String userReference, int count, ArrayList<String> arrFileEncoded, ArrayList<String> arrFileName, String userType) throws Exception{
				JSONParser jsonParser1 = null; 
				JSONObject jsonObject = null;
				JSONArray jsonArray = null;  
				String jsonMessage = null;
				CloseableHttpResponse urlresponse = null;  
				CloseableHttpClient httpClient = null;
				ArrayList<String> arrFilePath = null;
				String sbResponse = null;
				 List<NameValuePair> urlParameters  = null;
				 HttpPost post = null;
				
				try {

					SalesForceEnvironment.setComment(3, className,"INSIDE uploadFileSAN userType:" + userType);

					  post = new HttpPost(SalesForceEnvironment.getBioFrUrl());

				        // add request parameter, form parameters
				        urlParameters = new ArrayList<>();
				        urlParameters.add(new BasicNameValuePair("qs", "frg"));
				        urlParameters.add(new BasicNameValuePair("rules", "filesystemupload"));
				        urlParameters.add(new BasicNameValuePair("userref", userReference));
				        urlParameters.add(new BasicNameValuePair("usertype", userType ));
				        urlParameters.add(new BasicNameValuePair("count", String.valueOf(count)));
				        SalesForceEnvironment.setComment(2, className,"after setting url parameters  " + urlParameters);
					     
				        for(int j=0;j<count;j++) {
					        urlParameters.add(new BasicNameValuePair("filename"+(j+1), arrFileName.get(j)));
					        urlParameters.add(new BasicNameValuePair("file"+(j+1), arrFileEncoded.get(j)));
					        //PPWalletEnvironment.setComment(2, className,"file"+(j+1)+" "+ arrFileEncoded.get(j));
				        }
				        SalesForceEnvironment.setComment(2, className,"before post  " + urlParameters);

				        post.setEntity(new UrlEncodedFormEntity(urlParameters));
			        	//PPWalletEnvironment.setComment(3, className,"***** after POST urlParameters "+ urlParameters);

				          httpClient = HttpClients.createDefault();
				          SalesForceEnvironment.setComment(2, className,"after httpClient   " + httpClient);
				          urlresponse = httpClient.execute(post); 
				          SalesForceEnvironment.setComment(2, className,"after httpClient execute post   " + httpClient);

				        	sbResponse = EntityUtils.toString(urlresponse.getEntity());
				        	//jsonOutput_1 = response.getWriter();
				        	
						arrFilePath = new ArrayList<String>();
				          if(sbResponse != null) {
				        	  SalesForceEnvironment.setComment(3, className,"***** after receiving "+ sbResponse);

				        	  jsonParser1 = new JSONParser();
							  jsonObject = (JSONObject) jsonParser1.parse(sbResponse);
							  jsonMessage = (String) jsonObject.get("message");
							  
							  if(jsonMessage.equals("success")) {
								  jsonArray = (JSONArray) jsonObject.get("filepath");
								 int length = jsonArray.size();
								 for(int n = 0; n <length; n++) {
									 arrFilePath.add((String) jsonArray.get(n));
									 SalesForceEnvironment.setComment(2, className,"arrFilepath after extracting from  json  "+ n + " "+ arrFilePath.get(n));
								   }
							  }else  if(jsonMessage.equals("fail")) {
								  SalesForceEnvironment.setComment(2, className,"File system Failed  " );

							  }

				        	}else {
							       //PPWalletEnvironment.setComment(1, className,"***** Problem occurred in the File System ");
							       throw new Exception("****File System not responding*****");
				        	}
				        	
	                      try {
		                  	   if(arrFilePath != null) {
		                  		 SalesForceEnvironment.setComment(3, className,"arrFilePath is  " + arrFilePath.size() );
		                  	   }
							}catch(Exception e){
								SalesForceEnvironment.setComment(1, className, "Exception in File System : "+e.getMessage());
								throw new Exception("***File System not responding***");

							}finally {
								
							}	
					
				}catch(Exception e) {
					SalesForceEnvironment.setComment(1, className, "Exception in File System : "+e.getMessage());
					throw new Exception("***Error occured in fileupload SAN***");
					
				}finally {
					if(httpClient!=null) httpClient.close(); if(urlresponse!=null) urlresponse.close();
					 if(sbResponse !=null) sbResponse = null; if(jsonParser1 !=null) jsonParser1 = null;
					 if(jsonObject !=null) jsonObject = null; if(jsonArray !=null) jsonArray = null;
					 if(urlParameters != null) urlParameters = null; if(post != null) post.releaseConnection();
					
				}
				return arrFilePath;
	       	}
		public static void processMpesaTransactions(String message) {
			SalesForceEnvironment.setComment(3, className, "inside processMpesaTransaction ");
					String Amount = null;
					String MpesaReceiptNumber = null;
					String TransactionDate = null;
					String PhoneNumber = null;
					//String Balance = null;
					JSONParser parser = null; 
					JSONObject jsonObject = null;
					JSONObject jsonObject2 = null;
					JSONObject jsonObject3 = null;
					JSONObject jsonObject4 = null;
					String MerchantRequestID = null;
					String Item  = null;
					String ResultCode = null;
					String ResultDesc = null;
					String CallbackMetadata = null;
					//String CheckoutRequestID = null;
					JSONArray jsonArray  = null;
					ConcurrentHashMap<String , String> hashMpesaRes = null;
					boolean success = false;
					boolean success1 = false;
					boolean result=false;
					//User user = null;

					try {
					//read The json testObject
					  parser = new JSONParser();
					  jsonObject = (JSONObject) parser.parse(message);
					  String Body= (String) jsonObject.get("Body").toString();
					  jsonObject2 = (JSONObject) parser.parse(Body);
					  String stkCallback = jsonObject2.get("stkCallback").toString();
					   jsonObject3 = (JSONObject) parser.parse(stkCallback);
					   ResultCode = jsonObject3.get("ResultCode").toString();
					  
					  if(ResultCode.equals("0")) {
						  SalesForceEnvironment.setComment(3, className, "ResultCode is  "+ ResultCode);

						   MerchantRequestID = jsonObject3.get("MerchantRequestID").toString();
						  // CheckoutRequestID = jsonObject3.get("CheckoutRequestID").toString();
						   ResultDesc = jsonObject3.get("ResultDesc").toString();
						   CallbackMetadata = jsonObject3.get("CallbackMetadata").toString();
						   jsonObject4 = (JSONObject) parser.parse(CallbackMetadata);
						   Item = jsonObject4.get("Item").toString();
						  
						   jsonArray = (JSONArray) jsonObject4.get("Item");
						   hashMpesaRes = new ConcurrentHashMap<String, String>();
						  Iterator i = jsonArray.iterator();
							while (i.hasNext()) {
					            jsonObject = (JSONObject) i.next();
					            String Name =(String) jsonObject.get("Name");
					            Object Value = jsonObject.get("Value");
					            if(Value != null) { 
					            	hashMpesaRes.put(Name, Value.toString());
					            }
					          }
							Amount = hashMpesaRes.get("Amount");
							MpesaReceiptNumber = 	hashMpesaRes.get("MpesaReceiptNumber");
						//	Balance = 	hashMpesaRes.get("Balance");
							TransactionDate = 	hashMpesaRes.get("TransactionDate");
							PhoneNumber = 	hashMpesaRes.get("PhoneNumber");
							
							//call dao and store the transaction
							//success = (boolean)PaymentDao.class.getConstructor().newInstance().insertMpesaTransaction(MerchantRequestID, Amount, MpesaReceiptNumber, PhoneNumber, TransactionDate  );
							if (success) {
							// result= (boolean)PaymentDao.class.getConstructor().newInstance().mpesaCallBackConnector(MerchantRequestID,"S",ResultDesc ,TransactionDate );
							}

//							if(success) {
//								//upadate the payment table status
//								success1 = (boolean)PaymentDao.class.getConstructor().newInstance().updateMpesaPaymentDetails(MerchantRequestID, Amount, MpesaReceiptNumber, PhoneNumber, TransactionDate  );
//								 if(success1) {
//									 
//									 //get user datails
//										user=(User)PaymentDao.class.getConstructor().newInstance().getUserDetails(MerchantRequestID);
//									 
//									 //send message to customer to confirm the payment
//										if(user != null) {
//											 String[] contactNumber= new String[1];
//											 contactNumber[0]="+"+PhoneNumber; // add + to allow
//											 String customerMessage="Dear "+user.getUserName() +", your payment of KES "+Amount+ " for the traffic offence Reference ID : "+user.getOffenserefference() +" has been paid. To dispute this offense, ask the police officer to book a court date for you. Thank you"  ;
//											// ATSmsGateway.sendSMS(contactNumber, customerMessage);
//											PPWalletEnvironment.setComment(3, className, "Mpesa Transaction successfully Fetched and Message send successful to "+ contactNumber );
//
//										}else {
//											PPWalletEnvironment.setComment(1, className, "User Details not found when snding the message");
//											throw new Exception("User Details not found when snding the message");
//										}
//								 }else {
//										PPWalletEnvironment.setComment(1, className, "Error occured while updating the  Mpesa payment details");
//										throw new Exception("An Error occured");
//								 }
//		
//							}else {
//								PPWalletEnvironment.setComment(1, className, "An Error occured while saving the Mpesa Transaction");
//								throw new Exception("An Error occured");
//						 }

							SalesForceEnvironment.setComment(3, className, "Payment successfull     MerchantRequestID  "+ MerchantRequestID+"  Amount : "+Amount   +" MpesaReceiptNumber "+ MpesaReceiptNumber 
									+" TransactionDate "+  TransactionDate +" PhoneNumber "+  PhoneNumber);
					  }else {
						  SalesForceEnvironment.setComment(3, className, "ResultCode is  "+ ResultCode);
						   ResultDesc = jsonObject3.get("ResultDesc").toString();
						   MerchantRequestID = jsonObject3.get("MerchantRequestID").toString();
						   TransactionDate=Utilities.getMYSQLCurrentTimeStampForInsert();
						  // result= (boolean)PaymentDao.class.getConstructor().newInstance().mpesaCallBackConnector(MerchantRequestID,"F",ResultDesc ,TransactionDate);
						  // CheckoutRequestID = jsonObject3.get("CheckoutRequestID").toString();
						   SalesForceEnvironment.setComment(3, className, "Transaction Failed :MerchantRequestID  : "+ MerchantRequestID+"    ResultCode :    "+ResultCode   +" ResultDesc "+  ResultDesc);
					  }
					  
					}catch (Exception e) {
						SalesForceEnvironment.setComment(1, className, "Error from method processMpesaTransactions is  : "+e.getMessage());
					}finally {
						
						if(Amount != null) Amount = null;  if(MpesaReceiptNumber != null) MpesaReceiptNumber = null;  if(TransactionDate != null) TransactionDate = null;
						if(PhoneNumber != null) PhoneNumber = null;  if(parser != null) parser = null;  if(jsonObject != null) jsonObject = null;
						if(jsonObject2 != null) jsonObject2 = null;  if(jsonObject3 != null) jsonObject3 = null;  if(jsonObject4 != null) jsonObject4 = null;
						 if(MerchantRequestID != null) MerchantRequestID = null;  if(Item != null) Item = null;  if(ResultCode != null) ResultCode = null;
						 if(ResultDesc != null) ResultDesc = null;  if(CallbackMetadata != null) CallbackMetadata = null;  if(jsonArray != null) jsonArray = null;
						 if(hashMpesaRes != null) hashMpesaRes = null; 
						
					}
		
				}
		
		public static void jsonMobileResponse(String message, String error, HttpServletResponse response) throws Exception{
			try {
				PrintWriter jsonOutput_1 = null; jsonOutput_1 = response.getWriter(); JSONObject jsonResponse = null;
				ConcurrentHashMap<String,Object> responseDetails = new ConcurrentHashMap<String,Object>();
				responseDetails.put("message", message);
				responseDetails.put("error", error);
				jsonResponse = new JSONObject(responseDetails);
				try {
					jsonOutput_1.print(jsonResponse);
				}finally {
					if(jsonOutput_1!=null) {jsonOutput_1.flush(); jsonOutput_1.close();}
					if(message!=null)message = null; if(jsonResponse!=null) jsonResponse =null;
					if(responseDetails!=null)responseDetails=null;
				}
			}catch (Exception e1) {
				SalesForceEnvironment.setComment(1, className, "Error from method jsonMobileResponse : "+e1.getMessage());
			}
			}
		
		   public static String getFirstDayOfTheMonth() throws Exception{	
			   	LocalDate todaydate = LocalDate.now();
				return todaydate.withDayOfMonth(1).toString();
	          	}
	
}
