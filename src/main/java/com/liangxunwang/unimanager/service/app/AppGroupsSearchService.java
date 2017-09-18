package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.GroupsDao;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.query.AppGroupsQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appGroupsSearchService")
public class AppGroupsSearchService implements ListService {
    @Autowired
    @Qualifier("groupsDao")
    private GroupsDao groupsDao;

    @Override
    public Object list(Object object) throws ServiceException {
        AppGroupsQuery query = (AppGroupsQuery) object;

        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getKeywords())){
            map.put("keywords", query.getKeywords());
        }
        if(!StringUtil.isNullOrEmpty(query.getLikeids())){
            map.put("likeids", query.getLikeids());
        }

        List<HappyHandGroup> lists = groupsDao.listsSearch(map);

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
