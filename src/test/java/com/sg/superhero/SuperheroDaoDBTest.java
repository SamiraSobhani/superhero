package com.sg.superhero;

import com.sg.superhero.DAO.LocationDao;
import com.sg.superhero.DAO.OrganizationDao;
import com.sg.superhero.DAO.SightingDao;
import com.sg.superhero.DAO.SuperheroDao;
import com.sg.superhero.DTO.Location;
import com.sg.superhero.DTO.Organization;
import com.sg.superhero.DTO.Sighting;
import com.sg.superhero.DTO.Superhero;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperheroDaoDBTest {
    @Autowired
    LocationDao locationDao;
    @Autowired
    SuperheroDao superheroDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    SightingDao sightingDao;

    public SuperheroDaoDBTest() {
    }
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    @Before
    public void setUp() {
        List<Location> locations = locationDao.getAllLocation();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocation_id());
        }
        List<Superhero> superheroes = superheroDao.getAllSuperheros();
        for (Superhero superhero : superheroes) {
            superheroDao.deleteSuperheroById(superhero.getSuperhero_id());
        }
        List<Organization> organizations = organizationDao.getAllOrganization();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingByDate(sighting.getDate());
        }
    }

    @After
    public void tearDown() {
    }


}
