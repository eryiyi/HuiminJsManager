package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.SuggestDao;
import com.liangxunwang.unimanager.model.SuggestObj;
import com.liangxunwang.unimanager.mvc.vo.SuggestVO;
import com.liangxunwang.unimanager.query.SuggestQuery;
import com.liangxunwang.unimanager.service.*;
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
@Service("suggestService")
public class SuggestService implements ListService,SaveService,DeleteService,ExecuteService,UpdateService {
    @Autowired
    @Qualifier("suggestDao")
    private SuggestDao suggestDao;

    @Override
    public Object list(Object object) throws ServiceException {
        SuggestQuery query = (SuggestQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        List<SuggestVO> lists = suggestDao.lists(map);
        long count = suggestDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        SuggestObj suggestObj = (SuggestObj) object;
        suggestObj.setMm_suggest_id(UUIDFactory.random());
        suggestObj.setDateline(System.currentTimeMillis() + "");
        suggestDao.save(suggestObj);
        return 200;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String mm_suggest_id = (String) object;
        suggestDao.delete(mm_suggest_id);
        return 200;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return suggestDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        SuggestObj suggestObj = (SuggestObj) object;
        suggestDao.update(suggestObj);
        return 200;
    }
}
