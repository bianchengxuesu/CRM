package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);


    @Override
    public Map<String, List<DicValue>> getAll() {

        //将数据从数据字典取出, 先获得type的list
        List<DicType> typeList = dicTypeDao.getTypeList();

        Map<String,List<DicValue>> map = new HashMap<String, List<DicValue>>();

        //将字典类型列表遍历
        for (DicType dicType : typeList) {

            //取得每种类型的字典类型的编码, value表中typeCode的值对应的是type表中的code值
            String code = dicType.getCode();

            //根据字典类型取得字典值列表
            List<DicValue> valueList = dicValueDao.getListByCode(code);

            map.put(code+"List",valueList);

        }

       return map;
    }
}
