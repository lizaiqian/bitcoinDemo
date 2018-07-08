package com.itheima.btcoin.btcoindemo.bean;

/**
 * ClassName:MessageBean
 * Description:
 * message type class, which show the type of message , type 1 means the message is a transaction;
 * type 2 means a list of block.
 */
public class MessageBean {

    private int type;
    private String message;

    public MessageBean() {
    }

    public MessageBean(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
