package com.tp.projectbase.schedule.job;

import com.google.inject.Inject;
import com.tp.projectbase.service.DataService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTrendingJob implements Job {
    final Logger LOG = LoggerFactory.getLogger(UpdateTrendingJob.class);

    @Inject
    DataService dataService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Start Update Trending .................");
        try {
            dataService.updateTrending();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LOG.info("End Update Trending .................");
    }
}
