package com.liangxunwang.unimanager.chat.impl;

import com.liangxunwang.unimanager.chat.SendMessageAPI;
import com.liangxunwang.unimanager.chat.comm.EasemobAPI;
import com.liangxunwang.unimanager.chat.comm.OrgInfo;
import com.liangxunwang.unimanager.chat.comm.ResponseHandler;
import com.liangxunwang.unimanager.chat.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
