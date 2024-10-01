package com.mycompany.jobs_crawler.locations;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Locations")
public class Location {

    @Id
    private String id;

    private String nazwaMiasta;
    private String nazwaWojewodztwa;

    // Gettery i settery
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNazwaMiasta() {
        return nazwaMiasta;
    }

    public void setNazwaMiasta(String nazwaMiasta) {
        this.nazwaMiasta = nazwaMiasta;
    }

    public String getNazwaWojewodztwa() {
        return nazwaWojewodztwa;
    }

    public void setNazwaWojewodztwa(String nazwaWojewodztwa) {
        this.nazwaWojewodztwa = nazwaWojewodztwa;
    }
}
