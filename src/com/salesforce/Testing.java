package com.salesforce;

import com.salesforce.utilities.Utilities;

public class Testing {
	public static void main(String[] args) throws Exception{
		
		try {
			String test="test";
			
			System.out.println("Encryted password is"+Utilities.encryptString(test));
			
		}catch (Exception e) {
			System.out.println("Error in Testing is "+e.getMessage());
		}
		
	}
	
}
