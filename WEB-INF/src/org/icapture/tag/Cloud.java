package org.icapture.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.icapture.tag.deprecated.Constants;


/**
 * Represent and generate tag clouds from TagBeans
 * @author benjamgo
 *
 */
/**
 * @author benjamgo
 *
 */
public class Cloud {

	public List<Tag> tags;
	public String html;
	public static String style;
	public String inputQuery;

	/**
	 * Default constructor.  Matches color to time, size to frequency, and sorts alphabetically
	 *
	 */
	public Cloud(String inputQuery){
		setInputQuery(inputQuery);
		style = "/cloud/cloud_home.css";
		html = "";
		tags = new ArrayList<Tag>();	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void orderBy(String key){
		Comparator c = new TagComparator(key);
		Collections.sort(tags,c);
		return;
	}

	/**
	 * Set the cloudFeature (e.g. size) based on the tagFeature - e.g. frequency for a list of tags
	 * The range of the bins is determined by the max number in the input list
	 * cF = 1 - tF/(tfMax/BinMax)
	 * 
	 * Relative cloudWeights are directly proportional to input feature (not very good for highly skewed data)
	 * 
	 * @param tags
	 * @return
	 */
	public List<Tag> simpleFeatureToCloudMap( 
			String tagFeature, 
			String cloudFeature, 
			int BinMax){
		if(tags==null){
			return null;
		}
		Iterator i = tags.iterator();
		//get total
		int total = 0;
		double max = 0;
		double min = 100;
		double c = 0;
		double scale = 0;
		
		//set up the range for the bins
		while(i.hasNext()){
			Tag t = (Tag)i.next();
			c = t.tagFeatures.get(tagFeature).fDoubleVal;
			if(c > max){ max = c;}
			if(c < min){ min = c;}
			total+= c;
		}		
		
		i = tags.iterator();
		scale = max/BinMax;
		
		//assign the cloudFeature 
		while(i.hasNext()){
			Tag t = (Tag)i.next();
			int importance =  (int)(t.tagFeatures.get(tagFeature).fDoubleVal/scale);
			t.tagFeatures.put(cloudFeature, new TagFeature(importance));
		}
	
		return tags;
	}
	

	
	/**
	 * For the current set of tags, build an HTML cloud.  
	 *  
	 **/
	public void setHtmlAsList(String styleSheetURL){
		String cloud = "<link rel=\"stylesheet\" type=\"text/css\" href=\"" +
				styleSheetURL+"\">";
		
		cloud+= " <div id=\"tag-cloud-container\">"+
		  		" <div id=\"tag-cloud\">\n";
		
		for(int i=0;i<tags.size();i++){
		 		//write out tag links
				Tag tag = tags.get(i);
	    		String uri = 
	    		 "<a class=\"tag_cloud tag_cloud_frequency_"+tag.tagFeatures.get("size")+" " +
	    		 		" tag_cloud_recency_"+tag.tagFeatures.get("color")+"\" " +
	    				" href=\""; 
		    				uri+=tag.tagFeatures.get("targetURL");
	    			uri+="\" title=\""+tag.tagFeatures.get("name")+"\">"+tag.tagFeatures.get("name")+"</a>";
	    			tag.tagFeatures.put("htmlForCloud", new TagFeature(uri)); //htmlForCloud
	    		cloud+=uri+"\n";
		 }
	
		
		cloud+="</div></div>\n";
		this.html = cloud;
	}	
	
	/**
	 * For the current set of tags, build an HTML cloud.  
	 *  
	 **/
	public void setHtmlAsTable(String styleSheetURL){
		String cloud = "<link rel=\"stylesheet\" type=\"text/css\" href=\"" +
				styleSheetURL+"\">";
		
		cloud+= " <div id=\"tag-cloud-container\">"+
		  		" <div id=\"tag-cloud\">\n";
		int cols = 10;
		int rows = 10;
		int X = 1;
		int Y = 0;
		cloud+="<table><tr><td>Query:"+getInputQuery()+"</td>";
		//write out out the cells in English reading order
		for(int i=0;i<tags.size();i++){
			X++;
			//finish row
			if(X==cols-1){
				Y++;
				X=0;
				cloud+="</tr>";
				//finish table
				if(Y==rows-1){
					break;
				}
			}
		
		 		//write out tag links
			cloud+="<td>";	
			Tag tag = tags.get(i);
			String uri = 
	    		 "<a class=\"tag_cloud tag_cloud_frequency_"+tag.tagFeatures.get("size")+" " +
	    		 		" tag_cloud_recency_"+tag.tagFeatures.get("color")+"\" " +
	    				" href=\""; 
		   				uri+=tag.tagFeatures.get("targetURL");
		 
	    			uri+="\" title=\""+tag.tagFeatures.get("name")+"\">"+tag.tagFeatures.get("name")+"</a>";
	    			tag.tagFeatures.put("htmlForCloud", new TagFeature(uri)); //htmlForCloud
	    		cloud+=uri+"\n";
	    	cloud+="</td>";	
		 }
		cloud+="</table>";
		
		cloud+="</div></div>\n";
		this.html = cloud;
	}
	
	public List getTags() {
		return tags;
	}

	public void setTags(List tags) {
		this.tags = tags;
	}

	public String getHtml() {
		return html;
	}

	public String getInputQuery() {
		return inputQuery;
	}

	public void setInputQuery(String inputQuery) {
		this.inputQuery = inputQuery;
	}

	
}
