package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.Report;
import com.liangxunwang.unimanager.query.ReportQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/report")
public class ReportController extends ControllerConstants {

    @Autowired
    @Qualifier("reportService")
    private ListService reportServiceList;

    @Autowired
    @Qualifier("reportService")
    private UpdateService reportServiceUpdate;

    @Autowired
    @Qualifier("reportService")
    private ExecuteService reportServiceExe;

    @RequestMapping("list")
    public String list(HttpSession session,ModelMap map, ReportQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) reportServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/report/list";
    }


    @RequestMapping("update")
    @ResponseBody
    public String update(HttpSession session,Report report){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        reportServiceUpdate.update(report);
        return toJSONString(SUCCESS);
    }


    @RequestMapping("toEdit")
    public String toEdit(ModelMap map, String reportid) throws Exception {
        Report report = (Report) reportServiceExe.execute(reportid);
        map.put("report", report);
        return "/report/edit";
    }

}
