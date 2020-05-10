package com.lsc.designpattern.pattern.builder;

/**
 * Created by lsc on 2020-04-18 15:29.
 * E-Mail:2965219926@qq.com
 *
 * 导演类
 */
public class Director {

    Builder builder = new ConcreteProduct();
    Product getAProduct(){
        builder.setPart();

        return builder.buildProduct();
    }
}
