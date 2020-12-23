package com.sg.superhero;

import com.sg.superhero.DAO.LocationDao;
import com.sg.superhero.DAO.OrganizationDao;
import com.sg.superhero.DAO.SightingDao;
import com.sg.superhero.DAO.SuperheroDao;
import com.sg.superhero.DTO.Location;
import com.sg.superhero.DTO.Organization;
import com.sg.superhero.DTO.Sighting;
import com.sg.superhero.DTO.Superhero;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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

  public LocationDaoDBTest(){
  }

  @BeforeClass
  public static void setupClass() {
  }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setup(){
    List<Location> locations=locationDao.getAllLocation();
    for(Location location:locations){
        locationDao.deleteLocationById(location.getLocation_id());
    }
    List<Superhero> superheroes=superheroDao.getAllSuperheros();
    for(Superhero superhero:superheroes){
        superheroDao.deleteSuperheroById(superhero.getSuperhero_id());
    }
    List<Organization> organizations=organizationDao.getAllOrganization();
    for(Organization organization:organizations) {
        organizationDao.deleteOrganizationById(organization.getId());
    }
    List<Sighting> sightings= sightingDao.getAllSightings();
    for(Sighting sighting:sightings){
        sightingDao.deleteSightingByDate(sighting.getDate());
    }
    }
    @After
    public void tearDown(){
    }

    @Test
    public void testAddAndGetLocation(){
    Location location=new Location();
    location.setName("Test Name");
    location.setDescription("Test Description");
    location.setAddress("Test Address");
    location.setLatitude(15.123);
    location.setLongitude(45.1234);
    location=locationDao.addLocation(location);

    Location fromDao=locationDao.getLocationById(location.getLocation_id());

    assertEquals(location,fromDao);
    }

    @Test
  public void testGetAllLocation(){
      Location location=new Location();
      location.setName("Test Name");
      location.setDescription("Test Description");
      location.setAddress("Test Address");
      location.setLatitude(15.123);
      location.setLongitude(45.1234);
      location=locationDao.addLocation(location);

      Location location2=new Location();
      location2.setName("Test Name2");
      location2.setDescription("Test Description2");
      location2.setAddress("Test Address2");
      location2.setLatitude(25.123);
      location2.setLongitude(55.1234);
      location2=locationDao.addLocation(location2);

      List<Location> locations=locationDao.getAllLocation();
      assertEquals(2, locations.size());
      assertTrue(locations.contains(location));
      assertTrue(locations.contains(location2));
    }

    @Test
  public void testUpdateLocation(){
      Location location=new Location();
      location.setName("Test Name");
      location.setDescription("Test Description");
      location.setAddress("Test Address");
      location.setLatitude(15.123);
      location.setLongitude(45.1234);
      location=locationDao.addLocation(location);

      Location fromDao=locationDao.getLocationById(location.getLocation_id());

      assertEquals(location,fromDao);

      location.setName("New Test Name");
      locationDao.updateLocation(location);
      assertNotEquals(location, fromDao);
      fromDao=locationDao.getLocationById(location.getLocation_id());
      assertEquals(location,fromDao);
    }
    @Test
  public void testDeleteLocationById(){
      Location location=new Location();
      location.setName("Test Name");
      location.setDescription("Test Description");
      location.setAddress("Test Address");
      location.setLatitude(15.123);
      location.setLongitude(45.1234);
      location=locationDao.addLocation(location);

      Superhero superhero=new Superhero();
      superhero.setName("Test superhero Name");
      superhero.setSuperpower("superPower");
      superhero.setDescription("Test superhero desc");

      Location fromDao=locationDao.getLocationById(location.getLocation_id());
      assertEquals(location,fromDao);

      locationDao.deleteLocationById(location.getLocation_id());
      fromDao=locationDao.getLocationById(location.getLocation_id());
      assertNull(fromDao);
    }
}
