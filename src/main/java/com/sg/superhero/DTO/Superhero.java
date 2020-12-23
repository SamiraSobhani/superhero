package com.sg.superhero.DTO;

import java.util.List;
import java.util.Objects;

public class Superhero {

    private int superhero_id;
    private String name;
    private String description;
    private String superpower;
    private List<Organization> organizations;


    public int getSuperhero_id() {
        return superhero_id;
    }

    public void setSuperhero_id(int superhero_id) {
        this.superhero_id = superhero_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superhero)) return false;
        Superhero superhero = (Superhero) o;
        return getSuperhero_id() == superhero.getSuperhero_id() &&
                getName().equals(superhero.getName()) &&
                Objects.equals(getDescription(), superhero.getDescription()) &&
                getSuperpower().equals(superhero.getSuperpower()) &&
                getOrganizations().equals(superhero.getOrganizations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuperhero_id(), getName(), getDescription(), getSuperpower(), getOrganizations());
    }
}
