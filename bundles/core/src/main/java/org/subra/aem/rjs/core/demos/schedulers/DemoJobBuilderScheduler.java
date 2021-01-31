package org.subra.aem.rjs.core.demos.schedulers;

import org.apache.sling.event.jobs.JobBuilder.ScheduleBuilder;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subra.aem.rjs.core.demos.jobs.DemoJobConsumerNew;

/**
 * A JobConsumer Service implementation to demonstrate how to create a
 * JobConsumer to help schedule Jobs. A JOb is a special event that will be
 * processed exactly once. When a job is scheduled , it will be available in
 * /var/eventing/jobs . Even when the system fails, the jobs int he queue will
 * retry to execute till it fails
 */
@Component(service = DemoJobBuilderScheduler.class, immediate = true)
public class DemoJobBuilderScheduler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ScheduleBuilder scheduleBuilder;

    @Reference
    private JobManager jobManager;

    @Activate
    protected void activate() {
        log.info("Activate Method of DemoJobBuilderScheduler");
        startScheduledJob();
    }

    @Deactivate
    protected void deactivate() {
        log.info("Activate Method of DemoJobBuilderScheduler");
        if (scheduleBuilder != null) scheduleBuilder.suspend();
    }

    public void startScheduledJob() {
        scheduleBuilder = jobManager.createJob(DemoJobConsumerNew.TOPIC).schedule();
        scheduleBuilder.cron("0 0/2 * ? * * *");
        log.info("DemoJobBuilderScheduler startScheduledJob");
        if (scheduleBuilder.add() == null) {
            log.info("DemoJobConsumerNew Scheduled Failed");
            // something went wrong here, use scheduleBuilder.add(List<String>) instead to get further information about the error
        } else {
            log.info("DemoJobConsumerNew Scheduled");
        }
    }

}