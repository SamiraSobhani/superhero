package com.sg.superhero.dao;

import com.sg.superhero.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface SuperheroDao {
    Superhero addSuperhero(Superhero superhero);

    Superhero getSuperheroById(int id);

    int getSuperheroByName(String name);

    List<Superhero> getAllSuperheros();

    void deleteSuperheroById(int id);

    void updateSuperhero(Superhero superhero);

    List<Superpower> getSuperpowerForSuperhero(int Superhero_id);

    List<Organization> getOrganizationsForSuperhero(int superhero_id);

    List<Superhero> getSuperherosForLocation(int location_id);

    List<Superhero> getMemberForOrganization(int organization_id);

    List<Location> getLocationForSuperhero(int superhero_id);

    List<Superhero> getSuperheroForDateAndLocation(Date date,int location_id);

    void saveImage(MultipartFile imageFile);
}
