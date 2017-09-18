package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.ApplyBack;
import com.liangxunwang.unimanager.query.ApplyBackQuery;
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
@RequestMapping("/applyBack")
public class ApplyBackController extends ControllerConstants {

    @Autowired
    @Qualifier("applyBackService")
    private ListService applyBackServiceList;

    @Autowired
    @Qualifier("applyBackService")
    private UpdateService applyBackServiceUpate;

    @Autowired
    @Qualifier("applyBackService")
    private ExecuteService applyBackServiceExe;

    @RequestMapping("list")
    public String list(HttpSession session,ModelMap map, ApplyBackQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) applyBackServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/applyback/list";
    }


    @RequestMapping("update")
    @ResponseBody
    public String update(HttpSession session,ApplyBack applyBack){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        applyBackServiceUpate.update(applyBack);
        return toJSONString(SUCCESS);
    }


    @RequestMapping("toEdit")
    public String toEdit(ModelMap map, String applyid) throws Exception {
        ApplyBack report = (ApplyBack) applyBackServiceExe.execute(applyid);
        map.put("report", report);
        return "/applyback/edit";
    }

}
