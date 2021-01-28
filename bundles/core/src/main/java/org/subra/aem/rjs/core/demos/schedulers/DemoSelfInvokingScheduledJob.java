package org.subra.aem.rjs.core.demos.schedulers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = DemoSelfInvokingScheduledJob.Config.class)
@Component(service = Runnable.class)
public class DemoSelfInvokingScheduledJob implements Runnable {

    @ObjectClassDefinition(name = "A Demo self invoking scheduled job")
    @interface Config {

        @AttributeDefinition(name = "Cron-job expression", description = "Default value '0 0/2 * ? * * *' runs at 0th second in every 2 minutes")
        String scheduler_expression() default "0 0/2 * ? * * *";

        @AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run() {
        logger.info("Will Do Some task as scheduled...");
    }

    @Activate
    protected void activate(final Config config) {
        final String v ="Raghava";
        final String v2 ="Raghava";
        logger.debug("{}} activated with config {} *** Hash {} ____ Stringggg {}", getClass(), config, v.hashCode(), v2.hashCode());
    }

}
