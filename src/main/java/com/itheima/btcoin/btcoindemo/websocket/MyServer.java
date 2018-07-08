package com.itheima.btcoin.btcoindemo.websocket;

import com.alibaba.fastjson.JSON;
import com.itheima.btcoin.btcoindemo.bean.Block;
import com.itheima.btcoin.btcoindemo.bean.MessageBean;
import com.itheima.btcoin.btcoindemo.bean.NoteBook;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * ClassName:MyServer
 * Description:
 */
public class MyServer extends WebSocketServer {

    private int port;

    private NoteBook noteBook = NoteBook.getInstance();

//    private static MyServer myServer;

//    public static MyServer getInstance() {
//
//        if(myServer == null) {
//            synchronized (MyServer.class) {
//                if(myServer == null) {
//                    myServer = new MyServer()
//                }
//            }
//        }
//
//    }

    public MyServer(int port) {
        super(new InetSocketAddress(port));
        this.port = port;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(port + ":打开了连接");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(port + ":关闭了连接");

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        System.out.println(message);
        List<Block> list = noteBook.showList();
        if(message.equals("请求同步数据")) {
            MessageBean mb = new MessageBean(2, JSON.toJSONString(list));
            String listStr = JSON.toJSONString(mb);

            this.broadcast(listStr);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        System.out.println(port + ":发生了异常" + ex.getMessage());

    }

    @Override
    public void onStart() {
        System.out.println(port + ":开始了");

    }

    public void start() {
        new Thread(this).start();
    }

}
