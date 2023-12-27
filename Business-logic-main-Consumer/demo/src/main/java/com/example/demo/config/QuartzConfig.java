package com.example.demo.config;

import com.example.demo.jobs.DeleteCardsJob;
import com.example.demo.jobs.DeleteUsersJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfig {
    //https://www.freeformatter.com/cron-expression-generator-quartz.html
    @Bean
    public JobDetailFactoryBean deleteCardsJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DeleteCardsJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean deleteUsersJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DeleteUsersJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean deleteCardsTrigger(JobDetail deleteCardsJobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(deleteCardsJobDetail);
        factoryBean.setStartDelay(0);
        factoryBean.setCronExpression("0/5 * * ? * * *");
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean deleteUsersTrigger(JobDetail deleteUsersJobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(deleteUsersJobDetail);
        factoryBean.setStartDelay(0);
        factoryBean.setCronExpression("0/5 * * ? * * *");
        return factoryBean;
    }

}
