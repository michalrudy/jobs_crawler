package com.mycompany.jobs_crawler.locations;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {

    @Query(value = "{}", fields = "{nazwaWojewodztwa : 1}")
    List<Location> findDistinctVoivodeships();

    @Query(value = "{ 'nazwaWojewodztwa' : ?0 }", fields = "{nazwaMiasta : 1}")
    List<Location> findDistinctByNazwaWojewodztwa(String nazwaWojewodztwa);
}
