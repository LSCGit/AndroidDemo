package com.lsc.designpattern.pattern.builder;

import java.util.ArrayList;

/**
 * Created by lsc on 2020-04-18 15:12.
 * E-Mail:2965219926@qq.com
 *
 */
public abstract class CarModel {
    //方法执行的顺序
    private ArrayList<String> sequence = new ArrayList<>();
    abstract void start();
    abstract void stop();
    abstract void alarm();
    abstract void engineBoom();
    void run(){
        for (int i=0;i<sequence.size();i++){
            String actionName = this.sequence.get(i);
            if (actionName.equalsIgnoreCase("Start")){
                this.start();
            }
        }
    }

    void setSequence(ArrayList sequence){
        this.sequence = sequence;
    }
}
