package org.subra.aem.rjs.core.samples.listeners;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;

/**
 * A component that will listen to the /etc/commerce/special-products/ioa nodes
 * and create/update pages with the IOA product number.
 * 
 */
@Component(service = EventListener.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Demo to Event Listiner listen on changes in the resource tree", })
@Designate(ocd = SampleListner.Config.class)
public class SampleListner implements EventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleListner.class);

	private static final String IOA_PATH = "ioaPath";
	private static final String CONTENT_PAGES = "contentPages";
	private static final String SUBSERVICE_NAME = "subservice.name";
	private static final String IROA_TEMPLATE_SUFFIX = "/iroa";

	private static final Pattern IOA_PATH_PATTERN = Pattern.compile("^/?(\\w+)/productNumber$");

	protected String ioaPath;

	protected String contentPagesConfig;

	protected String subserviceName;

	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	private Replicator replicator;

	protected ObservationManager observationManager;
	protected ResourceResolver resourceResolver;
	protected Session session;

	protected Map<String, String> contentPages; // Map path->template

	@Activate
	protected void activate(final Config config) {
		ioaPath = config.ioaPath();
		subserviceName = config.subserviceName();
		contentPagesConfig = config.contentPagesConfig();
		contentPages = Stream.of(StringUtils.split(contentPagesConfig, ",")).collect(Collectors
				.toMap(c -> c.replaceFirst("\\|.*", ""), c -> c.contains("|") ? c.replaceFirst(".*\\|", "") : ""));

		LOGGER.info("Activated with ioaPath={}, subserviceName={}, contentPagesConfig={}", ioaPath, subserviceName,
				contentPagesConfig);
		try {
			resourceResolver = resolverFactory.getServiceResourceResolver(
					Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, subserviceName));
			session = resourceResolver.adaptTo(Session.class);
			observationManager = session.getWorkspace().getObservationManager();
			observationManager.addEventListener(this, Event.PROPERTY_ADDED | Event.PROPERTY_CHANGED, ioaPath, true,
					null, null, true);
		} catch (Throwable e) {
			LOGGER.error("Can not access the JCR to register event listener", e);
		}
	}

	@Deactivate
	protected void deactivate(final ComponentContext componentContext) {
		try {
			if (observationManager != null) {
				observationManager.removeEventListener(this);
			}
			if (resourceResolver != null) {
				resourceResolver.close();
			}
		} catch (RepositoryException e) {
			LOGGER.error("error removing the JCR event listener", e);
		}
	}

	@Override
	public void onEvent(final EventIterator events) {
		while (events.hasNext()) {
			Event event = events.nextEvent();
			try {
				String relativePath = StringUtils.removeStart(ObjectUtils.defaultIfNull(event.getPath(), ""), ioaPath);
				Matcher m = IOA_PATH_PATTERN.matcher(relativePath);
				if (m.matches()) {
					String channel = m.group(1);
					String productNumber = session.getProperty(event.getPath()).getString();
					LOGGER.debug("Got IOA event for channel={} product={}", channel, productNumber);
					contentPages.entrySet().forEach(
							e -> updateContentPage(resourceResolver, productNumber, channel, e.getKey(), e.getValue()));
				} else {
					LOGGER.trace("Got unsupported path from JCR event: {} (relative={})", event.getPath(),
							relativePath);
				}
			} catch (Throwable e) {
				LOGGER.error("Error updating content page", e);
			}
		}
	}

	/**
	 * Updates the given content page with the given IOA product number. The product
	 * number is written into 'prodNumber' property.
	 * 
	 * @param ResourceResolver the sling {@link ResourceResolver} to acquire
	 *                         resources with
	 * @param productNumber    the product number of the IOA
	 * @param channelCode      the channel code of the IOA
	 * @param contentPath      the path of the folder that contains the pages to
	 *                         find or create. Each channel is represented by a page
	 *                         of the same name, unless multiChannel is activated.
	 * @param templatePath     the template to use. Must match the existing template
	 *                         if the page already exists.
	 */
	private void updateContentPage(final ResourceResolver resourceResolver, final String productNumber,
			final String channelCode, final String contentPath, final String templatePath) {
		try {
			if (StringUtils.isNotBlank(templatePath)) {
				boolean isMultiChannel = templatePath.contains(IROA_TEMPLATE_SUFFIX);
				PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
				String path = contentPath + (isMultiChannel ? "" : ("/" + channelCode));
				Page page = pageManager.getPage(path);

				if (page != null) {
					Template template = page.getTemplate();
					if (template == null) {
						LOGGER.error("no template found for '{}'.", path);
						return;
					}
					String oldTemplatePath = template.getPath();
					if (!templatePath.equalsIgnoreCase(oldTemplatePath)) {
						LOGGER.error("wrong template found! '{}' instead of expected '{}'.", oldTemplatePath,
								templatePath);
						return;
					}
				} else {
					LOGGER.debug("Create a new page under: {} with channelCode: {}", contentPath, channelCode);
					page = pageManager.create(contentPath, channelCode, templatePath, channelCode);
				}

				Node jcrNode;
				if (page.hasContent()) {
					jcrNode = page.getContentResource().adaptTo(Node.class);
				} else {
					jcrNode = page.adaptTo(Node.class).addNode(JcrConstants.JCR_CONTENT, "cq:PageContent");
				}
				jcrNode.setProperty("prodNumber" + (isMultiChannel ? '-' + channelCode : ""), productNumber);

				session.refresh(true);
				session.save();

				replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
				LOGGER.debug("Set product number of '{}' to {}.", path, productNumber);
			} else {
				LOGGER.warn("TemplatePath for contentPath: {} not found!", contentPath);
			}
		} catch (Throwable e) {
			LOGGER.error("Failed to create page", e);
		}
	}

	@ObjectClassDefinition(name = "Demo Sample Event Listner Event Listiner", description = "Service for ehcache configuration")
	public @interface Config {
		@AttributeDefinition(name = IOA_PATH, description = "Base path for special products")
		String ioaPath() default "/etc/commerce/special-products/ioa/us";

		@AttributeDefinition(name = CONTENT_PAGES, description = "Comma-separated list of pages to write. Optional with the template in the format <path>|<template>", defaultValue = "/content/subra/en/catalog/products/specials|/apps/subra-commerce/templates/live-tv-on-air")
		String contentPagesConfig();

		@AttributeDefinition(name = SUBSERVICE_NAME, description = "Subservice name to run listener under", defaultValue = "productDataImporterListener")
		String subserviceName();
	}

}
