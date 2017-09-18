package com.liangxunwang.unimanager.push.model;

import com.baidu.yun.core.annotation.JSonPath;
import com.baidu.yun.push.model.TimerResultInfo;

import java.util.LinkedList;
import java.util.List;

public class QueryTimerListResponse extends com.baidu.yun.push.model.PushResponse {

	@JSonPath(path="response_params\\total_num")
	private int totalNum;
	
	@JSonPath(path="response_params\\result")
	private List<com.baidu.yun.push.model.TimerResultInfo> timerResultInfos = new LinkedList<com.baidu.yun.push.model.TimerResultInfo> ();
	
	// get
	public int getTotalNum () {
		return totalNum;
	}
	public List<TimerResultInfo> getTimerResultInfos () {
		return timerResultInfos;
	}
}
