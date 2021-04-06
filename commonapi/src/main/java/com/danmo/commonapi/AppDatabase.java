/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.danmo.commonapi;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonapi.db.entity.OperationRecord;
import com.danmo.commonapi.db.entity.User;
import com.danmo.commonapi.db.greendao.AUVBoardCellDeviceDao;
import com.danmo.commonapi.db.greendao.DaoMaster;
import com.danmo.commonapi.db.greendao.OperationRecordDao;
import com.danmo.commonapi.db.greendao.UserDao;
import com.mabeijianxi.smallvideorecord2.StringUtils;

import java.util.ArrayList;
import java.util.List;




/*
* 数据库数据工具
* */

public  class AppDatabase {
    public static AppDatabase sInstance;
    public  static UserDao userDao;
    public DaoMaster.DevOpenHelper helper;
    public static AUVBoardCellDeviceDao deviceDao;
    public static OperationRecordDao recordDao;
    public static final String DATABASE_NAME = "auv-db";

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance =new AppDatabase(context);
                }
            }
        }
        return sInstance;
    }

    public AppDatabase(Context context){
        helper=new DaoMaster.DevOpenHelper( context,DATABASE_NAME,null);
        userDao=new DaoMaster(helper.getWritableDb()).newSession().getUserDao();
        deviceDao=new DaoMaster(helper.getWritableDb()).newSession().getAUVBoardCellDeviceDao();
        recordDao=new DaoMaster(helper.getWritableDb()).newSession().getOperationRecordDao();
    }
    /*
    * 增加用户
    * */
    public void insertOrReplace(User user){
        userDao.insertOrReplace(user);
    }

    /**
     * 根据用户id查询设备
     *
     * @param barcode
     * @return
     */
    public  List<User> queryUser(String barcode){
        if(StringUtils.isBlank(barcode)){
            return  new ArrayList<>();
        }
       return userDao.queryBuilder().where(UserDao.Properties._id.eq(barcode)).list();
    }

    /**
     * 获取所有操作日志
     * @return
     */
    public List<OperationRecord> getAllOperation(){
        return  recordDao.loadAll();
    }
    public void insertOperation(OperationRecord operationRecord){
        recordDao.insert(operationRecord);
    }
    public void deleAllOperation(){
        recordDao.deleteAll();
    }

    /*
    * 设备修改
    *
     *  */
    public List<AUVBoardCellDevice> getAllDevice(){
        List<AUVBoardCellDevice> result=new ArrayList<>();
        result = deviceDao.queryBuilder().orderAsc(AUVBoardCellDeviceDao
                .Properties.CellNo).list();
       return  result;
    }

    /**
     * 查询用户关联的设备
      * @param barcode
     * @return
     */
    public  List<AUVBoardCellDevice> queryDevice(String barcode){
        if(StringUtils.isBlank(barcode)){
            return  new ArrayList<>();
        }
        return  deviceDao.queryBuilder().where(AUVBoardCellDeviceDao.Properties.PerNo.eq(barcode)).list();
    }

    /**
     * 插入设备
     * @param device
     */
    public void inser(AUVBoardCellDevice device) {
        deviceDao.insertOrReplace(device);
    }

    /**
     * 删除所有设备
     */
    public void deleteAllDevice(){
        deviceDao.deleteAll();
    }



}
