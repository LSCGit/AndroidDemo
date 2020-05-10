package com.lsc.designpattern.dip;

/**
 * Created by lsc on 2020-04-18 01:45.
 * E-Mail:2965219926@qq.com
 *
 * 场景
 */
public class Client {

    public static void main(String[] args) {
        Driver zhangSan = new Driver();
        Benz benz = new Benz();
        //张三开奔驰
        zhangSan.drive(benz);
    }
}
