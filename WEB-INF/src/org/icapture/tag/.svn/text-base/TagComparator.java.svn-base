/**
 * 
 */
package org.icapture.tag;

import java.util.Comparator;

/**
 * @author benjamgo
 *
 */
public class TagComparator implements Comparator {
	public String key; // will indicate which feature to compare with
	public TagComparator(String k){
		key = k;
	}

	/**
	 * Compares two tags 
	 * sorts in ascending alphabetical order if its a String
	 * in descending order if its a number
	 */
	public int compare(Object arg0, Object arg1) {
		Tag b1 = (Tag)arg0;
		Tag b2 = (Tag)arg1;
		TagFeature f1 = b1.tagFeatures.get(key);
		TagFeature f2 = b2.tagFeatures.get(key);
		//check for a String feature
		if(f1.fDoubleVal==-1||f2.fDoubleVal==-1){		
			return f1.fStringVal.compareToIgnoreCase(f2.fStringVal);
		}
		//otherwise use the double 
		else{
			double b1_compare = f1.fDoubleVal;
			double b2_compare = f2.fDoubleVal;
		
			if(b1_compare > b2_compare){
				return -1;
			}
			if(b1_compare==b2_compare){
				return 0;
			}
			if(b1_compare < b2_compare){
				return 1;
			}
		}
		return 0;
	}
}
