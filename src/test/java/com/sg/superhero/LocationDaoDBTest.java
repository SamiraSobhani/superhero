package com.sg.superhero;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

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

    public LocationDaoDBTest() {
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
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Name2");
        location2.setDescription("Test Description2");
        location2.setAddress("Test Address2");
        location2.setLatitude(25.123);
        location2.setLongitude(55.1234);
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocation();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);

        location.setName("New Test Name");
        locationDao.updateLocation(location);
        assertNotEquals(location, fromDao);
        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(15.123);
        location.setLongitude(45.1234);
        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setName("Test org Name");
        organization.setDescription("Test org Desc");
        organization.setContact("Test contact");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);


        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(superpower);


        Superhero superhero = new Superhero();
        superhero.setName("Test superhero Name");
        superhero.setDescription("Test superhero desc");
        superhero.setSuperpower(superpowers);
        superhero.setOrganization(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        locationDao.deleteLocationById(location.getId());
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }
}
