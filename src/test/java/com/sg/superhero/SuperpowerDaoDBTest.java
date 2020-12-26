package com.sg.superhero;

import com.sg.superhero.DAO.*;
import com.sg.superhero.DTO.*;
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
public class SuperpowerDaoDBTest {
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

    public SuperpowerDaoDBTest() {
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
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingByDate(sighting.getDate());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSuperpower() {

        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
    }

    @Test
    public void testGetAllSuperpowers() {
        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setName("test sup pow name");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));
    }

    @Test
    public void testUpdateSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setName("test sup pow name");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

        superpower.setName("test update name");
        superpowerDao.updateSuperpower(superpower);
        assertNotEquals(superpower, fromDao);

        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

    }

    @Test
    public void testDeleteSuperpower() {
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
        superhero.setName("test name");
        superhero.setDescription("ddddd");
        superhero.setSuperpowers(superpowers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

        superpowerDao.deleteSuperpowerById(superpower.getId());
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertNull(fromDao);

    }
}
