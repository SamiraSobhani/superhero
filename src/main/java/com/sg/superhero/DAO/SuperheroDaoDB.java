package com.sg.superhero.DAO;

import com.sg.superhero.DTO.*;
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
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "select * from superhero where id=?";
            Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setOrganizations(getOrganizationsForSuperhero(id));
            superhero.setSuperpowers(getSuperpowerForSuperhero(id));
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getSuperpowerForSuperhero(int superhero_id) {
        final String GET_SUPERPOWER_FOR_SUPERHERO = "SELECT s.* from superpower s"
                + " join superpower_superhero ss ON ss.superpower_id=s.id " +
                "where ss.superhero_id=?";
        return jdbc.query(GET_SUPERPOWER_FOR_SUPERHERO, new SuperpowerDaoDB.SuperpowerMapper(), superhero_id);
    }

    @Override
    public List<Organization> getOrganizationsForSuperhero(int superhero_id) {
        final String GET_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* from organization o"
                + " join organization_superhero os ON os.organization_id=o.id " +
                "where os.superhero_id=?";
        return jdbc.query(GET_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationDaoDB.OrganizationMapper(), superhero_id);
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        final String SELECT_ALL_SUPERHERO = "SELECT * FROM SUPERHERO";
        List<Superhero> superheroes = jdbc.query(SELECT_ALL_SUPERHERO, new SuperheroMapper());
        associateOrganizationAndSuperpower(superheroes);
        return superheroes;
    }

    private void associateOrganizationAndSuperpower(List<Superhero> superheroes) {
        for (Superhero superhero : superheroes) {
            superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getId()));
            superhero.setSuperpowers(getSuperpowerForSuperhero(superhero.getId()));
        }
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String ADD_SUPERHERO = "INSERT INTO superhero(name, description) values(?,?)";
        jdbc.update(ADD_SUPERHERO,
                superhero.getName(),
                superhero.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        insertSuperpowerSuperhero(superhero);
        insertOrganizationSuperhero(superhero);
        return superhero;
    }

    private void insertSuperpowerSuperhero(Superhero superhero) {
        final String INSERT_SUPERPOWER_SUPERHERO = "INSERT INTO " +
                "SUPERPOWER_SUPERHERO (superpower_id,superhero_id) values(?,?)";
        for (Superpower superpower : superhero.getSuperpowers()) {
            jdbc.update(INSERT_SUPERPOWER_SUPERHERO,
                    superpower.getId(),
                    superhero.getId());
        }
    }

    private void insertOrganizationSuperhero(Superhero superhero) {
        final String INSERT_ORGANIZATION_SUPERHERO = "INSERT INTO " +
                "ORGANIZATION_SUPERHERO (superhero_id,organization_id) VALUES(?,?)";
        for (Organization organization : superhero.getOrganizations()) {
            jdbc.update(INSERT_ORGANIZATION_SUPERHERO,
                    superhero.getId(),
                    organization.getId());
        }
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM ORGANIZATION_SUPERHERO WHERE superhero_id=?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, id);
        final String DELETE_LOCATION_SUPERHERO = "DELETE FROM LOCATION_SUPERHERO WHERE superhero_id=?";
        jdbc.update(DELETE_LOCATION_SUPERHERO, id);
        final String DELETE_SUPERPOWER_SUPERHERO = "DELETE FROM SUPERPOWER_SUPERHERO WHERE superhero_id=?";
        jdbc.update(DELETE_SUPERPOWER_SUPERHERO, id);
        final String DELETE_SUPERHERO = "DELETE FROM SUPERHERO WHERE id=?";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    @Override
    @Transactional
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero set name=?, description=? where id=?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getId());

        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM ORGANIZATION_SUPERHERO WHERE superhero_id=?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, superhero.getId());
        insertOrganizationSuperhero(superhero);
    }

    @Override
    public List<Superhero> getSuperherosForLocation(Location location) {
        final String GET_SUPERHERO_FOR_LOCATION = "SELECT s.* FROM superhero s JOIN location_superhero ls " +
                "ON ls.superhero_id=s.id where ls.location_id =?";
        List<Superhero> superheroes = jdbc.query(GET_SUPERHERO_FOR_LOCATION, new SuperheroMapper(), location.getId());
        return superheroes;
    }

    @Override
    public List<Superhero> getMemberForOrganization(Organization organization) {
        final String GET_MEMBER_FOR_ORGANIZATION = "select s.* from superhero s " +
                "join organization_superhero os on os.superhero_id=s.id where os.organization_id=?";
        List<Superhero> superheroes = jdbc.query(GET_MEMBER_FOR_ORGANIZATION, new SuperheroMapper(), organization.getId());
        return superheroes;
    }


    @Override
    public List<Superhero> getSuperheroForDateAndLocation(Date date, int location_id) {

        final String GET_LOCATIONANDSUPERHERO_FOR_DATE = "select s.* from superhero s" +
                " join location_superhero ls on s.id=ls.superhero_id where date=? && location_id=?";
        List<Superhero> superheroes = jdbc.query(GET_LOCATIONANDSUPERHERO_FOR_DATE, new SuperheroMapper(), date);
        return superheroes;
    }


    @Override
    public List<Location> getLocationForSuperhero(int superhero_id) {
        final String GET_LOCATION_FOR_SUPERHERO = "select l.* from location l " +
                "JOIN location_superhero ls on ls.location_id=l.id " +
                "where ls.superhero_id=?";

        return jdbc.query(GET_LOCATION_FOR_SUPERHERO, new LocationDaoDB.LocationMapper(), superhero_id);
    }



    public static final class SuperheroMapper implements RowMapper<Superhero> {


        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            return superhero;
        }
    }


}
