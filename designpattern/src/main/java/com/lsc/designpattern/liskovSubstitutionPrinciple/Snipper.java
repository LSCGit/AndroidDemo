package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:19.
 * E-Mail:2965219926@qq.com
 *
 * AUG狙击手类
 */
public class Snipper {

    public void killEnemy(AUG aug){
        aug.zoomOut();
        aug.shoot();
    }
}
