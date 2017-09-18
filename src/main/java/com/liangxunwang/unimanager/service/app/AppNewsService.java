package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.NewsDao;
import com.liangxunwang.unimanager.model.HappyHandNews;
import com.liangxunwang.unimanager.query.NewsQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appNewsService")
public class AppNewsService implements ListService,FindService{

    @Autowired
    @Qualifier("newsDao")
    private NewsDao newsDao;

    @Override
    public Object list(Object object) throws ServiceException {
        NewsQuery query = (NewsQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        List<HappyHandNews> lists = newsDao.lists(map);
        return lists;
    }


    @Override
    public Object findById(Object object) throws ServiceException {
        String noticeid= (String) object;
        HappyHandNews happyHandNewsa =  newsDao.findById(noticeid);
        return happyHandNewsa;
    }
}
