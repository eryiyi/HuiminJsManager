package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.KefuTel;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.KefuQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppTelController extends ControllerConstants {

    @Autowired
    @Qualifier("kefuTelService")
    private ListService kefuTelService;

    @RequestMapping(value = "/appTel", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appTel(KefuQuery query){
        try {
            List<KefuTel> lists = (List<KefuTel>) kefuTelService.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }



}
