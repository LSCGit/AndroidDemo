package com.lsc.designpattern.pattern.factory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsc on 2020-04-18 13:14.
 * E-Mail:2965219926@qq.com
 *
 * 延迟加载工厂类
 */
public class ProductFactory {

    private static final Map<String,Product> prMap = new HashMap<>();

    public static synchronized Product createProduct(String type) throws Exception{

        Product product =null;
        if (prMap.containsKey(type)){
            //如果Map中已经有这个对象
            product = prMap.get(type);
        }else{
            if (type.equals("Product1")){
                product = new ConcreteProduct1();
            }else{
                product = new ConcreteProduct1();
            }
            //把对象放到缓存容器中
            prMap.put(type,product);
        }

        return product;
    }
}
