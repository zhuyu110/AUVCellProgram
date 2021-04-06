package com.danmo.commonapi.api.usercenter;




import com.danmo.commonapi.db.entity.User;
import com.danmo.commonapi.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserCenterService {


    /*
     * 登录接口
     * */

    @FormUrlEncoded
    @POST("api/v1/login")
    Observable<BaseResponse<String>> login(@Field("username") String username, @Field("password") String password );

    @GET("api/v1/users")
    Observable<BaseResponse<List<User>>> getUserList();

}
