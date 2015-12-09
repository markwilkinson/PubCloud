import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ca.ubc.icapture.pubcloud.content.*;

public class cloud extends HttpServlet {
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Reading Three Request Parameters";
	    out.println(ServletUtilities.headWithTitle(title) +
	                "<BODY>\n" +
	                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
	                "<UL>\n" +
	                "  <LI>param1: "
	                + request.getParameter("param1") + "\n" +
	                "  <LI>param2: "
	                + request.getParameter("param2") + "\n" +
	                "  <LI>param3: "
	                + request.getParameter("param3") + "\n" +
	                "</UL>\n" +
	                "</BODY></HTML>");
		
		
	}
	
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	
}