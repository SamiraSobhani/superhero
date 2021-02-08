package com.sg.superhero.dto;



import java.sql.Date;
import java.util.Objects;


public class Sighting {

    private Date date;
    int location_id;
    int superhero_id;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getSuperhero_id() {
        return superhero_id;
    }

    public void setSuperhero_id(int superhero_id) {
        this.superhero_id = superhero_id;
    }

    public Sighting() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return getLocation_id() == sighting.getLocation_id() &&
                getSuperhero_id() == sighting.getSuperhero_id() &&
                getDate().equals(sighting.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getLocation_id(), getSuperhero_id());
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "date=" + date +
                ", location_id=" + location_id +
                ", superhero_id=" + superhero_id +
                '}';
    }
}
