package com.sg.superhero.dao;

import com.sg.superhero.dto.Location;

import java.util.List;

public interface LocationDao {

    Location getLocationById(int id);

    int getLocationByName(String name);

    List<Location> getAllLocation();

    Location addLocation(Location location);

    void deleteLocationById(int id);

    void updateLocation(Location location);
}
