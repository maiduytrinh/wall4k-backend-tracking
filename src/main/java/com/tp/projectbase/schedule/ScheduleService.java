package com.tp.projectbase.schedule;

import com.tp.projectbase.schedule.job.UpdateTrendingJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleService {
    final Logger LOG = LoggerFactory.getLogger(ScheduleService.class);

    public ScheduleService() {

    }

    public void updateTrending() {
        try {
            JobDetail job = JobBuilder.newJob(UpdateTrendingJob.class)
                    .withIdentity("update-trending", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")) // Cron Expression: Chạy mỗi 5 phút
                    .build();

            // Bắt đầu lên lịch với Scheduler
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOG.error("Scheduler exception", e);
        }
    }
}

