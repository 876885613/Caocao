package com.caocao.client.utils;

import com.blankj.utilcode.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class CheckNotNullUtils {

    public static String checkNotNull(Object obj, Map<String, String> map) throws IllegalAccessException {
        Class aClass = obj.getClass();
        Field[] fs = aClass.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            if ((f != null) && (!"".equals(f))) {
                Object o = f.get(obj);
                String fieldName = f.getName();
                if (map.containsKey(fieldName)) {
                    if (o == null) {
                        return map.get(fieldName);
                    }
                    if (o instanceof String) {
                        if (o == null || StringUtils.isEmpty((String) o)) {
                            return map.get(fieldName);
                        }
                    }
                    if (o instanceof Integer) {
                        if ((Integer) o == 0) {
                            return map.get(fieldName);
                        }
                    }
                }
            }
        }
        return null;
    }

}
