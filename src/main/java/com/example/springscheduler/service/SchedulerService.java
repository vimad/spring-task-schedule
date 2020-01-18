package com.example.springscheduler.service;

import com.example.springscheduler.model.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulerService {

    private Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    ScheduledFuture future;
    ScheduledFuture cronFuture;

    @Autowired
    SchedulerConfig schedulerConfig;

    public void startSchedule() {
        future = schedulerConfig.scheduleTask(()-> scheduleFixed(2), this::scheduleDate);
    }

    public void startCron(Config config) {
        String[] split = config.getScheduleTime().split(":");
        String cron = "0 " + split[1] + " " + split[0] + " * * ?";
        CronTrigger cronTrigger = new CronTrigger(cron);
        cronFuture = schedulerConfig.scheduleCronTask(()->scheduleCron(), cronTrigger);
    }

    public void cancelSchedule() {
        future.cancel(true);
    }

    public void cancelCronSchedule() {
        cronFuture.cancel(true);
    }

    public void scheduleFixed(int frequency) {
        logger.info("scheduleFixed: Next execution time of this will always be {} seconds in {}", frequency, Thread.currentThread().getName());
    }

    public void scheduleCron() {
        logger.info("cron job at {}, in {}", LocalDateTime.now(), Thread.currentThread().getName());
    }

    public Date scheduleDate(TriggerContext t) {
        Calendar nextExecutionTime = new GregorianCalendar();
        Date lastActualExecutionTime = t.lastActualExecutionTime();
        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
        nextExecutionTime.add(Calendar.SECOND, 2);
        return nextExecutionTime.getTime();
    }

}
