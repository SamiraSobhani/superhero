package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;
import com.sg.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SightingController {
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


    @GetMapping("/sighting.html")
    public String getAllSighting(Model model) {
        List<Test> tests=new ArrayList<>();
        List<Sighting> sightings = sightingDao.getAllSightings();
        sightings.forEach(sighting -> {
            Test test = new Test();
            test.setlName((locationDao.getLocationById(sighting.getLocation_id())).getName());
            test.setsName((superheroDao.getSuperheroById(sighting.getSuperhero_id())).getName());
            test.setsDate(sighting.getDate().toString());
            test.setLocationId(sighting.getLocation_id());
            test.setSuperheroId(sighting.getSuperhero_id());
            tests.add(test);
        });
        model.addAttribute("tests", tests);
        List<Location> locations = locationDao.getAllLocation();
        model.addAttribute("locations", locations);
        List<Superhero> superheroes = superheroDao.getAllSuperheros();
        model.addAttribute("superheroes", superheroes);

        return "sighting.html";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        int location_id = Integer.parseInt(request.getParameter("location_id"));
        int superhero_id = Integer.parseInt(request.getParameter("superhero_id"));
        String date = request.getParameter("date");
        Sighting sighting = new Sighting();
        sighting.setLocation_id(location_id);
        sighting.setSuperhero_id(superhero_id);
        sighting.setDate(Date.valueOf(date));
        sightingDao.addSighting(sighting);
        return "redirect:/sighting.html";
    }

    @GetMapping("editSighting.html")
    public String editSighting(HttpServletRequest request, Model model) {
        int location_id=Integer.parseInt(request.getParameter("location_id"));
        int superhero_id=Integer.parseInt(request.getParameter("superhero_id"));
        String myDate = request.getParameter("date");
        Date date = Date.valueOf(myDate);
        Sighting sighting = sightingDao.getSightingByLocationSuperheroDate(location_id, superhero_id,date);
        Test test1=new Test();
        test1.setlName((locationDao.getLocationById(sighting.getLocation_id())).getName());
        test1.setsName((superheroDao.getSuperheroById(sighting.getSuperhero_id())).getName());
        test1.setsDate(sighting.getDate().toString());
        test1.setLocationId(sighting.getLocation_id());
        test1.setSuperheroId(sighting.getSuperhero_id());
        model.addAttribute("test", test1);
        return "editSighting.html";
    }

    @PostMapping("editSighting.html")
    public String performEditSighting(HttpServletRequest request) {
        int location_id=Integer.parseInt(request.getParameter("location_id"));
        int superhero_id=Integer.parseInt(request.getParameter("superhero_id"));
        Date myDate = Date.valueOf(request.getParameter("date"));
        sightingDao.deleteSightingByLocationSuperheroDate(location_id, superhero_id,myDate);
        Sighting sighting=new Sighting();
        int newLocation_id=locationDao.getLocationByName(request.getParameter("new_location_name"));
        sighting.setLocation_id(newLocation_id);
        int newSuperhero_id=superheroDao.getSuperheroByName(request.getParameter("new_superhero_name"));
        sighting.setSuperhero_id(newSuperhero_id);
        sighting.setDate(Date.valueOf(request.getParameter("new_date")));
        sightingDao.addSighting(sighting);
        return "redirect:/sighting.html";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int location_id=Integer.parseInt(request.getParameter("location_id"));
        int superhero_id=Integer.parseInt(request.getParameter("superhero_id"));
        Date date = Date.valueOf(request.getParameter("date"));
        sightingDao.deleteSightingByLocationSuperheroDate(location_id, superhero_id,date);
        return "redirect:/sighting.html";
    }

    protected class Test{
        private String sName;
        private String lName;
        private String sDate;
        private int locationId;

        public int getLocationId() {
            return locationId;
        }

        public int getSuperheroId() {
            return superheroId;
        }

        private int superheroId;

        public void setLocationId(int locationId) {
            this.locationId = locationId;
        }

        public void setSuperheroId(int superheroId) {
            this.superheroId = superheroId;
        }

        public String getsName() {
            return sName;
        }

        public String getlName() {
            return lName;
        }

        public String getsDate() {
            return sDate;
        }

        public void setsDate(String sDate) {
            this.sDate = sDate;
        }

        public void setsName(String sName) {
            this.sName = sName;
        }

        public void setlName(String lName) {
            this.lName = lName;
        }
    }
}
