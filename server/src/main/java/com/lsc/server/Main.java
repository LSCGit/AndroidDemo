package com.lsc.server;


import com.lsc.server.http.HttpCallback;
import com.lsc.server.http.HttpServer;

import java.util.Map;

/**
 * Created by lsc on 2020-03-10 10:44.
 * E-Mail:2965219926@qq.com
 */
public class Main {

    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYRJR9kDonbh1Llaae85VyhzOG\n" +
            "DkOoQ0D+LcAkUhDKiguSe7z3Frdt4IpoQCWOe0GjqxpJ4ssgD25CyytLCcJ0rBlI\n" +
            "WP4muHCRQQYh2gdC3F+fSIpUNz5D93SlrFZ/HEn+nQBMb/5xJQ3uJuu73JitQDdf\n" +
            "cyouA5J1CVutRzXjewIDAQAB";
    private static final String PRI_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANhElH2QOiduHUuV\n" +
            "pp7zlXKHM4YOQ6hDQP4twCRSEMqKC5J7vPcWt23gimhAJY57QaOrGkniyyAPbkLL\n" +
            "K0sJwnSsGUhY/ia4cJFBBiHaB0LcX59IilQ3PkP3dKWsVn8cSf6dAExv/nElDe4m\n" +
            "67vcmK1AN19zKi4DknUJW61HNeN7AgMBAAECgYEAjuni5obkpyHRIh2mmeuFmsVV\n" +
            "ewT6GLs4u78n9dh1WCKZOxeh6/aLCIUVcYbSSrGQ0sNDAAhrCqinyGPSHCg64v61\n" +
            "/x2SInxG2PXvbjiXxZoxBEGP+0atjD7XQpsVjlsiaKRC54hK69C+ouGbD9cis25L\n" +
            "axUgcz/z56UcmJfUGqECQQD/zWquNc5H5rQA6GNJegPcIuHSmmemSgP/dPXG9wgC\n" +
            "mUHZRjil2tbXLeZcVC3YrNGgC8ooUdALhgLXGZPTiJ6FAkEA2G9YesziiI22BPqP\n" +
            "cNsweLbxy2k3RHV/YZkz7+rdgEGP0vToAStPbHr5xMmgHTS9MG7F4Perl301ovJo\n" +
            "NaEZ/wJBAIydpPkjBsQro+Oj24B2nEyUuEKHMlcM9Ommp7y56yNSkAoaOeWiSNt8\n" +
            "lddHRvSG+6zZDkXfw1eyUTlhDAwKW+kCQFfs5AT/NwhCia98Gsm+QfLIX3JDTDCD\n" +
            "izwbAP52a4WvHL6Wv6m57ooja1SGlIVI55LFbZ32/76LHz8TO0KOMAECQQDzCKjH\n" +
            "7T3r34KwxwLrizGdzculSuZvSj+oq173Vh041jx+rxoihJoiECwyrMVxb8JXmXBL\n" +
            "s9t52NhIhYqecOqS";


    public static void main(String[] args) {

        System.out.println("java Server Running");

        int content = 12321;
        String encryted = RSA.encrypt(content,PUB_KEY);
        System.out.println(encryted);
        System.out.println(RSA.decrypt(encryted,PRI_KEY));

        AES aes = new AES();
        String content1 = "this is aes";
        byte[] encryted1 = aes.encrypt(content1);
        System.out.println(new String(encryted1));
        byte[] decrypted1 = aes.decrypt(encryted1);
        System.out.println(new String(decrypted1));

        HttpServer server = new HttpServer(new HttpCallback() {
            private DH dh = new DH();
            private AES aes = new AES();
            @Override
            public byte[] onResponse(String request) {
                System.out.println("server 接收到的请求: " + request);
                if (isHandshake(request)){
                    //握手请求相应操作
                    Map<String,String> header = HttpServer.getHeader(request);
                    String handshake = header.get("handshake");//此处应判空
                    int dhPubKey = Integer.valueOf(RSA.decrypt(handshake,PRI_KEY));
                    //服务端拿到客户端的dh pubkey，生成dh的Secret。
                    aes.setKey(SHA256.sha256(dh.getSecreKey(dhPubKey)));

                    return SHA256.sha256(dh.getPublicKey());
                }else {
                    //应用请求的操作
                    byte[] result = aes.encrypt("imooc AES");
                    return result;
                }

            }
        });
        server.startHttpServer();

    }

    /**
     * 通过Header当中的 handshake字段判断是否为握手请求
     *
     * @param request
     * @return
     */
    private static boolean isHandshake(String request){
        return (request != null && request.contains("handshake"));
    }
}
