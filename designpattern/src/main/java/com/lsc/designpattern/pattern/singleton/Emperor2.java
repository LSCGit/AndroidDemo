package com.lsc.designpattern.pattern.singleton;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lsc on 2020-04-18 12:16.
 * E-Mail:2965219926@qq.com
 * 固定数量的皇帝类
 */
public class Emperor2 {

    //定义最多能产生的实例数量
    private static int maxNumOfEmperor = 2;
    //每个皇帝都有名字，使用一个ArrayList来容纳，每个对象的私有属性
    private static ArrayList<String> nameList = new ArrayList<>();
    //定义一个列表，容纳所有的皇帝实例
    private static ArrayList<Emperor2> emperor2s = new ArrayList<>();
    //当前皇帝序列号
    private static int countNumOfEmperor = 0;
    //产生所有的对象
    static{
        for (int i=0;i<maxNumOfEmperor;i++){
            emperor2s.add(new Emperor2());
        }
    }

    private Emperor2(){}

    private Emperor2(String name){
        nameList.add(name);
    }

    public static Emperor2 getInstance(){
        Random random = new Random();
        countNumOfEmperor = random.nextInt(maxNumOfEmperor);
        return emperor2s.get(countNumOfEmperor);
    }

    public static void say(){
        System.out.println(nameList.get(countNumOfEmperor));
    }
}
