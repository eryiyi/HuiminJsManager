package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.GroupsDao;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appGroupsService")
public class AppGroupsService implements ListService ,FindService{
    @Autowired
    @Qualifier("groupsDao")
    private GroupsDao groupsDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();

    @Override
    public Object list(Object object) throws ServiceException {
        String empid = (String) object;
        List<HappyHandGroup> lists = new ArrayList<>();
        for(HappyHandGroup happyHandGroup:lists){
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
    public Object findById(Object object) throws ServiceException {
        String groupid = (String) object;
        HappyHandGroup happyHandGroup = groupsDao.findById(groupid);
        if(happyHandGroup != null){
            if(!StringUtil.isNullOrEmpty(happyHandGroup.getPic())){
                if (happyHandGroup.getPic().startsWith("upload")) {
                    happyHandGroup.setPic(Constants.URL + happyHandGroup.getPic());
                }else {
                    happyHandGroup.setPic(Constants.QINIU_URL + happyHandGroup.getPic());
                }
            }
        }
        return happyHandGroup;
    }
}
