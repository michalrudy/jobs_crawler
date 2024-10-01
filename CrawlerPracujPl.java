package com.mycompany.jobs_crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class CrawlerPracujPl implements JobCrawler {
    
    @Override
    public List<Job> scrapeJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // Przeglądamy pierwsze 10 stron
            try {
                String url = "https://www.pracuj.pl/praca?page=" + i;
                Document document = Jsoup.connect(url).get();

     Elements jobAds = document.select("div.tiles_b1j1pbod.core_po9665q"); // Zaktualizuj selektor do klasy z kropką
                for (Element jobAd : jobAds) {
                    String jobTitle = jobAd.select("h2[data-test='offer-title']").text(); // Zaktualizuj selektor do klasy z kropką
                    String employer = jobAd.select("h3[data-test='text-company-name']").text(); // Zaktualizuj selektor do klasy z kropką
                    String location = jobAd.select("h4[data-test='text-region']").text(); // Zaktualizuj selektor do klasy z kropką
                    
                    jobs.add(new Job(jobTitle, employer, location, "pracuj.pl"));  

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jobs;
    }
}