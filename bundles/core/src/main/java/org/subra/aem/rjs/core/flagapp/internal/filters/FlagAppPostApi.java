package org.subra.aem.rjs.core.flagapp.internal.filters;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.servlets.annotations.SlingServletFilter;
import org.apache.sling.servlets.annotations.SlingServletFilterScope;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subra.aem.rjs.core.commons.helpers.RequestParser;
import org.subra.aem.rjs.core.flagapp.internal.services.FlagService;
import org.subra.aem.rjs.core.flagapp.internal.utils.FlagAppUtils;
import org.subra.commons.constants.HttpType;
import org.subra.commons.dtos.flagapp.NewFlag;
import org.subra.commons.dtos.flagapp.NewProject;
import org.subra.commons.exceptions.RJSRuntimeException;
import org.subra.commons.helpers.CommonHelper;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sling Servlet Filter Api for templates
 */
@Component
@SlingServletFilter(scope = SlingServletFilterScope.REQUEST, pattern = FlagAppUtils.FLAP_APP_POST_API_PATTERN, methods = HttpConstants.METHOD_POST)
@ServiceDescription("RJSFlagApp Post Api")
@ServiceRanking(-700)
@ServiceVendor("RJS")
public class FlagAppPostApi implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlagAppPostApi.class);

    @Reference
    FlagService fms;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {
        final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        response.setContentType(HttpType.MEDIA_TYPE_JSON.value());
        Pattern pattern = Pattern.compile(FlagAppUtils.FLAP_APP_POST_API_PATTERN);
        Matcher matcher = pattern.matcher(slingRequest.getRequestURI());
        Map<String, Object> result = new HashMap<>();
        if (matcher.matches()) {
            try {
                result = processOperation(slingRequest, matcher);
            } catch (IOException | RJSRuntimeException e) {
                setFailure(result, e.getMessage());
            }
        } else {
            result.put(FlagAppUtils.RS_STATUS, FlagAppUtils.PATTERN_ERROR);
        }
        response.getWriter().write(CommonHelper.writeValueAsString(result));
    }

    private Map<String, Object> processOperation(final SlingHttpServletRequest request, final Matcher matcher)
            throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put(FlagAppUtils.RS_STATUS, FlagAppUtils.SUCCESS);
        final String target = matcher.group(FlagAppUtils.TARGET);
        final String action = matcher.group(FlagAppUtils.ACTION);
        if (StringUtils.isNoneBlank(target, action)) {
            switch (action) {
                case "create":
                    processCreate(request, result, target);
                    break;
                case "update":
                    processUpdate(request, result, target);
                    break;
                default:
                    throw new UnsupportedOperationException(action + " is not supported...");
            }
        } else {
            setFailure(result, "Unsupported Operation...");
        }
        return result;
    }

    private void processCreate(final SlingHttpServletRequest request, Map<String, Object> result, final String target)
            throws IOException {
        if (StringUtils.equalsIgnoreCase(target, FlagAppUtils.FLAG)) {
            NewFlag newFlag = CommonHelper.convertToClass(RequestParser.getBody(request), NewFlag.class);
            if (newFlag != null && newFlag.getFlagValue() != null)
                fms.createOrUpdateFlag(newFlag);
            else
                throw new RJSRuntimeException("Missing Parameters...");
        } else {
            NewProject project = CommonHelper.convertToClass(RequestParser.getBody(request), NewProject.class);
            result.put(FlagAppUtils.RS_DATA, fms.createProject(project));
        }
    }

    private void setFailure(Map<String, Object> result, String message) {
        result.put(FlagAppUtils.RS_STATUS, FlagAppUtils.FAILURE);
        result.put(FlagAppUtils.RS_FAILURE_REASON, message);
    }

    private void processUpdate(final SlingHttpServletRequest request, Map<String, Object> result, final String target)
            throws IOException {
        if (StringUtils.equalsIgnoreCase(target, FlagAppUtils.FLAG)) {
            NewFlag newFlag = CommonHelper.convertToClass(RequestParser.getBody(request), NewFlag.class);
            if (newFlag != null && newFlag.getFlagValue() != null)
                fms.createOrUpdateFlag(newFlag);
            else
                throw new RJSRuntimeException("Missing Parameters...");
        } else {
            setFailure(result, "Unsupported Operation...");
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOGGER.debug("FlagAppPostApi initialised...");
    }

    @Override
    public void destroy() {
        LOGGER.debug("FlagAppPostApi destroyed...");
    }

}