package org.subra.aem.rjs.core.samples.servlets;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Comparator;

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

    private static final String SUPERSCRIPTS = "sup.dis";
    private static final String SUPERSCRIPTS_WRAPPER = "sup-wrapper";


    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String text = req.getParameter("text");
        String test = "<p>Sample text line<sup data-val=\"a\" class=\"dis\">a</sup> 1 end</p> <p>Sample text line<sup data-val=\"b\" class=\"dis\">b</sup><sup data-val=\"a\" class=\"dis\">a</sup><sup data-val=\"d\" class=\"dis\">d</sup><sup  data-val=\"c\" class=\"dis\">c</sup> 2 end</p><p>Sample text line<sup  data-val=\"c\" class=\"dis\">c</sup> 3 end</p>";
        Document document = Jsoup.parseBodyFragment(StringUtils.defaultIfBlank(text, test));
        String key = req.getParameter("key");
        if (StringUtils.equalsIgnoreCase(key, "text")) {
            resp.getWriter().write(document.toString());
        } else {
            sortSuperScripts(document);
            resp.getWriter().write(document.toString());
        }
    }

    private void sortSuperScripts(Document document) {
        document.select(SUPERSCRIPTS).forEach(el -> {
            if (hasAdjacentElement(el)) {
                el.html(sortedSiblingsMarkUp(el));
                el.siblingElements().remove();
                el.addClass(SUPERSCRIPTS_WRAPPER);
                el.children().forEach(sortedElements -> {
                    if (hasAdjacentElement(sortedElements))
                        sortedElements.append(",");
                });
            }
        });
        document.select("." + SUPERSCRIPTS_WRAPPER).forEach(Element::unwrap);
    }

    private String sortedSiblingsMarkUp(Element el) {
        Elements siblings = el.siblingElements();
        siblings.add(el);
        siblings.sort(Comparator.comparing(Element::ownText));
        return StringUtils.replace(StringUtils.normalizeSpace(siblings.outerHtml()), "> <", "><");
    }

    private boolean hasAdjacentElement(Element element) {
        Element nextElement = element.nextElementSibling();
        return nextElement != null && nextElement.is(SUPERSCRIPTS);
    }

}
