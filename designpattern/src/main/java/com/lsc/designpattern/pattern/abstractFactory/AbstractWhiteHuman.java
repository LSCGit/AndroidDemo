package com.lsc.designpattern.pattern.abstractFactory;


/**
 * Created by lsc on 2020-04-18 12:32.
 * E-Mail:2965219926@qq.com
 */
public abstract class AbstractWhiteHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("白色人中");
    }

    @Override
    public void talk() {
        System.out.println("白色人说话");
    }
}
