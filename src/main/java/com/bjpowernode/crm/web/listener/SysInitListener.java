package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

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

        //数据字典处理完毕后，处理Stage2Possibility.properties文件
        /*
            处理stage2possibility.properties文件步骤:
            解析该文件，将该属性文件中的键值对关系处理成为java中键值对关系（map）
            Map<String(阶段stage ) ,String(可能性possibility )>
            pMap = ....
            pMap.put("01资质审查", 10);
            pMap.put("02需求分析",25);
            pMap.put("07... ",...);
            pMap保存值之后，放在服务器缓存中
            application.setAttribute("pMap",pMap ) ;

         */

        Map<String, String> pMap = new HashMap<String, String>();

        //使用资源绑定器,因为文件放在resources目录下，所以不需要前面的/等，资源绑定器不用加.properties后缀
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){

            //阶段
            String key = e.nextElement();

            //可能性
            String value = rb.getString(key);

            pMap.put(key,value);

        }

        //讲pMap保存到服务器缓存中
        application.setAttribute("pMap",pMap);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
