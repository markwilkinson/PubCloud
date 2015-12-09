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
<TABLE cellpadding="5" border="0" width="415">
	<TR>
		<TD>
		<H1>PubCloud</H1>
		</TD>
		<TD><SPAN class="fine_print">Home</SPAN></TD>
		<TD><SPAN class="fine_print"><A
			HREF="http://www.cs.bell-labs.com/cm/cs/who/pfps/temp/web/www2007.org/posters/poster1046.pdf"
			TARGET="_BLANK">Publication</A></SPAN></TD>
		<TD><SPAN class="fine_print"><A HREF="faq.html"
			TARGET="_blank">FAQ</A></SPAN></TD>
		<TD><SPAN class="fine_print"><A
			HREF="javascript:window.print();">Print</A></SPAN></TD>
	</TR>
</TABLE>
<!-- ------------------------------------------------------------------------ -->


<FORM method="GET" action="gc.jsp" target="_blank"><!-- Required for query generation: PubMed Query and Cloud Type -->
<TABLE cellpadding="5" width="850">
	<TR>
		<TD>
		<TABLE border="0" width="800">
			<TR>
				<TD><SPAN class="option_title">PubMed Query: <INPUT
					TYPE="text" NAME="query" SIZE="100"></SPAN></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR>
		<TD>
		<TABLE border="0" width="500">
			<TR>
				<TD><SPAN class="option_title">Cloud Type: </SPAN></TD>
				<TD><SPAN class="option_text"><INPUT TYPE="radio"
					NAME="type" VALUE="abstract" CHECKED> Abstracts</SPAN></TD>
				<TD><SPAN class="option_text"><INPUT TYPE="radio"
					NAME="type" VALUE="author"> Authors</SPAN></TD>
				<TD><SPAN class="option_text"><INPUT TYPE="radio"
					NAME="type" VALUE="mesh"> MeSH Terms</SPAN></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
<!-- ---------------------------------------------------------- --> <BR>

<!-- ------------------- Control Panel ------------------------ -->
<TABLE cellpadding="5" width="850"
	STYLE="border-style: dashed; border-width: thin; border-color: blue">
	<TR>
		<TD><SPAN class="option_panel_title">Control Panel</SPAN></TD>
	</TR>
	
	<TR>
		<TD><SPAN class="option_title">Tag Option:</SPAN></TD>
		<TD>
		<TABLE cellpadding="5" border="0" width="650">
			<TR>
				<TD>
				<SPAN class="option_text">
				<INPUT TYPE="radio" NAME="tag_option" VALUE="numTag" CHECKED>
				Number: <INPUT TYPE="text" NAME="numTagCount" SIZE="10" VALUE="100">
				</SPAN>
				</TD>
			</TR>
			<TR>
				<TD>
				<SPAN class="option_text">
				<INPUT TYPE="radio" NAME="tag_option" VALUE="percentTags">
				Percentage: <INPUT TYPE="text" NAME="percentTagCount" SIZE="10" VALUE="50">%
				</SPAN>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	
	<TR>
		<TD><SPAN class="option_title">Date Range: </SPAN></TD>
		<TD>
		<TABLE cellpadding="5" border="0" width="650">
			<TR>
				<TD><SPAN class="option_text">From <INPUT TYPE="text"
					NAME="startYear" SIZE="5"> to <INPUT TYPE="text"
					NAME="endYear" SIZE="5"> (Default: no range)</SPAN></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>

	<TR>
		<TD><SPAN class="option_title">Search Option: </SPAN></TD>
		<TD>
		<TABLE cellpadding="5" border="0" width="650">
			<TR>
				<TD><SPAN class="option_text"><INPUT TYPE="radio"
					NAME="option" VALUE="most_recent" CHECKED> Most (NCBI
				search) relevant <INPUT TYPE="text" NAME="recent" SIZE="5"
					VALUE="100"> articles (Default: 100 articles)</SPAN></TD>
			</TR>
			<TR>
				<TD><SPAN class="option_text"><INPUT TYPE="radio"
					NAME="option" VALUE="random"> Randomly choose <INPUT
					TYPE="text" NAME="percent" SIZE="5" VALUE="10">% articles
				(Default: 10%)</SPAN></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
<!-- --------------------------------------------------------------- -->



<!-- ------------------------ Buttons ------------------------------ -->
<TABLE cellpadding="5" border="0" width="415">
	<TR>
		<TD><INPUT TYPE="submit" VALUE="Generate Cloud"> <INPUT
			TYPE="reset" VALUE="Reset"></TD>
	</TR>
</TABLE>
<!-- --------------------------------------------------------------_ -->
</FORM>
<BR>
<SPAN class="fine_print">Comments or questions, please contact: <I><A
	HREF="mailto:bkuo@mrl.ubc.ca">Byron Kuo</A></I>. Copyright (c) 2006</SPAN>
</BODY>
</HTML>
