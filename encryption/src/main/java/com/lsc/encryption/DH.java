package com.lsc.encryption;

import java.util.Random;

/**
 * Created by lsc on 2020-03-12 11:08.
 * E-Mail:2965219926@qq.com
 */
public class DH {

    private static final int DH_P = 23;
    private static final int DH_G = 5;

    private int mPriKey;

    public DH(){
        Random random = new Random();
        mPriKey = random.nextInt(10);
        System.out.println("DH prikey is：" + mPriKey);
    }

    /**
     * 使用公钥计算共识，计算出自己的公钥。用以交换
     * @return
     */
    public int getPublicKey(){
        return (int) (Math.pow(DH_G,mPriKey) % DH_P);
    }

    /**
     * 接收对方的公钥，与自己的私钥通过密钥公式产生密钥
     *
     * @param publicKey
     * @return
     */
    public int getSecreKey(int publicKey){
        return (int)(Math.pow(publicKey,mPriKey) % DH_P);
    }
}
