package com.lsc.designpattern.pattern.singleton;

/**
 * Created by lsc on 2020-04-18 10:15.
 * E-Mail:2965219926@qq.com
 */
public class Emperor {

    private static final Emperor emperor = new Emperor();
    private Emperor(){}
    public static Emperor getInstance(){
        return emperor;
    }

    public void say(){
        System.out.println("我就是皇帝其实黄");
    }
}
