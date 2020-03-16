package com.lsc.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private SecretKey mKey;

    public AES(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

            //创建随机密码，并设置种子
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(System.currentTimeMillis());

            //初始化密钥对象
            keyGenerator.init(128,secureRandom);
            //生成AES密钥并且保持
            mKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public AES(byte[] dhKey){
        mKey = new SecretKeySpec(dhKey,"AES");
    }

    public byte[] encrypt(String content){
        if (mKey == null){
            return new byte[]{-1};
        }
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,mKey);
            return cipher.doFinal(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return new byte[]{-1};
    }

    public byte[] decrypt(byte[] content){
        if (mKey == null){
            return new byte[]{-1};
        }

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,mKey);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return new byte[]{-1};
    }
}
