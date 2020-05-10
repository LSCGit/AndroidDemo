package com.lsc.designpattern.lkp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsc on 2020-04-18 02:39.
 * E-Mail:2965219926@qq.com
 */
public class Client {

    public static void main(String[] args) {
        List<Girl> listGirls = new ArrayList<>();
        for (int i=0;i<20;i++){
            listGirls.add(new Girl());
        }
        Teacher teacher = new Teacher();

        teacher.commond(new GroupLeader(listGirls));
    }
}
