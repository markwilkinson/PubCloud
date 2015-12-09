/**
 * 
 */
package org.icapture.tag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.apache.log4j.Logger;

/**
 * @author benjamgo
 *
 */
public class Tag {
	//Map feature names such as "frequency" and "recency" to values for this tag
	public Map<String,TagFeature> tagFeatures;
	//private static Logger logger = Logger.getLogger("Tag");
	
	public Tag(){
		tagFeatures = new HashMap<String,TagFeature>();
	}
	
	public static void main(String[] args) {
		Tag tm = new Tag();
		tm.tagFeatures.put("name", new TagFeature("hairy"));
		tm.tagFeatures.put("frequency", new TagFeature(10));
		tm.tagFeatures.put("emotion", new TagFeature("love"));
		double f = tm.tagFeatures.get("frequency").fDoubleVal;
		//logger.info(tm);
	}
	
	/**
	 * Provide a String representation of this tag.  
	 */
	public String toString(){
		String tag = "Tag:\n";
		Iterator t = tagFeatures.entrySet().iterator();
		while(t.hasNext()){
			Map.Entry s = (Map.Entry)t.next();
			tag+=s.getKey()+"\t"+s.getValue()+"\n";
		}	
		return tag;
	}


	public Map getTagFeatures() {
		return tagFeatures;
	}

	public void setTagFeatures(Map<String,TagFeature> tagFeatures) {
		this.tagFeatures = tagFeatures;
	}
	
	
}
