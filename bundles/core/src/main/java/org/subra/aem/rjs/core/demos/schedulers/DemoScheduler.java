package org.subra.aem.rjs.core.demos.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = DemoScheduler.Config.class)
@Component(service = DemoScheduler.class, immediate = true)
public class DemoScheduler {

    @Reference
    private Scheduler scheduler;

    @Reference
    private DemoJob demoJob;

    @ObjectClassDefinition(name = "A Demo scheduled job")
    @interface Config {

        @AttributeDefinition(name = "Enable Scheduler")
        boolean scheduler_enabled() default true;

        @AttributeDefinition(name = "Cron-job expression", description = "Default value '0 0/2 * ? * * *' runs at 0th second in every 2 minutes")
        String scheduler_expression() default "0 0/2 * ? * * *";

        @AttributeDefinition(name = "Cron-job expression", description = "Default value '0 0/2 * ? * * *' runs at 0th second in every 2 minutes")
        String scheduler_name() default "Demo Scheduler";

        @AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Modified
    @Activate
    protected void activate(final Config config) {
        removeScheduler(config);
        addScheduler(config);
    }

    @Deactivate
    protected void deActivate(final Config config) {
        removeScheduler(config);
    }

    private void removeScheduler(final Config config) {
        logger.debug("Removing Scheduler Job '{}'", config.scheduler_name());
        scheduler.unschedule(config.scheduler_name());
    }

    private void addScheduler(final Config config) {
        if (config.scheduler_enabled()) {
            ScheduleOptions scheduleOptions = scheduler.EXPR(config.scheduler_expression());
            scheduleOptions.name(config.scheduler_name());
            scheduleOptions.canRunConcurrently(false);
            scheduler.schedule(demoJob, scheduleOptions);
            logger.debug("Scheduler added successfully");
        } else {
            logger.debug("DemoScheduler is Disabled, no scheduler job created");
        }
    }

}
