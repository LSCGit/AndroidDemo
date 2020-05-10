package com.lsc.designpattern.pattern.builder;

/**
 * Created by lsc on 2020-04-18 15:27.
 * E-Mail:2965219926@qq.com
 *
 * 具体建造者
 */
public class ConcreteProduct extends Builder {

    private Product product = new Product();

    @Override
    void setPart() {
        //产品类的逻辑处理
    }

    //组件一个产品
    @Override
    Product buildProduct() {
        return product;
    }
}
