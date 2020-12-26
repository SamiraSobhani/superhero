package com.sg.superhero.DAO;

import com.sg.superhero.DTO.Sighting;

import java.util.Date;
import java.util.List;


public interface SightingDao {
    Sighting addSighting(Sighting sighting);

    Sighting getSightingByDate(Date date);

    List<Sighting> getAllSightings();

    void deleteSightingByDate(Date date);

    void updateSighting(Sighting sighting);


}
