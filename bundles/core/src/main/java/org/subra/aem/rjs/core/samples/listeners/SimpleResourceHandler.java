package org.subra.aem.rjs.core.samples.listeners;

import org.apache.sling.api.SlingConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventHandler.class,
        immediate = false,
        enabled = false,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Demo to listen on changes in the resource tree",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/*"
        })
public class SimpleResourceHandler implements EventHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleEvent(final Event event) {
        logger.debug("Resource event: {} at: {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
    }
}

