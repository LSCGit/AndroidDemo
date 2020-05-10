package com.lsc.designpattern.lkp;

import java.util.List;

/**
 * Created by lsc on 2020-04-18 02:36.
 * E-Mail:2965219926@qq.com
 */
public class GroupLeader {


    private List<Girl> listGirls;
    public GroupLeader(List<Girl> listGirls){
        this.listGirls = listGirls;
    }

    public void countGirls(){
        System.out.println("女生数量是：" + listGirls.size());
    }
}
