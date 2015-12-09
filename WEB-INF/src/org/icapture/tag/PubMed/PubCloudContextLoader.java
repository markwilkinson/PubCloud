package org.icapture.tag.PubMed;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PubCloudContextLoader {
	
	private String WEBAPP_PATH = null;
	
	/**
	 * Default Constructor
	 */
	public PubCloudContextLoader () {
		init();
	}
	
	private void init () {
		
		try {
			Context c = new InitialContext();
			
			try {
				String path = null;
				path = (String) c.lookup("java:comp/env/WEBAPP_PATH");
				
				if (path != null && !path.equals("")) {
					setWEBAPP_PATH(path);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getWEBAPP_PATH () {
		return this.WEBAPP_PATH;
	}
	
	public void setWEBAPP_PATH (String path) {
		this.WEBAPP_PATH = path;
	}
	
}