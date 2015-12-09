package org.icapture.tag.PubMed;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

public class PubMedMain {
	
	public static void main (String[] args) {
		/*
		String inputQuery = "icapture";
		ArrayList<PubMedAbstract> abstractList = new ArrayList<PubMedAbstract>();
		PubMedTextProcessor PMTxtProc = new PubMedTextProcessor();
		
		PubMedConnectionBean PMConnect= new PubMedConnectionBean();
		PubMedConnectionContentParserBean PMConnectContentParser = new PubMedConnectionContentParserBean();
		
		String idURL = PMConnect.buildIdURL(inputQuery, "2006", "2006"); // Step 1
		String idURLContent = PMConnect.getIdURLContent(idURL); // Step 2
		
		//System.out.println(idURLContent);
		
		// ----------- Test ID List ONE BIG SUBMISSION ---------------//
		
		String idList = PMConnectContentParser.parsePubMedId(idURLContent);
		
		String fetchURL = PMConnect.buildFetchURL(idList);
		String fetchContent = PMConnect.getFetchURLContent(fetchURL);

		//System.out.println (fetchContent);
		
		abstractList  = PMConnectContentParser.getAbstractList(fetchContent);
		*/
		
		/*
		System.out.println("Start Cloud");
		
		PubMedCloudGeneratorBean CloudGen = new PubMedCloudGeneratorBean();
		
		CloudGen.mainBuildCloud("icapture", "", "", "abstract", "random", 100, 10000);
		
		String cloudHTML = CloudGen.generateHtmlCloud("continuous");
		
		System.out.println(cloudHTML);
		*/
		
		//PubMedCloud PubCloud = CloudGen.buildAbstractCloud(abstractList, inputQuery);

		// -------------- Test Getting Tag Info from Cloud Object -------------- //
		/*
		List tagList = PubCloud.getTags();
		Iterator<PubMedTag> tagIter = tagList.iterator();
		while (tagIter.hasNext()) {
			PubMedTag tag = tagIter.next();
			System.out.println ("Name: " + tag.getRepresentName() + "; Frequency: " + tag.getFrequency() +
								"; Recency: " + tag.getRe0cency());
		}
		*/
		// --------------------------------------------------------------------- //
		
		// ----------------- Test Abstract list printout ----------------- //
		/*
		Iterator <PubMedAbstract> abstractIterator = abstractList.iterator();
		while (abstractIterator.hasNext()) {
			PubMedAbstract currentAbstract = abstractIterator.next();
			System.out.println("PMID: " + currentAbstract.getPMID());
			System.out.println("Title: " + currentAbstract.getAbstractTitle());
			System.out.println("Abstract: " + currentAbstract.getAbstractText());
			System.out.println("Date: " + currentAbstract.getAbstractDate());
			System.out.println("Authors: " + currentAbstract.getAuthors());
			System.out.println("Mesh Terms: " + currentAbstract.getMeshTerms());
			System.out.println("");
		}
		*/
		// --------------------- End Test -------------------------------- //
		
		/*
		// go through the ID's
		for (int i = 0; i < idArray.length; i++) {
			String id = idArray[i];
			
			String fetchURL = PMConnect.buildFetchURL(id); // Step 3
			String fetchContent  = PMConnect.getFetchURLContent(fetchURL); // Step 4
			
			String originalIdAbstract = PMConnectContentParser.parsePubMedAbstract(fetchContent);
			Date idAbstractDate = PMConnectContentParser.parseAbstractDate(fetchContent);
			
			String stemmedIdAbstract = PMTxtProc.stemText(originalIdAbstract);
			
			PubMedAbstract PMAbstract = new PubMedAbstract(originalIdAbstract, stemmedIdAbstract, idAbstractDate);
			
			System.out.println(stemmedIdAbstract);

			//String summaryURL = PMConnect.buildSummaryURL(id); // Step 5
			//String summaryContent = PMConnect.getSummaryURLContent(summaryURL); // Step 6
		}
		*/
		
		PubMedCloudGeneratorBean CloudGen = new PubMedCloudGeneratorBean();
		
		String [] icapture_pmid_sample_array = {"18252918", "18220230", "18188017", "18187736", "18163821",
										  		"18094086", "18083905", "18073404", "18073399", "18060101",
										  		"18042978", "18039131", "17949479", "17713417", "17848167",
										  		"17823629", "17823628", "17822686", "17762191", "17720209",
										  		"17717322", "17705561", "17657165", "17644777", "17632592",
										  		"17556723", "17556723", "17513451", "17509528", "17487253",
										  		"17487248", "17468777", "17446135", "17426230", "17413871",
										  		"17413125", "17390088", "17346312", "17332924", "17331973"};

		System.out.println("PMID\\Count\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10");
		
		
		for (int i = 0; i < icapture_pmid_sample_array.length; i++) {

			String currentID = icapture_pmid_sample_array[i];
			CloudGen.mainBuildCloud(currentID, "", "", "abstract", "random", 100, 10000);				
			Vector<Integer> currentFrequencyVector = CloudGen.computeTagStemmingFrequency();
			
			int[] tempCountArray = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			for(int v = 0; v < currentFrequencyVector.size(); v++) {
				Integer currentValue = currentFrequencyVector.get(v);
				
				tempCountArray[currentValue.intValue()-1]++;
			}

			System.out.print(currentID);
			
			for (int j = 0; j < tempCountArray.length; j++) {
				
				System.out.print("\t" + tempCountArray[j]);
				
			}

			System.out.print("\n");

		}
	
	
	}
	

	
}