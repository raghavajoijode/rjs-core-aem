package org.subra.aem.rjs.core.samples.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.Servlet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=ContactUsFormServlet Demo Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/contactform" })
@Designate(ocd = ContactUsFormServlet.Config.class)
public class ContactUsFormServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactUsFormServlet.class);

	private static final String DEFAULT_MAIL_PROTOCOL = "smtp";
	private static final String DEFAULT_PORT_NUMBER = "587";
	private static final String DEFAULT_MAIL_HOST = "Outlook.office365.com";
	private static final String DEFAULT_MAIL_FROM = "usha.dandu@abc.com";
	private static final String DEFAULT_MAIL_TO = "usha.dandu@abc.com";
	private static final String DEFAULT_USERNAME = "us310476@abc.com";
	private static final String DEFAULT_PWORD = "Purple@1234";

	private static final String PROP_MAIL_PROTOCOL = "mail.protocol";

	private static final String PROP_MAIL_PORTNUMBER = "mail.portnumber";

	private static final String PROP_MAIL_HOST = "mail.host";

	private static final String PROP_MAIL_FROM = "mail.from";

	private static final String PROP_MAIL_TO = "mail.to";

	private static final String ANALYSTRELATIONS_PROP_MAIL_TO = "analyst.relations.mail.to";

	private static final String ALLIANCES_PROP_MAIL_TO = "alliances.mail.to";

	private static final String CAREER_SEEKERS_PROP_MAIL_TO = "career.seekers.mail.to";

	private static final String INVESTOR_RELATIONS_PROP_MAIL_TO = "investor.relations.mail.to";

	private static final String PUBLIC_RELATIONS_PROP_MAIL_TO = "public.relations.mail.to";

	private static final String REQUEST_SERVICES_PROP_MAIL_TO = "request.services.mail.to";

	private static final String OTHERS_PROP_MAIL_TO = "others.mail.to";

	private static final String PROP_USERNAME = "mail.user";

	private static final String PROP_PWORD = "mail.password";

	private static final String DB_HOST = "db.host";

	private static final String DB_USER = "db.user";

	private static final String DB_PWORD = "db.password";

	@ObjectClassDefinition(name = "ACS AEM Commons - Site Map Servlet", description = "Page and Asset Site Map Servlet")
	public @interface Config {

		@AttributeDefinition(defaultValue = DEFAULT_MAIL_PROTOCOL, name = "Mail Transport Protocol", description = "Mail Transport Protocol to be used.")
		String mailProtocol();

		@AttributeDefinition(defaultValue = DEFAULT_PORT_NUMBER, name = "Mail Port Number", description = "Mail Port Number to be used.")
		String mailPortNumber();

		@AttributeDefinition(defaultValue = DEFAULT_MAIL_HOST, name = "Mail Host", description = "Mail Host to be used.")
		String mailHost();

		@AttributeDefinition(defaultValue = DEFAULT_MAIL_FROM, name = "Mail FROM", description = "Mail From to be used.")
		String mailFrom();

		@AttributeDefinition(defaultValue = DEFAULT_MAIL_TO, name = "Mail To", description = "Mail To to be used.")
		String mailTo();

		@AttributeDefinition(name = "Analyst Relations Mail To", description = "Analyst RelationsMail To to be used.")
		String analystRelationsMailTo();

		@AttributeDefinition(name = "Alliances Mail To", description = "Alliances Mail To to be used.")
		String alliancesMailTo();

		@AttributeDefinition(name = "Career Seekers Mail To", description = "Career Seekers Mail To to be used.")
		String careerSeekersMailTo();

		@AttributeDefinition(name = "Investor Relations Mail To", description = "Investor Relations Mail To to be used.")
		String investorRelationsMailTo();

		@AttributeDefinition(name = "Media and Public Relations Mail To", description = "Media and Public Relations Mail To to be used.")
		String publicRelationsMailTo();

		@AttributeDefinition(name = "Request for Services Mail To", description = "Request for Services Mail To to be used.")
		String requestServicesMailTo();

		@AttributeDefinition(name = "Others Mail To", description = "Others Mail To to be used.")
		String othersMailTo();

		@AttributeDefinition(defaultValue = DEFAULT_USERNAME, name = "Mail User Name", description = "Mail User Name To to be used.")
		String mailUser();

		@AttributeDefinition(defaultValue = DEFAULT_PWORD, name = "Mail Password", description = "Mail Password To to be used.")
		String mailPassword();

		@AttributeDefinition(name = "Host", description = "Host to be used.")
		String dbHost();

		@AttributeDefinition(name = "DB User Name", description = "Db User Name to be used.")
		String dbUser();

		@AttributeDefinition(name = "DB Password", description = "DB Password to be used.")
		String dbPassword();

	}

	@Reference
	private SlingRepository repository;

	private String mailProtocol;
	private String portNumber;
	private String hostNumber;
	private String from;
	private String to;
	private String anarelto;
	private String allito;
	private String carseekto;
	private String investrelto;
	private String pubrelto;
	private String reqserto;
	private String othto;
	private String userName;
	private String password;
	private String dbHost;
	private String dbUser;
	private String dbPwd;

	@Activate
	protected void activate(Map<String, Object> properties) {
		LOGGER.info("inside activate");

		this.mailProtocol = PropertiesUtil.toString(properties.get(PROP_MAIL_PROTOCOL), DEFAULT_MAIL_PROTOCOL);
		this.portNumber = PropertiesUtil.toString(properties.get(PROP_MAIL_PORTNUMBER), DEFAULT_PORT_NUMBER);
		this.hostNumber = PropertiesUtil.toString(properties.get(PROP_MAIL_HOST), DEFAULT_MAIL_HOST);
		this.from = PropertiesUtil.toString(properties.get(PROP_MAIL_FROM), DEFAULT_MAIL_FROM);
		this.to = PropertiesUtil.toString(properties.get(PROP_MAIL_TO), DEFAULT_MAIL_TO);
		this.anarelto = PropertiesUtil.toString(properties.get(ANALYSTRELATIONS_PROP_MAIL_TO), "");
		this.allito = PropertiesUtil.toString(properties.get(ALLIANCES_PROP_MAIL_TO), "");
		this.carseekto = PropertiesUtil.toString(properties.get(CAREER_SEEKERS_PROP_MAIL_TO), "");
		this.investrelto = PropertiesUtil.toString(properties.get(INVESTOR_RELATIONS_PROP_MAIL_TO), "");
		this.pubrelto = PropertiesUtil.toString(properties.get(PUBLIC_RELATIONS_PROP_MAIL_TO), "");
		this.reqserto = PropertiesUtil.toString(properties.get(REQUEST_SERVICES_PROP_MAIL_TO), "");
		this.othto = PropertiesUtil.toString(properties.get(OTHERS_PROP_MAIL_TO), "");
		this.userName = PropertiesUtil.toString(properties.get(PROP_USERNAME), DEFAULT_USERNAME);
		this.password = PropertiesUtil.toString(properties.get(PROP_PWORD), DEFAULT_PWORD);
		this.dbHost = PropertiesUtil.toString(properties.get(DB_HOST), "");
		this.dbUser = PropertiesUtil.toString(properties.get(DB_USER), "");
		this.dbPwd = PropertiesUtil.toString(properties.get(DB_PWORD), "");
		LOGGER.info("inside end activate");
	}

	public void bindRepository(SlingRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.info("inside contactus DoPost");
		Connection con = getConnection();
		if (con != null) {
			LOGGER.info("connection established");
		}
		Statement stmt = null;
		boolean status = false;
		String errormessgae = "Application error";
		String recapcharesponse = request.getParameter("g_recaptcha_response");

		try {
			// Get the submitted form data that is sent from the
			// CQ web page

			String firstName = getParameter("firstName", request);
			firstName = java.net.URLDecoder.decode(firstName, StandardCharsets.UTF_8.name());
			String lastName = getParameter("lastName", request);
			lastName = java.net.URLDecoder.decode(lastName, StandardCharsets.UTF_8.name());
			String company = getParameter("organization", request);
			company = java.net.URLDecoder.decode(company, StandardCharsets.UTF_8.name());
			String email = getParameter("email", request);
			email = java.net.URLDecoder.decode(email, StandardCharsets.UTF_8.name());
			String jobTitle = getParameter("jobTit", request);
			jobTitle = java.net.URLDecoder.decode(jobTitle, StandardCharsets.UTF_8.name());
			String compName = getParameter("compName", request);
			compName = java.net.URLDecoder.decode(compName, StandardCharsets.UTF_8.name());
			String inqType = getParameter("inqType", request);
			inqType = java.net.URLDecoder.decode(inqType, StandardCharsets.UTF_8.name());
			String othInq = getParameter("othInq", request);
			othInq = java.net.URLDecoder.decode(othInq, StandardCharsets.UTF_8.name());
			String code = getParameter("code", request);
			code = java.net.URLDecoder.decode(code, StandardCharsets.UTF_8.name());
			String country = getParameter("country", request);
			country = java.net.URLDecoder.decode(country, StandardCharsets.UTF_8.name());
			String phone = getParameter("phone", request);
			phone = java.net.URLDecoder.decode(phone, StandardCharsets.UTF_8.name());
			String indBlg = getParameter("indBlg", request);
			indBlg = java.net.URLDecoder.decode(indBlg, StandardCharsets.UTF_8.name());
			Double phno;
			String descInq = getParameter("descInq", request);
			descInq = java.net.URLDecoder.decode(descInq, StandardCharsets.UTF_8.name());
			String refUrl = getParameter("refUrl", request);
			refUrl = java.net.URLDecoder.decode(refUrl, StandardCharsets.UTF_8.name());
			String iPage = getParameter("iPage", request);
			iPage = java.net.URLDecoder.decode(iPage, StandardCharsets.UTF_8.name());
			String refParam = getParameter("refParam", request);
			refParam = java.net.URLDecoder.decode(refParam, StandardCharsets.UTF_8.name());
			String lPage = getParameter("lastLandingPage", request);
			lPage = java.net.URLDecoder.decode(lPage, StandardCharsets.UTF_8.name());
			if (jobTitle.equals(StringUtils.EMPTY)) {
				jobTitle = "N/A";
			}
			if (phone.equals(StringUtils.EMPTY)) {
				phone = "N/A";
				phno = 0.0;
			} else {
				phno = Double.parseDouble(phone);
			}
			if (othInq.equals(StringUtils.EMPTY)) {
				othInq = "N/A";
			}
			if (inqType.equals("Request for Services")) {
				to = reqserto;
			} else if (inqType.equals("Alliances")) {
				to = allito;
			} else if (inqType.equals("Analyst Relations")) {
				to = anarelto;
			} else if (inqType.equals("Career Seekers")) {
				to = carseekto;
			} else if (inqType.equals("Investor Relations")) {
				to = investrelto;
			} else if (inqType.equals("Media and Public Relations")) {
				to = pubrelto;
			} else {
				to = othto;
			}

			String[] recipientList = to.split(",");
			InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
			int counter = 0;
			for (String recipient : recipientList) {
				recipientAddress[counter] = new InternetAddress(recipient.trim());
				counter++;
			}

			// Get the session object
			java.util.Properties properties = System.getProperties();
			properties.put("mail.transport.protocol", mailProtocol);
			properties.setProperty("mail.smtp.host", hostNumber);
			properties.put("mail.smtp.port", portNumber);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "true");
			properties.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
			session.setDebug(true);
			response.setContentType("text/html");
			if (validateRequest(recapcharesponse)) {
				try {
					status = queryExecution(con, firstName, lastName, company, email, jobTitle, compName, inqType,
							othInq, code, country, phone, indBlg, phno, descInq, refUrl, iPage, refParam, lPage,
							recipientAddress, session);
				} catch (NullPointerException e) {
					status = false;
					errormessgae = "error while inserting data";
				} finally {
					LOGGER.info("in finally");
					if (con != null) {
						con.close();
					}
					if (stmt != null) {
						stmt.close();
					}
				}
			} else {
				status = false;
				errormessgae = "error while verifying captcha";
			}

		} catch (Exception e) {
			LOGGER.error("exception occured: {}", e);

		}

		JSONObject obj = new JSONObject();
		try {
			if (status) {
				obj.put("status", status);
			} else {
				obj.put("status", status);
				obj.put("error", errormessgae);
			}
		} catch (JSONException e) {
			LOGGER.error("exception occured: {}", e);
		}

		String jsonData = obj.toString();
		response.getWriter().write(jsonData);
	}

	private boolean queryExecution(Connection con, String firstName, String lastName, String company, String email,
			String jobTitle, String compName, String inqType, String othInq, String code, String country, String phone,
			String indBlg, Double phno, String descInq, String refUrl, String iPage, String refParam, String lPage,
			InternetAddress[] recipientAddress, Session session)
			throws MessagingException, AddressException, SQLException {
		boolean status;
		String query;
		javax.mail.Transport transport = session.getTransport(mailProtocol);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, recipientAddress);
		message.setSubject("Contact-" + inqType);

		message.setContent(
				"<b>First Name</b> : " + firstName + "<br><br>" + "<b>Last Name</b> : " + lastName + "<br><br>"
						+ "<b>Email</b> : " + email + "<br><br>" + "<b>Job Title</b> : " + jobTitle + "<br><br>"
						+ "<b>Company/Organization/School</b> : " + company + "<br><br>"
						+ "<b>Company/Organization/School Name</b> : " + compName + "<br><br>"
						+ "<b>Inquiry Type</b> : " + inqType + "<br><br>" + "<b>Others Inquiry Type</b> : " + othInq
						+ "<br><br>" + "<b>Country</b> : " + country + "<br><br>" + "<b>Code</b> : " + code + "<br><br>"
						+ "<b>Phone</b> : " + phone + "<br><br>" + "<b>Industry Belongs To</b> : " + indBlg + "<br><br>"
						+ "<b>Description Of Inquiry</b> : " + descInq + "<br><br>" + "<b>Referrer URL</b> : " + refUrl
						+ "<br><br>" + "<b>Referrer Parameters</b> : " + refParam + "<br><br>"
						+ "<b>Initial Landing Page</b> : " + iPage + "<br><br>" + "<b>Last Landing Page</b> : " + lPage,
				"text/html");
		// Send message

		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		con.setAutoCommit(false);

		query = "INSERT INTO custom_contactus_contact (contact_id ,contact_firstname,contact_lastname,contact_email,contact_jobtitle,contact_organization,contact_organization_name,contact_inquirytype,contact_othersinquirytype,contact_country,contact_code,contact_phoneno,contact_industrybelongsto,contact_descriptionofinquiry,contact_upload) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, 0);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, email);
			pstmt.setString(5, jobTitle);
			pstmt.setString(6, company);
			pstmt.setString(7, compName);
			pstmt.setString(8, inqType);
			pstmt.setString(9, othInq);
			pstmt.setString(10, country);
			pstmt.setString(11, code);
			pstmt.setDouble(12, phno);
			pstmt.setString(13, indBlg);
			pstmt.setString(14, descInq);
			pstmt.setString(15, "");
			pstmt.executeUpdate();
		}

		con.commit();

		status = true;
		return status;
	}

	private boolean validateRequest(String recapcharesponse) {
		try {

			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("https://www.google.com/recaptcha/api/siteverify");
			NameValuePair[] data = { new NameValuePair("secret", "6LfXYvkSAAAAAHDCvykff72o6i2IsM1v5Vk6JXaO"),
					new NameValuePair("response", recapcharesponse) };
			post.setRequestBody(data);
			client.executeMethod(post);

			String result = post.getResponseBodyAsString();
			post.releaseConnection();
			return result.contains("true");
		} catch (Exception e) {
			LOGGER.info("Exception occured while verifying capcha", e);
			return false;
		}
	}

	public static String getParameter(String paramenter, SlingHttpServletRequest request) {
		return request.getParameter(paramenter) != null ? request.getParameter(paramenter) : StringUtils.EMPTY;
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.info("inside contactus Doget1");
		this.doPost(request, response);
		LOGGER.info("inside contactus Doget2");

	}

	protected Connection getConnection() {
		LOGGER.info("inside getconnection");
		String host = dbHost;
		String uname = dbUser;
		String pwd = dbPwd;
		try (Connection con = DriverManager.getConnection(host, uname, pwd);) { // Inject the DataSourcePool right here!
			LOGGER.info("after getting connection1");
			return con;
		} catch (Exception e) {
			LOGGER.error("connection error: {}", e);
		}
		return null;
	}
}
