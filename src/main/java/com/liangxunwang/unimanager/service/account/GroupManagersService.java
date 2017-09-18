package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.EmpGroupsDao;
import com.liangxunwang.unimanager.dao.EmpGroupsManagerDao;
import com.liangxunwang.unimanager.dao.GroupsDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpGroupManager;
import com.liangxunwang.unimanager.model.EmpGroups;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import io.swagger.client.model.NewOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("groupManagersService")
public class GroupManagersService implements FindService ,DeleteService, SaveService{
    @Autowired
    @Qualifier("groupsDao")
    private GroupsDao groupsDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("empGroupsManagerDao")
    private EmpGroupsManagerDao empGroupsManagerDao;

    @Override
    public Object findById(Object object) throws ServiceException {
        String groupid = (String) object;
        Map<String, Object> map1 = new HashMap<>();
        map1.put("groupid", groupid);
        List<EmpGroupManager> lists = empGroupsManagerDao.lists(map1);
        if(lists != null){
            for(EmpGroupManager member:lists){
                if(!StringUtil.isNullOrEmpty(member.getCover())){
                    if (member.getCover().startsWith("upload")) {
                        member.setCover(Constants.URL + member.getCover());
                    }else {
                        member.setCover(Constants.QINIU_URL + member.getCover());
                    }
                }
            }
        }
        return lists;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        EmpGroupManager empGroupManager= (EmpGroupManager) object;
        //先删除本地数据库
        empGroupsManagerDao.delete(empGroupManager);
        //更换环信的用户OWNER
        //设置该用户为群管理员
        NewOwner newOwner = new NewOwner();
        newOwner.newowner(Constants.DEFAULT_GROUP_OWNER);
        easemobChatGroup.transferChatGroupOwner(empGroupManager.getGroupid(), newOwner);
        return 200;
    }

    @Autowired
    @Qualifier("empGroupsDao")
    private EmpGroupsDao empGroupsDao;

    @Override
    public Object save(Object object) throws ServiceException {
        EmpGroupManager empGroupManager = (EmpGroupManager) object;
        String mobile = empGroupManager.getMobile();
        String groupid = empGroupManager.getGroupid();
        if(StringUtil.isNullOrEmpty(mobile)){
            throw new ServiceException("mobile_null");
        }
        if(StringUtil.isNullOrEmpty(groupid)){
            throw new ServiceException("groupid_null");
        }
        Emp emp = empDao.findByMobile(mobile);
        if(emp == null){
            throw new ServiceException("emp_null");
        }
        //先判断用户是否在群组里面，如果你不在群里面，先加入群
        Map<String, Object> map = new HashMap<>();
        map.put("empid", emp.getEmpid());
        map.put("groupid", groupid);
        List<EmpGroups> lists = empGroupsDao.findById(map);
        if(lists != null && lists.size()>0){
            //说明加入群聊了 不用管
        }else {
            //说明没有加入群聊，加入
            EmpGroups empGroups = new EmpGroups();
            empGroups.setEmpgroupsid(UUIDFactory.random());
            empGroups.setDateline(System.currentTimeMillis()+"");
            empGroups.setEmpid(emp.getEmpid());
            empGroups.setGroupid(groupid);
            empGroupsDao.save(empGroups);
            //环信加入群聊
            easemobChatGroup.addSingleUserToChatGroup(empGroups.getGroupid(), empGroups.getEmpid());
        }
        //获取以前的管理员  之后让他退出群
        EmpGroupManager empGroupManager1 = empGroupsManagerDao.findById(groupid);//这
        if(empGroupManager1 != null){// 个是以前的管理员
            EmpGroups empGroups = new EmpGroups();
            empGroups.setEmpid(empGroupManager1.getEmpid());
            empGroups.setGroupid(groupid);
            empGroupsDao.delete(empGroups);//以前的管理员退群
        }

        //设置该用户为群管理员
        NewOwner newOwner = new NewOwner();
        newOwner.newowner(emp.getEmpid());
        easemobChatGroup.transferChatGroupOwner(groupid, newOwner);

        empGroupsManagerDao.deleteByGroupid(groupid);

        empGroupManager.setEmpid(emp.getEmpid());
        empGroupManager.setEmp_group_manager_id(UUIDFactory.random());
        empGroupManager.setDateline(System.currentTimeMillis()+"");
        empGroupsManagerDao.save(empGroupManager);

        if(empGroupManager1 != null){
            //以前的管理员退群-环信那边退出
            easemobChatGroup.removeSingleUserFromChatGroup(groupid, empGroupManager1.getEmpid());
        }


        return 200;
    }
}
