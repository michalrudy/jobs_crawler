package com.mycompany.jobs_crawler;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PdfReportGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfReportGenerator.class);

    @Autowired
    private JobService jobService;

    public void generateReport(List<Job> jobs, String filePath) {
        if (jobs == null || jobs.isEmpty()) {
            logger.warn("Brak ofert pracy do wygenerowania raportu PDF.");
            return;
        }

        logger.info("Rozpoczęto generowanie raportu PDF...");

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            for (Job job : jobs) {
                Paragraph paragraph = new Paragraph(
                    "Tytuł: " + job.getTitle() + "\n" +
                    "Pracodawca: " + job.getEmployer() + "\n" +
                    "Lokalizacja: " + job.getLocation() + "\n" +
                    "Ostatnia aktualizacja: " + job.getLastUpdated() + "\n" +
                    "Strona: " + job.getSite() + "\n\n"
                );
                document.add(paragraph);
            }

            document.close();
            logger.info("Raport PDF został wygenerowany.");
        } catch (DocumentException | FileNotFoundException e) {
            logger.error("Błąd podczas generowania raportu PDF: ", e);
        }
    }

    public void generateFilteredReport(String city, String company, String title, LocalDate startDate, LocalDate endDate, String filePath) {
        List<Job> jobs = jobService.getJobsByFilters(city, company, title, startDate, endDate);
        generateReport(jobs, filePath);
    }
}
