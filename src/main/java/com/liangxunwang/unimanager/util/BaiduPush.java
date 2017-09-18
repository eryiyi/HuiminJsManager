package com.liangxunwang.unimanager.util;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/3/26 0026.
 */
public class BaiduPush {
    //msgtypeid  :1系统消息 2系统资讯 3活动公告  4交往消息 5加好友请求
    public static void PushMsgToAll (int deveiceType, String title, String content, String msgtypeid) {
        /*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */

        PushKeyPair pair = null;
        if (deveiceType == 3) {
            pair = new PushKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
        }else {
            pair = new PushKeyPair(Constants.IOS_API_KEY, Constants.IOS_SECRET_KEY);
        }
        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler() {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. 设置请求参数，创建请求实例
            PushMsgToAllRequest request = null;
            if(deveiceType == 3){
//                request = new PushMsgToAllRequest().
//                        addMsgExpires(new Integer(3600)).
//                        addMessageType(1).
//                        addMessage("{\"title\":\"" + title + "\",\"description\":\"" + content + "\",\"custom_content\":{\"msgtypeid\":\"" + msgtypeid + "\"}}").
//                        addDeviceType(deveiceType);
                 request = new PushMsgToAllRequest()
                        .addMsgExpires(new Integer(3600)).addMessageType(0)
                        .addMessage(title) //添加透传消息
                        .addSendTime(System.currentTimeMillis() / 1000 + 120) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送
                        .addDeviceType(3);

            }
            if(deveiceType == 4){
//                request = new PushMsgToAllRequest().
//                        addMsgExpires(new Integer(3600)).
//                        addMessageType(1).
//                        addMessage("{\"title\":\"" +title + "\",\"description\":\"" + content + "\",\"custom_content\":{\"msgtypeid\":\"" + msgtypeid+"\"}}").
//                        addDeviceType(deveiceType).addDepolyStatus(1);

                JSONObject notification = new JSONObject();
                JSONObject jsonAPS = new JSONObject();
                jsonAPS.put("alert", title);
                jsonAPS.put("sound", "ttt"); // 设置通知铃声样式,例如"ttt"，用户自定义。
                notification.put("aps", jsonAPS);
                notification.put("msgtypeid", msgtypeid);

                request = new PushMsgToAllRequest()
                        .addMsgExpires(new Integer(3600)).addMessageType(1)
                        .addMessage(notification.toString())
                        .addSendTime(System.currentTimeMillis() / 1000 + 120) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送
                        .addDepolyStatus(2).addDeviceType(4);
            }
            // 5. 执行Http请求
            PushMsgToAllResponse response = pushClient.
                    pushMsgToAll(request);
            // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());


        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushClientException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushServerException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
    }

    public static void PushMsgToSingleDevice (int deveiceType, String title, String content, String msgtypeid ,String channelId) {
          /*1. 创建PushKeyPair
         *用于app的合法身份认证
         *apikey和secretKey可在应用详情中获取
         */
        PushKeyPair pair = null;
        if (deveiceType == 3) {
            pair = new PushKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
        }else {
            pair = new PushKeyPair(Constants.IOS_API_KEY, Constants.IOS_SECRET_KEY);
        }

        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler (new YunLogHandler() {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            PushMsgToSingleDeviceRequest request= null;
            if(deveiceType == 3){
                JSONObject notification = new JSONObject();
                notification.put("title", title);
                notification.put("description",content);
                notification.put("notification_builder_id", 0);
                notification.put("notification_basic_style", 4);
                notification.put("open_type", 1);
                notification.put("url", "http://push.baidu.com");
                JSONObject jsonCustormCont = new JSONObject();
                jsonCustormCont.put("msgtypeid", msgtypeid); //自定义内容，key-value
                notification.put("custom_content", jsonCustormCont);

                 request = new PushMsgToSingleDeviceRequest()
                        .addChannelId(channelId)
                        .addMsgExpires(new Integer(3600)). // message有效时间
                                addMessageType(1).// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
                                addMessage(notification.toString()).
                                addDeviceType(3);// deviceType => 3:android, 4:ios

            }
            if(deveiceType == 4){
                JSONObject notification = new JSONObject();
                JSONObject jsonAPS = new JSONObject();
                jsonAPS.put("alert", title);
                jsonAPS.put("sound", "ttt"); // 设置通知铃声样式，例如"ttt"，用户自定义。
                notification.put("aps", jsonAPS);
                notification.put("msgtypeid", msgtypeid );

               request = new PushMsgToSingleDeviceRequest()
                        .addChannelId(channelId)
                        .addMsgExpires(new Integer(3600)). // 设置message的有效时间
                                addMessageType(1).// 1：通知,0:透传消息.默认为0 注：IOS只有通知.
                                addMessage(notification.toString()).addDeployStatus(2). // IOS,
                                // DeployStatus
                                // => 1: Developer
                                // 2: Production.
                                addDeviceType(4);// deviceType => 3:android, 4:ios
            }
            // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.
                    pushMsgToSingleDevice(request);
            // 6. Http请求返回值解析
            System.out.println("msgId: " + response.getMsgId()
                    + ",sendTime: " + response.getSendTime());
        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushClientException e1) {
                    e1.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                try {
                    throw e;
                } catch (PushServerException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }

    }


}
