package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.SuggestObj;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AppSuggestController extends ControllerConstants {

    @Autowired
    @Qualifier("suggestService")
    private SaveService suggestService;

    @RequestMapping(value = "/appSaveSuggest", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveSuggest(SuggestObj suggestObj){
        try {
            suggestService.save(suggestObj);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "保存数据失败，请稍后重试！"));
        }
    }



}
