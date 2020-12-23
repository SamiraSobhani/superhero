package com.sg.superhero.DAO;

import com.sg.superhero.DTO.Location;
import com.sg.superhero.DTO.Organization;
import com.sg.superhero.DTO.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;

@Repository
public class SuperheroDaoDB implements SuperheroDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "select * from superhero where superhero_id=?";
            Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setOrganizations(getOrganizationsForSuperhero(id));
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        final String SELECT_ALL_SUPERHERO="SELECT * FROM SUPERHERO";
        return jdbc.query(SELECT_ALL_SUPERHERO,new SuperheroMapper());
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String ADD_SUPERHERO="INSERT INTO superhero(name, description, superpower) values(?,?,?)";
        jdbc.update(ADD_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower());
        int newId=jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setSuperhero_id(newId);
        insertOrganizationSuperhero(superhero);
        return superhero;

    }

    private void insertOrganizationSuperhero(Superhero superhero) {
        final String INSERT_ORGANIZATION_SUPERHERO = "INSERT INTO ORGANIZATION_SUPERHERO (ID,SUPERHERO_ID) VALUES(?,?)";
        for (Organization organization : superhero.getOrganizations()) {
            jdbc.update(INSERT_ORGANIZATION_SUPERHERO,
                    superhero.getSuperhero_id(),
                    organization.getId());
        }
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
    final String DELETE_ORGANIZATION_SUPERHERO="DELETE FROM ORGANIZATION_SUPERHERO WHERE superhero_id=?";
    jdbc.update(DELETE_ORGANIZATION_SUPERHERO,id);
    final String DELETE_LOCATION_SUPERHERO="DELETE FROM LOCATION_SUPERHERO WHERE superhero_id=?";
    jdbc.update(DELETE_LOCATION_SUPERHERO,id);
    final String DELETE_SUPERHERO="DELETE FROM SUPERHERO WHERE superhero_id=?";
    jdbc.update(DELETE_SUPERHERO,id);
    }

    @Override
    @Transactional
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO="UPDATE superhero set name=?, description=?, superpower=? where superhero_id=?";
        jdbc.update(UPDATE_SUPERHERO,superhero.getName(),superhero.getDescription(),superhero.getSuperpower(),superhero.getSuperhero_id());
        final String DELETE_ORGANIZATION_SUPERHERO="DELETE FROM ORGANIZATION_SUPERHERO WHERE superhero_id=?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO,superhero.getSuperhero_id());
        insertOrganizationSuperhero(superhero);
    }

    @Override
    public List<Superhero> getSuperherosForLocation(Location location) {
        final String GET_SUPERHERO_FOR_LOCATION="SELECT s.* FROM superhero s JOIN location_superhero ls " +
                "ON ls.superhero_id=s.superhero_id where ls.location_id =?";
       List<Superhero> superheroes=jdbc.query(GET_SUPERHERO_FOR_LOCATION,new SuperheroMapper(),location.getLocation_id());
        return superheroes;
    }
    @Override
    public List<Superhero> getMemberForOrganization(Organization organization) {
        final String GET_MEMBER_FOR_ORGANIZATION="select s.* from superhero s join organization_superhero os on os.superhero_id=s.superhero_id where os.id=?";
        List<Superhero> superheroes=jdbc.query(GET_MEMBER_FOR_ORGANIZATION,new SuperheroMapper(),organization.getId());
        return superheroes;
    }


    @Override
    public List<Superhero> getSuperheroForDateAndLocation(Date date, Location location){

        return null;
    }




    @Override
    public List<Location> getLocationForSuperhero(int superhero_id) {
        final String GET_LOCATION_FOR_SUPERHERO= "select l.* from location l " +
                "JOIN location_superhero ls on ls.location_id=l.location_id " +
                "where ls.superhero_id=?";

        return jdbc.query(GET_LOCATION_FOR_SUPERHERO, new LocationDaoDB.LocationMapper(),superhero_id);
    }



    @Override
    public List<Organization> getOrganizationsForSuperhero(int superhero_id) {
        final String GET_ORGANIZATIONS_FOR_SUPERHERO="SELECT o.* from organization o"
                +" join organization_superhero os ON os.id=o.id " +
                "where os.superhero_id=?";
        return jdbc.query(GET_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationDaoDB.OrganizationMapper(),superhero_id);
    }


    public static final class SuperheroMapper implements RowMapper<Superhero>{

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero=new Superhero();
            superhero.setSuperhero_id(rs.getInt("superhero_id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            superhero.setSuperpower(rs.getString("superpower"));
            return superhero;
        }
    }
}
