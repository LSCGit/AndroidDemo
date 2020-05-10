package com.lsc.designpattern.pattern.abstractFactory;


/**
 * Created by lsc on 2020-04-18 12:31.
 * E-Mail:2965219926@qq.com
 */
public abstract class AbstractYellowHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("黄种人");
    }

    @Override
    public void talk() {
        System.out.println("黄种人说话");
    }
}
