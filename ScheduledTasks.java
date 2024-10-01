package com.mycompany.jobs_crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private LocalDateTime lastRunTime;

    @Autowired
    private JobService jobService;

    @Scheduled(fixedRate = 96 * 60 * 60 * 1000) 
    public void performTask() {
        lastRunTime = LocalDateTime.now();
        logger.info("Rozpoczęcie zadania scrapowania. Czas: {}", lastRunTime);

        scrapeAndSaveJobs("PracujPl");
        scrapeAndSaveJobs("PracaPl");

        logger.info("Zakończono zadanie scrapowania. Czas: {}", lastRunTime);
    }

    private void scrapeAndSaveJobs(String crawlerType) {
        try {
            JobCrawler crawler = CrawlerFactory.getCrawler(crawlerType);
            List<Job> jobs = crawler.scrapeJobs();
            if (jobs == null || jobs.isEmpty()) {
                logger.warn("Brak ofert pracy do zapisania z {}", crawlerType);
                return;
            }
            jobService.saveJobsFromCrawler(jobs);
            logger.info("Zapisano {} ofert pracy z {}", jobs.size(), crawlerType);
        } catch (Exception e) {
            logger.error("Exception occurred in scrapeAndSaveJobs method: ", e);
        }
    }

    public LocalDateTime getLastRunTime() {
        return lastRunTime;
    }
}
