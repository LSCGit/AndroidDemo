package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:56.
 * E-Mail:2965219926@qq.com
 *
 * 场景类
 */
public class Client {

    public static void main(String[] args) {
        Creator creator = new ConcreteCreator();
        Product product = creator.createProduct(ConcreteProduct1.class);

    }
}
