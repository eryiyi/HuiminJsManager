package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.PersonQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
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
public class AppTuijianController extends ControllerConstants {


    @Autowired
    @Qualifier("appGroupsService")
    private ListService appGroupsServiceList;

    //推荐群
    @RequestMapping(value = "/appTuijianGroups", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appTuijianGroups(String empid){
        try {
            List<HappyHandGroup> lists = (List<HappyHandGroup>) appGroupsServiceList.list(empid);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }



    //按条件搜索技师或者会员
    @Autowired
    @Qualifier("appEmpSearchService")
    private ListService appEmpSearchServiceList;
    @RequestMapping(value = "/appSearchPeoplesByKeyWords", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSearchPeoplesByKeyWords(PersonQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
            List<Emp> lists = (List<Emp>) appEmpSearchServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    //查询推荐的会员或者技师
    @Autowired
    @Qualifier("appEmpTuijianService")
    private ListService appEmpTuijianServicelist;
    @RequestMapping(value = "/appTuijianEmpsOrYy", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appTuijianEmpsOrYy(PersonQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
            List<Emp> lists = (List<Emp>) appEmpTuijianServicelist.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appEmpSearchService")
    private ExecuteService appEmpSearchServiceExe;
    @RequestMapping(value = "/appSearchNearby", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSearchNearby(PersonQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
            List<Emp> lists = (List<Emp>) appEmpSearchServiceExe.execute(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


}
