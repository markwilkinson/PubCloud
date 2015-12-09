package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class gc_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      org.icapture.tag.PubMed.PubMedCloudGeneratorBean PubCloudGenerator = null;
      synchronized (session) {
        PubCloudGenerator = (org.icapture.tag.PubMed.PubMedCloudGeneratorBean) _jspx_page_context.getAttribute("PubCloudGenerator", PageContext.SESSION_SCOPE);
        if (PubCloudGenerator == null){
          PubCloudGenerator = new org.icapture.tag.PubMed.PubMedCloudGeneratorBean();
          _jspx_page_context.setAttribute("PubCloudGenerator", PubCloudGenerator, PageContext.SESSION_SCOPE);
        }
      }
      out.write("\r\n");
      out.write("\r\n");
      out.write("<HTML>\r\n");
      out.write("\r\n");
      out.write("<HEAD>\r\n");
      out.write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<META http-equiv=\"Content-Style-Type\" content=\"text/css\">\r\n");
      out.write("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("\r\n");
      out.write("<SCRIPT LANGUAGE=\"JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction showHide(elementid){\r\n");
      out.write("\t\tif (document.getElementById(elementid).style.display == 'none'){\r\n");
      out.write("\t\t\tdocument.getElementById(elementid).style.display = '';\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tdocument.getElementById(elementid).style.display = 'none';\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * Following function is inspired by Blogger's select label function\r\n");
      out.write(" * Taken and edited from http://www.blogger.com/v-app/scripts/720937736-richedit.blogger.js\r\n");
      out.write(" * 2007/09/28\r\n");
      out.write(" * Responds to a click on a label by adding that label to the input box. */\r\n");
      out.write("\r\n");
      out.write("\tfunction BrowseAddTag(tag) {\r\n");
      out.write("\t\tvar tagInput = document.getElementById(\"browse-tags\");\r\n");
      out.write("\t  \r\n");
      out.write("\t\tif (!tagInput) return;\r\n");
      out.write("\t  \r\n");
      out.write("\t\tvar curVal = Trim(tagInput.value);\r\n");
      out.write("\t\r\n");
      out.write("\t\tif (curVal == \"\") {\r\n");
      out.write("\t\t\ttagInput.value = tag.innerHTML;\r\n");
      out.write("\t  \t} else {\r\n");
      out.write("\t    \t// Remove excess whitespace\r\n");
      out.write("\t    \tvar newTag = Trim(tag.innerHTML);\r\n");
      out.write("\t    \tvar tags = curVal.split(',');\r\n");
      out.write("\t    \tvar found = false;\r\n");
      out.write("\t    \t\r\n");
      out.write("\t    \t// See if the tag already is in the text box\r\n");
      out.write("\t    \tfor (var i=0; i < tags.length; i++) {\r\n");
      out.write("\t      \t\ttags[i] = Trim(tags[i]);\r\n");
      out.write("\t      \t\tif (tags[i] == newTag) found = true;\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t    \r\n");
      out.write("\t    \t// If not, add it.\r\n");
      out.write("\t    \tif (!found) {\r\n");
      out.write("\t      \t\ttags[tags.length] = newTag;\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t    \t\r\n");
      out.write("\t    \t// Remove any whitespace-only elements from the array.\r\n");
      out.write("\t    \tvar newTags = new Array();\r\n");
      out.write("\t    \tfor (var i=0; i < tags.length; i++) {\r\n");
      out.write("\t      \t\tif (tags[i] != \"\") {\r\n");
      out.write("\t        \t\tnewTags[newTags.length] = tags[i];\r\n");
      out.write("\t      \t\t}\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t    \r\n");
      out.write("\t    \t// Put it back together.\r\n");
      out.write("\t    \ttagInput.value = newTags.join(\", \") + \", \";\r\n");
      out.write("\t  \t}\r\n");
      out.write("\t}\r\n");
      out.write("-->\r\n");
      out.write("\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("\r\n");
      out.write("</HEAD>\r\n");
      out.write("\r\n");
      out.write("<TITLE>PubCloud</TITLE>\r\n");
      out.write("\r\n");
      out.write("<BODY>\r\n");
      out.write("<!-- -------------------------------- Title --------------------------------- -->\r\n");
      out.write("<TABLE cellpadding=\"5\" border=\"0\" width=\"500\">\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<H1>PubCloud</H1>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\">Publication (<A\r\n");
      out.write("\t\t\tHREF=\"http://www.cs.bell-labs.com/cm/cs/who/pfps/temp/web/www2007.org/posters/poster1046.pdf\"\r\n");
      out.write("\t\t\tTARGET=\"BLANK\">PDF</A>) (<A\r\n");
      out.write("\t\t\tHREF=\"http://www2007.org/htmlposters/poster1046/\" TARGET=\"BLANK\">HTML</A>)</SPAN></TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\"><A HREF=\"legend.html\"\r\n");
      out.write("\t\t\tTARGET=\"_blank\"\r\n");
      out.write("\t\t\tONCLICK=\"window.open('legend.html', 'PubCloud Legend', 'toolbar=no, directories=no, location=no, status=no, menubar=no, resizable=no, scrollbars=no, width=258,height=200'); return false\">Legend</A></SPAN></TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\"><A\r\n");
      out.write("\t\t\tHREF=\"javascript:window.print();\">Print</A></SPAN></TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\"><A\r\n");
      out.write("\t\t\tHREF=\"javascript:window.close();\">Close</A></SPAN></TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("</TABLE>\r\n");
      out.write("<!-- ------------------------------------------------------------------------ -->\r\n");
      out.write("\r\n");
      out.write("<!-- \r\n");
      out.write("<FORM method=\"GET\" action=\"gc.jsp\" target=\"_blank\" id=\"tagform\">\r\n");
      out.write("<INPUT type=\"text\" name=\"browse-tags\" id=\"browse-tags\" tabindex=\"6\" autocomplete=\"off\">\r\n");
      out.write("<a href=\"#\" class=\"clickable-tag\" onclick=\"javascript:BrowseAddTag(this); return false;\">Tag</a> \r\n");
      out.write("<a href=\"#\" class=\"clickable-tag\" onclick=\"javascript:BrowseAddTag(this); return false;\">Cloud</a>\r\n");
      out.write("</FORM>\r\n");
      out.write("-->\r\n");
      out.write("\r\n");

String query = request.getParameter("query");
String startYear = request.getParameter("startYear");
String endYear = request.getParameter("endYear");
String type = request.getParameter("type");
String numTagCount = request.getParameter("numTagCount");
String option = request.getParameter("option");
String percent = request.getParameter("percent");
String recent = request.getParameter("recent");


int percentValue = 10;
int recentValue = 100;
int numTagCountValue = 100;

try {
	percentValue = Integer.parseInt(percent);
	recentValue = Integer.parseInt(recent);
	numTagCountValue = Integer.parseInt(numTagCount);
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
	
	out.println("<SPAN class=\"option_text\">");
	out.println("PubMed Query: <SPAN class=\"key\">" + query + "</SPAN><br>");
	out.println("Cloud Type: <SPAN class=\"key\">" + type + "</SPAN><BR>");


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
		out.println("Cloud Option: <SPAN class=\"key\">Most relevant " + optionValue + " articles</SPAN><BR>");
	}

	// Continue to print out parameter information
	out.println("Date range from <SPAN class=\"key\">" + startYear + "</SPAN> to <SPAN class=\"key\">" + endYear + "</SPAN><BR>");

	boolean checkCloud = false;
	checkCloud = PubCloudGenerator.mainBuildCloud(newQuery, startYear, endYear, type, option, optionValue, numTagCountValue);
	
	// make a split search string for later use
	String keywordSplitSearchString = newQuery.replace(" ", "+");	

	String idListHtml = PubCloudGenerator.getPMIDListHtml();
	int idListCount = PubCloudGenerator.getPMIDListCount();
	//out.println("PubMed IDs: <SPAN class=\"key\">" + idListHtml + "</SPAN><BR>");
	//out.println("</SPAN>");
	out.println("<a href=\"javascript:showHide('divID')\">Show/Hide all " + idListCount + " PubMed ID(s) </a>" +
				"<div id=\"divID\" class=\"key\" style=\"display:none;\">" + idListHtml + "</div>\n");

	//out.println("<TABLE cellpadding=\"5\" border=\"0\" style=\"background-image:url(blank-cloud.gif); background-repeat: no-repeat; background-position: center center;\"><TR><TD><BR><CENTER>");
	out.println("<TABLE cellpadding=\"5\" border=\"0\"><TR><TD><BR><CENTER>");

	String cloudHTML = "";
	
	if (checkCloud) {
		cloudHTML = PubCloudGenerator.generateHtmlCloud_CountTags(numTagCountValue);
	}
	else {
		cloudHTML = "<FONT color=\"red\">Number of articles exceeds maximum allowed!</FONT><BR>\n";
	}

	out.println(cloudHTML);
	out.println("</CENTER></TD></TR></TABLE>");
}

      out.write("\r\n");
      out.write("<BR>\r\n");
      out.write("<SPAN class=\"fine_print\">Comments or questions, please contact: <I><A\r\n");
      out.write("\tHREF=\"mailto:bkuo@mrl.ubc.ca\">Byron Kuo</A></I>. Copyright (c) 2006</SPAN>\r\n");
      out.write("</BODY>\r\n");
      out.write("</HTML>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
