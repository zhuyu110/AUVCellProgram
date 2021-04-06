package com.danmo.commonapi.model;
import java.io.Serializable;

/**
 * retrofit网络请求基类
 * @author zhuyu
 *
 */
public class BaseResponse<T> implements Serializable {
	String msg;
	int status_code ;
	T data;
	String uuid;
	String token;
	public BaseResponse( String msg, int status,String uid) {
		this.msg = msg;
		this.status_code = status;
		this.uuid=uid;
	}

	public BaseResponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
