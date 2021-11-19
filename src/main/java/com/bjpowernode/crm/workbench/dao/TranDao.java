package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    List<Tran> getAllTrans();

    int save(Tran t);

    Tran detail(String id);
}
