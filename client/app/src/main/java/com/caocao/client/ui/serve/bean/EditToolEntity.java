package com.caocao.client.ui.serve.bean;

public class EditToolEntity {
    public Object params;
    public int iconId;
    public String des;


    public EditToolEntity(String des, int iconId) {
        this.des = des;
        this.iconId = iconId;
    }

    public EditToolEntity() {
    }

    public EditToolEntity(String des, Object params, int iconId) {
        this.params = params;
        this.iconId = iconId;
    }
}
