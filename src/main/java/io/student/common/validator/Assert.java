package io.student.common.validator;

import io.student.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * @author duanxin
 * @email sxz147@163.com
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
