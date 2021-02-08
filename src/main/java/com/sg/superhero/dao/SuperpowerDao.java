package com.sg.superhero.dao;


import com.sg.superhero.dto.Superpower;

import java.util.List;

public interface SuperpowerDao {

    Superpower addSuperpower(Superpower superpower);

    Superpower getSuperpowerById(int id);

    Superpower getSuperpowerByName(String name);

    List<Superpower> getAllSuperpowers();

    void deleteSuperpowerById(int id);

    void updateSuperpower(Superpower superpower);
}
