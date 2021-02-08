package com.sg.superhero.dao;

import com.sg.superhero.dto.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;


    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "select * from location where id=?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public int getLocationByName(String name) {

        final String SELECT_LOCATION_BY_NAME = "select * from location where name=?";
        int location_id=jdbc.queryForObject(SELECT_LOCATION_BY_NAME,
                new LocationMapper(), name).getId();
        return location_id;
    }


    @Override
    public List<Location> getAllLocation() {
        final String GET_ALL_LOCATION = "select * from location";
        return jdbc.query(GET_ALL_LOCATION, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, address, latitude, longitude)"
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_LOCATION_SUPERHERO_BY_ID = "delete from location_superhero where location_id=?";
        jdbc.update(DELETE_LOCATION_SUPERHERO_BY_ID, id);
        final String DELETE_LOCATION_BY_ID = "delete from location where id=?";
        jdbc.update(DELETE_LOCATION_BY_ID, id);

    }


    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "update location set name=?, description=?,"
                + "address=?, latitude=?, longitude=? where id=?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }


    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getDouble("latitude"));
            location.setLongitude(rs.getDouble("longitude"));
            return location;
        }
    }
}
