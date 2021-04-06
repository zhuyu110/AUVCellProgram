package com.danmo.commonapi.model.device;

import java.io.Serializable;

public class Bin implements Serializable {
    int bin_id;
    String bin_name;
    int locker_id;
    int qty_total;
    int status;

    public Bin(int bin_id, String bin_name, int locker_id, int qty_total, int status) {
        this.bin_id = bin_id;
        this.bin_name = bin_name;
        this.locker_id = locker_id;
        this.qty_total = qty_total;
        this.status = status;
    }

    public int getBin_id() {
        return bin_id;
    }

    public void setBin_id(int bin_id) {
        this.bin_id = bin_id;
    }

    public String getBin_name() {
        return bin_name;
    }

    public void setBin_name(String bin_name) {
        this.bin_name = bin_name;
    }

    public int getLocker_id() {
        return locker_id;
    }

    public void setLocker_id(int locker_id) {
        this.locker_id = locker_id;
    }

    public int getQty_total() {
        return qty_total;
    }

    public void setQty_total(int qty_total) {
        this.qty_total = qty_total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
