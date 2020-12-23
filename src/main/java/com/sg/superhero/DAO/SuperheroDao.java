package com.sg.superhero.DAO;

import com.sg.superhero.DTO.Location;
import com.sg.superhero.DTO.Organization;
import com.sg.superhero.DTO.Superhero;

import java.util.Date;
import java.util.List;

public interface SuperheroDao {
    Superhero addSuperhero(Superhero superhero);
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperheros();
    void deleteSuperheroById(int id);
    void updateSuperhero(Superhero superhero);
    List<Superhero> getSuperherosForLocation(Location location);
    List<Superhero> getMemberForOrganization(Organization organization);
    List<Organization> getOrganizationsForSuperhero(int superhero_id);
    List<Location> getLocationForSuperhero(int superhero_id);
    List<Superhero> getSuperheroForDateAndLocation(Date date, Location location);
}
