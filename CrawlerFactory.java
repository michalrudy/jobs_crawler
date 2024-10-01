/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jobs_crawler;

/**
 *
 * @author MICHAŁ
 */
public class CrawlerFactory {

    public static JobCrawler getCrawler(String siteType) {
        switch (siteType) {
            case "PracujPl" -> {
                return new CrawlerPracujPl();
            }
            case "PracaPl" -> {
                return new CrawlerPracaPL();  
            }
            default -> throw new IllegalArgumentException("Unknown site type: " + siteType);
        }
    }
}