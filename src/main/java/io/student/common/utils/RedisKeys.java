package io.student.common.utils;

/**
 * Redis所有Keys
 *
 * @author duanxin
 * @email sxz147@163.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
