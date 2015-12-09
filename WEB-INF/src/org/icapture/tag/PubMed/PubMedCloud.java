package org.icapture.tag.PubMed;

import org.icapture.tag.Cloud;
import org.icapture.tag.PubMed.PubMedTag;

public class PubMedCloud extends Cloud {
	
	private String CloudType;
	private String CloudOption;
	private String CloudOptionValue;
	private String StartYear;
	private String EndYear;
	
	public PubMedCloud (String input_query) {
		super(input_query);
	}
	
	public PubMedCloud (String input_query, String cloud_type, String cloud_option, 
			            String cloud_option_value, String start_year, String end_year) {
		super(input_query);
		CloudType = cloud_type;
		CloudOption = cloud_option;
		CloudOptionValue = cloud_option_value;
		StartYear = start_year;
		EndYear = end_year;
		
	}
	
	public void addTag (PubMedTag tag) {
		tags.add(tag);
		return;
	}
	
	public String getCloudType () {
		return CloudType;
	}
	
	public void setCloudType (String cloud_type) {
		CloudType = cloud_type;
	}
	
	public String getCloudOption () {
		return CloudOption;
	}
	
	public void setCloudOption (String cloud_option) {
		CloudOption = cloud_option;
	}
	
	public String getCloudOptionValue () {
		return CloudOptionValue;
	}
	
	public void setCloudOptionValue (String cloud_option_value) {
		CloudOptionValue = cloud_option_value;
	}
	
	public String getStartYear () {
		return StartYear;
	}
	
	public void setStartYear (String start_year) {
		StartYear = start_year;
	}
	
	public String getEndYear () {
		return EndYear;
	}
	
	public void setEndYear (String end_year) {
		EndYear = end_year;
	}
}