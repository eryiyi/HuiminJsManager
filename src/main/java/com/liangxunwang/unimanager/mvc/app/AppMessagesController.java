package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.model.MsgCount;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.MessagesQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppMessagesController extends ControllerConstants {

    @Autowired
    @Qualifier("appMessagesService")
    private ListService appMessagesServiceList;

    @Autowired
    @Qualifier("appMessagesService")
    private FindService appMessagesServiceFind;

    @RequestMapping(value = "/appMessages", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appMessages(MessagesQuery query, Page page){
        try {
            List<HappyHandMessage> lists = (List<HappyHandMessage>) appMessagesServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @RequestMapping(value = "/appMessagesById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appMessagesById(String msgid){
        try {

            HappyHandMessage happyHandMessage = (HappyHandMessage) appMessagesServiceFind.findById(msgid);
            DataTip tip = new DataTip();
            tip.setData(happyHandMessage);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appMsgService")
    private ListService appMsgService;

    @RequestMapping(value = "/appMsgAllList", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appMsgAllList(String empid){
        try {
            //查询10条系统消息  10条系统资讯 10条活动公告  10条交往消息
            MsgCount msgCount = (MsgCount) appMsgService.list(empid);
            DataTip tip = new DataTip();
            tip.setData(msgCount);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
