package com.example.springscheduler.controller;

import com.example.springscheduler.model.Config;
import com.example.springscheduler.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    SchedulerService schedulerService;

    @PostMapping("/schedule")
    public void schedule(@RequestBody Config config) {
        logger.info("Scheduled");
        schedulerService.startSchedule();
    }

    @PostMapping("/cancel")
    public void cancel(@RequestBody Config config) {
        logger.info("Canceled");
        schedulerService.cancelSchedule();
    }

    @PostMapping("/cron")
    public void cron(@RequestBody Config config) {
        logger.info("Scheduled cron");
        schedulerService.startCron(config);
    }

    @PostMapping("/cancel-cron")
    public void cronCancel(@RequestBody Config config) {
        logger.info("Scheduled cron");
        schedulerService.cancelCronSchedule();
    }
}
