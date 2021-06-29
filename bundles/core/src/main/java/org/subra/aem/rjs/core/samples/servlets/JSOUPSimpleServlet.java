package org.subra.aem.rjs.core.samples.servlets;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Comparator;

@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=ContactUsFormServlet Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/test/jsoup"})
@ServiceDescription("Simple JSoup Demo Servlet")
public class JSOUPSimpleServlet extends SlingSafeMethodsServlet {

    private static final String SUPERSCRIPTS = "sup.dis";
    private static final String SUPERSCRIPTS_WRAPPER = "sup-wrapper";

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String text = request.getParameter("text");
        String test = "<p>Sample<sup  data-val=\"c\" class=\"dis\">6</sup> text line<sup  data-val=\"c\" class=\"dis\">4</sup><sup  data-val=\"c\" class=\"dis\">2</sup> 3 end</p><p>Sample text line<sup data-val=\"a\" class=\"dis\">a</sup> 1 end</p> <p>Sample text line<sup data-val=\"b\" class=\"dis\">b</sup><sup data-val=\"a\" class=\"dis\">2</sup><sup data-val=\"d\" class=\"dis\">d</sup><sup  data-val=\"c\" class=\"dis\">c</sup> 2 end</p><p>Sample<sup  data-val=\"c\" class=\"dis\">6</sup> text line<sup  data-val=\"c\" class=\"dis\">4</sup><sup  data-val=\"c\" class=\"dis\">2</sup> 3 end</p>";
        Document document = Jsoup.parseBodyFragment(StringUtils.defaultIfBlank(text, test));
        String key = request.getParameter("key");
        if (!StringUtils.equalsIgnoreCase(key, "text")) {
            sortSuperScripts(document);
        }
        response.getWriter().write(document.toString());
    }

    private void sortSuperScripts(Document document) {
        document.select(SUPERSCRIPTS).forEach(el -> {
            if (hasAdjacentElement(el)) {
                el.html(sortedSiblingsMarkUp(el));
                el.nextElementSiblings().remove();
                el.addClass(SUPERSCRIPTS_WRAPPER);
                el.children().forEach(sortedElement -> {
                    if (sortedElement.nextSibling() instanceof TextNode || hasAdjacentElement(sortedElement))
                        sortedElement.append(",");
                });
            }
        });
        document.select("." + SUPERSCRIPTS_WRAPPER).forEach(Element::unwrap);
    }

    private String sortedSiblingsMarkUp(Element el) {
        Elements siblings = el.nextElementSiblings();
        siblings.add(el);
        siblings.sort(Comparator.comparing(Element::ownText));
        return StringUtils.normalizeSpace(siblings.outerHtml());
    }

    private boolean hasAdjacentElement(Element element) {
        Node nextSibling = element.nextSibling();
        return nextSibling != null && nextSibling.attr("class").equalsIgnoreCase(element.className());
    }

}
