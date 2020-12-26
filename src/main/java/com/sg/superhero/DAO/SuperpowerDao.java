package com.sg.superhero.DAO;


import com.sg.superhero.DTO.Superpower;

import java.util.List;

public interface SuperpowerDao {
    Superpower addSuperpower(Superpower superpower);

    Superpower getSuperpowerById(int id);

    List<Superpower> getAllSuperpowers();

    void deleteSuperpowerById(int id);

    void updateSuperpower(Superpower superpower);
}
