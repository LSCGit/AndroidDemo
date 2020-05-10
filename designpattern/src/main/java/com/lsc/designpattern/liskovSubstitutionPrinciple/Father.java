package com.lsc.designpattern.liskovSubstitutionPrinciple;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by lsc on 2020-04-18 01:24.
 * E-Mail:2965219926@qq.com
 */
public class Father {

    public Collection doSomething(HashMap map){
        System.out.println("父类被执行。。。");

        return map.values();
    }
}
