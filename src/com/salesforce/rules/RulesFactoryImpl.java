package com.salesforce.rules;

public class RulesFactoryImpl implements RulesFactory{
	
	private Rules rules;

	@Override
	public Rules createRule(String ruleName) throws Exception {
		
		switch(ruleName){
	
	
		case Rules.DEFAULT_LOGIN_MODULE: case Rules.JSON_LOGIN_VALIDATE: case Rules.LOGOUT_MODULE: case Rules.PROFILE_USER_DASHBOARD_PAGE:
				rules = new UserProfileRulesImpl();
		break;
		
			
		
		default: throw new Exception ("rule not defined");
			
		}
		
		return this.rules;
	}

}
