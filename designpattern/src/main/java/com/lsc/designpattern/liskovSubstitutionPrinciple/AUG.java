package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:18.
 * E-Mail:2965219926@qq.com
 */
public class AUG extends Rifle {

    public void zoomOut(){
        System.out.println("通过望远镜观察敌人");
    }

    @Override
    public void shoot() {
        System.out.println("AUG射击。。。");
    }
}
