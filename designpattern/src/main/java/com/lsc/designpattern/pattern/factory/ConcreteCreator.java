package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:53.
 * E-Mail:2965219926@qq.com
 *
 * 具体工厂类
 */
public class ConcreteCreator extends Creator {

    @Override
    public <T extends Product> T createProduct(Class<T> clazz) {

        Product product = null;
        try{
            product = (Product)Class.forName(clazz.getName()).newInstance();

        }catch (Exception e){}
        return (T)product;
    }
}
