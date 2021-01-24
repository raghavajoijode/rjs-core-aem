package org.subra.aem.rjs.core.flagapp.internal.helpers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subra.aem.rjs.core.commons.helpers.ActivationHelper;
import org.subra.aem.rjs.core.flagapp.internal.services.FlagService;

@Component(service = ActivationHelper.class, immediate = true)
@ServiceDescription("FMS - Activator Helper")
public class FMSActivationHelperImpl implements ActivationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FMSActivationHelperImpl.class);

    @Reference
    private FlagService flagService;

    @Activate
    private void activate() {
        LOGGER.info("FMS Activation HelperImpl activated");
    }

    @Deactivate
    private void deActivate() {
        flagService = null;
    }
}