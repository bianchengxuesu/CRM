package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    /**
     * 该方法是用来监听上下文域对象的方法，当服务器启动，上下文域对象创建对象创建完毕后，马上执行该方法
     * event:该参数能够取得监听的对象
     * 监听的是什么对象,就可以通过该参数能取得什么对象例如我们现在监听的是上下文域对象
     * 通过该参数就可以获取上下文域对象
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        System.out.println("上下文域对象创建了");

        ServletContext application = servletContextEvent.getServletContext();

        //取出数据字典
        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());

        //获取数据字典type的值，通过type的值获取对应的包含value的List
        Map<String, List<DicValue>> map = ds.getAll();
        //将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        for (String key : set) {

            //设置
            application.setAttribute(key,map.get(key));

        }
            


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
