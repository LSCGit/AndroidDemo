package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:30.
 * E-Mail:2965219926@qq.com
 */
public class BlackHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("黑色人");
    }

    @Override
    public void talk() {
        System.out.println("黑人说话");
    }
}
