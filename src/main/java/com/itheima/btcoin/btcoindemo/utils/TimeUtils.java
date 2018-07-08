package com.itheima.btcoin.btcoindemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName:TimeUtils
 * Description:
 */
public class TimeUtils {

    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
