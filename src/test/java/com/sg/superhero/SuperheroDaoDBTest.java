package com.sg.superhero;

import com.sg.superhero.DAO.*;
import com.sg.superhero.DTO.*;
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
public class SuperheroDaoDBTest {
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
    public void testAddAndGetSuperhero() {

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
        superhero.setName("test name");
        superhero.setDescription("test desc");
        superhero.setSuperpowers(superpowers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }


    @Test
    public void testGetAllSuperheros() {
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
        superhero.setName("test name");
        superhero.setDescription("test desc");
        superhero.setSuperpowers(superpowers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("test name2");
        superhero2.setDescription("test desc2");
        superhero2.setSuperpowers(superpowers);
        superhero2.setOrganizations(organizations);
        superhero2 = superheroDao.addSuperhero(superhero2);

        List<Superhero> superheros = superheroDao.getAllSuperheros();
        assertEquals(2, superheros.size());
        assertTrue(superheros.contains(superhero));
        assertTrue(superheros.contains(superhero2));
    }

    @Test
    public void testUpdateSuperhero() {

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
        superhero.setName("test name");
        superhero.setDescription("test desc");
        superhero.setSuperpowers(superpowers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superhero.setName("New Test superhero Name");
        Organization organization2 = new Organization();
        organization2.setName("Test org name 2");
        organization2.setDescription("Test desc2");
        organization2.setContact("test org contact2");
        organization2 = organizationDao.addOrganization(organization2);
        organizations.add(organization2);
        superhero.setOrganizations(organizations);

        superheroDao.updateSuperhero(superhero);
        assertNotEquals(superhero, fromDao);

        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    public void testDeleteSuperhero() {
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
        superhero.setName("test name");
        superhero.setDescription("test desc");
        superhero.setOrganizations(organizations);
        superhero.setSuperpowers(superpowers);
        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superheroDao.deleteSuperheroById(superhero.getId());

        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertNull(fromDao);


    }

}
