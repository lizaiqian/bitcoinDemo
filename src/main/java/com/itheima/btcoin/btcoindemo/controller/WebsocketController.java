package com.itheima.btcoin.btcoindemo.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.btcoin.btcoindemo.BtcoindemoApplication;
import com.itheima.btcoin.btcoindemo.bean.MessageBean;
import com.itheima.btcoin.btcoindemo.bean.Transaction;
import com.itheima.btcoin.btcoindemo.websocket.MyClient;
import com.itheima.btcoin.btcoindemo.websocket.MyServer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * ClassName:WebsocketController
 * Description:
 * controller of websocket client and server
 */
@RestController
public class WebsocketController {

    HashSet<String> set = new HashSet<>();
    ArrayList<MyClient> list = new ArrayList<>();

    MyServer myServer;

    public WebsocketController() {
        myServer = new MyServer(BtcoindemoApplication.port + 1);
        myServer.start();
    }

    @RequestMapping("/regist")
    public String regist(String port) throws URISyntaxException {
        try {
            set.add("ws://127.0.0.1:" + port);
            return "注册成功";
        } catch (Exception e) {
            return "注册失败";
        }
    }

    @RequestMapping("/conn")
    public String conn() {

        list.clear();
        try {
            for (String uristr : set) {
                URI uri = new URI(uristr);
                MyClient myClient = new MyClient(uri, uristr);

                list.add(myClient);
                myClient.connect();
            }
            return "连接成功";
        } catch (Exception e) {
            return "连接失败";
        }
    }

    @RequestMapping("/broadcast")
    public String broadcast(String msg) {

        Transaction transaction = JSON.parseObject(msg, Transaction.class);
        MessageBean mb = new MessageBean(1, JSON.toJSONString(transaction));

        String message = JSON.toJSONString(mb);

        try {
            myServer.broadcast(message);
            return "广播成功";
        } catch (Exception e) {
            return "广播失败";
        }
    }

    @RequestMapping("/syndata")
    public String syndata() {
        try {
            for (MyClient myClient : list) {
                myClient.send("请求同步数据");
            }
            return "成功";
        } catch (NotYetConnectedException e) {
            return "失败";
        }

    }

}
