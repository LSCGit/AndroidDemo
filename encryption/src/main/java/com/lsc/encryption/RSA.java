package com.lsc.encryption;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by lsc on 2020-03-12 09:42.
 * E-Mail:2965219926@qq.com
 */
public class RSA {

    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    //加密函数，传入明文，公钥；返回密文
    public static String encrypt(int data,String pubKey){

        //dh参数为int类型，这里设计成对int加密
        String message = String.valueOf(data);

        //使用android提供的Base64，移到java中会报错，注意
        byte[] decoded = Base64.decode(pubKey,Base64.NO_WRAP);
        byte[] result = new byte[0];

        try {
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            result = cipher.doFinal(message.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        //将结果转化为Base64
        return Base64.encodeToString(result,Base64.NO_WRAP);
    }

    //传入密文及私钥，返回明文
    public static String decrypt(String encrypted,String prikey){

        byte[] decoded = Base64.decode(prikey,Base64.NO_WRAP);
        byte[] content = Base64.decode(encrypted,Base64.NO_WRAP);

        byte[] result = new byte[]{0};

        //初始工作完成，开始进行解密
        try {
            RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            result = cipher.doFinal(content);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
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

        return new String(result);
    }
}
