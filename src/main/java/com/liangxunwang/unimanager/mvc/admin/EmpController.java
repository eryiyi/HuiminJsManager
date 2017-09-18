package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class EmpController extends ControllerConstants {

    @Autowired
    @Qualifier("empService")
    private ListService empServiceList;

    @RequestMapping("/emp/toAdd")
    public String toAdd(){
        return "/emp/add";
    }

    @RequestMapping("/emp/list")
    public String list(HttpSession session,ModelMap map, EmpQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) empServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/emp/list";
    }


    @Autowired
    @Qualifier("empService")
    private FindService empServiceFind;

    @Autowired
    @Qualifier("provinceService")
    private ListService provinceServiceList;


    @RequestMapping("/emp/toEdit")
    public String toEdit(HttpSession session,ModelMap map, String empid) throws Exception {
        Emp member = (Emp) empServiceFind.findById(empid);
        map.put("cover", member.getCover());
        if(!StringUtil.isNullOrEmpty(member.getCover())){
            if (member.getCover().startsWith("upload")) {
                member.setCover(Constants.URL + member.getCover());
            }else {
                member.setCover(Constants.QINIU_URL + member.getCover());
            }
        }
        map.put("emp", member);
        //查询省份
        List<Province> listProvinces = (List<Province>) provinceServiceList.list("");
        map.put("listProvinces", listProvinces);
        return "/emp/edit";
    }


    @RequestMapping("/emp/toEditPwr")
    public String toEditPwr(HttpSession session,ModelMap map, String empid) throws Exception {
        Emp member = (Emp) empServiceFind.findById(empid);


        map.put("emp", member);

        return "/emp/editpwr";
    }


    @Autowired
    @Qualifier("empUpdateService")
    private UpdateService empUpdateService;

    @RequestMapping(value = "/emp/edit", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String edit(Emp emp){
        try {
            empUpdateService.update(emp);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "修改会员信息失败，请稍后重试！"));
        }
    }

    @Autowired
    @Qualifier("empUpdatePwrService")
    private UpdateService empUpdatePwrService;

    @RequestMapping(value = "/emp/editPwr", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String editPwr(Emp emp){
        try {
            empUpdatePwrService.update(emp);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "修改密码失败，请稍后重试！"));
        }
    }

    @Autowired
    @Qualifier("empHyrzService")
    private ListService empHyrzServiceList;

    @RequestMapping("/emp/listhy")
    public String listhy(HttpSession session,ModelMap map, EmpQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) empHyrzServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/emp/listhy";
    }

    @RequestMapping("/emp/listManager")
    public String listManager(HttpSession session,ModelMap map, EmpQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) empServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/admin/add_list";
    }


    @RequestMapping("/emp/listsf")
    public String listsf(HttpSession session,ModelMap map, EmpQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        query.setRzstate1("1");
        Object[] results = (Object[]) empServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/emp/listsf";
    }

}
