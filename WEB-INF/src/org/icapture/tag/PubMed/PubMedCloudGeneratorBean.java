package org.icapture.tag.PubMed;

import org.icapture.tag.PubMed.PubCloudContextListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.icapture.text.*;

public class PubMedCloudGeneratorBean {
	
	private String common_word_file;
	private Hashtable commonWordHash;
	private PorterStemmer stemmer;
	private PubMedCloud generatedCloud;
	private String inputQuery;
	private String cloudType;
	private String cloudOption;
	private String cloudOptionValue;
	private String startYear;
	private String endYear;
	private String PMIDListHtml;
	private int PMIDListCount;
	private int maxGetUrlLength;
	
	/*
	 * Default Constructor
	 */
	public PubMedCloudGeneratorBean() {

		maxGetUrlLength = 2000;
		generatedCloud = new PubMedCloud(inputQuery);
		inputQuery = "";
		stemmer = new PorterStemmer();
		
		//common_word_file = "/usr/local/tomcat/webapps/PubCloud/common_words.txt";
		common_word_file = "D:\\common_words.txt";
		//common_word_file = "/usr/local/tomcat6/webapps/PubCloud/words/common_words.txt";
		commonWordHash = new Hashtable();
		
		PMIDListHtml = ""; // Initialize the ID list variable
		PMIDListCount = 0; // Initialize the ID list count variable
		
		// Read the common words into the hash
		FileReader in;	BufferedReader in_buffer;
		
		
		/*
		ServletContext context = getServletContext();
		BufferedReader br = new BufferedReader(new FileReader(new File(context.getRealPath( "/" ) + "/commonwords.txt")));
		*/
		
		
		try {
			in = new FileReader(common_word_file);
			in_buffer = new BufferedReader(in);
			String common_word;
			try {
				while ((common_word = in_buffer.readLine()) != null) {
					commonWordHash.put(common_word, new Integer(0));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// Method for randomly sample the desired portion of articles
	public String getIdListRandomOption (ArrayList<String> idArrayList, int optionValue) {
		String processedIdList = "";
		int idListArrayLength = idArrayList.size();
		
		// Compute the number of idLists to keep
		int numIdToGet = (idListArrayLength * optionValue) / 100;
	
		if (numIdToGet < 1) {
			numIdToGet = 1;
		}
		
		Vector indexCheck = new Vector();
		Random generator = new Random();
		
		int numIdToGetCount = 0;
		while (numIdToGetCount < numIdToGet) {
			
			int randomIndex = generator.nextInt(idListArrayLength);
			if (!indexCheck.contains(randomIndex)) {
				if (numIdToGetCount == 0) {
					processedIdList += 	idArrayList.get(randomIndex);
				}
				else {
					processedIdList += ("," + idArrayList.get(randomIndex));
				}
				indexCheck.add(randomIndex);
				numIdToGetCount++;				
			}
		}
		
		return processedIdList;
	}
	
	
	// Method for getting most relevant X articles
	public String getIdListMostRelevantOption (ArrayList<String> idArrayList, int optionValue) {
		String processedIdList = "";
		int idListArrayLength = idArrayList.size();
		
		// Compute the number of idLists to keep
		int numIdToGet = optionValue;
		if (optionValue > idListArrayLength) {
			numIdToGet = idListArrayLength;
		}
		
		for (int i = 0; i < numIdToGet; i++) {
			if (i > 0) {
				processedIdList += ",";
			}
			processedIdList += idArrayList.get(i);
		}
		return processedIdList;
	}


	// mainBuildCloud method
	public boolean mainBuildCloud (String keyword, String startYear, String endYear, String type, String option, int optionValue, int numTagsValue) {
		boolean buildOk = false;
		
		// The next 3 lines need to be further tested to make them work
		String toMatch = "\\+";
		String toReplace = "";
		inputQuery = keyword.replaceFirst(toMatch, toReplace);

		this.startYear = startYear;
		this.endYear = endYear;
		this.cloudType = type;
		this.cloudOption = option;
		this.cloudOptionValue = Integer.toString(optionValue);
		
		boolean sortOption = false;
		if (option.equals("most_recent")) {
			sortOption = true;
		}
		
		ArrayList<PubMedAbstract> abstractList = new ArrayList<PubMedAbstract>();
		
		PubMedConnectionBean PMConnect= new PubMedConnectionBean();
		PubMedConnectionContentParserBean PMConnectContentParser = new PubMedConnectionContentParserBean();
		
		String idURLCount = PMConnect.buildIdURL(inputQuery, startYear, endYear, 0, sortOption); // build this prior to step 1 to get article count number
		int idCount = PMConnect.retrieveIdCount(idURLCount); // Parse out the count number of PMIDs

		String idURL = PMConnect.buildIdURL(inputQuery, startYear, endYear, idCount, sortOption); // Step 1
		ArrayList<String> idArrayList = PMConnect.retrieveArticleIDList(idURL);

		String idList = "";
		
		//---- Depending on option, get the relevent ID List -----//
		if (option.equals("random")) {
			idList = getIdListRandomOption(idArrayList, optionValue);
		}
		else if (option.equals("most_recent")) {
			idList = getIdListMostRelevantOption(idArrayList, optionValue);
		}
		//--------------------------------------------------------//
		
		if (idList != "") { // Process only if idList is not null (i.e. some IDs are returned in query)
			String fetchURL = PMConnect.buildFetchURL(idList); // Step 3
			
			int fetchURLlength = fetchURL.length();
			//System.out.println("maxGetUrlLength=" + maxGetUrlLength + "; fetchURLlength=" + fetchURLlength);
			
			if (fetchURLlength <= maxGetUrlLength) { // Make sure URL does not exceed the maximum allowed length by HTTP GET
	
				NodeList articleNodeList = PMConnect.retrieveArticleNodeList(fetchURL);
				abstractList = PMConnectContentParser.retrieveAbstractObjects(articleNodeList);
	
				generatePMIDListHtml(abstractList); // generate PMID List in HTML string for output

				if (type.contains("abstract")) {
					cloudType = "abstract";
					generatedCloud = buildAbstractCloud (abstractList, inputQuery);
				}
				else if (type.contains("mesh")) {
					cloudType = "mesh";
					generatedCloud = buildMeshTermCloud(abstractList, inputQuery);
				}
				else if (type.contains("author")) {
					cloudType = "author";
					generatedCloud = buildAuthorCloud(abstractList, inputQuery);				
				}
				else {
					//
				}
				buildOk = true;
			}
		}
		return buildOk;
	}

	public PubMedCloud buildAbstractCloud (ArrayList abstractList, String inputQuery) {
		PubMedCloud abstractCloud = new PubMedCloud(inputQuery, cloudType, cloudOption, cloudOptionValue, startYear, endYear);
		List<PubMedTag> tagList = new ArrayList<PubMedTag>();
		Hashtable wordCountHash = new Hashtable(); // hash of word count
		Hashtable wordScoreHash = new Hashtable(); // added 2007/07/13 to compute frequency on the fly (avoid frequency bias)
		Hashtable wordCountHashTemp; // added 2007/07/13 to compute frequency on the fly (avoid frequency bias)
		Hashtable wordListHash = new Hashtable();  // hash of word lists
		Hashtable wordDateHash = new Hashtable(); // hash to store words and their years; store the latest
		int count = 0; // count the number of tags
		
		Iterator <PubMedAbstract> abstractIterator = abstractList.iterator();
		while (abstractIterator.hasNext()) { // Go through each abstract
			
			PubMedAbstract currentAbstract = abstractIterator.next(); // get abstract object
			Date abstractDate = currentAbstract.getAbstractDate(); // get abstract date
			String abstractText = currentAbstract.getAbstractText(); // get abstract text
			String[] abstractTextArray = abstractText.split(" "); // split words into an array
			int daySince1900 = computeDaysSince1900(abstractDate); // Compute days since 1900
			
			wordCountHashTemp = new Hashtable();// added 2007/07/13 to compute frequency on the fly (avoid frequency bias)
			
			for (int abs_index = 0; abs_index < abstractTextArray.length; abs_index++) {
				String word = abstractTextArray[abs_index]; // this word
				
				//System.out.println(word); // for debugging
				
				word = removeSymbols(word); // remove symbols
				
				String stemmedWord = "";
				
				// check for word length > 1 and word does not contain digits, and does not contain
				// symbols like '/' or '-'
				if ((word.length() > 2) && (! containsDigit(word)) && (! containsJoinSymbol(word))) {
					word = word.toLowerCase();
					//System.out.println(word); // for debugging
					stemmedWord = stemmer.stem(word); // stemming
				}
				else if (word.length() > 1) {
					stemmedWord = word;
				}
				else {
					stemmedWord = "";
				}
				
				Integer prev = null;
				if ( (stemmedWord.length() > 2) && (!isCommonWord(word)) && 
					 (!stemmedWord.contains("invalid")) && (!stemmedWord.contains("term")) ) {
					
					if ((prev = (Integer)wordCountHash.get(stemmedWord)) != null) {
						wordCountHash.put(stemmedWord, new Integer(prev.intValue() + 1));
						wordCountHashTemp.put(stemmedWord, new Integer(prev.intValue() + 1)); // added 2007/07/13 to compute frequency on the fly (avoid frequency bias)

						/*  
						 * Add new days to the wordDateHash
						 * This method puts more weight on the more frequently occuring tags
						 */	
							Integer storedDays = (Integer)wordDateHash.get(stemmedWord);
							Integer newDays = new Integer(storedDays.intValue() + daySince1900);
							wordDateHash.put(stemmedWord, newDays);
						/*------------------------------------------------------------------------------*/
						
						/** following segment of code is used to check if the word is already in hash **/
						String toAddWord = (String) wordListHash.get(stemmedWord);
						boolean addCheck = false;
						if (toAddWord.contains(word))
							addCheck = true;
						/**---------------------------------------------------------------------------**/
						
						if (addCheck == false)
							wordListHash.put(stemmedWord, new String(wordListHash.get(stemmedWord) + ", " + word));
					}
					else { // does not exist, add to hash
						wordCountHashTemp.put(stemmedWord, new Integer(1)); // added 2007/07/13 to compute frequency on the fly (avoid frequency bias)
						wordCountHash.put(stemmedWord, new Integer(1)); // add new word to hash & count = 1
						wordListHash.put(stemmedWord, new String(word)); // add new word to word list hash
						wordDateHash.put(stemmedWord, new Integer(daySince1900)); // add date to the hash
						count++; // increment # tag count
					}				
				}
			}
		}
		
		int maxWordCount = 0;
		int minTotalDays = 1000000000;
		int maxTotalDays = 0;
		
		// Go through the hash keys and generate a tag object for each word
		for (Enumeration e = wordCountHash.keys(); e.hasMoreElements();) {
			String word = (String)e.nextElement(); // get word
			int wordCount = ((Integer)wordCountHash.get(word)).intValue(); // get count
			String wordList = (String) wordListHash.get(word); // get word list
			String[] wordListArray = wordList.split(","); 
			String representName = wordListArray[0]; // get represent name	
			int totalDays = ((Integer)wordDateHash.get(word)).intValue(); // get total days
			int totalDaysAverage = totalDays / wordCount;
			
			if (wordCount > maxWordCount) // determine maxWordCount for frequency calculation
				maxWordCount = wordCount;
			if (totalDaysAverage > maxTotalDays) // determine maxTotalDays for recency calculation
				maxTotalDays = totalDaysAverage;
			if (totalDaysAverage < minTotalDays) // determine minTotalDays for recency calculation
				minTotalDays = totalDaysAverage;
			
			// set tag attributes
			PubMedTag tag = new PubMedTag();
			tag.setStemmedName(word);
			tag.setCount(wordCount);
			tag.setSimilarWordList(wordList);
			tag.setRepresentName(representName);
			tag.setTotalAgeDays(totalDays);	
			tag.setTotalAgeDaysAverage(totalDaysAverage);
			tagList.add(tag); // add to tagList
		}
		
		/*
		 * After getting all the tags, go through each and compute the
		 * relative frequency and relative recency
		 */
		Iterator<PubMedTag> tagIter = tagList.iterator();
		while (tagIter.hasNext()) {
			PubMedTag thisTag = tagIter.next();
			thisTag.computeFrequency(maxWordCount);
			thisTag.computeRecency(minTotalDays, maxTotalDays);
		}
		abstractCloud.setTags(tagList);
		
		abstractCloud.orderBy("representName");
		return abstractCloud;
	}
	
	
	/*
	 * Generate a Author Cloud
	 */
	public PubMedCloud buildAuthorCloud (ArrayList abstractList, String inputQuery) {
		PubMedCloud authorCloud = new PubMedCloud(inputQuery, cloudType, cloudOption, cloudOptionValue, startYear, endYear);
		List<PubMedTag> tagList = new ArrayList<PubMedTag>();
		Hashtable wordCountHash = new Hashtable(); // hash of word count
		Hashtable wordDateHash = new Hashtable(); // hash to store words and their years; store the latest
		int count = 0; // count the number of tags
		
		Iterator <PubMedAbstract> abstractIterator = abstractList.iterator();
		while (abstractIterator.hasNext()) {
			
			PubMedAbstract currentAbstract = abstractIterator.next(); // get abstract object
			Date abstractDate = currentAbstract.getAbstractDate(); // get abstract date
			ArrayList authors = currentAbstract.getAuthors(); // get abstract text
			int daySince1900 = computeDaysSince1900(abstractDate); // Compute days since 1900
			
			Iterator<String> authorIterator = authors.iterator();
			while (authorIterator.hasNext()) {
				String currentAuthor = authorIterator.next(); // current mesh term
				
				Integer prev = null;
				if ((prev = (Integer)wordCountHash.get(currentAuthor)) != null) {
					wordCountHash.put(currentAuthor, new Integer(prev.intValue() + 1));
				}
				else { // does not exist, add to hash
					wordCountHash.put(currentAuthor, new Integer(1)); // add new word to hash & count = 1
					wordDateHash.put(currentAuthor, new Integer(daySince1900)); // add date to the hash
					count++; // increment # tag count
				}	
			}
		}
		
		int maxWordCount = 0;
		int minTotalDays = 1000000000;
		int maxTotalDays = 0;
		
		// Go through the hash keys and generate a tag object for each word
		for (Enumeration e = wordCountHash.keys(); e.hasMoreElements();) {
			String word = (String)e.nextElement(); // get word
			int wordCount = ((Integer)wordCountHash.get(word)).intValue(); // get count
			int totalDays = ((Integer)wordDateHash.get(word)).intValue(); // get total days
			int totalDaysAverage = totalDays / wordCount;

			
			if (wordCount > maxWordCount) // determine maxWordCount for frequency calculation
				maxWordCount = wordCount;
			if (totalDaysAverage > maxTotalDays) // determine maxTotalDays for recency calculation
				maxTotalDays = totalDaysAverage;
			if (totalDaysAverage < minTotalDays) // determine minTotalDays for recency calculation
				minTotalDays = totalDaysAverage;
			
			// set tag attributes
			PubMedTag tag = new PubMedTag();
			tag.setStemmedName(word);
			tag.setCount(wordCount);
			tag.setRepresentName(word);
			tag.setTotalAgeDays(totalDays);	
			tag.setTotalAgeDaysAverage(totalDaysAverage);
			tagList.add(tag); // add to tagList
		}
		
		/*
		 * After getting all the tags, go through each and compute the
		 * relative frequency and relative recency
		 */
		Iterator<PubMedTag> tagIter = tagList.iterator();
		while (tagIter.hasNext()) {
			PubMedTag thisTag = tagIter.next();
			thisTag.computeFrequency(maxWordCount);
			thisTag.computeRecency(minTotalDays, maxTotalDays);
		}

		authorCloud.setTags(tagList);
		authorCloud.orderBy("representName");
		return authorCloud;
	}
	
	
	/*
	 * Generate an Mesh Term Cloud
	 */
	public PubMedCloud buildMeshTermCloud (ArrayList abstractList, String inputQuery) {
		PubMedCloud meshCloud = new PubMedCloud(inputQuery, cloudType, cloudOption, cloudOptionValue, startYear, endYear);
		List<PubMedTag> tagList = new ArrayList<PubMedTag>();
		Hashtable wordCountHash = new Hashtable(); // hash of word count
		Hashtable wordDateHash = new Hashtable(); // hash to store words and their years; store the latest
		int count = 0; // count the number of tags
		
		Iterator <PubMedAbstract> abstractIterator = abstractList.iterator();
		while (abstractIterator.hasNext()) {
			
			PubMedAbstract currentAbstract = abstractIterator.next(); // get abstract object
			Date abstractDate = currentAbstract.getAbstractDate(); // get abstract date
			ArrayList meshTerms = currentAbstract.getMeshTerms(); // get abstract text
			int daySince1900 = computeDaysSince1900(abstractDate); // Compute days since 1900
			
			Iterator<String> meshTermIterator = meshTerms.iterator();
			while (meshTermIterator.hasNext()) {
				String currentTerm = meshTermIterator.next(); // current mesh term
				
				Integer prev = null;
				if ((prev = (Integer)wordCountHash.get(currentTerm)) != null) {
					wordCountHash.put(currentTerm, new Integer(prev.intValue() + 1));
				}
				else { // does not exist, add to hash
					wordCountHash.put(currentTerm, new Integer(1)); // add new word to hash & count = 1
					wordDateHash.put(currentTerm, new Integer(daySince1900)); // add date to the hash
					count++; // increment # tag count
				}	
			}
		}
		
		int maxWordCount = 0;
		int minTotalDays = 1000000000;
		int maxTotalDays = 0;
		
		// Go through the hash keys and generate a tag object for each word
		for (Enumeration e = wordCountHash.keys(); e.hasMoreElements();) {
			String word = (String)e.nextElement(); // get word
			//word = word.replaceAll("\\*", "");

			
			int wordCount = ((Integer)wordCountHash.get(word)).intValue(); // get count
			int totalDays = ((Integer)wordDateHash.get(word)).intValue(); // get total days
			int totalDaysAverage = totalDays / wordCount;
			
			if (wordCount > maxWordCount) // determine maxWordCount for frequency calculation
				maxWordCount = wordCount;
			if (totalDaysAverage > maxTotalDays) // determine maxTotalDays for recency calculation
				maxTotalDays = totalDaysAverage;
			if (totalDaysAverage < minTotalDays) // determine minTotalDays for recency calculation
				minTotalDays = totalDaysAverage;
			
			// set tag attributes
			PubMedTag tag = new PubMedTag();
			tag.setStemmedName(word);
			tag.setCount(wordCount);
			tag.setRepresentName(word);
			tag.setTotalAgeDays(totalDays);	
			tag.setTotalAgeDaysAverage(totalDaysAverage);
			tagList.add(tag); // add to tagList
		}
		
		/*
		 * After getting all the tags, go through each and compute the
		 * relative frequency and relative recency
		 */
		Iterator<PubMedTag> tagIter = tagList.iterator();
		while (tagIter.hasNext()) {
			PubMedTag thisTag = tagIter.next();
			thisTag.computeFrequency(maxWordCount);
			thisTag.computeRecency(minTotalDays, maxTotalDays);
		}

		meshCloud.setTags(tagList);
		meshCloud.orderBy("representName");
		return meshCloud;
	}
	
	public String generateHtmlCloud (String linkType) {
		String cloudHTML = "";
		
		if (linkType.equals("igoogle")) {
			generatedCloud.orderBy("frequency");
		}
		List tagList = generatedCloud.getTags();
		
		
		Iterator<PubMedTag> tagIter = tagList.iterator();
		int iGoogleGadgetTagCount = 0;
		
		while (tagIter.hasNext()) {
			
			PubMedTag tag = tagIter.next();
			
			String link = "";
			if (linkType.equals("continuous")) { //*** Create Link for continuous browsing ***//	
				link = "http://bioinfo.icapture.ubc.ca:8090/PubCloud/gc.jsp?";		
			}
			else if (linkType.equals("pubmed")) { //*** Create Link for browsing PubMed Articles ***//
				link = "http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&term=";			
			}
			else if (linkType.equals("igoogle")) {
				link = "http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&term=";	
			}
			
			String tagName = tag.getRepresentName(); // name to be displayed
			String tagWordList = tag.getSimilarWordList(); // similar word list
			int tagFrequency = tag.getFrequency(); // frequency
			int tagRecency = tag.getRecency(); // recency			
			
			int minFrequencyRestriction = 1; // default is excluding bottom 10%
			
			if (tagFrequency > minFrequencyRestriction) {
				
				inputQuery = inputQuery.replaceFirst("^\\s", ""); // remove the space character at the beginning of string
				String inputQuerySplit = inputQuery.replace(" ", "+");

				String newQuery = "";

				if (inputQuerySplit.matches(".*" + tagName + ".*")) {
					newQuery = inputQuerySplit;
				}
				else {
					newQuery = inputQuerySplit + "+" + tagName;
				}

				
				if (startYear.equals("--")) // remove "--" in startYear parameter
					startYear = "";
				if (endYear.equals("--")) // remove "--" in endYear parameter
					endYear = "";
				
				if (linkType.equals("continuous")) {//*** Create Link for continuous browsing ***//
					link += ("query=" + newQuery + "&type=" + cloudType + 
							 "&startYear=" + startYear + "&endYear=" + endYear +
							 "&option=" + cloudOption + 
							 "&percent=" + cloudOptionValue + "&recent=" + cloudOptionValue);
				}
				else if (linkType.equals("pubmed") || linkType.equals("igoogle")) { //*** Create Link for browsing PubMed Articles ***//
					link += newQuery;
				}

				cloudHTML = cloudHTML + "<a class=\"tag_cloud_frequency_" + tagFrequency +
				" tag_cloud_recency_" + tagRecency + "\" href=\"" + link + "\" target=\"blank\" title=\"" + tagWordList + "\" > " + 
				tagName + "</a> \n";
			}

			iGoogleGadgetTagCount++;
		}
		return cloudHTML;
	}
	
	public String generateHtmlCloud_CountTags (int numTags) {
		String cloudHTML = "";

		generatedCloud.orderBy("frequency");

		List tagList = generatedCloud.getTags();
		
		// create a new PubMedCloud for simplified cloud
		PubMedCloud simplifiedCloud = new PubMedCloud(inputQuery);
		
		Iterator<PubMedTag> tagIter = tagList.iterator();
		int tagCount = 0;

		
		while (tagIter.hasNext()) {
			
			if (tagCount == numTags) {
				break;
			}
			
			PubMedTag tag = tagIter.next();
			simplifiedCloud.addTag(tag);
			
			tagCount++;
		}
		
		simplifiedCloud.orderBy("representName");
		List simplifiedTagList = simplifiedCloud.getTags();
		Iterator<PubMedTag> simplifiedTagIter = simplifiedTagList.iterator();
		List<String> iGoogleTagList = new ArrayList<String>();
		
		while (simplifiedTagIter.hasNext()) {
			String link = "http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&term=";	

			PubMedTag tag = simplifiedTagIter.next();
			
			String tagName = tag.getRepresentName(); // name to be displayed
			String tagWordList = tag.getSimilarWordList(); // similar word list
			int tagFrequency = tag.getFrequency(); // frequency
			int tagRecency = tag.getRecency(); // recency			

				
			inputQuery = inputQuery.replaceFirst("^\\s", ""); // remove the space character at the beginning of string
			String inputQuerySplit = inputQuery.replace(" ", "+");

			String newQuery = "";

			if (inputQuerySplit.matches(".*" + tagName + ".*")) {
				newQuery = inputQuerySplit;
			}
			else {
				newQuery = inputQuerySplit + "+" + tagName;
			}

			link += newQuery;

			String tagElementString = "<a class=\"tag_cloud_frequency_" + tagFrequency +
			" tag_cloud_recency_" + tagRecency + "\" href=\"" + link + "\" target=\"blank\" title=\"" + tagWordList + "\" > " + 
			tagName + "</a> \n";
		
			cloudHTML = cloudHTML + tagElementString;
		}
				
		return cloudHTML;
	}
	
	
	public String generateHtmlCloud_iGoogle () {
		String cloudHTML = "";

		generatedCloud.orderBy("frequency");

		List tagList = generatedCloud.getTags();
		
		// create a new PubMedCloud for simplified cloud
		PubMedCloud simplifiedCloud = new PubMedCloud(inputQuery);
		
		Iterator<PubMedTag> tagIter = tagList.iterator();
		int iGoogleGadgetTagCount = 0;
		int iGoogleGadgetTagCountMax = 50;

		
		while (tagIter.hasNext()) {
			
			if (iGoogleGadgetTagCount == iGoogleGadgetTagCountMax) {
				break;
			}
			
			PubMedTag tag = tagIter.next();
			simplifiedCloud.addTag(tag);
			
			iGoogleGadgetTagCount++;
		}
		
		simplifiedCloud.orderBy("representName");
		List simplifiedTagList = simplifiedCloud.getTags();
		Iterator<PubMedTag> simplifiedTagIter = simplifiedTagList.iterator();
		List<String> iGoogleTagList = new ArrayList<String>();
		
		while (simplifiedTagIter.hasNext()) {
			String link = "http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&term=";	

			PubMedTag tag = simplifiedTagIter.next();
			
			String tagName = tag.getRepresentName(); // name to be displayed
			String tagWordList = tag.getSimilarWordList(); // similar word list
			int tagFrequency = tag.getFrequency(); // frequency
			int tagRecency = tag.getRecency(); // recency			

				
			inputQuery = inputQuery.replaceFirst("^\\s", ""); // remove the space character at the beginning of string
			String inputQuerySplit = inputQuery.replace(" ", "+");

			String newQuery = "";

			if (inputQuerySplit.matches(".*" + tagName + ".*")) {
				newQuery = inputQuerySplit;
			}
			else {
				newQuery = inputQuerySplit + "+" + tagName;
			}

			link += newQuery;

			String tagElementString = "<a class=\"tag_cloud_frequency_" + tagFrequency +
			" tag_cloud_recency_" + tagRecency + "\" href=\"" + link + "\" target=\"blank\" title=\"" + tagWordList + "\" > " + 
			tagName + "</a> \n";
		
			cloudHTML = cloudHTML + tagElementString;
		}
				
		return cloudHTML;
	}
	

	private int computeDaysSince1900 (Date abstractDate) {
		int year = abstractDate.getYear();
		int month = abstractDate.getMonth();
		int day = abstractDate.getDate();
		int daysSince1900 = (year-1900)*365 + month * 30 + day;
		return daysSince1900;
	}
	
	
	/**************************************************
	 * Checks if a word is a common word
	 * 
	 * @param
	 * @return  
	 **************************************************/ 
	private boolean isCommonWord (String word) {
		Integer prev = null;
		if ((prev = (Integer)commonWordHash.get(word)) != null)
			return true;
		else 
			return false;
	}
	
	
	/**************************************************
	 * Method that checks if the word contains a digit
	 * 
	 * @param
	 * @return  
	 **************************************************/ 
	private boolean containsDigit (String word) {
		if ( word.contains("0") || word.contains("1") || word.contains("2") || word.contains("3") || 
			 word.contains("4") || word.contains("5") || word.contains("6") || word.contains("7") || 
			 word.contains("8") || word.contains("9") )
			return true;
		else
			return false;
	}
	
	
	/**************************************************
	 * Method that checks if the word contains a join symbol
	 * 
	 * @param
	 * @return  
	 **************************************************/ 
	private boolean containsJoinSymbol (String word) {

		if ( word.contains("-") || word.contains("_") || word.contains("/") || word.contains("://"))
				return true;
			else
				return false;
	}
	
	
	/*************************************************************************
	 * Stem word-by-word the abstract text using Porter's Stemming Algorithm *
	 * 
	 * @param
	 * @return  
	 *************************************************************************/
	private String removeSymbols (String word) {
		String tempWord = word;
		
		// Remove unnecessary symbols
		tempWord = tempWord.replace("(", "");	tempWord = tempWord.replace(")", "");
		tempWord = tempWord.replace("_", ""); 	tempWord = tempWord.replace("&", "");
		tempWord = tempWord.replace("%", ""); 	tempWord = tempWord.replace("+", "");
		tempWord = tempWord.replace("=", ""); 	tempWord = tempWord.replace("*", "");
		tempWord = tempWord.replace("^", "");	tempWord = tempWord.replace("$", "");
		tempWord = tempWord.replace("#", "");	tempWord = tempWord.replace("@", "");
		tempWord = tempWord.replace("~", "");	tempWord = tempWord.replace("`", "");
		tempWord = tempWord.replace("?", "");	tempWord = tempWord.replace("|", "");
		tempWord = tempWord.replace("{", "");	tempWord = tempWord.replace("}", "");
		tempWord = tempWord.replace("[", "");	tempWord = tempWord.replace("]", "");
		tempWord = tempWord.replace("<", "");	tempWord = tempWord.replace(",", "");
		tempWord = tempWord.replace(".", "");	tempWord = tempWord.replace(":", "");
		tempWord = tempWord.replace("!", "");	tempWord = tempWord.replace("\\", "");
		tempWord = tempWord.replace("\"", "");	tempWord = tempWord.replace("\'", "");

		return tempWord;
	}
	
	public String getPMIDListHtml () {
		return PMIDListHtml;
	}
	
	public int getPMIDListCount () {
		return PMIDListCount;
	}

	private void generatePMIDListHtml (ArrayList<PubMedAbstract> abstractList) {
		String listHTML = "";
		String PubMedAPI = "http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?DB=pubmed&Cmd=ShowDetailView&TermToSearch=";
		PMIDListCount = abstractList.size();
		
		for (int i = 0; i < abstractList.size(); i++) {
			PubMedAbstract currentAbstract = abstractList.get(i);
			
			String currentID = currentAbstract.getPMID();
			String currentTitle = currentAbstract.getAbstractTitle();

			PMIDListHtml += "<A HREF=\"" + PubMedAPI + currentID + "\" TITLE=\"" + currentTitle + "\" TARGET=\"_blank\">" + currentID + "</A>\n";

		}
		
		/*
		PMIDListHtml = "";
		String [] IDListArray = IDListString.split(",");
		PMIDListCount = IDListArray.length;
		
		for (int i = 0; i < IDListArray.length; i++) {
			String currentID = IDListArray[i];
		}
		*/
		
		return;
	}
	
	/*
	private void generatePMIDListHtml (String IDListString) {
		//String listHTML = "";
		PMIDListHtml = "";
		String [] IDListArray = IDListString.split(",");
		
		
		String PubMedAPI = "http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?DB=pubmed&Cmd=ShowDetailView&TermToSearch=";
		
		for (int i = 0; i < IDListArray.length; i++) {
			String currentID = IDListArray[i];
			PMIDListHtml += "<A HREF=\"" + PubMedAPI + currentID + "\" TARGET=\"_blank\">" + currentID + "</A>\n";
		}
		
		return;
	}
	*/
	
	
	public Vector<Integer> computeTagStemmingFrequency () {
	
		Vector<Integer> countStemmedWordsVector = new Vector<Integer>();
		List tagList = generatedCloud.getTags();
		Iterator<PubMedTag> tagIter = tagList.iterator();
		
		while (tagIter.hasNext()) {
			PubMedTag tag = tagIter.next();
			String similarWordList = tag.getSimilarWordList();
			String[] similarWordListSplit = similarWordList.split(", ");
			int similarWordListSplitLength = similarWordListSplit.length;
			countStemmedWordsVector.add(new Integer(similarWordListSplitLength));
		}

		return countStemmedWordsVector;
	}
	
}