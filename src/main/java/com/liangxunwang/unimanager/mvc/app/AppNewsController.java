package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.HappyHandNews;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.NewsQuery;
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
public class AppNewsController extends ControllerConstants {

    @Autowired
    @Qualifier("appNewsService")
    private ListService appNewsServiceList;

    @Autowired
    @Qualifier("appNewsService")
    private FindService appNewsServiceFind;


    @RequestMapping(value = "/appNews", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appNews(NewsQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
            List<HappyHandNews> lists = (List<HappyHandNews>) appNewsServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @RequestMapping(value = "/appNewsById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appNewsById(String newsid){
        try {
            HappyHandNews happyHandNotice = (HappyHandNews) appNewsServiceFind.findById(newsid);
            DataTip tip = new DataTip();
            tip.setData(happyHandNotice);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }
}
