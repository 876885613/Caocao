package com.caocao.client.utils;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.caocao.client.ui.bean.CheckBean;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import static com.caocao.client.ui.bean.CheckBean.CheckType.CARD_IMAGE;
import static com.caocao.client.ui.bean.CheckBean.CheckType.ID_CARD;
import static com.caocao.client.ui.bean.CheckBean.CheckType.INTEGER;
import static com.caocao.client.ui.bean.CheckBean.CheckType.PHONE;
import static com.caocao.client.ui.bean.CheckBean.CheckType.SERVE_PASSWORD;
import static com.caocao.client.ui.bean.CheckBean.CheckType.STRING;

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


    public static String checkNotNull(Map<CheckBean, String> map) {
        Iterator<CheckBean> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            CheckBean key = iter.next();
            String value = map.get(key);
            if (key == null || key.o == null) {
                return value;
            }
            if (key.type == STRING) {
                if (StringUtils.isEmpty((CharSequence) key.o)) {
                    return value;
                }
            }
            if (key.type == INTEGER) {
                if ((Integer) key.o == 0) {
                    return value;
                }
            }

            if (key.type == PHONE) {
                if (!RegexUtils.isMobileExact((String) key.o)) {
                    return value;
                }
            }

            if (key.type == ID_CARD) {
                if (!RegexUtils.isIDCard18((String) key.o)) {
                    return value;
                }
            }

            if (key.type == SERVE_PASSWORD) {
                String password = ((String) key.o);
                if (password.length() < 6) {
                    return value;
                }
            }

            if (key.type == CARD_IMAGE) {
                String cardImg = ((String) key.o);
                if (StringUtils.isEmpty(cardImg) || cardImg.split(",").length != 2) {
                    return value;
                }
            }
        }
        return null;
    }
}
