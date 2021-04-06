package com.danmo.commonapi.model.device;

import java.io.Serializable;
import java.util.List;

public class DataBean<T> implements Serializable {
    List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
