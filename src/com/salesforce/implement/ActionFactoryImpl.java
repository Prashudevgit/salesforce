
package com.salesforce.implement;

public class ActionFactoryImpl implements ActionFactory{
	private Action action;
	
	
	private final static String LOGOUT_MODULE= "lgt";
	private final static String LOGIN_MODULE = "lgn";
	//added
	private final static String FORGOT_PASSWORD = "fgt";
	private final static String LOGO_REDIRECT = "logoredirect";
	private final static String DASHBOARD_MODULE = "dash";

	

	@Override
	public Action createAction(String actionName) {
		switch (actionName){
		 	case LOGIN_MODULE: case LOGOUT_MODULE: case FORGOT_PASSWORD: case LOGO_REDIRECT: case DASHBOARD_MODULE:
		 		action=new UserLoginActionImpl();
			break;
		 	
		}
		return this.action;
	}

}
