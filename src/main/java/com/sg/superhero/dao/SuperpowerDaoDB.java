package com.sg.superhero.dao;


import com.sg.superhero.dto.Superhero;
import com.sg.superhero.dto.Superpower;
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
public class SuperpowerDaoDB implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String ADD_SUPERPOWER = "INSERT into superpower(name) values(?)";
        jdbc.update(ADD_SUPERPOWER,
                superpower.getName());
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(id);
       //getSuperherosForSuperpower(superpower.getId());
        return superpower;
    }

    private List<Superhero> getSuperherosForSuperpower(int id) {
        final String GET_SUPERHEROS_FOR_SUPERPOWER = "SELECT s.* FROM SUPERHERO S " +
                "JOIN SUPERPOWER_SUPERHERO SS ON SS.SUPERHERO_ID=S.ID WHERE superpower_id=? ";
        return jdbc.query(GET_SUPERHEROS_FOR_SUPERPOWER, new SuperheroDaoDB.SuperheroMapper(), id);
    }

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String GET_SUPERPOWER_BY_ID = "select * from superpower where id=?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }


    @Override
    public Superpower getSuperpowerByName(String name){
        final String GET_SUPERPOWER_BY_NAME="select * from superpower where name=?";
        return jdbc.queryForObject(GET_SUPERPOWER_BY_NAME, new SuperpowerMapper(), name);
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String GET_ALL_SUPERPOWERS = "select * from SUPERPOWER";
        List<Superpower> superpowers = jdbc.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
        return superpowers;
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        final String DELETE_SUPERPOWER_SUPERHERO_BY_ID = "delete from superpower_superhero where superpower_id=?";
        jdbc.update(DELETE_SUPERPOWER_SUPERHERO_BY_ID, id);
        final String DELETE_SUPERPOWER_BY_ID = "delete from superpower where id=?";
        jdbc.update(DELETE_SUPERPOWER_BY_ID, id);

    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "update superpower set name=? where id=?";
        jdbc.update(UPDATE_SUPERPOWER,
                superpower.getName(),
                superpower.getId());
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {
        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("id"));
            superpower.setName(rs.getString("name"));
            return superpower;
        }
    }
}
