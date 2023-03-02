package com.taoli.niceplace.utils;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 时间工具类
 *
 * @author taoli
 */
public class DateTimeUtils {


    /**
     * 作用:返回时间规范及标示实例代码
     * @return DateTimeFormatter
     * 这种方式只适合在list数据处理时才好用,弊端就是要VO类的时间创建其String类型属性
     *
     * 其它更好的方式:
     * 1、因为返回前端都是字符串形式,所以直接加注解使其变成字符串格式
     * 加注解 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
     *
     */
    public static DateTimeFormatter dateTimeFormatter2Str() {
        //时间转码
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //使用实例
//        DateTimeFormatter fmt = DateTimeUtils.dateTimeFormatter2Str();
//        list.forEach(res -> {
//            res.setExpireTimeStr(res.getExpireTime().format(fmt));
//        });
        return fmt;
    }



}
