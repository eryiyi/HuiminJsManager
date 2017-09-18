package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.EmpGroups;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppEmpGroupsController extends ControllerConstants {

    @Autowired
    @Qualifier("appEmpGroupsService")
    private ListService appEmpGroupsService;

    @Autowired
    @Qualifier("appEmpGroupsListService")
    private ListService appEmpGroupsListServiceList;

    @Autowired
    @Qualifier("appEmpGroupsService")
    private SaveService appEmpGroupsServiceSave;

    @Autowired
    @Qualifier("appEmpGroupsService")
    private FindService appEmpGroupsServiceFind;


    @Autowired
    @Qualifier("appEmpGroupsService")
    private DeleteService appEmpGroupsServiceDel;

    //查询用户的群组
    @RequestMapping(value = "/appEmpGroupsByEmpId", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appEmpGroupsByEmpId(String empid){
        if(StringUtil.isNullOrEmpty(empid)){
            return toJSONString(new ErrorTip(1, "会员ID为空，请检查或者重新登录！"));
        }
        try {
            List<EmpGroups> lists = (List<EmpGroups> ) appEmpGroupsService.list(empid);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    //加群
    @RequestMapping(value = "/appEmpGroupsSave", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appEmpGroupsSave(EmpGroups empGroups){
        if(StringUtil.isNullOrEmpty(empGroups.getEmpid())){
            return toJSONString(new ErrorTip(1, "会员ID为空，请检查或者重新登录！"));
        }
        if(StringUtil.isNullOrEmpty(empGroups.getGroupid())){
            return toJSONString(new ErrorTip(1, "群组ID为空，请检查或者重新登录！"));
        }
        try {
            appEmpGroupsServiceSave.save(empGroups);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
        }
    }


    //查询用户是否加入该群
    @RequestMapping(value = "/appEmpIsExist", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appEmpIsExist(EmpGroups empGroups){
        if(StringUtil.isNullOrEmpty(empGroups.getEmpid())){
            return toJSONString(new ErrorTip(1, "会员ID为空，请检查或者重新登录！"));
        }
        if(StringUtil.isNullOrEmpty(empGroups.getGroupid())){
            return toJSONString(new ErrorTip(1, "群组ID为空，请检查或者重新登录！"));
        }
        try {
            Boolean flag = (Boolean) appEmpGroupsServiceFind.findById(empGroups);
            if(flag){
                DataTip tip = new DataTip();
                tip.setData(SUCCESS);
                return toJSONString(tip);
            }else {
                return toJSONString(new ErrorTip(2, ""));
            }

        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
        }
    }

    //根据用户id和群组iD删除群信息
    @RequestMapping(value = "/appDeleteGroupsById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appDeleteGroupsById(EmpGroups empGroups){
        if(StringUtil.isNullOrEmpty(empGroups.getEmpid())){
            return toJSONString(new ErrorTip(1, "会员ID为空，请检查或者重新登录！"));
        }
        if(StringUtil.isNullOrEmpty(empGroups.getGroupid())){
            return toJSONString(new ErrorTip(1, "群组ID为空，请检查或者重新登录！"));
        }
        try {
            appEmpGroupsServiceDel.delete(empGroups);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "退群操作失败，请稍后重试！"));
        }
    }

    //查询用户组根据群ID
    @RequestMapping(value = "/appEmpByGroupId", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appEmpByGroupId(String groupid, String keywords){
        if(StringUtil.isNullOrEmpty(groupid)){
            return toJSONString(new ErrorTip(1, "群组ID为空，请检查或者重新登录！"));
        }
        try {
            if(StringUtil.isNullOrEmpty(keywords)){
                keywords = "";
            }
            String[] arras = {groupid, keywords};
            List<EmpGroups> lists = (List<EmpGroups> ) appEmpGroupsListServiceList.list(arras);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }




    @Autowired
    @Qualifier("appEmpGroupsService")
    private ExecuteService appEmpGroupsServiceExe;

    //删除群成员
    @RequestMapping(value = "/appDeleteMembers", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appDeleteMembers(String groupid, String empids){
        if(StringUtil.isNullOrEmpty(empids)){
            return toJSONString(new ErrorTip(1, "会员ID为空，请选择要删除的会员！"));
        }
        if(StringUtil.isNullOrEmpty(groupid)){
            return toJSONString(new ErrorTip(1, "群组ID为空，请检查或者重新登录！"));
        }
        try {
            String[] arras = {groupid, empids};
            appEmpGroupsServiceExe.execute(arras);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "退群操作失败，请稍后重试！"));
        }
    }

}
