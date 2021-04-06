package com.danmo.commonapi.api.usercenter;


import com.blankj.utilcode.util.ReflectUtils;
import com.danmo.commonapi.RetrofitParameterBuilder;
import com.danmo.commonapi.base.BaseImpl;
import com.danmo.commonapi.base.UUIDGenerator;


import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;

public class UserCenterImpl extends BaseImpl<UserCenterService> {

    public UserCenterImpl( String baseUrl, int currentParse) {
        super( baseUrl, currentParse);
    }

    /*
     * 登录
     * */
    public String login(String userName, String password){
        String uuid=UUIDGenerator.getUUID();
        sub(mService.login(userName,password),uuid,"login");
        return  uuid;
    }
    /**
     * 获取用户信息
     */
    public String getUserList(){
        String uuid=UUIDGenerator.getUUID();
        sub(mService.getUserList(),uuid,"getUserList");
        return  uuid;
    }

}
