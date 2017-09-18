package com.liangxunwang.unimanager.push.model;

import com.baidu.yun.core.annotation.JSonPath;

public class KeyValueForMsg {

	@JSonPath(path="key")
	private String timestamp;
	
	@JSonPath(path="value")
	private com.baidu.yun.push.model.MsgStatUnit value = null;
	
	public void setKey (String key) {
		this.timestamp = key;
	}
	
	public void setValue (com.baidu.yun.push.model.MsgStatUnit value) {
		this.value = value;
	}
	
	public String getKey () {
		return timestamp;
	}
	
	public com.baidu.yun.push.model.MsgStatUnit getValue () {
		return value;
	}
}
