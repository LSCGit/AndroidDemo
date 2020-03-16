package com.lsc.server;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

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

    /**
     * 加密函数
     *
     * @param data 明文
     * @param pubKey 公钥
     * @return 返回密文
     */
    public static String encrypt(int data,String pubKey){

        //dh参数为int类型，这里设计成对int加密
        String message = String.valueOf(data);

        //使用android提供的Base64，移到java中会报错，注意
        byte[] decoded = Base64.getMimeDecoder().decode(pubKey);
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
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 解密函数
     *
     * @param encrypted 密文
     * @param prikey 私钥
     * @return 返回明文
     */
    //传入密文及私钥，返回明文
    public static String decrypt(String encrypted,String prikey){

        byte[] decoded = Base64.getMimeDecoder().decode(prikey);
        byte[] content = Base64.getMimeDecoder().decode(encrypted);

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
