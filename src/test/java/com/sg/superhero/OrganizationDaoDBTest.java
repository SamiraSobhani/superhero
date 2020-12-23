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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    LocationDao locationDao;
    @Autowired
    SuperheroDao superheroDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    SightingDao sightingDao;

    public OrganizationDaoDBTest() {
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

    @Test
    public void testAddAndGetOrganization() {
        Organization organization = new Organization();
        organization.setName("Test org Name");
        organization.setDescription("Test org Desc");
        organization.setContact("Test contact");
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }

    @Test
    public void testGetAllOrganization(){

            Organization organization = new Organization();
            organization.setName("Test org Name");
            organization.setDescription("Test org Desc");
            organization.setContact("Test contact");
            organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("Test org Name2");
        organization2.setDescription("Test org Desc2");
        organization2.setContact("Test contact2");
        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizations=organizationDao.getAllOrganization();

        assertEquals(2,organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

@Test
    public void testUpdateOrganization(){
    Organization organization = new Organization();
    organization.setName("Test org Name");
    organization.setDescription("Test org Desc");
    organization.setContact("Test contact");
    organization = organizationDao.addOrganization(organization);

    Organization fromDao = organizationDao.getOrganizationById(organization.getId());
    assertEquals(organization, fromDao);

    organization.setName("new test org name");
    organizationDao.updateOrganization(organization);

    assertNotEquals(organization, fromDao);
    fromDao=organizationDao.getOrganizationById(organization.getId());
    assertEquals(organization, fromDao);
}
@Test
    public void testDeleteOrganizationById(){

    Organization organization = new Organization();
    organization.setName("Test org Name");
    organization.setDescription("Test org Desc");
    organization.setContact("Test contact");
    organization = organizationDao.addOrganization(organization);

    Superhero superhero=new Superhero();
    superhero.setName("test name");
    superhero.setDescription("ddddd");
    superhero.setSuperpower("ggggg");
    superhero=superheroDao.addSuperhero(superhero);

    Organization fromDao=organizationDao.getOrganizationById(organization.getId());
    assertEquals(organization,fromDao);

    organizationDao.deleteOrganizationById(organization.getId());
    fromDao=organizationDao.getOrganizationById(organization.getId());
    assertNull(fromDao);

}



}
