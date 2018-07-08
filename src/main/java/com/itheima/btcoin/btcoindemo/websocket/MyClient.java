package com.itheima.btcoin.btcoindemo.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.btcoin.btcoindemo.bean.Block;
import com.itheima.btcoin.btcoindemo.bean.MessageBean;
import com.itheima.btcoin.btcoindemo.bean.NoteBook;
import com.itheima.btcoin.btcoindemo.bean.Transaction;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;

/**
 * ClassName:MyClient
 * Description:
 */
public class MyClient extends WebSocketClient {
    private String name;

    private NoteBook noteBook = NoteBook.getInstance();

    public MyClient(URI serverUri, String name) {
        super(serverUri);
        this.name = name;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println(name + "客户端打开了连接");
    }

    @Override
    public void onMessage(String message) {
        System.out.println(name + "客户端接收了消息" + message);
        //获取MessageBean对象
        MessageBean mb = JSON.parseObject(message, MessageBean.class);

        //如果广播的是Transaction
        if(mb.getType() == 1) {
            Transaction transaction = JSON.parseObject(mb.getMessage(), Transaction.class);
            boolean result = transaction.verify();

            if(result) {
                String transactionStr = JSON.toJSONString(transaction);
                noteBook.addNote(transactionStr);
            }
        }

        //如果广播的是list
        if(mb.getType() == 2) {
            List<Block> newList = JSONObject.parseArray(mb.getMessage(), Block.class);
            //存储上一个区块list
            List<Block> preList = noteBook.showList();

            if(newList.size() > noteBook.showList().size()) {
                //如果收到的list长度比自己的长，则更新list
                noteBook.updateList(newList);
                //更新后校验，
                if(noteBook.checkList().equals("校验成功")) {
                    System.out.println("同步成功");
                } else {
                    noteBook.updateList(preList);
                    System.out.println("同步失败");

                }
            } else {
                System.out.println("同步失败");
            }
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println(name + "客户端关闭了连接");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        System.out.println(name + "客户端出错了");
    }
}
