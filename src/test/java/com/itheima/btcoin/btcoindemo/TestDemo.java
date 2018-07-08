package com.itheima.btcoin.btcoindemo;

import com.itheima.btcoin.btcoindemo.bean.Transaction;
import com.itheima.btcoin.btcoindemo.bean.Wallet;
import com.itheima.btcoin.btcoindemo.websocket.MyClient;
import com.itheima.btcoin.btcoindemo.websocket.MyServer;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * ClassName:TestDemo
 * Description:
 */
public class TestDemo {

    @Test
    public void test() throws Exception {
//        NoteBook noteBook = new NoteBook();
//        Block block = new Block();
//        block.setId(1);
//
//        int n = noteBook.mine("1" + "转账");
//        noteBook.addNote(block);

    }


    @Test
    public void testTransaction() {


    }

    public static void main(String[] args) {
        Wallet wallet = new Wallet("lizaiqian");
        Wallet wallet2 = new Wallet("lizaiqian");
        String receivePublicKey = Base64.encode(wallet2.getPublicKey().getEncoded());

        Transaction transaction = wallet.sendMoney(receivePublicKey, "200");

        System.out.println(transaction);
    }

    @Test
    public void testwebsocket() throws URISyntaxException {
        MyServer myServer = new MyServer(7000);
        myServer.start();


        URI uri = new URI("ws://localhost:7000");
        MyClient myClient1 = new MyClient(uri, "1111");
        MyClient myClient2 = new MyClient(uri, "2222");

        myClient1.connect();
        myClient2.connect();

    }

}
