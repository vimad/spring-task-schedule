package com.example.springscheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Function;

@Service
public class SchedulerConfig implements SchedulingConfigurer {

    private Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        if (this.scheduledTaskRegistrar == null) {
            this.scheduledTaskRegistrar = scheduledTaskRegistrar;
        }

        if (scheduledTaskRegistrar.getScheduler() == null) {
            scheduledTaskRegistrar.setScheduler(getSchedulerPool());
        }
    }

    private TaskScheduler getSchedulerPool() {
        ThreadPoolTaskScheduler poolTaskScheduler = new ThreadPoolTaskScheduler();
        poolTaskScheduler.setThreadNamePrefix("test-thread-");
        poolTaskScheduler.setPoolSize(2);
        poolTaskScheduler.initialize();
        return poolTaskScheduler;
    }

    public ScheduledFuture scheduleTask(Runnable runnable, Function<TriggerContext, Date> startAt) {
        return scheduledTaskRegistrar.getScheduler().schedule(runnable, t -> startAt.apply(t));
    }

    public ScheduledFuture scheduleCronTask(Runnable runnable, CronTrigger cronTrigger) {
        return scheduledTaskRegistrar.getScheduler().schedule(runnable, cronTrigger);
    }

}
