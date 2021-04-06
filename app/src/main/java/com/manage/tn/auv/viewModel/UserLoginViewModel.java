package com.manage.tn.auv.viewModel;


import com.danmo.commonapi.DataRepository;
import com.manage.tn.auv.base.BaseViewModel;


/*
* 绑定View和Data
* */
public class UserLoginViewModel extends BaseViewModel {

    public UserLoginViewModel(DataRepository dataRepository){
        super(dataRepository);
    }

    /*
     *
     * 登录
     * */
    public void  login(String userName,String password ){
        mDataSource.commonApi.userCenterImpl.login(userName,password);
    }

    public  void getUserList(){
        mDataSource.commonApi.userCenterImpl.getUserList();
    }



}
