package com.liangxunwang.unimanager.push.model;

import com.baidu.yun.core.annotation.JSonPath;

public class KeyValueForTopic {

	@JSonPath(path="key")
	private String timestamp;
	
	@JSonPath(path="value")
	private com.baidu.yun.push.model.TopicStatUnit value = null;
	
	public void setKey (String key) {
		this.timestamp = key;
	}
	
	public void setValue (com.baidu.yun.push.model.TopicStatUnit value) {
		this.value = value;
	}
	// get
	public String getKey () {
		return timestamp;
	}
	public com.baidu.yun.push.model.TopicStatUnit getValue () {
		return value;
	}
}
