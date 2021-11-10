package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {


    int save(Clue c);


    List<Clue> getAllClues();

    Clue getClueById(String id);
}
