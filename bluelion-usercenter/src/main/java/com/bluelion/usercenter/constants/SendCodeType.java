package com.bluelion.usercenter.constants;

public enum SendCodeType {

    LOST_PASSWORD(1), MODIFY_PASSWORD(2);

    private int index;

    SendCodeType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
