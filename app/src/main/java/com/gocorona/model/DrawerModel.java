package com.gocorona.model;

public class DrawerModel {

    public String icon;
    public String name;
    public Integer clickId = -1;

    public Integer getClickId() {
        return clickId;
    }

    public void setClickId(Integer clickId) {
        this.clickId = clickId;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon_id) {
        this.icon = icon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String itemName) {
        this.name = itemName;
    }
}
