package com.danmo.commonapi.model.device;

import java.io.Serializable;

public class Item implements Serializable {
        int item_id;
        String item_name;
        String part_name;
        String serial_number;
        String expiry_date;
        int bin_id;
        int status;

    public Item(int item_id, String item_name, String part_name, String serial_number, String expiry_date, int bin_id, int status) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.part_name = part_name;
        this.serial_number = serial_number;
        this.expiry_date = expiry_date;
        this.bin_id = bin_id;
        this.status = status;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public int getBin_id() {
        return bin_id;
    }

    public void setBin_id(int bin_id) {
        this.bin_id = bin_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
