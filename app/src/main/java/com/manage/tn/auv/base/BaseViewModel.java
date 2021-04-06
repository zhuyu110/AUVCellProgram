package com.manage.tn.auv.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;

import com.blankj.utilcode.util.ToastUtils;
import com.danmo.commonapi.DataRepository;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.model.BaseResponse;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.jeremyliao.liveeventbus.core.Observable;

public class BaseViewModel extends ViewModel {
    public DataRepository mDataSource;
    public BaseViewModel(DataRepository dataRepository){
        mDataSource=dataRepository;
    }
    /**
     * 获取Livedata，注册监听数据返回
     * @param key 存储MediatorLiveData的key
     * @param modelClass 返回的数据类型
     * @param fromNetWork 是否使用网络获取数据，true 网络访问接口  false 本地数据库接口访问
     * @param <T>
     * @return
     */
    public <T>Observable<T> getMLDByCode(LifecycleOwner owner, String key, Class<T> modelClass, boolean fromNetWork){
        Observable<T> observable= null;
        if(fromNetWork){
            observable= LiveEventBus.get(key,modelClass);
            Observable<T> finalObservable = observable;
            LiveEventBus.get(key,BaseResponse.class).observe(owner,(response->{
                if(response==null){
                    return;
                }
                if(response.getStatus_code()==0){
                    if(response.getToken()!=null){
                        finalObservable.post((T)response.getToken());
                    }
                }else if(response.getStatus_code()== Constant.SUCCESS ){
                    if(response.getData()!=null){
                        finalObservable.post((T)response.getData());
                    }
                }else{
                    finalObservable.post(null);
                    ToastUtils.showShort(response.getMsg());
                }
            }));
        }else{
            observable= LiveEventBus.get(key,modelClass);
        }
        return  observable;

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
