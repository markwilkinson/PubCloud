package org.icapture.tag.PubMed;

import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.*;

import org.w3c.dom.*;


public class PubMedConnectionContentParserBean {
	
	/*
	 * Default Constructor
	 * @param
	 */
	public PubMedConnectionContentParserBean() {
		
	}
	
	/******************************************
	 * Parse out the PubMed ID's into an      *
	 * ArrayList                              *
	 *                                        *
	 * @param idContent                       *
	 * @return                                *
	 ******************************************/
	public ArrayList<String> parsePubMedIdList (NodeList articleList) {
		int numArticle = articleList.getLength();
		ArrayList<String> idArrayList = new ArrayList();

		String toParse = "PMID";
		
		for (int i = 0; i < numArticle; i++) {
			Element articleElement = (Element)articleList.item(i);
			NodeList idNodeList = articleElement.getElementsByTagName(toParse);
			
			Element idElement = (Element)idNodeList.item(0);
			NodeList idValueNodeList = idElement.getChildNodes();
			String idValue = ((Node)idValueNodeList.item(0)).getNodeValue().trim();
			System.out.println("idValue: " + idValue);
			idArrayList.add(idValue);
		}
		
		return idArrayList;
	}

	
	/********************************************
	 * Parse out abstract of the PubMed article *
	 * 
	 * @param	
	 * @return   
	 ********************************************/
	public ArrayList<PubMedAbstract> retrieveAbstractObjects (NodeList articleNodeList) {
		ArrayList<PubMedAbstract> abstractArrayList= new ArrayList<PubMedAbstract>();
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> meshTerms = new ArrayList<String>();
		
		String parseTagPMID = "PMID";
		String parseTagArticleTitle = "ArticleTitle";
		String parseTagAbstractText = "AbstractText";
		String parseTagArticleDate = "PubMedPubDate";
		String parseTagAuthor = "Author";
		String parseTagMeshHeading = "MeshHeading";
			
		int articleNodeListSize = articleNodeList.getLength();
		
		for (int i = 0; i < articleNodeListSize; i++) {
			
			Element articleElement = (Element)articleNodeList.item(i);
			
			/***** Get PMID *****/
			NodeList abstractIdNodeList = articleElement.getElementsByTagName(parseTagPMID);
			Element abstractIdElement = (Element)abstractIdNodeList.item(0);
			NodeList abstractIdValueNodeList = abstractIdElement.getChildNodes();
			String abstractIdValue = (abstractIdValueNodeList.item(0)).getNodeValue().trim();
			
			/***** Get ArticleTitle *****/
			NodeList abstractTitleNodeList = articleElement.getElementsByTagName(parseTagArticleTitle);
			Element abstractTitleElement = (Element)abstractTitleNodeList.item(0);
			NodeList abstractTitleValueNodeList = abstractTitleElement.getChildNodes();
			String abstractTitleValue = (abstractTitleValueNodeList.item(0)).getNodeValue().trim();
			
			/***** Get AbstractText *****/
			NodeList abstractTextNodeList = articleElement.getElementsByTagName(parseTagAbstractText);	
			String abstractTextValue = "";
			if (abstractTextNodeList.getLength() > 0) {
				Element abstractTextElement = (Element)abstractTextNodeList.item(0);
				NodeList abstractTextValueNodeList = abstractTextElement.getChildNodes();
				abstractTextValue = (abstractTextValueNodeList.item(0)).getNodeValue().trim();
			}
			
			/***** Get ArticleDate *****/
			NodeList abstractDateNodeList = articleElement.getElementsByTagName(parseTagArticleDate);
			
			String toParseStatus = "PubStatus";
			String toMatchStatusValue = "pubmed";
			String toParseYear = "Year";
			String toParseMonth = "Month";
			String toParseDay = "Day";
			
			int abstractYearValue = 0;
			int abstractMonthValue = 0;
			int abstractDayValue = 0;
			
			for (int j = 0; j < abstractDateNodeList.getLength(); j++) {
				Element abstractDateElement = (Element)abstractDateNodeList.item(j);
				
				if (abstractDateElement.getAttribute(toParseStatus).equalsIgnoreCase(toMatchStatusValue)) {
				
					NodeList abstractYearNodeList = abstractDateElement.getElementsByTagName(toParseYear);
					NodeList abstractMonthNodeList = abstractDateElement.getElementsByTagName(toParseMonth);
					NodeList abstractDayNodeList = abstractDateElement.getElementsByTagName(toParseDay);
					
					Element abstractYearElement = (Element)abstractYearNodeList.item(0);
					Element abstractMonthElement = (Element)abstractMonthNodeList.item(0);
					Element abstractDayElement = (Element)abstractDayNodeList.item(0);
					
					NodeList abstractYearValueNodeList = abstractYearElement.getChildNodes();
					NodeList abstractMonthValueNodeList = abstractMonthElement.getChildNodes();
					NodeList abstractDayValueNodeList = abstractDayElement.getChildNodes();
					
					abstractYearValue = Integer.parseInt((abstractYearValueNodeList.item(0)).getNodeValue().trim());
					abstractMonthValue = Integer.parseInt((abstractMonthValueNodeList.item(0)).getNodeValue().trim());
					abstractDayValue = Integer.parseInt((abstractDayValueNodeList.item(0)).getNodeValue().trim());
				}
			}
			Date abstractDate = new Date(abstractYearValue, abstractMonthValue, abstractDayValue);
			
			/***** Get Author *****/
			NodeList abstractAuthorNodeList = articleElement.getElementsByTagName(parseTagAuthor);
			
			String toParseLastName = "LastName";
			String toParseForeName = "ForeName";
			String toParseFirstName = "FirstName";
			String toParseCollectiveName = "CollectiveName";
			
			for (int j = 0; j < abstractAuthorNodeList.getLength(); j++) {
				Element abstractAuthorElement = (Element)abstractAuthorNodeList.item(j);
				
				NodeList abstractLastNameNodeList = abstractAuthorElement.getElementsByTagName(toParseLastName);
				NodeList abstractForeNameNodeList = abstractAuthorElement.getElementsByTagName(toParseForeName);
				NodeList abstractCollectiveNameNodeList = abstractAuthorElement.getElementsByTagName(toParseCollectiveName);
				
				if (abstractForeNameNodeList.getLength() == 0) {
					abstractForeNameNodeList = abstractAuthorElement.getElementsByTagName(toParseFirstName);
				}
				
				String authorFullName = "";
				
				if (abstractForeNameNodeList.getLength() > 0 && abstractLastNameNodeList.getLength() > 0) {
					Element abstractLastNameElement = (Element)abstractLastNameNodeList.item(0);
					Element abstractForeNameElement = (Element)abstractForeNameNodeList.item(0);
					
					NodeList abstractLastNameValueNodeList = abstractLastNameElement.getChildNodes();
					NodeList abstractForeNameValueNodeList = abstractForeNameElement.getChildNodes();
					
					String abstractLastNameValue = (abstractLastNameValueNodeList.item(0)).getNodeValue().trim();
					String abstractForeNameValue = (abstractForeNameValueNodeList.item(0)).getNodeValue().trim();
					
					authorFullName = abstractLastNameValue + " " + abstractForeNameValue;
				}
				else if (abstractCollectiveNameNodeList.getLength() > 0){
					Element abstractCollectiveNameElement = (Element)abstractCollectiveNameNodeList.item(0);
					NodeList abstractCollectiveNameValueNodeList = abstractCollectiveNameElement.getChildNodes();
					String abstractCollectiveNameValue = (abstractCollectiveNameValueNodeList.item(0)).getNodeValue().trim();
					
					authorFullName = abstractCollectiveNameValue;
				}
				
				if (!authorFullName.equals("")) {
					authors.add(authorFullName);
				}

			}

			/***** Get MeshHeading *****/
			NodeList abstractMeshHeadingNodeList = articleElement.getElementsByTagName(parseTagMeshHeading);
			
			String toParseDescriptor = "DescriptorName";

			for (int j = 0; j < abstractMeshHeadingNodeList.getLength(); j++) {
				Element abstractMeshHeadingElement = (Element)abstractMeshHeadingNodeList.item(j);
			
				NodeList abstractMeshDescriptorNodeList = abstractMeshHeadingElement.getElementsByTagName(toParseDescriptor);
				Element abstractMeshDescriptorElement = (Element)abstractMeshDescriptorNodeList.item(0);
				NodeList abstractMeshDescriptorValueNodeList = abstractMeshDescriptorElement.getChildNodes();
				String abstractMeshDescriptorValue = (abstractMeshDescriptorValueNodeList.item(0)).getNodeValue().trim();
				
				meshTerms.add(abstractMeshDescriptorValue);
			}
			
			PubMedAbstract tempAbstract = new PubMedAbstract(abstractIdValue, abstractTitleValue,
											abstractTextValue, abstractDate,
											authors, meshTerms); 
			
			abstractArrayList.add(tempAbstract);
		}
		
		return abstractArrayList;
	}
	
	
	/**************************************************
	 * Method that checks if the word contains a digit
	 * 
	 * @param
	 * @return  
	 **************************************************/ 
/*
	private boolean containsDigit (String word) {
		
		if ( word.contains("0") || word.contains("1") || word.contains("2") || word.contains("3") || 
			 word.contains("4") || word.contains("5") || word.contains("6") || word.contains("7") || 
			 word.contains("8") || word.contains("9") ) {
			return true;
		}
		else {
			return false;
		}
		
	}
*/
	
	/**************************************************
	 * Method used to convert Month in text to digit
	 * i.e. Jan -> 1, Feb -> 2...etc
	 * 
	 * @param
	 * @return  
	 **************************************************/ 
/*	
	private int convertMonthTextToInt (String monthText) {
		
		int monthInt = 0;
		monthText = monthText.toLowerCase();
		
		if (monthText.contains("jan"))
			monthInt = 1;
		else if (monthText.contains("feb"))
			monthInt = 2;
		else if (monthText.contains("mar"))
			monthInt = 3;
		else if (monthText.contains("apr"))
			monthInt = 4;
		else if (monthText.contains("may"))
			monthInt = 5;
		else if (monthText.contains("jun"))
			monthInt = 6;
		else if (monthText.contains("jul"))
			monthInt = 7;
		else if (monthText.contains("aug"))
			monthInt = 8;
		else if (monthText.contains("sep"))
			monthInt = 9;
		else if (monthText.contains("oct"))
			monthInt = 10;
		else if (monthText.contains("nov"))
			monthInt = 11;
		else if (monthText.contains("dec"))
			monthInt = 12;
		
		return monthInt;
	}
*/
}