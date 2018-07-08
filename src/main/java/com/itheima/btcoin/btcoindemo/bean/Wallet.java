package com.itheima.btcoin.btcoindemo.bean;

import com.itheima.btcoin.btcoindemo.utils.RSAUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ClassName:Wallet
 * Description:
 */
public class Wallet {

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private double amount;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Wallet(String name) {
        File priFile = new File(name + ".pri");
        File pubFile = new File(name + ".pub");

        if (!priFile.exists() || !pubFile.exists() || pubFile.length() == 0 || priFile.length() == 0) {
            RSAUtils.generateKeysJS("RSA", name + ".pri", name + ".pub");
        }

        this.privateKey = RSAUtils.getPrivateKey("RSA", name + ".pri");
        this.publicKey = RSAUtils.getPublicKeyFromFile("RSA", name + ".pub");
    }

    public Transaction sendMoney(String receivePublicKey, String content) {

        PublicKey receivePublic = RSAUtils.getPublicKeyFromString("RSA", receivePublicKey);
        String sendPublicKey = Base64.encode(publicKey.getEncoded());

        String signature = RSAUtils.getSignature("SHA256withRSA", privateKey, content);

        return new Transaction(sendPublicKey, receivePublicKey, content, signature);
    }


    public static void main(String[] args){
        Wallet wallet = new Wallet("a");
    }

}
