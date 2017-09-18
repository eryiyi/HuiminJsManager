package com.liangxunwang.unimanager.chat.impl;

import com.liangxunwang.unimanager.chat.ChatMessageAPI;
import com.liangxunwang.unimanager.chat.comm.EasemobAPI;
import com.liangxunwang.unimanager.chat.comm.OrgInfo;
import com.liangxunwang.unimanager.chat.comm.ResponseHandler;
import com.liangxunwang.unimanager.chat.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.ChatHistoryApi;


public class EasemobChatMessage  implements ChatMessageAPI {

    private ResponseHandler responseHandler = new ResponseHandler();
    private ChatHistoryApi api = new ChatHistoryApi();

    @Override
    public Object exportChatMessages(final Long limit,final String cursor,final String query) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatmessagesGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(),query,limit+"",cursor);
            }
        });
    }
}
