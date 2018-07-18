package com.lsi.agile.px.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.agile.api.AgileSessionFactory;
import com.agile.api.CommonConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IAttachmentFile;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;

public class TestUrlPx extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(TestUrlPx.class);
	Properties props = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Entered doGet method..");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Entered doPost method..");
		logger.info("Let us print the remote host details as well .."
				+ request.getRemoteHost() + "-" + request.getRemotePort() + "-"
				+ request.getRemoteAddr());
		PrintWriter out = response.getWriter();
		PropertyFileAdapter pfa = new PropertyFileAdapter();
		try {
			props = pfa.loadProperties();
		} catch (Throwable te) {
			logger.error(
					"Exception occured in Properties file loading logic..", te);
			out.println("Exception occured in Properties file loading logic..");
			te.printStackTrace();
			return;
		}
		try {
			IAgileSession lu_agile_sess = getLoggedInUserSession(request, props);
			if (lu_agile_sess != null) {
				logger.info("Getting the user name..");
				logger.info("The LoggedInUser session is for user - "
						+ lu_agile_sess.getCurrentUser().getName());
				String itemNumber = request.getParameter("agile.1001");
				logger.info("itemNumber is " + itemNumber);
				IItem item = (IItem) lu_agile_sess.getObject(
						ItemConstants.CLASS_PARTS_CLASS, itemNumber);
				ITable table = item.getTable(ItemConstants.TABLE_ATTACHMENTS);
				if (table.size() > 0) {
					Iterator iter = table.iterator();
					InputStream content = null;
					String fileName = null;
					while (iter.hasNext()) {
						IRow each_row = (IRow) iter.next();
						logger.info("Each File from that attachment TAB is ->"
								+ each_row
										.getValue(
												CommonConstants.ATT_ATTACHMENTS_FILE_NAME) 
										.toString());
						/*if (each_row
								.getValue(
										CommonConstants.ATT_ATTACHMENTS_FILE_TYPE)
								.toString().equals("xls")) {*/
							logger.info("Found a file with same name..");
							content = ((IAttachmentFile) each_row).getFile();
							fileName = each_row.getValue(
									CommonConstants.ATT_ATTACHMENTS_FILENAME)
									.toString();
							logger.info("file name is " + fileName);
							break;
						}
				//	}
			//	InputStream content = new FileInputStream(System.getProperty("catalina.base")+"/webapps/testurlpx/Manta.xls");
					if (content != null) {
						/*String email = lu_agile_sess.getCurrentUser()
								.getValue(UserConstants.ATT_GENERAL_INFO_EMAIL)
								.toString();
						logger.info("file name is " + fileName);
						sendExcelToRequester(content, props, fileName, email,
								itemNumber);*/
						lu_agile_sess.close();
						out.println("Sucessfully got  file from attachments...");
					} else {
						out.println("File not found on this object...");
					}
				} else {
					out.println("Attachments not found..");
				}
			} else {
				logger.info("The Agile is session is null. Cannot continue..");
				out.println("The Agile is session is null. Cannot continue..");
				return;
			}
		} catch (Throwable te) {
			logger.error("Exception occured in Session fetching logic..", te);
			te.printStackTrace();
			out.println(ExceptionUtils.analyzeThrowable(te));
			return;
		}
	}

	/*private void sendExcelToRequester(InputStream is, Properties props,
			String fileName, String email, String itemNumber) {
		logger.info("Entered into sendExcelToRequester");
		try {
			MultiPartEmail mpe = new MultiPartEmail();
			mpe.setHostName(props.getProperty("smtp.host.name"));
			mpe.addTo(email);
			mpe.setSubject("Excel file of  - " + itemNumber);
			mpe.setFrom(props.getProperty("admin.email"), "Email from Agile - "
					+ props.getProperty("env.name"));
			ByteArrayDataSource bads = new ByteArrayDataSource(is,
					"application/xls");
			mpe.attach(bads, fileName, fileName);
			mpe.send();
			logger.info("Sucessfully sent Excel file in email...");
		} catch (Throwable te) {
			logger.error("Exception occured ..", te);
		}
	}
*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IAgileSession getLoggedInUserSession(HttpServletRequest request,
			Properties props) {
		logger.debug("Entered getLoggedInUserSession");
		IAgileSession session = null;
		try {
			String agileURL = props.getProperty("agile.url");
			logger.debug("agile url is " + agileURL);
			AgileSessionFactory factory = AgileSessionFactory
					.getInstance(agileURL);
			HashMap params = new HashMap();
			logger.info("AgileSessionFactory.PX_REQUEST "
					+ AgileSessionFactory.PX_REQUEST);
			if (request != null) {
				logger.info("request is not null");
			}
			params.put(AgileSessionFactory.PX_REQUEST, request);
			session = factory.createSession(params);
		} catch (Throwable te) {
			te.printStackTrace();
			logger.debug(
					"Exception occured in getLoggedInUserSession() method..",
					te);
		}
		return session;
	}
}
