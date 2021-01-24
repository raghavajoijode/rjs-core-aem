package org.subra.aem.rjs.core.account.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.subra.aem.rjs.core.account.services.MyAEMUserService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {Constants.SERVICE_DESCRIPTION + "=Email Demo Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/subra/user"})
public class MyUserDemoServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = -7639144471855594170L;

    @Reference
    transient MyAEMUserService userService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {

        String userName = userService.createUser("Raghava", "Ragi@1234", "subra-end-user");
        resp.setContentType("text/plain");
        if (userService.isExistingUserName(userName)) {
            resp.getWriter().write("User Created Successfully");
        } else {
            resp.getWriter().write("Error crating user");
        }
    }
}
