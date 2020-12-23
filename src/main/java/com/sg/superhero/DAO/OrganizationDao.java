package com.sg.superhero.DAO;

import com.sg.superhero.DTO.Organization;
import com.sg.superhero.DTO.Superhero;

import java.util.List;

public interface OrganizationDao {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganization();
    Organization addOrganization(Organization organization);
    void deleteOrganizationById(int id);
    void updateOrganization(Organization organization);

}
