package com.liangxunwang.unimanager.push.model;

import com.baidu.yun.core.annotation.JSonPath;

public class TopicStatUnit {

	@JSonPath(path="ack")
	private int ack;
	
	public int getAckNum () {
		return ack;
	}
}
