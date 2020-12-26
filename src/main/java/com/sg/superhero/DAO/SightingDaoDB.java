package com.sg.superhero.DAO;

import com.sg.superhero.DTO.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String ADD_SIGHTING = "INSERT into location_superhero (superhero_id, location_id, date) values(?,?,?)";
        jdbc.update(ADD_SIGHTING,
                sighting.getSuperhero_id(),
                sighting.getLocation_id(),
                sighting.getDate());
        return sighting;
    }

    @Override
    public Sighting getSightingByDate(Date date) {
        try {
            final String GET_SIGHTING_BY_DATE = "SELECT * FROM location_superhero where date=? ";
            return jdbc.queryForObject(GET_SIGHTING_BY_DATE, new SightingMapper(), date);

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM location_superhero";
        return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public void deleteSightingByDate(Date date) {
        final String DELETE_SIGHTING_BY_DATE = "DELETE FROM location_superhero where date=?";
        jdbc.update(DELETE_SIGHTING_BY_DATE, date);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE LOCATION_SUPERHERO SET location_id=?, superhero_id=?,date=?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getLocation_id(),
                sighting.getSuperhero_id(),
                sighting.getDate());
    }



    public static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setDate(rs.getDate("date"));
            sighting.setSuperhero_id(rs.getInt("superhero_id"));
            sighting.setLocation_id(rs.getInt("location_id"));
            return sighting;
        }
    }

}
