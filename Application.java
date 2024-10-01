package com.mycompany.jobs_crawler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static JobService jobService;

    static LocalDateTime getLastScrapeTime() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Autowired
    public Application(JobService jobService) {
        Application.jobService = jobService;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w Job Crawler. Wybierz opcje:");
        System.out.println("1. Generuj raport PDF");
        System.out.println("2. Generuj raport CSV");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Podaj miasto (lub 'none' jeśli nie dotyczy):");
        String city = scanner.nextLine();

        System.out.println("Podaj nazwę firmy (lub 'none' jeśli nie dotyczy):");
        String company = scanner.nextLine();

        System.out.println("Podaj nazwę stanowiska (lub 'none' jeśli nie dotyczy):");
        String title = scanner.nextLine();

        System.out.println("Podaj datę początkową (RRRR-MM-DD) lub 'none' jeśli nie dotyczy:");
        String startDateInput = scanner.nextLine();
        LocalDate startDate = "none".equals(startDateInput) ? null : LocalDate.parse(startDateInput);

        System.out.println("Podaj datę końcową (RRRR-MM-DD) lub 'none' jeśli nie dotyczy:");
        String endDateInput = scanner.nextLine();
        LocalDate endDate = "none".equals(endDateInput) ? null : LocalDate.parse(endDateInput);

        switch (choice) {
            case 1:
                generateFilteredPdfReport(city, company, title, startDate, endDate);
                break;
            case 2:
                generateFilteredCsvReport(city, company, title, startDate, endDate);
                break;
            default:
                System.out.println("Nieprawidłowy wybór");
        }

        scanner.close();
    }

    private static void generateFilteredPdfReport(String city, String company, String title, LocalDate startDate, LocalDate endDate) {
        try {
            List<Job> jobs = jobService.getJobsByFilters(city, company, title, startDate, endDate);
            logger.info("Znaleziono {} ofert pracy dla filtrów: miasto={}, firma={}, stanowisko={}, data początkowa={}, data końcowa={}", jobs.size(), city, company, title, startDate, endDate);
            if (jobs == null || jobs.isEmpty()) {
                logger.warn("Brak ofert pracy do wygenerowania raportu PDF.");
                return;
            }
            jobService.generatePdfReport(jobs, "filtered_report.pdf");
            logger.info("Raport PDF został wygenerowany.");
        } catch (Exception e) {
            logger.error("Exception occurred in generateFilteredPdfReport method: ", e);
        }
    }

    private static void generateFilteredCsvReport(String city, String company, String title, LocalDate startDate, LocalDate endDate) {
        try {
            List<Job> jobs = jobService.getJobsByFilters(city, company, title, startDate, endDate);
            logger.info("Znaleziono {} ofert pracy dla filtrów: miasto={}, firma={}, stanowisko={}, data początkowa={}, data końcowa={}", jobs.size(), city, company, title, startDate, endDate);
            if (jobs == null || jobs.isEmpty()) {
                logger.warn("Brak ofert pracy do wygenerowania raportu CSV.");
                return;
            }
            jobService.generateCsvReport(jobs, "filtered_report.csv");
            logger.info("Raport CSV został wygenerowany.");
        } catch (Exception e) {
            logger.error("Exception occurred in generateFilteredCsvReport method: ", e);
        }
    }
}
