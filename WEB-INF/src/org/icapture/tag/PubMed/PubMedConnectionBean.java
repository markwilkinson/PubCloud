package org.icapture.tag.PubMed;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class PubMedConnectionBean {
	
	private String db;
	//private int maxNumArticle;
	
	
	/***********************
	 * Default Constructor *
	 ***********************/ 
	public PubMedConnectionBean () {
		db = "pubmed";
		//maxNumArticle = 100;		
	}
	
	
	
	/*********************************************
	 * Step 1
	 * Method used to build URL to search for ID *
	 * 
	 * @param	search term, start year, end year
	 * @return  constructed URL for step 2 search
	 *********************************************/
	public String buildIdURL (String term, String startYear, String endYear, int maxNumArticle, boolean sortByDate) {
		
		String baseIdURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?";
		String queryTerm = term.replace(' ', '+');
		String queryMode = "xml";
		int maxArticleLimit = 100000;
		
		//----- if maxNumArticle is specified -----//
		String retMax = "";
		if (maxNumArticle > 0) {
			if (maxNumArticle > maxArticleLimit) {
				maxNumArticle = maxArticleLimit;
			}

			retMax = "&retmax=" + maxNumArticle;

		}
		//-----------------------------------------//
		
		//----- if sortByDate is specified -----//
		String sortOption = "";
		if (sortByDate == true) {
			sortOption = "&sort=pub+date";
		}
		//--------------------------------------//
		String constructedURL = baseIdURL + "db=" + db + "&term="+ queryTerm + 
								"&retmode=" + queryMode + 
								retMax + sortOption +
								"&mindate=" + startYear + "&maxdate=" + endYear;
		
		return constructedURL;
	}
	
	
	
	/********************************************************
	 * Step 2
	 * Method used to connecto to NCBI to get a list of IDs *
	 * 
	 * @param	constructed URL from Step 1
	 * @return  a list of IDs in XML format
	 ********************************************************/ 	
	public int retrieveIdCount (String url) {
		
		String content = "";
		String lineContent = "";
		URL urlConnect; // First create a URL bean
		int idCount = 0;
		
		/******** Parse XML ********/
		String toParseElement = "eSearchResult";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url);

			NodeList list = doc.getElementsByTagName(toParseElement);
			Element searchElement = (Element) list.item(0);
			
			NodeList countNodeList = searchElement.getElementsByTagName("Count");
			Element countElement = (Element)countNodeList.item(0);
			
			NodeList valueNodeList = countElement.getChildNodes();
			idCount = Integer.parseInt(((Node)valueNodeList.item(0)).getNodeValue().trim());
		}
		catch (Exception e) {
			System.err.println(e);
		}
		/**************************/

		return idCount;
	}
	
	
	
	/**********************************************
	 * Step 3
	 * Method used to build URL to fetch abstract *
	 * 
	 * @param	a PubMedID
	 * @return  consructed URL for step 4 search
	 **********************************************/
	public String buildFetchURL (String id) {
		
		String baseFetchURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";
		String dbQuery = "db=";
		String idQuery = "id=";
		String modeQuery = "retmode=";
		String mode = "xml"; // Temporarily modified on 2007/09/19, originally "text"
		String typeQuery = "rettype=";
		String type = "pubmed"; // Temporarily modified on 2007/09/19, originally "medline"
		String constructedURL = baseFetchURL + dbQuery + db + "&" + idQuery + id + "&" + 
							    modeQuery + mode + "&" + typeQuery + type;
		
		return constructedURL;
	}
	
	
	
	/***************************************************************
	 * Step 4
	 * Method used to connecto to NCBI to Fetch Literature Content *
	 *                                                             *
	 * @param url                                                  *
	 * @return Fetched Content in XML format                       *
	 ***************************************************************/ 
	public ArrayList<String> retrieveArticleIDList (String url) {
		
		ArrayList<String> articleIdList = new ArrayList<String>();

		/***** Parse XML *****/
		String toParseElement = "IdList";
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			//System.out.println(url); // for debug
			
			Document doc = builder.parse(url);
			
			NodeList articleIdNodeList = doc.getElementsByTagName(toParseElement);
			Element searchElement = (Element) articleIdNodeList.item(0);
			
			NodeList idNodeList = searchElement.getElementsByTagName("Id");
			int articleCount = idNodeList.getLength();
			
			for (int i = 0; i < articleCount; i++) {
				Element idElement = (Element)idNodeList.item(i);
				
				NodeList idValueNodeList = idElement.getChildNodes();
				
				String id = ((Node)idValueNodeList.item(0)).getNodeValue().trim();
				articleIdList.add(id);
			}
		}
		catch (Exception e) {
			System.err.println(e);
		}
		/*********************/
		
		return articleIdList;
	}
	
	public NodeList retrieveArticleNodeList (String url) {
		
		NodeList articleNodeList = null;

		/***** Parse XML *****/
		String toParseElement = "PubmedArticle";
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url);
			
			articleNodeList = doc.getElementsByTagName(toParseElement);
			
		}
		catch (Exception e) {
			System.err.println(e);
		}
		/*********************/
		
		return articleNodeList;
	}
	
	
	/**************************************************
	 * Step 5 (optional)
	 * Method used to build URL to search for summary *
	 * 
	 * @param
	 * @return  
	 **************************************************/ 
	public String buildSummaryURL (String id) {
		
		String baseSummaryURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?";
		String dbQuery = "db=";
		String idQuery = "id=";
		String constructedURL = baseSummaryURL + dbQuery + db + "&" + idQuery + id;
		
		return constructedURL;
	}
	
	
	
	/**********************************************************************
	 * Step 6 (optional)
	 * Method used to connecto to NCBI to get literature summary of an ID *
	 * Summary contains author information, but no abastract
	 * @param
	 * @return  
	 **********************************************************************/ 
	public String getSummaryURLContent (String url) {
		String summary = "";
		String lineSummary = "";
		URL urlSummary; // First create a URL bean

		try {
			urlSummary = new URL (url);
			InputStream in = urlSummary.openStream();
			DataInputStream dataIn = new DataInputStream(new BufferedInputStream(in));

			try {
				while ((lineSummary = dataIn.readLine()) != null) {
					summary = summary + lineSummary + "\n";
				}
			}
			catch (FileNotFoundException e) {
				System.out.println(e);
			}
			
			in.close();
		}
		catch (MalformedURLException e){
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return summary;
	}
	
	
	public String getAbstractTitle (String PMID) {
		String abstractTitle = "";
		
		
		
		
		return abstractTitle;
	}
	
}