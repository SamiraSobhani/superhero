package com.sg.superhero.dto;

import java.util.List;
import java.util.Objects;

public class Superhero {

    private int id;
    private String name;
    private String description;
    private List<Superpower> superpower;
    private List<Organization> organization;
    private String photo;

    public Superhero() {
    }

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

    public List<Superpower> getSuperpower() {
        return superpower;
    }

    public void setSuperpower(List<Superpower> superpower) {
        this.superpower = superpower;
    }

    public List<Organization> getOrganization() {
        return organization;
    }

    public void setOrganization(List<Organization> organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superhero)) return false;
        Superhero superhero = (Superhero) o;
        return getId() == superhero.getId() &&
                getName().equals(superhero.getName()) &&
                Objects.equals(getDescription(), superhero.getDescription()) &&
                getSuperpower().equals(superhero.getSuperpower()) &&
                getOrganization().equals(superhero.getOrganization());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getSuperpower(), getOrganization());
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", superpower=" + superpower +
                ", organization=" + organization +
                '}';
    }
}
