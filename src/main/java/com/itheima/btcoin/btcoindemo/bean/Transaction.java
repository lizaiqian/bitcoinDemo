package com.itheima.btcoin.btcoindemo.bean;

import com.itheima.btcoin.btcoindemo.utils.RSAUtils;

import java.security.PublicKey;

/**
 * ClassName:Transaction
 * Description:
 */
public class Transaction {

    private String sendPublicKey;
    private String receivePublicKey;
    private String content;
    private String signature;

    @Override
    public String toString() {
        return "Transaction{" +
                "sendPublicKey='" + sendPublicKey + '\'' +
                ", receivePublicKey='" + receivePublicKey + '\'' +
                ", content='" + content + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public Transaction() {
    }

    public Transaction(String sendPublicKey, String receivePublicKey, String amount, String signature) {
        this.sendPublicKey = sendPublicKey;
        this.receivePublicKey = receivePublicKey;
        this.content = amount;
        this.signature = signature;
    }

    public String getSendPublicKey() {
        return sendPublicKey;
    }

    public void setSendPublicKey(String sendPublicKey) {
        this.sendPublicKey = sendPublicKey;
    }

    public String getReceivePublicKey() {
        return receivePublicKey;
    }

    public void setReceivePublicKey(String receivePublicKey) {
        this.receivePublicKey = receivePublicKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public boolean verify () {

        PublicKey publicKey = RSAUtils.getPublicKeyFromString("RSA", sendPublicKey);
        return RSAUtils.verifyDataJS("SHA256withRSA", publicKey, content, signature);

    }


}
