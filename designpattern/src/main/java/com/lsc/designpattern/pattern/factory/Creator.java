package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:51.
 * E-Mail:2965219926@qq.com
 *
 * 抽象工厂类
 */
public abstract class Creator {

    //创建一个产品对象，其输入参数类型可以自行设置
    public abstract <T extends Product> T createProduct(Class<T> clazz);
}
