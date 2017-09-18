package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.VersonCodeObj;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.VersionCodeQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhl on 2015/2/2.
 */
@Controller
public class AppVersionCodeController extends ControllerConstants {
    @Autowired
    @Qualifier("versionCodeObjService")
    private ListService versionCodeObjService;

    @RequestMapping(value = "/getVersionCode", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getVersionCode(VersionCodeQuery query){
        try {
            List<VersonCodeObj> list = (List<VersonCodeObj>) versionCodeObjService.list(query);
            if(list != null && list.size() >0 ){
                //说明存在
                VersonCodeObj versonCodeObj = list.get(0);
                DataTip tip = new DataTip();
                tip.setData(versonCodeObj);
                return toJSONString(tip);
            }else {
                return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
            }

        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
