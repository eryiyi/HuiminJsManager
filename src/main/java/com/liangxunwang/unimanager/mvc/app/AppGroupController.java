package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.EmpGroupManager;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.AppGroupsQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppGroupController extends ControllerConstants {


    @Autowired
    @Qualifier("appGroupsService")
    private FindService appGroupsServiceFind;

    @RequestMapping(value = "/appGroupsById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appGroupsById(String groupid){
        try {
            HappyHandGroup happyHandGroup = (HappyHandGroup) appGroupsServiceFind.findById(groupid);
            DataTip tip = new DataTip();
            tip.setData(happyHandGroup);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appGroupsPublicService")
    private ListService appGroupsPublicServiceList;

    @RequestMapping(value = "/appPublicGroups", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appPublicGroups(){
        try {
            List<HappyHandGroup> lists = (List<HappyHandGroup>) appGroupsPublicServiceList.list("");
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    @Autowired
    @Qualifier("appGroupsSearchService")
    private ListService appGroupsSearchServiceList;

    //按照条件查询群列表
    @RequestMapping(value = "/appSearchGroupsByKeywords", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSearchGroupsByKeywords(AppGroupsQuery query){
        try {
            List<HappyHandGroup> lists = (List<HappyHandGroup>) appGroupsSearchServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }




    //查询群管理员
    @Autowired
    @Qualifier("groupManagersService")
    private FindService groupManagersService;

    @RequestMapping(value = "/appGroupsManager", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appGroupsManager(String groupid){
        try {
            List<EmpGroupManager> lists = (List<EmpGroupManager>) groupManagersService.findById(groupid);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
