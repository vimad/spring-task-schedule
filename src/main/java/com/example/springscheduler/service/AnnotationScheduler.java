package com.example.springscheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AnnotationScheduler {

    private Logger logger = LoggerFactory.getLogger(AnnotationScheduler.class);

    @Scheduled(fixedRate = 1000)
    public void fixedRateScheduler() {
        logger.info("This is the fixed scheduler in {}", Thread.currentThread().getName());
    }
}
