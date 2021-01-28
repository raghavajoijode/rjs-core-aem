package org.subra.aem.rjs.core.demos.schedulers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = DemoJob.Config.class)
@Component(service = DemoJob.class)
public class DemoJob implements Runnable {

    @ObjectClassDefinition(name = "A Demo scheduled job")
    @interface Config {
        @AttributeDefinition(name = "A parameter", description = "sample value")
        String myParameter() default "DemoJob-value";
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String myParameter;

    @Override
    public void run() {
        logger.info("________________Will do some task when scheduled.... {}", myParameter);
    }

    @Activate
    protected void activate(final Config config) {
        logger.debug("************************************** ACTIVATED ?????? {} ????? ******************************************", config.myParameter());
        myParameter = config.myParameter();
    }

}
