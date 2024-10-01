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
public class CrawlerPracaPL implements JobCrawler {

    @Override
   public List<Job> scrapeJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) { // Przeglądamy pierwsze 10 stron
            try {
                String url = "https://www.praca.pl/oferty-pracy_" + i; // Modyfikacja URL dla kolejnych stron
                Document document = Jsoup.connect(url).get();

                Elements jobAds = document.select(".listing__item");
                for (Element jobAd : jobAds) {
                    String jobTitle = jobAd.select(".listing__title").text();
                    String employer = jobAd.select(".listing__employer-name").text();
                    String location = jobAd.select(".listing__location-name").text().trim();

                    jobs.add(new Job(jobTitle, employer, location, "praca.pl")); 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        return jobs;
        
   }
   
}

    
