package com.salesforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import com.salesforce.SalesForceEnvironment;
import com.salesforce.model.User;
import com.salesforce.utilities.Utilities;

public class UserProfileDao extends HandleConnections{

	private static final long serialVersionUID = 1L;
	private static String className = UserProfileDao.class.getSimpleName(); 	
	public String validateUser(String userEmail, String password) throws Exception{
		// TODO Auto-generated method stub
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		 String message=null;
		 String tempUserName="";
		 String tempUserPwd="";
		try{
			connection = super.getConnection();	
			
			query = "select user_email, userpassword from user_details where user_email=?";	
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userEmail);
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){ 
				 	while(rs.next()){
				 		tempUserName =  StringUtils.trim(rs.getString("user_name"));
				 		tempUserPwd =  StringUtils.trim(rs.getString("userpassword"));
				 	} // end of while
				 } 
			 if(tempUserName!=null &&tempUserName.equals(userEmail)) {
									 
					 if(tempUserPwd.equals(Utilities.encryptString(password))==false) {
						 
						 message = "Password is incorrect";
						 throw new  Exception("Password is incorrect");
						 
					   }else {
						 message = "success";
					}
			 }else {
				 message = "Wrong User Name";
				 throw new  Exception("Wrong User Name");
				 
			 }
			 
		}catch(Exception e){
			SalesForceEnvironment.setComment(1,className,"The exception in method validateUser  is  "+e.getMessage());
			// DO NOT THROW EXCEPTION HERE
			//throw new Exception ("The exception in method validatePersoCustomer  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if (tempUserName!=null)tempUserName=null; if(tempUserPwd!=null)tempUserPwd=null;
				if (userEmail!=null)userEmail=null; if(password!=null)password=null;
			}
		return message;
	}
	public User getUserDetails(String userEmail) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User m_user = null;
		
		try {
			connection=super.getConnection();
	
			query ="select relno,user_name,nationalid,usertype,user_email,user_phoneno,status, date_of_employment,createdon from user_details where user_email=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userEmail);
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 
				 	while(rs.next()){
				 		m_user=new User();
				 		m_user.setRelationshipNo(StringUtils.trim(rs.getString("relno")));
				 		m_user.setUserName(StringUtils.trim(rs.getString("user_name")));
				 		m_user.setNationalId(StringUtils.trim(rs.getString("nationalid")));
				 		m_user.setUserType(StringUtils.trim(rs.getString("usertype")));
				 		m_user.setUserEmail(StringUtils.trim(rs.getString("user_email")));
				 		m_user.setUserPhoneNo(StringUtils.trim(rs.getString("user_phoneno")));
				 		m_user.setStatus(StringUtils.trim(rs.getString("status")));
				 		m_user.setDateOfEmployment(StringUtils.trim(rs.getString("date_of_employment")));
				 		m_user.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
				 		
				 	}
			 }	 	
		}catch(Exception e){
			SalesForceEnvironment.setComment(1,className,"The exception in method getUserDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getUserDetails  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); if (userEmail!=null)userEmail=null;
			}
		return m_user;	
		}

}
