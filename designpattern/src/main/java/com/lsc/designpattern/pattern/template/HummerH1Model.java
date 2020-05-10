package com.lsc.designpattern.pattern.template;

/**
 * Created by lsc on 2020-04-18 14:50.
 * E-Mail:2965219926@qq.com
 */
public class HummerH1Model extends HummerModel {

    @Override
    void alarm() {

    }

    @Override
    void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    void engineBoom() {

    }

    @Override
    void run() {

        this.start();
        this.engineBoom();
        this.alarm();
        this.stop();
    }
}
