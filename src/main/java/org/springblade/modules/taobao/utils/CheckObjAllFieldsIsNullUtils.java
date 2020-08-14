package org.springblade.modules.taobao.utils;

import java.lang.reflect.Field;

/**
 * 类字段判空工具
 * 给与objext 如果有任何字段为空返回true
 * 全部为空返回false
 * @author SeventySeven
 * @since 2020-08-14
 * **/
public class CheckObjAllFieldsIsNullUtils {
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) == null) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
