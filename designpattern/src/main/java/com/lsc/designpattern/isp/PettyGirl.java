package com.lsc.designpattern.isp;

/**
 * Created by lsc on 2020-04-18 02:15.
 * E-Mail:2965219926@qq.com
 */
public class PettyGirl implements IPettyGirl {

    private String name;

    public PettyGirl(String name){
        this.name = name;
    }

    @Override
    public void googLooking() {
        System.out.println(this.name +"-- 脸蛋很漂亮");

    }

    @Override
    public void greatTemperament() {
        System.out.println(this.name + "--- 气质很好");
    }

    @Override
    public void niceFigure() {
        System.out.println(this.name + "--身材非常棒！");
    }
}
