package org.subra.aem.rjs.core;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.subra.aem.rjs.core.pdf.BadgeGenerator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

@SuppressWarnings("serial")

@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes ="test/rj/testpdf" , methods = HttpConstants.METHOD_POST)
@ServiceDescription("Simple Demo Servlet")

public class BadgePDFGeneratorServlet extends SlingAllMethodsServlet {

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}

	public static ThreadLocal<ResourceResolver> resolverLocal = new ThreadLocal<>();
	private void process(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException{
		try {
			response.setContentType("application/pdf");
			resolverLocal.set(request.getResourceResolver());
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			new BadgeGenerator(URLDecoder.decode(request.getParameter("html"), "UTF-8"), bais).generatePdf();
			response.setContentLength(bais.size());
			bais.writeTo(response.getOutputStream());
			bais.flush();
			response.flushBuffer();
		}finally {
			resolverLocal.remove();
		}
	}

}
