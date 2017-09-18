package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.RecordFavour;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.mvc.vo.RecordFavourVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppRecordsFavourController extends ControllerConstants {

    @Autowired
    @Qualifier("appRecordFavourService")
    private SaveService appRecordFavourServiceSave;

    @Autowired
    @Qualifier("appRecordFavourService")
    private ListService appRecordFavourServiceList;


    @RequestMapping(value = "/appRecordFavourSave", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordFavourSave(RecordFavour recordFavour){
        try {
            appRecordFavourServiceSave.save(recordFavour);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("hasFavour")){
                    return toJSONString(new ErrorTip(1, "已经赞过！"));
                }else{
                    return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
                }
            }else{
                return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
            }
        }
    }


    @RequestMapping(value = "/appRecordsFavour", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordsFavour(RecordQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());

            List<RecordFavourVO> lists = (List<RecordFavourVO>) appRecordFavourServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
