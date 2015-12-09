<%@ page import="java.util.*"%>
<jsp:useBean id="PubCloudGenerator"
	class="org.icapture.tag.PubMed.PubMedCloudGeneratorBean"
	scope="session" />

<HTML>

<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="style.css" rel="stylesheet" type="text/css">
</HEAD>

<TITLE>PubCloud</TITLE>

<BODY>
<!-- -------------------------------- Title --------------------------------- -->
<TABLE cellpadding="5" border="0" width="500">
	<TR>
		<TD>
		<H1>PubCloud</H1>
		</TD>
		<TD><SPAN class="fine_print">Publication (<A
			HREF="http://www.cs.bell-labs.com/cm/cs/who/pfps/temp/web/www2007.org/posters/poster1046.pdf"
			TARGET="BLANK">PDF</A>) (<A
			HREF="http://www2007.org/htmlposters/poster1046/" TARGET="BLANK">HTML</A>)</SPAN></TD>
		<TD><SPAN class="fine_print"><A HREF="legend.html"
			TARGET="_blank"
			ONCLICK="window.open('legend.html', 'PubCloud Legend', 'toolbar=no, directories=no, location=no, status=no, menubar=no, resizable=no, scrollbars=no, width=258,height=200'); return false">Legend</A></SPAN></TD>
		<TD><SPAN class="fine_print"><A
			HREF="javascript:window.print();">Print</A></SPAN></TD>
		<TD><SPAN class="fine_print"><A
			HREF="javascript:window.close();">Close</A></SPAN></TD>
	</TR>
</TABLE>
<!-- ------------------------------------------------------------------------ -->

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
	/************ Session Code ***************/
	/*
	HttpSession tsession = request.getSession();
	String pQuery = tsession.getAttribute("pQuery");
	String nQuery = pQuery + " " + query;
	tsession.setAttribute("pQuery", nQuery);
	*/
	/*****************************************/

	if (startYear.equals("")) {
		startYear = "--";
	}
	if (endYear.equals("")) {
		 endYear = "--";
	}
	
	int optionValue = 10;

	if (option.equals("random")) {
		if (! percent.equals("")) {
			optionValue = Integer.parseInt(percent);
		}
		else {
			optionValue = 10;
		}
		out.println("Cloud Option: <SPAN class=\"key\">Randomly choose " + optionValue + "% of all articles</SPAN><BR>");
	}
	else if (option.equals("most_recent")) {
		if (! recent.equals("")) {
			optionValue = Integer.parseInt(recent);
		}
		else {
			optionValue = 100;
		}
		out.println("Cloud Option: <SPAN class=\"key\">Most recent " + optionValue + " articles</SPAN><BR>");
	}



	
	boolean checkCloud = false;
	checkCloud = PubCloudGenerator.mainBuildCloud(newQuery, startYear, endYear, type, option, optionValue);
	
	// make a split search string for later use
	String keywordSplitSearchString = newQuery.replace(" ", "+");	

	out.println("<TABLE border=\"0\"><TR><TD><BR><CENTER>");

	String cloudHTML = "";
	
	if (checkCloud == true) {
		cloudHTML = PubCloudGenerator.generateHtmlCloud();
	}

	out.println("<SPAN class=\"option_text\">");

	out.println("PubMed Query: <SPAN class=\"key\">" + query + "</SPAN><br>");
	out.println("Cloud Type: <SPAN class=\"key\">" + type + "</SPAN><BR>");
	out.println("Date range from <SPAN class=\"key\">" + startYear + "</SPAN> to <SPAN class=\"key\">" + endYear + "</SPAN><BR>");
	out.println("PubMed Articles: <SPAN class=\"key\">" + idListHtml + "</SPAN>");

	out.println("</SPAN>");	


	out.println(cloudHTML);
	out.println("</CENTER></TD></TR></TABLE>");
}
%>
<BR>
<SPAN class="fine_print">Comments or questions, please contact: <I><A
	HREF="mailto:bkuo@mrl.ubc.ca">Byron Kuo</A></I>. Copyright (c) 2006</SPAN>
</BODY>
</HTML>
