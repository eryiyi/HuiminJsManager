package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.HappyHandNotice;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.NoticeQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppNoticesController extends ControllerConstants {

    @Autowired
    @Qualifier("appNoticeService")
    private ListService appNoticeServiceList;

    @Autowired
    @Qualifier("appNoticeService")
    private FindService appNoticeServiceFind;


    @RequestMapping(value = "/appNotices", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appNotices(NoticeQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());

            List<HappyHandNotice> lists = (List<HappyHandNotice>) appNoticeServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @RequestMapping(value = "/appNoticeById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appNoticeById(String noticeid){
        try {

            HappyHandNotice happyHandNotice = (HappyHandNotice) appNoticeServiceFind.findById(noticeid);
            DataTip tip = new DataTip();
            tip.setData(happyHandNotice);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }
}
