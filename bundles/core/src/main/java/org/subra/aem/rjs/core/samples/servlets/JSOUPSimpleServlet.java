package org.subra.aem.rjs.core.samples.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
/*@Component(service = {Servlet.class})
@SlingServletResourceTypes(
        resourceTypes = "rjs-core/components/page",
        methods = HttpConstants.METHOD_GET,
        extensions = "txt")*/

@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=ContactUsFormServlet Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/test/jsoup"})
@ServiceDescription("Simple Demo Servlet")
public class JSOUPSimpleServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        String test = "<div><p>Raghava test <sup class=\"rag zero\">a</sup></p><p>other text <sup class=\"rag first\">b</sup><sup class=\"rag second\">a</sup></p></div>";
        Document doc = Jsoup.parseBodyFragment(test);
        doc.select("sup.rag").stream().filter(e -> {
            if (e.nextElementSibling() != null && e.nextElementSibling().hasClass("rag")) {
                return true;
            }
            return false;
        }).forEach(el -> {
            Element nextEl = el.nextElementSibling();
            //el.empty();
            el.append(nextEl.outerHtml());
           // el.append(currentEL.outerHtml());
        });
        resp.getWriter().write("Title = " + doc.html().toLowerCase());
    }
}
