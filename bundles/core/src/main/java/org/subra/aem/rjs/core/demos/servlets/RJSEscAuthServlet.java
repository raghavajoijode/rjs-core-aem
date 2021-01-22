package org.subra.aem.rjs.core.demos.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subra.aem.rjs.core.commons.utils.RJSDateTimeUtils;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Test Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/rjs/dates",
		"sling.auth.requirements=" + "-/bin/rjs/dates" })
public class RJSEscAuthServlet extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RJSEscAuthServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

		LOGGER.info("In Do Get Request");
		response.setContentType("text/html");

		String s = "<h3>" + "LDT: " + RJSDateTimeUtils.localDateTimeString() + "</h3>";
		s = s + "<h3>" + "LDT getZonedDateTime at India: " + RJSDateTimeUtils.localDateTimeStringAtZone(RJSDateTimeUtils.ZoneIdUtil.IST.zone()) + "</h3>";
		s = s + "<h3>" + "LDT getLocalDateTime at Tokyo: " + RJSDateTimeUtils.localDateTimeAtZone(RJSDateTimeUtils.ZoneIdUtil.JST.zone()) + "</h3>";
		s = s + "<h3>" + "LDT getZonedDateTime at Tokyo: " + RJSDateTimeUtils.zonedDateTime(RJSDateTimeUtils.ZoneIdUtil.JST.zone()) + "</h3>";
		s = s + "<h3>" + "LDT getLocalDateTime at UTC: " + RJSDateTimeUtils.localDateTimeAtUTC() + "</h3>";
		s = s + "<h3>" + "LDT getZonedDateTime at UTC: " + RJSDateTimeUtils.zonedDateTimeAtUTC() + "</h3>";

		response.getWriter().write(s);
	}

}
