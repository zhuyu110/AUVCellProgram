package com.danmo.commonapi.api.device;


import com.danmo.commonapi.model.BaseResponse;
import com.danmo.commonapi.model.device.Device;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.List;

public interface DeviceService {


    @POST("api/v1/lockers/create-or-update")
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    Observable<BaseResponse<List>> uploadLockers(@Body RequestBody requestBody);

    @POST("api/v1/bins/create-or-update")
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    Observable<BaseResponse<List>> uploadBins(@Body RequestBody requestBody);

    @POST("api/v1/items/create-or-update")
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    Observable<BaseResponse<List>> uploadItems(@Body RequestBody requestBody);

    @POST("api/v1/transaction/logs")
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    Observable<BaseResponse<List>> uploadOperators(@Body RequestBody requestBody);

}