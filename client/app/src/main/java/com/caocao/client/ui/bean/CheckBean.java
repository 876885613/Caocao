package com.caocao.client.ui.bean;

public class CheckBean {
    public CheckType type;

    public Object o;

    public CheckBean(CheckType string, Object obj) {
        this.type = string;
        this.o = obj;
    }

    public CheckBean(Object obj) {
        this(CheckType.STRING, obj);
    }

    public enum CheckType {
        STRING,
        INTEGER,
        BOOLEAN,
        OBJECT,
        ID_CARD,
        PHONE,
        SERVE_PASSWORD,
        CARD_IMAGE,
    }
}
