/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jobs_crawler;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author MICHAŁ
 */
public interface JobRepository extends MongoRepository<Job, String> {
   
}