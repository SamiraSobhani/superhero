package com.sg.superhero.dao;

import com.sg.superhero.dto.Organization;
import com.sg.superhero.dto.Superhero;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORGANIZATION_BY_ID = "select * from organization where id=?";
            return jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }
    @Override
    public Organization getOrganizationByName(String name){
        final String GET_ORGANIZATION_BY_NAME="select * from organization where name=?";
        return jdbc.queryForObject(GET_ORGANIZATION_BY_NAME, new OrganizationMapper(), name);
    }

    @Override
    public List<Organization> getAllOrganization() {
        final String GET_ALL_ORGANIZATION = "select * from organization";
        List<Organization> organizations = jdbc.query(GET_ALL_ORGANIZATION, new OrganizationMapper());
        return organizations;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization (name, description, contact)"
                + "VALUES(?,?,?)";
        int update = jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getContact());
               int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(id);
             return organization;
    }

    private List<Superhero> getMembersForOrganization(int id) {
        final String GET_MEMBERS_FOR_ORGANIZATION = "SELECT S.* FROM SUPERHERO S " +
                "JOIN ORGANIZATION_SUPERHERO OS ON OS.SUPERHERO_ID=S.ID WHERE ID=? ";
        return jdbc.query(GET_MEMBERS_FOR_ORGANIZATION, new SuperheroDaoDB.SuperheroMapper(), id);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_ORGANIZATION_SUPERHERO_BY_ID = "delete from organization_superhero where organization_id=?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO_BY_ID,id);
        final String DELETE_ORGANIZATION_BY_ID = "delete from organization where id=?";
        jdbc.update(DELETE_ORGANIZATION_BY_ID, id);
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "update organization set name=?, description=?, contact=? where id=?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getContact(),
                organization.getId());
    }


    public static final class OrganizationMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setContact(rs.getString("contact"));
            return organization;
        }
    }
}
