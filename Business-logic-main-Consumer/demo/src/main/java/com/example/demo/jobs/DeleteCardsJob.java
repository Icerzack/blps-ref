package com.example.demo.jobs;

import com.example.demo.service.impl.QuartzServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCardsJob implements Job {
    @Autowired
    private QuartzServiceImpl quartzServiceImpl;

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Running cron task on deleting cards");
        quartzServiceImpl.deleteNonValidCards();
    }

}
