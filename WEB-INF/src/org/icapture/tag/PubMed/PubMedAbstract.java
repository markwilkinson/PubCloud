package org.icapture.tag.PubMed;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class PubMedAbstract {

	private String PMID;
	private String abstractTitle;
	private String abstractText;
	private Date abstractDate;
	private ArrayList<String> authors;
	private ArrayList<String> meshTerms;
	
	/*
	 * Default Constructor
	 */
	public PubMedAbstract () {
		PMID = "";
		abstractTitle = "";
		abstractText = "";
		abstractDate = new Date();
		authors = new ArrayList<String>();
		meshTerms = new ArrayList<String>();
	}
	
	/*
	 * Constructor with arguments
	 */
	public PubMedAbstract (String inputPMID, String inputAbstractTitle,
						   String inputAbstractText, Date inputAbstractDate,
						   ArrayList<String> inputAuthors, ArrayList<String> inputMeshTerms) {
		PMID = inputPMID;
		abstractTitle = inputAbstractTitle;
		abstractText = inputAbstractText;
		abstractDate = inputAbstractDate;
		authors = inputAuthors;
		meshTerms = inputMeshTerms;
	}
	
	public void setPMID (String inputPMID) {
		PMID = inputPMID;
		return;
	}
	
	public void setAbstractTitle (String inputAbstractTitle) {
		abstractTitle = inputAbstractTitle;
		return;
	}
	
	public void setAbstractText (String inputAbstractText) {
		abstractText = inputAbstractText;
		return;
	}
	
	public void setAbstractDate (Date inputAbstractDate) {
		abstractDate = inputAbstractDate;
		return;
	}
	
	public String getPMID () {
		return PMID;
	}
	
	public String getAbstractTitle () {
		return abstractTitle;
	}
	
	public String getAbstractText () {
		return abstractText;
	}
	
	public Date getAbstractDate () {
		return abstractDate;
	}
	
	public ArrayList<String> getAuthors () {
		return authors;
	}
	
	public ArrayList<String> getMeshTerms () {
		return meshTerms;
	}
}