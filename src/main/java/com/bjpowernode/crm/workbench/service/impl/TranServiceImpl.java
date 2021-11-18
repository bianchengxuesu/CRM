package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;

import java.util.List;

public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);


    @Override
    public List<Contacts> getContactsListByName(String cname) {

        List<Contacts> cList =  contactsDao.getContactsListByName(cname);

        return cList;

    }

    @Override
    public boolean save(Tran t, String customerName) {

        boolean flag = true;
        /*
            交易添加业务:
                在做添加之前，参数t里面就少了一项信息，就是客户的主键，customerId先处理客户相关的需求
                (1）判断customerName，根据客户名称在客户表进行精确查询
                    如果有这个客户，则取出这个客户的id，封装到t对象中
                    如果没有这个客户，则再客户表新建一条客户信息，然后将新建的客户的id取出，封装到t对象中
                (2）经过以上操作后，t对象中的信息.就全了，
                  执行添加交易的操作
                (3）添加交易完毕后，需要创建一条交易历史
         */
        Customer customer = customerDao.getCustomerByName(customerName);
        if(customer == null){

            //如果为空，创建一个
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(t.getContactSummary());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setOwner(t.getOwner());
            customer.setName(customerName);

            //添加客户
            int count1 = customerDao.save(customer);
            if (count1 != 1){
                flag = false;
            }

        }

        //通过以上处理，不管是查询出来的客户，还是创建出来的客户，我们都有了
        //因此id就有了,将客户id封装到t对象中
        t.setCustomerId(customer.getId());

        //添加交易
        int count2 = tranDao.save(t);
        if(count2 != 1){
            flag = false;
        }

        //添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());

        int count3 = tranHistoryDao.save(th);
        if (count3 != 1){
            flag = false;
        }

        return flag;

    }
}
