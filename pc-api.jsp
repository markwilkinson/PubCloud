<%@ page import="java.util.*"%>
<jsp:useBean id="PubCloudGenerator"
	class="org.icapture.tag.PubMed.PubMedCloudGeneratorBean"
	scope="session" />
<%
String query = request.getParameter("query");
String startYear = request.getParameter("startYear");
String endYear = request.getParameter("endYear");
String type = request.getParameter("type");
String option = request.getParameter("option");
String percent = request.getParameter("percent");
String recent = request.getParameter("recent");

int percentValue = 10;
int recentValue = 100;

try {
	percentValue = Integer.parseInt(percent);
	recentValue = Integer.parseInt(recent);
}
catch (Exception e) {
}

if (query == null) {
	//out.println("Await keyword search!");
}
else if (query == "") {
	out.println("<FONT COLOR=\"red\"><B>ERROR:</B> You have not entered a PubMed query</FONT><BR>");
}

else if ( (option.equals("random")) && ((percentValue <= 0) || (percentValue > 100)) ) {
	out.println("<FONT COLOR=\"red\"><B>ERROR:</B> You have specified an invalid % value</FONT><BR>");
	out.println(percentValue + "% selected");
}
else if ( (option.equals("most_recent")) && (recentValue <= 0) ) {
	out.println("<FONT COLOR=\"red\"><B>ERROR:</B> You have specified an invalid number of articles</FONT><BR>");
	out.println(recentValue + " articles selected");
}
else {
	String prevQuery = "";
	String newQuery = prevQuery + " " + query;

	int optionValue = 10;

	if (option.equals("random")) {
		if (! percent.equals("")) {
			optionValue = Integer.parseInt(percent);
		}
		else {
			optionValue = 10;
		}
	}
	else if (option.equals("most_recent")) {
		if (! recent.equals("")) {
			optionValue = Integer.parseInt(recent);
		}
		else {
			optionValue = 100;
		}
	}

	boolean checkCloud = false;
	checkCloud = PubCloudGenerator.mainBuildCloud(newQuery, startYear, endYear, type, option, optionValue);
	
	// make a split search string for later use
	String keywordSplitSearchString = newQuery.replace(" ", "+");	

	String idListHtml = PubCloudGenerator.getPMIDListHtml();

	String cloudHTML = "";
	
	if (checkCloud) {
		cloudHTML = PubCloudGenerator.generateHtmlCloud("pubmed");
	}
	else {
		cloudHTML = "<FONT color=\"red\">Number of articles exceeds maximum allowed!</FONT><BR>\n";
	}

	out.println(cloudHTML);

}
%>
