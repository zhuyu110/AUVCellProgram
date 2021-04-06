package com.danmo.commonapi.model.device;

import java.io.Serializable;
import java.util.List;

public class Device implements Serializable {
   int locker_id;
   String locker;
   int is_open;
   String room_id;

    public Device(int locker_id, String locker, int is_open, String room_id) {
        this.locker_id = locker_id;
        this.locker = locker;
        this.is_open = is_open;
        this.room_id = room_id;
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

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
