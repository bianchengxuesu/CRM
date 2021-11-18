package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranService {
    List<Contacts> getContactsListByName(String cname);

    boolean save(Tran t, String customerName);
}
