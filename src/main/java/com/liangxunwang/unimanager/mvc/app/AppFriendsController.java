package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.FriendsQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppFriendsController extends ControllerConstants {

    @Autowired
    @Qualifier("appFriendsService")
    private SaveService appFriendsServiceSave;

    @Autowired
    @Qualifier("appFriendsService")
    private ListService appFriendsServiceList;

    @Autowired
    @Qualifier("appFriendsService")
    private UpdateService appFriendsServiceUpdate;

    @Autowired
    @Qualifier("appFriendsService")
    private ExecuteService appFriendsServiceExe;

    @RequestMapping(value = "/appSaveFriends", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveFriends(Friends friends){
        try {
            appFriendsServiceSave.save(friends);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("empId1Null")){
                    return toJSONString(new ErrorTip(1, "请检查会员ID是否存在！"));
                }else if(msg.equals("empId2Null")){
                    return toJSONString(new ErrorTip(1, "请检查会员ID是否存在！"));
                }else if(msg.equals("isFriends")){
                    return toJSONString(new ErrorTip(1, "已经是好友了，不能重复添加！"));
                }else if(msg.equals("hasApply")){
                    return toJSONString(new ErrorTip(1, "今天已经申请过了！"));
                }else if(msg.equals("applyTooMuch")){
                    return toJSONString(new ErrorTip(1, "对该会员的申请次数超出限制！最多三次！"));
                }else if(msg.equals("itinyourbk")){
                    return toJSONString(new ErrorTip(1, "对方在您的黑名单中，不能添加好友！"));
                }else if(msg.equals("youinitbk")){
                    return toJSONString(new ErrorTip(1, "您在对方的黑名单中，不能添加好友！"));
                }
                else{
                    return toJSONString(new ErrorTip(1, "申请失败，请稍后重试！"));
                }
            }else{
                return toJSONString(new ErrorTip(1, "申请失败，请稍后重试！"));
            }

        }
    }

    @RequestMapping(value = "/appFriends", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appFriends(FriendsQuery query){
        try {
            List<Friends> lists = (List<Friends>) appFriendsServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    //接受好友申请
    @RequestMapping(value = "/appAcceptFriends", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appAcceptFriends(Friends friends){
        try {
            appFriendsServiceUpdate.update(friends);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("friendsidnull")){
                    return toJSONString(new ErrorTip(1, "请检查该申请ID是否存在！"));
                }else if(msg.equals("ischecknull")){
                    return toJSONString(new ErrorTip(1, "请确认是否传值正确！"));
                }else if(msg.equals("hasFriends")){
                    return toJSONString(new ErrorTip(2, "对方已经是您的好友了！"));
                }
                else{
                    return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
                }
            }else{
                return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
            }

        }
    }

    //删除好友
    @RequestMapping(value = "/appDeleteFriends", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appDeleteFriends(Friends friends){
        try {
            appFriendsServiceExe.execute(friends);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("empidisnull")){
                    return toJSONString(new ErrorTip(1, "请检查该会员ID是否存在！"));
                }else if(msg.equals("noexist")){
                    return toJSONString(new ErrorTip(1, "该会员和您不是好友关系！"));
                }else if(msg.equals("isjwing")){
                    return toJSONString(new ErrorTip(1, "请先解除交往关系！"));
                }
                else{
                    return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
                }
            }else{
                return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
            }

        }
    }

}
