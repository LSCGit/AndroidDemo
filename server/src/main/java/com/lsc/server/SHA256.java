package com.lsc.server;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lsc on 2020-03-12 11:26.
 * E-Mail:2965219926@qq.com
 */
public class SHA256 {

    public static byte[] sha256(int data){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            //将int转换为byte[]
            //一个int对应4个byte；
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.putInt(data);

            messageDigest.update(byteBuffer.array());
            return messageDigest.digest();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new byte[]{-1};
    }
}
