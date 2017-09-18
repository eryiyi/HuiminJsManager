package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Admin;
//import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2015/8/17.
 */
@Controller
public class AppShareRegController extends ControllerConstants {

    @Autowired
    @Qualifier("empService")
    private FindService appMemberService;

    /**
     * 推广页面 推广注册 带推广者ID
     * @return
     */
    @RequestMapping(value = "/appShareReg", produces = "text/plain;charset=UTF-8")
    public String appGetAdEmp(HttpSession session,ModelMap map, String emp_id){
        try {
            Emp member = (Emp) appMemberService.findById(emp_id);
            map.put("member", member);
            return "/reg_app/reg_app";
        }catch (ServiceException e){
            return "/reg_app/reg_app";
        }
    }


    @Autowired
    @Qualifier("appEmpService")
    private SaveService memberRegisterService;

    @RequestMapping("saveEmpShare")
    @ResponseBody
    public String saveEmpShare(HttpSession session,Emp member){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        try {
            Emp member1 = (Emp) memberRegisterService.save(member);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals(Constants.HAS_EXISTS)){
                return toJSONString(new ErrorTip(1, "操作失败！该手机号已注册，请直接登录"));//手机号已经注册了，换个试试
            }
            if (msg.equals(Constants.SAVE_ERROR)){
                return toJSONString(new ErrorTip(1, "操作失败！请稍后重试"));//注册失败，请稍后重试
            }
            if(msg.equals(Constants.HX_ERROR)){
                return toJSONString(new ErrorTip(1, "操作失败！环信注册失败"));//环信注册失败
            }
        }
        return toJSONString(new ErrorTip(1, "操作失败！"));//注册失败
    }



}
