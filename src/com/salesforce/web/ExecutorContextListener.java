package com.salesforce.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.salesforce.SalesForceEnvironment;
import com.salesforce.utilities.DaemonThreadFactory;

/**
 * Application Lifecycle Listener implementation class MailExecutorContextListener
 *
 */
@WebListener
public class ExecutorContextListener implements ServletContextListener {
	private  static String className = ExecutorContextListener.class.getSimpleName();

	private  ExecutorService executorEmail;
	//private  ExecutorService executorSomethingElse;
    /**
     * Default constructor. 
     */
    public ExecutorContextListener() {
    	SalesForceEnvironment.setComment(3,className,"Inside constructor method of MailExecutorContextListener");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
		//System.out.println("============== starting :" +className + " at "+java.time.LocalTime.now());

    	ServletContext context = sce.getServletContext();
    	
		try {
			if(SalesForceEnvironment.getInstance()==null) {
				SalesForceEnvironment.init();
				SalesForceEnvironment.setComment(3,className,"===> PPWalletEnvironment is initialized......");
				//PPWalletEnvironment.setComment(3,className,"Inside contextInitialized method of MailExecutorContextListener");
			}
			
		} catch (Exception e1) {
			System.out.println("CRITICAL ERROR : Failed to initialize PPWalletEnvironment "+e1.getMessage());		
			}
    	
    	
		SalesForceEnvironment.setComment(3,className,"============== starting :" +className + " at "+java.time.LocalTime.now());

    	try {
			int nr_executors = Integer.parseInt(SalesForceEnvironment.getEmailThreadCount());
	        ThreadFactory daemonFactory = new DaemonThreadFactory();
	        try {
	            nr_executors = Integer.parseInt(context.getInitParameter("nr-executors"));
	        } catch (NumberFormatException ignore ) {}

	        if(nr_executors <= 1) {
	        	executorEmail = Executors.newSingleThreadExecutor(daemonFactory);
	        } else {
	        	executorEmail = Executors.newFixedThreadPool(nr_executors,daemonFactory);
	       }
	       // Publish the executor in Setvlet Environment to be grabbed by workflows   
	       context.setAttribute("EMAIL_EXECUTOR", executorEmail);
			
			
		} catch (Exception e) {
			SalesForceEnvironment.setComment(1,className,"Inside init method of MailExecutorContextListener");		} 
    	
    }
	

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	try {
    		SalesForceEnvironment.setComment(3,className,"attempt to shutdown executor");
    	    executorEmail.shutdown();
    	    executorEmail.awaitTermination(5, TimeUnit.SECONDS);
    	}
    	catch (InterruptedException e) {
    		SalesForceEnvironment.setComment(1,className,"tasks interrupted "+e.getMessage());
    	}
    	finally {
    	    if (!executorEmail.isTerminated()) {
    	    	SalesForceEnvironment.setComment(3,className,"cancel non-finished tasks");
    	    }
    	    executorEmail.shutdownNow();
    	    SalesForceEnvironment.setComment(3,className,"shutdown finished");
    	}
    }


}
