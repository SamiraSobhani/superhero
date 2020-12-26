package com.sg.superhero.DTO;

import java.util.List;
import java.util.Objects;

public class Superhero {

    private int id;
    private String name;
    private String description;
    private List<Superpower> superpowers;
    private List<Organization> organizations;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
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
        return getId() == superhero.getId() &&
                getName().equals(superhero.getName()) &&
                Objects.equals(getDescription(), superhero.getDescription()) &&
                getSuperpowers().equals(superhero.getSuperpowers()) &&
                getOrganizations().equals(superhero.getOrganizations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getSuperpowers(), getOrganizations());
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", superpowers=" + superpowers +
                ", organizations=" + organizations +
                '}';
    }
}
