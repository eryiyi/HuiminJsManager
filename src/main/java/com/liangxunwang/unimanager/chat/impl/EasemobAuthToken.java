package com.liangxunwang.unimanager.chat.impl;


import com.liangxunwang.unimanager.chat.AuthTokenAPI;
import com.liangxunwang.unimanager.chat.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
