package com.mycompany.jobs_crawler;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.jobs_crawler.locations.Location;
import com.mycompany.jobs_crawler.locations.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CrawlerPracujPl crawlerPracujPl;

    @Autowired
    private LocationRepository locationRepository;

    public void saveJobsFromCrawler() {
        List<Job> jobs = crawlerPracujPl.scrapeJobs();
        LocalDateTime now = LocalDateTime.now();
        for (Job job : jobs) {
            job.setLastUpdated(now);
        }
        jobRepository.saveAll(jobs);
        System.out.println("Zapisano zadania pobrane przez crawlera.");
    }

    public void saveJobsFromCrawler(List<Job> jobs) {
        LocalDateTime now = LocalDateTime.now();
        for (Job job : jobs) {
            job.setLastUpdated(now);
        }
        jobRepository.saveAll(jobs);
    }

    public void runCrawler() {
        List<Job> jobs = crawlerPracujPl.scrapeJobs();
        saveJobsFromCrawler(jobs);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public Job getJob(String id) {
        return jobRepository.findById(id).orElse(null);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void generatePdfReport() {
        List<Job> jobs = jobRepository.findAll();
        generatePdfReport(jobs, "JobReport.pdf");
    }

    public void generateCsvReport() {
        List<Job> jobs = jobRepository.findAll();
        generateCsvReport(jobs, "JobReport.csv");
    }

    public void generateFilteredPdfReport(String city, String company, String title, LocalDate startDate, LocalDate endDate) {
        List<Job> jobs = getJobsByFilters(city, company, title, startDate, endDate);
        generatePdfReport(jobs, "FilteredJobReport.pdf");
    }

    public void generateFilteredCsvReport(String city, String company, String title, LocalDate startDate, LocalDate endDate) {
        List<Job> jobs = getJobsByFilters(city, company, title, startDate, endDate);
        generateCsvReport(jobs, "FilteredJobReport.csv");
    }

    public List<Job> getJobsByFilters(String city, String company, String title, LocalDate startDate, LocalDate endDate) {
        return jobRepository.findAll().stream()
            .filter(job -> filterJobByCriteria(job, city, company, title, startDate, endDate))
            .collect(Collectors.toList());
    }

    private boolean filterJobByCriteria(Job job, String city, String company, String title, LocalDate startDate, LocalDate endDate) {
        boolean matchesCity = (city == null || city.equalsIgnoreCase("none")) || job.getLocation().toLowerCase().contains(city.toLowerCase());
        boolean matchesCompany = (company == null || company.equalsIgnoreCase("none")) || job.getEmployer().toLowerCase().contains(company.toLowerCase());
        boolean matchesTitle = (title == null || title.equalsIgnoreCase("none")) || job.getTitle().toLowerCase().contains(title.toLowerCase());
        boolean matchesDateRange = (startDate == null || !job.getLastUpdated().toLocalDate().isBefore(startDate)) && (endDate == null || !job.getLastUpdated().toLocalDate().isAfter(endDate));
        return matchesCity && matchesCompany && matchesTitle && matchesDateRange;
    }

    public List<String> getAllCities() {
        return locationRepository.findAll().stream()
            .map(Location::getNazwaMiasta)
            .distinct()
            .collect(Collectors.toList());
    }

    public void generatePdfReport(List<Job> jobs, String fileName) {
        if (jobs.isEmpty()) {
            System.out.println("Brak ofert pracy do wygenerowania raportu PDF.");
            return;
        }

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Paragraph("Raport ofert pracy"));
            document.add(new Paragraph("Liczba ofert: " + jobs.size()));
            document.add(new Paragraph(" "));

            for (Job job : jobs) {
                document.add(new Paragraph("Nazwa: " + job.getTitle()));
                document.add(new Paragraph("Pracodawca: " + job.getEmployer()));
                document.add(new Paragraph("Lokalizacja: " + job.getLocation()));
                document.add(new Paragraph("Ostatnia aktualizacja: " + job.getLastUpdated()));
                document.add(new Paragraph("Strona: " + job.getSite()));
                document.add(new Paragraph("-----------"));
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public void generateCsvReport(List<Job> jobs, String fileName) {
        if (jobs.isEmpty()) {
            System.out.println("Brak ofert pracy do wygenerowania raportu CSV.");
            return;
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Nazwa,Pracodawca,Lokalizacja,Ostatnia aktualizacja,Strona\n");
            for (Job job : jobs) {
                writer.append(job.getTitle()).append(",");
                writer.append(job.getEmployer()).append(",");
                writer.append(job.getLocation()).append(",");
                writer.append(job.getLastUpdated().toString()).append(",");
                writer.append(job.getSite()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void generateFilteredCsvReport(String string, String string0, String string1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void generateFilteredPdfReport(String string, String string0, String string1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
