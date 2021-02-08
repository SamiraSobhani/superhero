package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;
import com.sg.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;



@Controller
public class indexController {
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

    @RequestMapping("/")
    public String getAllSighting(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        System.out.println(sightings);
        model.addAttribute("sightings", sightings);
        return "index.html";

    }
}
