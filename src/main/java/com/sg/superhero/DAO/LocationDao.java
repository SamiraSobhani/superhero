package com.sg.superhero.DAO;

import com.sg.superhero.DTO.Location;
import java.util.List;

public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocation();
    Location addLocation(Location location);
    void deleteLocationById(int id);
    void updateLocation(Location location);
}
