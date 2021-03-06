package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.LxDxkLevelDao;
import com.liangxunwang.unimanager.model.LxDxkLevel;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("lxDxkLevelService")
public class LxDxkLevelService implements ListService,ExecuteService, UpdateService {
    @Autowired
    @Qualifier("lxDxkLevelDao")
    private LxDxkLevelDao lxDxkLevelDao;

    @Override
    public Object list(Object object) throws ServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<LxDxkLevel> lists = lxDxkLevelDao.lists(map);
        return lists;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return lxDxkLevelDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        LxDxkLevel lxAttribute = (LxDxkLevel) object;
        lxDxkLevelDao.update(lxAttribute);
        return null;
    }
}
