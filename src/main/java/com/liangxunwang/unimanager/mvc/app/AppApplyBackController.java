package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.ApplyBack;
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
public class AppApplyBackController extends ControllerConstants {

    @Autowired
    @Qualifier("applyBackService")
    private SaveService applyBackService;

    @RequestMapping(value = "/appSaveApplyBack", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveApplyBack(ApplyBack applyBack){
        try {
            int i = (int) applyBackService.save(applyBack);
            if(i==1){
                return toJSONString(new ErrorTip(2, "已经提交过申请，请等待管理员处理！"));
            }else {
                DataTip tip = new DataTip();
                tip.setData(SUCCESS);
                return toJSONString(tip);
            }

        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "提交申请失败，请稍后重试！"));
        }
    }



}
