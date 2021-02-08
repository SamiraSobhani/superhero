package com.sg.superhero.dao;

import com.sg.superhero.dto.Sighting;

import java.util.Date;
import java.util.List;


public interface SightingDao {
    Sighting addSighting(Sighting sighting);

    Sighting getSightingByLocationSuperheroDate(int location_id,int superhero_id,Date date);

    List<Sighting> getAllSightings();

    void deleteSightingByLocationSuperheroDate(int location_id,int superhero_id,Date date);

    void updateSighting(Sighting sighting);


}
