package com.lsc.designpattern.pattern.builder;

/**
 * Created by lsc on 2020-04-18 15:26.
 * E-Mail:2965219926@qq.com
 *
 * 抽象建造者
 */
public abstract class Builder {

    //设置产品不同部分，以获得不同的产品
    abstract void setPart();
    //建造产品
    abstract Product buildProduct();
}
