package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.HappyHandCompany;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppAboutUsController extends ControllerConstants {

    @Autowired
    @Qualifier("aboutUsService")
    private ListService aboutUsServiceList;

    @RequestMapping(value = "/appAboutUs", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appAboutUs(){
        try {
            List<HappyHandCompany> lists = (List<HappyHandCompany>) aboutUsServiceList.list("");
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }



}
