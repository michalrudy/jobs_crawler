package com.mycompany.jobs_crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CsvReportGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CsvReportGenerator.class);

    @Autowired
    private JobService jobService;

    public void generateReport(List<Job> jobs, String filePath) {
        if (jobs == null || jobs.isEmpty()) {
            logger.warn("Brak ofert pracy do wygenerowania raportu CSV.");
            return;
        }

        logger.info("Rozpoczęto generowanie raportu CSV...");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Tytuł,Pracodawca,Lokalizacja,Ostatnia aktualizacja,Strona\n");
            for (Job job : jobs) {
                writer.append(job.getTitle()).append(",");
                writer.append(job.getEmployer()).append(",");
                writer.append(job.getLocation()).append(",");
                writer.append(job.getLastUpdated().toString()).append(",");
                writer.append(job.getSite()).append("\n");
            }
            logger.info("Raport CSV został wygenerowany.");
        } catch (IOException e) {
            logger.error("Błąd podczas generowania raportu CSV: ", e);
        }
    }

    public void generateFilteredReport(String city, String company, String title, LocalDate startDate, LocalDate endDate, String filePath) {
        List<Job> jobs = jobService.getJobsByFilters(city, company, title, startDate, endDate);
        generateReport(jobs, filePath);
    }
}
