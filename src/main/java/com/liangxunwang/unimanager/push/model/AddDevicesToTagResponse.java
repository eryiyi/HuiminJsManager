package com.liangxunwang.unimanager.push.model;

import com.baidu.yun.core.annotation.JSonPath;
import com.baidu.yun.push.model.DeviceInfo;

import java.util.LinkedList;
import java.util.List;

public class AddDevicesToTagResponse extends com.baidu.yun.push.model.PushResponse {

	@JSonPath(path="response_params\\result")
	private List<com.baidu.yun.push.model.DeviceInfo> devicesInfoAfterAdded = new LinkedList<com.baidu.yun.push.model.DeviceInfo> ();
	
	// get
	public List<DeviceInfo> getDevicesInfoAfterAdded () {
		return devicesInfoAfterAdded;
	}
}
