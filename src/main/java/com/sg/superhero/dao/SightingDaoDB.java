package com.sg.superhero.dao;

import com.sg.superhero.dto.Sighting;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String ADD_SIGHTING = "INSERT into location_superhero (location_id,superhero_id, date) values(?,?,?)";
        jdbc.update(ADD_SIGHTING,
                sighting.getLocation_id(),
                sighting.getSuperhero_id(),
                sighting.getDate());
        return sighting;
    }

    @Override
    public Sighting getSightingByLocationSuperheroDate(int location_id,int superhero_id,Date date) {
        try {
            final String GET_SIGHTING_BY_LOCATION_SUPERHERO_DATE = "SELECT * FROM location_superhero where location_id=? and superhero_id=? and date=? ";
            return jdbc.queryForObject(GET_SIGHTING_BY_LOCATION_SUPERHERO_DATE, new SightingMapper(),location_id,superhero_id, date);

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
    @Transactional
    public void deleteSightingByLocationSuperheroDate(int location_id,int superhero_id,Date date) {
        final String DELETE_SIGHTING_BY_LOCATION_SUPERHERO_DATE = "DELETE FROM location_superhero where location_id=? and superhero_id=? and date=?";
        jdbc.update(DELETE_SIGHTING_BY_LOCATION_SUPERHERO_DATE,location_id,superhero_id,date);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE LOCATION_SUPERHERO SET location_id=?, superhero_id=? where date=?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getLocation_id(),
                sighting.getSuperhero_id(),
                sighting.getDate());
    }



    public static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setLocation_id(Integer.parseInt(rs.getString("location_id")));
            sighting.setSuperhero_id(Integer.parseInt(rs.getString("superhero_id")));
            sighting.setDate(rs.getDate("date"));
            return sighting;
        }
    }

}
