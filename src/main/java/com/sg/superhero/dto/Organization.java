package com.sg.superhero.dto;

import java.util.Objects;

public class Organization {
    private int id;
    private String name;
    private String description;
    private String contact;

    public Organization() {
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return getId() == that.getId() &&
                getName().equals(that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                getContact().equals(that.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getContact());
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
