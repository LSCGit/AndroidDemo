package com.lsc.network.bean;

/**
 * Created by lsc on 2020-03-02 11:33.
 * E-Mail:2965219926@qq.com
 */
public class ChatMessage {


    /**
     * contactName : 路人甲
     * time : 2018-06-12T13:31
     * content : 我说啥了我？Get out!
     */

    private String contactName;
    private String time;
    private String content;

    public ChatMessage(String contactName, String time, String content) {
        this.contactName = contactName;
        this.time = time;
        this.content = content;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
