package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.EmpGroupsDao;
import com.liangxunwang.unimanager.model.EmpGroups;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appEmpGroupsService")
public class AppEmpGroupsService implements ListService,SaveService,FindService,DeleteService ,ExecuteService{
    @Autowired
    @Qualifier("empGroupsDao")
    private EmpGroupsDao empGroupsDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();


    @Override
    public Object list(Object object) throws ServiceException {
        String empid = (String) object;
        Map<String, Object> map = new HashMap<>();
        map.put("empid", empid);
        List<EmpGroups> lists = empGroupsDao.lists(map);

        for(EmpGroups happyHandGroup:lists){
            if(!StringUtil.isNullOrEmpty(happyHandGroup.getPic())){
                if (happyHandGroup.getPic().startsWith("upload")) {
                    happyHandGroup.setPic(Constants.URL + happyHandGroup.getPic());
                }else {
                    happyHandGroup.setPic(Constants.QINIU_URL + happyHandGroup.getPic());
                }
            }
        }
        return lists;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        EmpGroups empGroups = (EmpGroups) object;
        empGroups.setEmpgroupsid(UUIDFactory.random());
        empGroups.setDateline(System.currentTimeMillis()+"");

        Map<String, Object> map = new HashMap<>();
        map.put("empid", empGroups.getEmpid());
        map.put("groupid", empGroups.getGroupid());
        List<EmpGroups> lists = empGroupsDao.findById(map);
        if(lists != null && lists.size()>0){
            return 0;
        }else {
            empGroupsDao.save(empGroups);
            //环信加入群聊
            easemobChatGroup.addSingleUserToChatGroup(empGroups.getGroupid(), empGroups.getEmpid());
        }

        return 200;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        EmpGroups empGroups = (EmpGroups) object;
        Map<String, Object> map = new HashMap<>();
        map.put("empid", empGroups.getEmpid());
        map.put("groupid", empGroups.getGroupid());
        List<EmpGroups> lists = empGroupsDao.findById(map);
        if(lists != null && lists.size()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        EmpGroups empGroups = (EmpGroups) object;
        empGroupsDao.delete(empGroups);
        return 200;
    }

    @Override
    public Object execute(Object object) throws Exception {
        String[] arras = (String[]) object;
        String groupid = arras[0];
        String empids = arras[1];
        if(StringUtil.isNullOrEmpty(groupid) || StringUtil.isNullOrEmpty(empids)){
            throw new ServiceException("null");
        }

        String[] arrEmp = empids.split(",");
        for(String empid:arrEmp){
            //先删除本地的
            EmpGroups empGroups = new EmpGroups();
            empGroups.setGroupid(groupid);
            empGroups.setEmpid(empid);
            empGroupsDao.delete(empGroups);
            //再删除环信的
            easemobChatGroup.removeSingleUserFromChatGroup(groupid, empid);
        }

        return 200;
    }
}
