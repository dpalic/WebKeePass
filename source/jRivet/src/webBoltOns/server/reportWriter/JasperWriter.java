package webBoltOns.server.reportWriter;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class JasperWriter extends GenericReport {

	private static final long serialVersionUID = 1L;

	public void reportService(ServletRequest request, ServletResponse response) {
		
		String rpt = request.getParameter("Lng").trim() + 
						System.getProperty("file.separator") +
						request.getParameter("JasperFile").trim();
		
		if (rpt != null)
			buildReport(request, response, rpt);
		else
			buildErrorPage(request, response, " -- Jasper Report Not Loaded --");
	}	
		

	private void  buildReport(ServletRequest request, ServletResponse response, String rpt) {
	
		try {

			File reportFile = new File(dataAccess.getScriptPath() + rpt);
			HashMap<String, String> parms = new HashMap<String, String>();
			Enumeration ep = request.getParameterNames();
			
			while (ep.hasMoreElements()) {
				String p = (String) ep.nextElement();
				String v = request.getParameter(p);
				if (!p.equals("JasperFile") && !p.equals("")  && !v.equals(""))
					parms.put(p,v);
			}
			
			buildPDFReportPage(request, response, 
						net.sf.jasperreports.engine.JasperRunManager.runReportToPdf(
								reportFile.getPath(), parms, dataAccess.pool.getConnection()));
		
		} catch (Exception e) {
			buildErrorPage(request, response, e.getMessage());
		}
	}
}
