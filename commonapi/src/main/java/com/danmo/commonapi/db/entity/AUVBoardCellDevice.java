package com.danmo.commonapi.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/*
* 柜体设备
* */
@Entity
public class AUVBoardCellDevice {
    @Index(unique = true)//设置唯一性
    private int cellNo;//lockid
    private boolean cellLockOpen;//lokerisOpen true :open   false:close
    private boolean cellLightOpen;//lightOpen true :open   false:close
    private boolean cellGoodsExist;
    private String deviceNo;
    private String partNo;//deviceType
    private String name;
    private String endTime;
    private String perNo;
    private String returnTime;
    private String borrowTime;
    private boolean isGoodOrFail;//true good  false fail
    @Transient
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    @Generated(hash = 946963949)
    public AUVBoardCellDevice(int cellNo, boolean cellLockOpen,
            boolean cellLightOpen, boolean cellGoodsExist, String deviceNo,
            String partNo, String name, String endTime, String perNo,
            String returnTime, String borrowTime, boolean isGoodOrFail) {
        this.cellNo = cellNo;
        this.cellLockOpen = cellLockOpen;
        this.cellLightOpen = cellLightOpen;
        this.cellGoodsExist = cellGoodsExist;
        this.deviceNo = deviceNo;
        this.partNo = partNo;
        this.name = name;
        this.endTime = endTime;
        this.perNo = perNo;
        this.returnTime = returnTime;
        this.borrowTime = borrowTime;
        this.isGoodOrFail = isGoodOrFail;
    }

    @Generated(hash = 1496416362)
    public AUVBoardCellDevice() {
    }
    public int getCellNo() {
        return this.cellNo;
    }
    public void setCellNo(int cellNo) {
        this.cellNo = cellNo;
    }
    public boolean getCellLockOpen() {
        return this.cellLockOpen;
    }
    public void setCellLockOpen(boolean cellLockOpen) {
        this.cellLockOpen = cellLockOpen;
    }
    public boolean getCellLightOpen() {
        return this.cellLightOpen;
    }
    public void setCellLightOpen(boolean cellLightOpen) {
        this.cellLightOpen = cellLightOpen;
    }
    public boolean getCellGoodsExist() {
        return this.cellGoodsExist;
    }
    public void setCellGoodsExist(boolean cellGoodsExist) {
        this.cellGoodsExist = cellGoodsExist;
    }
    public String getDeviceNo() {
        return this.deviceNo;
    }
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
    public String getPartNo() {
        return this.partNo;
    }
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getPerNo() {
        return this.perNo;
    }
    public void setPerNo(String perNo) {
        this.perNo = perNo;
    }
    public String getReturnTime() {
        return this.returnTime;
    }
    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public boolean getIsGoodOrFail() {
        return this.isGoodOrFail;
    }

    public void setIsGoodOrFail(boolean isGoodOrFail) {
        this.isGoodOrFail = isGoodOrFail;
    }

    public String getBorrowTime() {
        return this.borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

   

   

}
