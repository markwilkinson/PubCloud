package org.icapture.tag.PubMed;

import javax.servlet.*;

public final class PubCloudContextListener implements ServletContextListener {
	
	private final String newline = System.getProperty("line.separator");
	
	public void contextInitialized (ServletContextEvent servletContextEvent) {
		PubCloudContextLoader cLoader = new PubCloudContextLoader();
		
		if (cLoader.getWEBAPP_PATH() != null) {
			System.getProperty("WEBAPP_PATH", cLoader.getWEBAPP_PATH());	
		}
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}
	
}