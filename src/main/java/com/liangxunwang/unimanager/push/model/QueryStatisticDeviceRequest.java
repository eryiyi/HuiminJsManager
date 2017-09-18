package com.liangxunwang.unimanager.push.model;

public class QueryStatisticDeviceRequest extends com.baidu.yun.push.model.PushRequest {

    public QueryStatisticDeviceRequest addDeviceType (Integer deviceType) {
    	this.deviceType = deviceType;
    	return this;
    }
	public QueryStatisticDeviceRequest addExpires(Long requestTimeOut) {
		this.expires = requestTimeOut;
		return this;
	}
}
