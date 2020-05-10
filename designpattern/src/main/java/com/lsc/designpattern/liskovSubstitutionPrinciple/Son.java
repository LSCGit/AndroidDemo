package com.lsc.designpattern.liskovSubstitutionPrinciple;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsc on 2020-04-18 01:25.
 * E-Mail:2965219926@qq.com
 */
public class Son extends Father {

    public Collection doSomething(HashMap map) {
        System.out.println("子类被执行");
        return map.values();
    }
}
