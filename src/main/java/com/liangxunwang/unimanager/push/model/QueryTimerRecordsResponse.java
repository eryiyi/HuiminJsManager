package com.liangxunwang.unimanager.push.model;

import com.baidu.yun.core.annotation.HttpParamKeyName;
import com.baidu.yun.core.annotation.JSonPath;
import com.baidu.yun.core.annotation.R;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.model.Record;

import java.util.LinkedList;
import java.util.List;

public class QueryTimerRecordsResponse extends com.baidu.yun.push.model.PushResponse {

	@JSonPath(path="response_params\\timer_id")
	@HttpParamKeyName(name=BaiduPushConstants.TIMER_ID, param=R.REQUIRE)
	private String timerId;
	
	@JSonPath(path="response_params\\result")
	@HttpParamKeyName(name=BaiduPushConstants.TIMER_RECORDS, param=R.REQUIRE)
	private List<com.baidu.yun.push.model.Record> timerRecords = new LinkedList<com.baidu.yun.push.model.Record>();
	
	// get
	public String getTimerId () {
		return timerId;
	}
	public List<Record> getTimerRecords () {
		return timerRecords;
	}
}
