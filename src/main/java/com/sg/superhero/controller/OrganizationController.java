package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Organization;
import com.sg.superhero.dto.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class OrganizationController {
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    SuperheroDao superheroDao;

    @GetMapping("organization.html")
    public String getAllOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganization();
        model.addAttribute("organizations", organizations);
        System.out.println(organizations.toString());
        return "organization";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String contact = request.getParameter("contact");
        Organization organization = new Organization();
        organization.setName(name);
        organization.setDescription(description);
        organization.setContact(contact);
        organizationDao.addOrganization(organization);
        return "redirect:/organization.html";
    }

    @GetMapping("editOrganization.html")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @PostMapping("editOrganization.html")
    public String performEditOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        organization.setName(request.getParameter("name"));
        organization.setDescription(request.getParameter("description"));
        organization.setContact(request.getParameter("contact"));
        organizationDao.updateOrganization(organization);
        return "redirect:/organization.html";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id =Integer.parseInt(request.getParameter("id"));
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organization.html";
    }

}
