<%@ page import="java.util.*"%>
<jsp:useBean id="PubCloudGenerator"
	class="org.icapture.tag.PubMed.PubMedCloudGeneratorBean"
	scope="session" />

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<style type="text/css">
<!--
a:link {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}

a:hover {
	background-color: #55FF55;
}

body {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 0.9em;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #FFFFFF;
}

.key_title {
	font-size: 0.8em;
}

.key {
	font-size: 0.8em;
	color: blue;
	font-style: bold;
	font-weight: bolder;
}

a.tag_cloud_frequency_0 {
	font-size: 5px
}

a.tag_cloud_frequency_1 {
	font-size: 6px
}

a.tag_cloud_frequency_2 {
	font-size: 7px
}

a.tag_cloud_frequency_3 {
	font-size: 8px
}

a.tag_cloud_frequency_4 {
	font-size: 9px
}

a.tag_cloud_frequency_5 {
	font-size: 10px
}

a.tag_cloud_frequency_6 {
	font-size: 11px
}

a.tag_cloud_frequency_7 {
	font-size: 12px
}

a.tag_cloud_frequency_8 {
	font-size: 13px
}

a.tag_cloud_frequency_9 {
	font-size: 14px
}

a.tag_cloud_frequency_10 {
	font-size: 15px
}

a.tag_cloud_recency_0 {
	color: #5B7884
}

a.tag_cloud_recency_1 {
	color: #5B7884
}

a.tag_cloud_recency_2 {
	color: #94AAB5
}

a.tag_cloud_recency_3 {
	color: #94AAB5
}

a.tag_cloud_recency_4 {
	color: #C0B0C4
}

a.tag_cloud_recency_5 {
	color: #C0B0C4
}

a.tag_cloud_recency_6 {
	color: #C0B0C4
}

a.tag_cloud_recency_7 {
	color: #FF8499
}

a.tag_cloud_recency_8 {
	color: #FF8499
}

a.tag_cloud_recency_9 {
	color: #EA1A29
}

a.tag_cloud_recency_10 {
	color: #EA1A29
}
-->
</style>

<script type="text/javascript">

function getInputText() {
	value = document.SampleForm.myText.value.toUpperCase()
	return value
}

function getCookie(c_name)
{
	if (document.cookie.length>0)
  	{
  		c_start=document.cookie.indexOf(c_name + "=")
  		if (c_start!=-1)
    	{ 
    		c_start=c_start + c_name.length+1 
    		c_end=document.cookie.indexOf(";",c_start)
    		if (c_end==-1) c_end=document.cookie.length
    		return unescape(document.cookie.substring(c_start,c_end))
    	} 
  	}
	return ""
}

function setCookie(c_name, value, expiredays)
{
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
		((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

function checkCookie()
{
	querystring=getCookie('query')
	if (querystring!=null && querystring!="")
 	{
 		window.location="http://bioinfo.icapture.ubc.ca:8090/PubCloud/pc.jsp?query=" + querystring
 	}
}

function setClickCookie()
{
  	var textfield = document.getElementById('query-field')
  	querystring=textfield.value
  		
  	if (querystring!=null && querystring!="") {
	    setCookie('query',querystring,365)    		
    }
}

</script>

</HEAD>
<BODY>

<FONT color="blue"><B>iPubCloud</B></FONT>
<BR>
<FORM method="GET"
	action="http://bioinfo.icapture.ubc.ca:8090/PubCloud/pcg.jsp">
Query: <INPUT TYPE="text" NAME="query" ID="query-field" SIZE="13">
<INPUT TYPE="submit" VALUE="Get Cloud" ONCLICK="setClickCookie()">
</FORM>

<%
String query = request.getParameter("query");
String startYear = "";
String endYear = "";
String type = "abstract";
String option = "most_recent";
int	optionValue = 100;
int recentValue = 100;

if (query == null) {
	//out.println("Await keyword search!");
}
else if (query == "") {
	out.println("<FONT COLOR=\"red\"><B>ERROR:</B> You have not entered a PubMed query</FONT><BR>");
}
else {

	out.println("Query: <SPAN class=\"key\">" + query + "</SPAN><br><br>");
	
	boolean checkCloud = PubCloudGenerator.mainBuildCloud(query, startYear, endYear, type, option, optionValue);

	String idListHtml = PubCloudGenerator.getPMIDListHtml();

	String cloudHTML = "";
	
	if (checkCloud) {
		cloudHTML = PubCloudGenerator.generateHtmlCloud_iGoogle();
	}
	else {
		cloudHTML = "<FONT color=\"red\">Number of articles exceeds maximum allowed!</FONT><BR>\n";
	}

	out.println( "<CENTER>\n" + cloudHTML + "\n</CENTER>\n");

}
%>

</BODY>

</HTML>