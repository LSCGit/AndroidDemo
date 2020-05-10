package com.lsc.designpattern.isp;

/**
 * Created by lsc on 2020-04-18 02:18.
 * E-Mail:2965219926@qq.com
 */
public abstract class AbstractSearcher {

    protected IPettyGirl pettyGirl;
    public AbstractSearcher(IPettyGirl pettyGirl){
        this.pettyGirl = pettyGirl;
    }

    public abstract void show();
}
