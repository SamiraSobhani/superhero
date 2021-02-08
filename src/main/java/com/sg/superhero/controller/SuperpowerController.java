package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperpowerController {
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

    @PostMapping("/addSuperpower")
    public String addSuperpower(HttpServletRequest request) {
        String name = request.getParameter("name");
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpowerDao.addSuperpower(superpower);
        return "redirect:/superpower.html";
    }

    @GetMapping("superpower.html")
    public String getAllSuperpower(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpower.html";
    }

    @GetMapping("editSuperpower.html")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }

    @PostMapping("editSuperpower.html")
    public String performEditSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        superpower.setName(request.getParameter("name"));
        superpowerDao.updateSuperpower(superpower);
        return "redirect:/superpower.html";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id= Integer.parseInt(request.getParameter("id"));
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpower.html";
    }
}
