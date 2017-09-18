package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.Role;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.AdminQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zhl on 2015/8/12.
 */
@Controller
public class AdminController extends ControllerConstants {

    @Autowired
    @Qualifier("adminLoginService")
    private ExecuteService adminLoginService;

    @Autowired
    @Qualifier("adminService")
    private ExecuteService adminExecuteService;

    @Autowired
    @Qualifier("adminService")
    private FindService adminFindService;

    @Autowired
    @Qualifier("adminService")
    private ListService adminListService;

    @Autowired
    @Qualifier("adminService")
    private SaveService adminServiceSave;

    @RequestMapping(value = "/changePass", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String changePass(HttpSession session, String manager_pass, String manager_id,String manager_admin) throws Exception {
        try {
            Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
            Object[] params = new Object[]{manager_id, manager_pass, manager_admin};
            adminExecuteService.execute(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1,"改变密码失败！"));
        }
    }

    @RequestMapping("/toChangePass")
    public String toChanagePass(){
        return "/admin/pass";
    }

    @RequestMapping("/admin/list")
    public String list(HttpSession session,ModelMap map, AdminQuery query, Page page){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) adminListService.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/admin/list";
    }


    //添加管理员
    @RequestMapping(value = "/admin/addAdmin", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addPiao(HttpSession session,Admin admin){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        try {
            adminServiceSave.save(admin);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("MobileIsUse")){
                    return toJSONString(new ErrorTip(1, "添加管理员失败！"));
                }
            }else{
                return toJSONString(new ErrorTip(1, "添加管理员失败！"));
            }

        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping(value = "/adminLogin", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String adminLogin(HttpSession session,HttpServletRequest request,String username, String password) throws Exception {
        try {
            Object[] params = new Object[]{username, password};
            Object[] results = (Object[])adminLoginService.execute(params);
            Admin admin = (Admin) results[0];
            session.setAttribute(ControllerConstants.ACCOUNT_KEY, admin);
            String permissions = (String) results[1];

            if(permissions == null || permissions.isEmpty()) {
                return toJSONString(new ErrorTip(1, "登录失败，该账号没有权限登录后台！"));
            }
            session.setAttribute(ControllerConstants.PERMISSIONS, permissions);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("USERNAME_ERROR")){
                    return toJSONString(new ErrorTip(1, "账号不存在！"));
                }else  if (msg.equals("PASSWORD_ERROR")){
                    return toJSONString(new ErrorTip(1, "密码错误！"));
                } if (msg.equals("QUANXIAN_ERROR")){
                    return toJSONString(new ErrorTip(1, "账号已被禁用，请联系管理员！"));
                }else{
                    return toJSONString(new ErrorTip(1, "登录失败，请稍后重试！"));
                }
            }else{
                return toJSONString(new ErrorTip(1, "登录失败，请稍后重试！"));
            }

        }
    }
    @RequestMapping("/admin/detail")
    public String adminDetail(ModelMap map, String manager_id) throws Exception {
        Object[] results = (Object[]) adminFindService.findById(manager_id);
        if(results != null){
            Admin admin = (Admin) results[0];
            String permissions = (String) results[1];
            Role role  = (Role) results[2];
            map.put("admin", admin);
            if(role != null){
                map.put("role", role);
            }else {
                map.put("roleRname", "最高管理员");
            }
            map.put("permissions_admin", permissions);
        }
        return "/admin/detail";
    }

    @Autowired
    @Qualifier("adminService")
    private UpdateService adminUpdateService;

    @RequestMapping(value = "/admin/updateType", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateType(HttpSession session, String manager_id, String is_use ){
        try {
            Object[] params = new Object[]{manager_id, is_use};
            adminUpdateService.update(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "修改失败，请稍后重试！"));
        }
    }

    @RequestMapping("/toAddManager")
    public String toAddManager(HttpSession session){
        return "/admin/addManager";
    }


    @Autowired
    @Qualifier("adminService")
    private SaveService adminSave;


    @RequestMapping(value = "/addManager", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addManager(HttpSession session, Admin admin){
        if(StringUtil.isNullOrEmpty(admin.getManager_admin())){
            return toJSONString(new ErrorTip(1, "账号不能为空！"));
        }
        if(StringUtil.isNullOrEmpty(admin.getManager_pass())){
            return toJSONString(new ErrorTip(2, "密码不能为空！"));
        }
        if(StringUtil.isNullOrEmpty(admin.getManager_cover())){
            return toJSONString(new ErrorTip(3, "头像不能为空！"));
        }
        try {
            adminSave.save(admin);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("has_exist")){
                    return toJSONString(new ErrorTip(4, "账号已经存在，换一个试试！"));
                }else{
                    return toJSONString(new ErrorTip(5, "新增管理员失败，请稍后重试！"));
                }
            }else{
                return toJSONString(new ErrorTip(5, "新增管理员失败，请稍后重试！"));
            }

        }
    }


    @Autowired
    @Qualifier("roleService")
    private ListService roleService;

    @RequestMapping("/admin/role")
    public String adminRole(ModelMap map, String id) throws Exception {
        Object[] results = (Object[]) adminFindService.findById(id);
        if(results != null){
            Admin admin = (Admin) results[0];
            String permissions = (String) results[1];
            Role role  = (Role) results[2];
            map.put("admin", admin);
            if(role != null){
                map.put("role", role);
            }else {
                map.put("roleRname", "最高管理员");
            }
            map.put("permissions_admin", permissions);
        }
        //角色
        List<Role> roles = (List<Role>) roleService.list("");
        map.put("roles", roles);
        return "/admin/adminRole";
    }



    @Autowired
    @Qualifier("adminRolesService")
    private ExecuteService adminRolesServiceExe;
    //赋权限
    @RequestMapping(value = "/changroles", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String changroles(String permissions, String id){
        try {
            Object[] params = new Object[]{id, permissions};
            adminRolesServiceExe.execute(params);
            return toJSONString(SUCCESS);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "操作失败，请稍后重试！"));
        }
    }


}
