package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.SuggestObj;
import com.liangxunwang.unimanager.mvc.vo.SuggestVO;
import com.liangxunwang.unimanager.query.SuggestQuery;
import com.liangxunwang.unimanager.service.*;
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
@RequestMapping("/suggest")
public class SuggestController extends ControllerConstants {

    @Autowired
    @Qualifier("suggestService")
    private ListService levelService;

    @Autowired
    @Qualifier("suggestService")
    private SaveService levelServiceSave;


    @Autowired
    @Qualifier("suggestService")
    private DeleteService levelServiceSaveDel;

    @Autowired
    @Qualifier("suggestService")
    private ExecuteService levelServiceSaveExe;

    @Autowired
    @Qualifier("suggestService")
    private UpdateService levelServiceUpdate;

    @RequestMapping("list")
    public String list(HttpSession session,ModelMap map, SuggestQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) levelService.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/suggest/list";
    }


    @RequestMapping("addSuggest")
    @ResponseBody
    public String addPiao(HttpSession session,SuggestObj level){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        levelServiceSave.save(level);
        //日志记录
        return toJSONString(SUCCESS);
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(HttpSession session,String mm_suggest_id){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        levelServiceSaveDel.delete(mm_suggest_id);
        return toJSONString(SUCCESS);
    }


    @RequestMapping("toEdit")
    public String toEdit(ModelMap map, String mm_suggest_id) throws Exception {
        SuggestVO report = (SuggestVO) levelServiceSaveExe.execute(mm_suggest_id);
        map.put("report", report);
        return "/suggest/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(HttpSession session,SuggestObj report){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        levelServiceUpdate.update(report);
        return toJSONString(SUCCESS);
    }

}
