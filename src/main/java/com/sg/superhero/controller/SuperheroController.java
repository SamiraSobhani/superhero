package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Organization;
import com.sg.superhero.dto.Superhero;
import com.sg.superhero.dto.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SuperheroController {
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

    @GetMapping("/superhero.html")
    public String getAllSuperheros(Model model) {
        List<Superhero> superheroes = superheroDao.getAllSuperheros();
        List<Organization> organizations = organizationDao.getAllOrganization();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("organizations", organizations);
        model.addAttribute("superpowers", superpowers);

        return "superhero.html";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(HttpServletRequest request) {
        String name=request.getParameter("name");
        String description= request.getParameter("description");
        String[] organizationIds = request.getParameterValues("organization_id");
        String[] superpowerIds = request.getParameterValues("superpower_id");

        List<Organization> organizations = new ArrayList<>();
        for (String organization_id : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organization_id)));
        }
        List<Superpower> superpowers = new ArrayList<>();
        for (String superpower_id : superpowerIds) {
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpower_id)));
        }
        Superhero superhero=new Superhero();
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setOrganization(organizations);
        superhero.setSuperpower(superpowers);
        superheroDao.addSuperhero(superhero);
        return "redirect:/superhero.html";
    }


    @GetMapping("editSuperhero.html")
    public String editSuperhero(Integer id, Model model) {

        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Organization> organizations = organizationDao.getAllOrganization();
        model.addAttribute("superhero", superhero);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        return "editSuperhero.html";
    }

    @PostMapping("editSuperhero.html")
    public String performEditSuperhero(HttpServletRequest request) {
        int id=Integer.parseInt(request.getParameter("id"));
        Superhero superhero=superheroDao.getSuperheroById(id);
        String[] superpowerIds = request.getParameterValues("superpower_id");
        String[] organizationIds = request.getParameterValues("organization_id");
        List<Superpower> superpowers = new ArrayList<>();
        for (String superpowerId : superpowerIds) {
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        }
        List<Organization> organizations = new ArrayList<>();
        for (String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }
        String name=request.getParameter("new_name");
        String description= request.getParameter("new_description");

        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);

        superheroDao.updateSuperhero(superhero);
        return "redirect:/superhero.html";
    }


    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superheroDao.deleteSuperheroById(id);
        return "redirect:/superhero.html";
    }

    @GetMapping("/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile){
        superheroDao.saveImage(imageFile);
        return "redirect:/superhero.html";
    }
}
