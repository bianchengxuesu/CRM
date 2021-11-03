package com.powernode.settings.test;

import com.powernode.crm.utils.DateTimeUtil;
import com.powernode.crm.utils.MD5Util;
import org.junit.Test;

public class Test1 {

//    @Test
//    public static void main(String[] args) {
//        System.out.println("123");
////        System.out.println(1/0);
//    }

    @Test
    public  void test1(){
        String time = "2021-12-12 10:10:10";
        //当前系统时间
        String sysTime = DateTimeUtil.getSysTime();
        int test = sysTime.compareTo(time);
        System.out.println(test);

        String IP = "192.1.1.0";
        //允许访问的ip地址群
        String ips = "192.1.1.0,127.0.0.1";
        if(ips.contains(IP)){
            System.out.println("ip ok");
        }else {
            System.out.println("ip is not ok,please call administator");

        }

        String pwd = "123456";
        String pwd1 = "123455";
        String md5 = MD5Util.getMD5(pwd);
        String md = MD5Util.getMD5(pwd1);
        System.out.println(md5);
        System.out.println(md);
    }
}
