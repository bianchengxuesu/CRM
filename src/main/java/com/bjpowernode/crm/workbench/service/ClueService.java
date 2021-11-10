package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {

    boolean deleteById(String id);

    boolean save(Clue c);

    List<Clue> getAllClues();

    Clue detail(String id);
}
