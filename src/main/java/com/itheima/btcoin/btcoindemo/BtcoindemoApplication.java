package com.itheima.btcoin.btcoindemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//
//@SpringBootApplication
//public class BtcoindemoApplication {
//    public static int port;
//
//    public static void main(String[] args) {
//
//        Scanner scanner = new Scanner(System.in);
//        port = Integer.parseInt(scanner.nextLine());
//        new SpringApplicationBuilder(BtcoindemoApplication.class).properties("server.port=" + port).run(args);
//    }
//}

@SpringBootApplication
public class BtcoindemoApplication {
    public static int port = 8080;

    public static void main(String[] args) {

        new SpringApplicationBuilder(BtcoindemoApplication.class).properties("server.port=" + port).run(args);
    }
}
