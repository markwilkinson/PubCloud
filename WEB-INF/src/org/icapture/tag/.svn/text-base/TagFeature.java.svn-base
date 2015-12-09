/**
 * 
 */
package org.icapture.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author benjamgo
 *
 */
public class TagFeature {
	public String fStringVal;
	public double fDoubleVal;
	public int	  fIntVal;
	public List   fListVal;
	
	TagFeature(int iVal){
		fIntVal = iVal;
		fDoubleVal = (double)iVal;
		fStringVal = ""+iVal;
		fListVal = new ArrayList();
		fListVal.add(iVal);
	}
	
	public TagFeature(double dVal){
		fIntVal = (int)dVal;
		fDoubleVal = dVal;
		fStringVal = ""+dVal;
		fListVal = new ArrayList();
		fListVal.add(dVal);
	}
	
	public TagFeature(String Val){
		fIntVal = -1;
		fDoubleVal = -1;
		fStringVal = Val;
		fListVal = new ArrayList();
		fListVal.add(Val);
	}
	
	public TagFeature(List lVal){
		fIntVal = -1;
		fDoubleVal = -1;
		Iterator i = lVal.iterator();
		while(i!=null&&i.hasNext()){
			fStringVal += i.next().toString()+"\t";
		}
		fListVal = lVal;
	}
	
	public String toString(){
		return fStringVal;
	}
}
