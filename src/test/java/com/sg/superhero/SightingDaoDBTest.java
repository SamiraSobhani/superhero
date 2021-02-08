package com.sg.superhero;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {

    @Autowired
    LocationDao locationDao;
    @Autowired
    SuperheroDao superheroDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    SightingDao sightingDao;
    @Autowired
    SuperpowerDao superpowerDao;

    public SightingDaoDBTest() {
    }

    @BeforeClass
    public static void setupClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setup() {
        List<Location> locations = locationDao.getAllLocation();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        List<Superhero> superheroes = superheroDao.getAllSuperheros();
        for (Superhero superhero : superheroes) {
            superheroDao.deleteSuperheroById(superhero.getId());
        }
        List<Organization> organizations = organizationDao.getAllOrganization();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingByLocationSuperheroDate(sighting.getLocation_id(),sighting.getSuperhero_id(),sighting.getDate());
        }
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSighting() {
        Organization organization = new Organization();
        organization.setName("test organization name");
        organization.setDescription("test desc");
        organization.setContact("test org contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("test name su");
        superhero.setDescription("test desc");
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Name lo");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation_id(location.getId());
        sighting.setSuperhero_id(superhero.getId());
        Date newDate = new java.sql.Date(2020, 12, 24);
        sighting.setDate(newDate);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingByLocationSuperheroDate(sighting.getLocation_id(),sighting.getSuperhero_id(),sighting.getDate());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSighting() {
        Organization organization = new Organization();
        organization.setName("test organization name");
        organization.setDescription("test desc");
        organization.setContact("test org contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("test name su");
        superhero.setDescription("test desc");
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Name lo");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation_id(location.getId());
        sighting.setSuperhero_id(superhero.getId());
        Date newDate = new java.sql.Date(2020, 12, 24);
        sighting.setDate(newDate);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setLocation_id(location.getId());
        sighting2.setSuperhero_id(superhero.getId());
        Date newDate2 = new java.sql.Date(2020, 12, 20);
        sighting2.setDate(newDate2);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {

        Organization organization = new Organization();
        organization.setName("test organization name");
        organization.setDescription("test desc");
        organization.setContact("test org contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("test name su");
        superhero.setDescription("test desc");
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Name lo");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation_id(location.getId());
        sighting.setSuperhero_id(superhero.getId());
        Date newDate = new java.sql.Date(2020, 12, 24);
        sighting.setDate(newDate);
        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingByLocationSuperheroDate(sighting.getLocation_id(),sighting.getSuperhero_id(),sighting.getDate());
        assertEquals(sighting, fromDao);


        superhero.setName("test update name");
        superhero.setDescription("test desc2");
        Superpower superpower2 = new Superpower();
        superpower2.setName("update super power name");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        superpowers.add(superpower2);
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);


        superhero = superheroDao.addSuperhero(superhero);
        sighting.setSuperhero_id(superhero.getId());
        sighting.setLocation_id(location.getId());
        Date newDate2 = new java.sql.Date(2019, 05, 16);
        sighting.setDate(newDate2);

        sightingDao.updateSighting(sighting);
        assertNotEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSighting() {

        Organization organization = new Organization();
        organization.setName("test organization name");
        organization.setDescription("test desc");
        organization.setContact("test org contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);

        Superhero superhero = new Superhero();
        superhero.setName("test name su");
        superhero.setDescription("test desc");
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Name lo");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation_id(location.getId());
        sighting.setSuperhero_id(superhero.getId());
        Date newDate = new java.sql.Date(2020, 12, 24);
        sighting.setDate(newDate);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingByLocationSuperheroDate(sighting.getLocation_id(),sighting.getSuperhero_id(),sighting.getDate());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingByLocationSuperheroDate(sighting.getLocation_id(),sighting.getSuperhero_id(),sighting.getDate());

        fromDao = sightingDao.getSightingByLocationSuperheroDate(sighting.getLocation_id(),sighting.getSuperhero_id(),sighting.getDate());
        assertNull(fromDao);

    }


}
