package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/appEmpVipController")
public class AppEmpVipController extends ControllerConstants {

    //-------------------每天凌晨执行-------------------------
    public String updateEmpVip(){
        updatePaihangVip();
        return null;
    }

    @Autowired
    @Qualifier("appEmpUpdateCard")
    private ExecuteService appEmpUpdateCardExe;

    @RequestMapping("/updatePaihangVip")
    @ResponseBody
    public String updatePaihangVip(){
        try {
            appEmpUpdateCardExe.execute("");
            return toJSONString(SUCCESS);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1,""));
        }
    }

    //发送即将到期通知给用户
    @Autowired
    @Qualifier("appEmpUpdateCard")
    private FindService appEmpUpdateCardFind;

    public String noticeEmpEnd(){
        updateEmpNoticeEnd();
        return null;
    }

    @RequestMapping("/updateEmpNoticeEnd")
    @ResponseBody
    public String updateEmpNoticeEnd(){
        try {
            appEmpUpdateCardFind.findById("");
            return toJSONString(SUCCESS);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1,""));
        }
    }



    //会员登录状态更改
    @Autowired
    @Qualifier("empLoginService")
    private ExecuteService empLoginService;

    public String emploginout(){
        updateEmplogout();
        return null;
    }

    @RequestMapping("/updateEmplogout")
    @ResponseBody
    public String updateEmplogout(){
        try {
            empLoginService.execute("");
            return toJSONString(SUCCESS);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1,""));
        }
    }
}
