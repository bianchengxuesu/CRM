package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {
    List<Contacts> getContactsListByName(String cname);

    boolean save(Tran t, String customerName);

    List<Tran> getAllTrans();

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
