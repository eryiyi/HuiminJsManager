package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.GroupsDao;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appGroupsPublicService")
public class AppGroupsPublicService implements ListService {
    @Autowired
    @Qualifier("groupsDao")
    private GroupsDao groupsDao;

    @Override
    public Object list(Object object) throws ServiceException {
        List<HappyHandGroup> lists = new ArrayList<>();
        String[] groupids = {Constants.DEFAULT_GROUP_ID1, Constants.DEFAULT_GROUP_ID2};
        if (groupids != null && groupids.length != 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupids", groupids);
            lists = groupsDao.listsPublic(map);
        }
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


}
