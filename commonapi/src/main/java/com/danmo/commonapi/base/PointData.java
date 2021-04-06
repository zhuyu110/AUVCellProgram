package com.danmo.commonapi.base;


import java.io.Serializable;

public class PointData implements Serializable {
    public String k;
    public String v;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
