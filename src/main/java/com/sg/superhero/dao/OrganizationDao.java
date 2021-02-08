package com.sg.superhero.dao;

import com.sg.superhero.dto.Organization;

import java.util.List;

public interface OrganizationDao {

    Organization getOrganizationById(int id);

    Organization getOrganizationByName(String name);

    List<Organization> getAllOrganization();

    Organization addOrganization(Organization organization);

    void deleteOrganizationById(int id);

    void updateOrganization(Organization organization);

}
