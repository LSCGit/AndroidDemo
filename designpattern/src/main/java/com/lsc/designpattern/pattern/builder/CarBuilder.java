package com.lsc.designpattern.pattern.builder;

import java.util.ArrayList;

/**
 * Created by lsc on 2020-04-18 15:19.
 * E-Mail:2965219926@qq.com
 */
public abstract class CarBuilder {

    abstract void setSequence(ArrayList<String> sequence);
    abstract CarModel getCarModel();
}
