package com.danmo.commonapi.api.device;


import com.blankj.utilcode.util.StringUtils;
import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.base.UUIDGenerator;
import com.danmo.commonapi.base.Watt;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonapi.db.entity.OperationRecord;
import com.danmo.commonapi.model.device.Bin;
import com.danmo.commonapi.model.device.DataBean;
import com.danmo.commonapi.model.device.Device;
import com.danmo.commonapi.model.device.Item;
import com.danmo.commonapi.model.device.Operation;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;


public class DeviceImpl extends BaseImpl<DeviceService> {

    public DeviceImpl(String baseUrl, int currentParse) {
        super( baseUrl, currentParse);
    }

    /**
     * 上传锁信息
     * @param device
     * @return
     */
    public String uploadLockers(List<AUVBoardCellDevice> device){
        String uuid=UUIDGenerator.getUUID();
        List<Device> date=new ArrayList<>();
        DataBean<Device> dataBean=new DataBean<Device>();
        if(device!=null){
            for(AUVBoardCellDevice a:device){
                date.add(new Device(a.getCellNo(),a.getName(),(a.getCellLockOpen())?1:0,a.getCellNo()+""));
            }
        }
        dataBean.setData(date);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(dataBean));
        sub(mService.uploadLockers(body),uuid,"uploadLockers");
        return  uuid;
    }

    public String uploadBins(List<AUVBoardCellDevice> device){
        String uuid=UUIDGenerator.getUUID();
        List<Bin> date=new ArrayList<>();
        DataBean<Bin> dataBean=new DataBean<Bin>();
        if(device!=null){
            for(AUVBoardCellDevice a:device){
                date.add(new Bin(a.getCellNo(),a.getName(),a.getCellNo(),(StringUtils.isEmpty(a.getDeviceNo()))?0:1,(a.getCellGoodsExist())?1:0));
            }
        }
        dataBean.setData(date);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(dataBean));
        sub(mService.uploadBins(body),uuid,"uploadBins");
        return  uuid;
    }

    public String uploadItems(List<AUVBoardCellDevice> device){
        String uuid=UUIDGenerator.getUUID();
        List<Item> date=new ArrayList<>();
        DataBean<Item> dataBean=new DataBean<Item>();
        if(device!=null){
            for(AUVBoardCellDevice a:device){
                date.add(new Item(a.getCellNo(),a.getName(),a.getPartNo(),a.getDeviceNo(),a.getEndTime(),a.getCellNo(),1));
            }
        }
        dataBean.setData(date);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(dataBean));
        sub(mService.uploadItems(body),uuid,"uploadItems");
        return  uuid;
    }
    public String uploadOperators(List<OperationRecord> records){
        String uuid=UUIDGenerator.getUUID();
        List<Operation> date=new ArrayList<>();
        DataBean<Operation> dataBean=new DataBean<Operation>();
        if(records!=null){
            for(OperationRecord a:records){
                date.add(new Operation(a.getLocker_id()+"","Room 1",a.getLocker_id(),a.getLocker(),a.getBin_id(),a.getBin_name(),a.getItem_id(),a.getItem_name(),a.getPart_name(),a.getSerial_number(),a.getUsername(),a.getUser_id(),a.getQty_changed(),a.getQty_current(),a.getQty_total(),a.getType_of_service(),a.getOperation_time()));
            }
        }
        dataBean.setData(date);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(dataBean));
        sub(mService.uploadOperators(body),uuid,"uploadOperators");
        return  uuid;
    }


}
