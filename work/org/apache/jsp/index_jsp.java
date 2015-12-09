package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("</HEAD>\r\n");
      out.write("\r\n");
      out.write("<TITLE>PubCloud</TITLE>\r\n");
      out.write("\r\n");
      out.write("<BODY>\r\n");
      out.write("<!-- -------------------------------- Title --------------------------------- -->\r\n");
      out.write("<TABLE cellpadding=\"5\" border=\"0\" width=\"415\">\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<H1>PubCloud</H1>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\">Home</SPAN></TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\"><A\r\n");
      out.write("\t\t\tHREF=\"http://www.cs.bell-labs.com/cm/cs/who/pfps/temp/web/www2007.org/posters/poster1046.pdf\"\r\n");
      out.write("\t\t\tTARGET=\"_BLANK\">Publication</A></SPAN></TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\"><A HREF=\"faq.html\"\r\n");
      out.write("\t\t\tTARGET=\"_blank\">FAQ</A></SPAN></TD>\r\n");
      out.write("\t\t<TD><SPAN class=\"fine_print\"><A\r\n");
      out.write("\t\t\tHREF=\"javascript:window.print();\">Print</A></SPAN></TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("</TABLE>\r\n");
      out.write("<!-- ------------------------------------------------------------------------ -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<FORM method=\"GET\" action=\"gc.jsp\" target=\"_blank\"><!-- Required for query generation: PubMed Query and Cloud Type -->\r\n");
      out.write("<TABLE cellpadding=\"5\" width=\"850\">\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<TABLE border=\"0\" width=\"800\">\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_title\">PubMed Query: <INPUT\r\n");
      out.write("\t\t\t\t\tTYPE=\"text\" NAME=\"query\" SIZE=\"100\"></SPAN></TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t</TABLE>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<TABLE border=\"0\" width=\"500\">\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_title\">Cloud Type: </SPAN></TD>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_text\"><INPUT TYPE=\"radio\"\r\n");
      out.write("\t\t\t\t\tNAME=\"type\" VALUE=\"abstract\" CHECKED> Abstracts</SPAN></TD>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_text\"><INPUT TYPE=\"radio\"\r\n");
      out.write("\t\t\t\t\tNAME=\"type\" VALUE=\"author\"> Authors</SPAN></TD>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_text\"><INPUT TYPE=\"radio\"\r\n");
      out.write("\t\t\t\t\tNAME=\"type\" VALUE=\"mesh\"> MeSH Terms</SPAN></TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t</TABLE>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("</TABLE>\r\n");
      out.write("<!-- ---------------------------------------------------------- --> <BR>\r\n");
      out.write("\r\n");
      out.write("<!-- ------------------- Control Panel ------------------------ -->\r\n");
      out.write("<TABLE cellpadding=\"5\" width=\"850\"\r\n");
      out.write("\tSTYLE=\"border-style: dashed; border-width: thin; border-color: blue\">\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD><SPAN class=\"option_panel_title\">Control Panel</SPAN></TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("\t\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD><SPAN class=\"option_title\">Tag Option:</SPAN></TD>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<TABLE cellpadding=\"5\" border=\"0\" width=\"650\">\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD>\r\n");
      out.write("\t\t\t\t<SPAN class=\"option_text\">\r\n");
      out.write("\t\t\t\t<INPUT TYPE=\"radio\" NAME=\"tag_option\" VALUE=\"numTag\" CHECKED>\r\n");
      out.write("\t\t\t\tNumber: <INPUT TYPE=\"text\" NAME=\"numTagCount\" SIZE=\"10\" VALUE=\"100\">\r\n");
      out.write("\t\t\t\t</SPAN>\r\n");
      out.write("\t\t\t\t</TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD>\r\n");
      out.write("\t\t\t\t<SPAN class=\"option_text\">\r\n");
      out.write("\t\t\t\t<INPUT TYPE=\"radio\" NAME=\"tag_option\" VALUE=\"percentTags\">\r\n");
      out.write("\t\t\t\tPercentage: <INPUT TYPE=\"text\" NAME=\"percentTagCount\" SIZE=\"10\" VALUE=\"50\">%\r\n");
      out.write("\t\t\t\t</SPAN>\r\n");
      out.write("\t\t\t\t</TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t</TABLE>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("\t\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD><SPAN class=\"option_title\">Date Range: </SPAN></TD>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<TABLE cellpadding=\"5\" border=\"0\" width=\"650\">\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_text\">From <INPUT TYPE=\"text\"\r\n");
      out.write("\t\t\t\t\tNAME=\"startYear\" SIZE=\"5\"> to <INPUT TYPE=\"text\"\r\n");
      out.write("\t\t\t\t\tNAME=\"endYear\" SIZE=\"5\"> (Default: no range)</SPAN></TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t</TABLE>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD><SPAN class=\"option_title\">Search Option: </SPAN></TD>\r\n");
      out.write("\t\t<TD>\r\n");
      out.write("\t\t<TABLE cellpadding=\"5\" border=\"0\" width=\"650\">\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_text\"><INPUT TYPE=\"radio\"\r\n");
      out.write("\t\t\t\t\tNAME=\"option\" VALUE=\"most_recent\" CHECKED> Most (NCBI\r\n");
      out.write("\t\t\t\tsearch) relevant <INPUT TYPE=\"text\" NAME=\"recent\" SIZE=\"5\"\r\n");
      out.write("\t\t\t\t\tVALUE=\"100\"> articles (Default: 100 articles)</SPAN></TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t\t<TR>\r\n");
      out.write("\t\t\t\t<TD><SPAN class=\"option_text\"><INPUT TYPE=\"radio\"\r\n");
      out.write("\t\t\t\t\tNAME=\"option\" VALUE=\"random\"> Randomly choose <INPUT\r\n");
      out.write("\t\t\t\t\tTYPE=\"text\" NAME=\"percent\" SIZE=\"5\" VALUE=\"10\">% articles\r\n");
      out.write("\t\t\t\t(Default: 10%)</SPAN></TD>\r\n");
      out.write("\t\t\t</TR>\r\n");
      out.write("\t\t</TABLE>\r\n");
      out.write("\t\t</TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("</TABLE>\r\n");
      out.write("<!-- --------------------------------------------------------------- -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- ------------------------ Buttons ------------------------------ -->\r\n");
      out.write("<TABLE cellpadding=\"5\" border=\"0\" width=\"415\">\r\n");
      out.write("\t<TR>\r\n");
      out.write("\t\t<TD><INPUT TYPE=\"submit\" VALUE=\"Generate Cloud\"> <INPUT\r\n");
      out.write("\t\t\tTYPE=\"reset\" VALUE=\"Reset\"></TD>\r\n");
      out.write("\t</TR>\r\n");
      out.write("</TABLE>\r\n");
      out.write("<!-- --------------------------------------------------------------_ -->\r\n");
      out.write("</FORM>\r\n");
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
