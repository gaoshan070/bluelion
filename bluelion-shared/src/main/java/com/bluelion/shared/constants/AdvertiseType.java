package com.bluelion.shared.constants;

public enum AdvertiseType {

    START(1, "Boot Ads"), INNER(2, "Interal Ads");

    int index;
    String value;

    AdvertiseType(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
