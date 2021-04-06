package com.danmo.commonapi.model.device;

import java.io.Serializable;

public class Operation implements Serializable {
    private String room_id;
    private String room;
    private  int locker_id;
    private  String locker;
    private  int bin_id;
    private  String bin_name;
    private  int item_id;
    private  String item_name;
    private  String part_name;
    private  String serial_number;
    private  String username;
    private  String user_id;
    private int qty_changed;
    private int qty_current;
    private int qty_total;
    private int type_of_service;
    private String operation_time;

    public Operation(String room_id, String room, int locker_id, String locker, int bin_id, String bin_name, int item_id, String item_name, String part_name, String serial_number, String username, String user_id, int qty_changed, int qty_current, int qty_total, int type_of_service, String operation_time) {
        this.room_id = room_id;
        this.room = room;
        this.locker_id = locker_id;
        this.locker = locker;
        this.bin_id = bin_id;
        this.bin_name = bin_name;
        this.item_id = item_id;
        this.item_name = item_name;
        this.part_name = part_name;
        this.serial_number = serial_number;
        this.username = username;
        this.user_id = user_id;
        this.qty_changed = qty_changed;
        this.qty_current = qty_current;
        this.qty_total = qty_total;
        this.type_of_service = type_of_service;
        this.operation_time = operation_time;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getLocker_id() {
        return locker_id;
    }

    public void setLocker_id(int locker_id) {
        this.locker_id = locker_id;
    }

    public String getLocker() {
        return locker;
    }

    public void setLocker(String locker) {
        this.locker = locker;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getQty_changed() {
        return qty_changed;
    }

    public void setQty_changed(int qty_changed) {
        this.qty_changed = qty_changed;
    }

    public int getQty_current() {
        return qty_current;
    }

    public void setQty_current(int qty_current) {
        this.qty_current = qty_current;
    }

    public int getQty_total() {
        return qty_total;
    }

    public void setQty_total(int qty_total) {
        this.qty_total = qty_total;
    }

    public int getType_of_service() {
        return type_of_service;
    }

    public void setType_of_service(int type_of_service) {
        this.type_of_service = type_of_service;
    }

    public String getOperation_time() {
        return operation_time;
    }

    public void setOperation_time(String operation_time) {
        this.operation_time = operation_time;
    }
}
