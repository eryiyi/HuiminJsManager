package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.NoticeDao;
import com.liangxunwang.unimanager.model.HappyHandNotice;
import com.liangxunwang.unimanager.query.NoticeQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appNoticeService")
public class AppNoticeService implements ListService,FindService{

    @Autowired
    @Qualifier("noticeDao")
    private NoticeDao noticeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        NoticeQuery query = (NoticeQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        List<HappyHandNotice> lists = noticeDao.lists(map);
        return lists;
    }


    @Override
    public Object findById(Object object) throws ServiceException {
        String noticeid= (String) object;
        HappyHandNotice happyHandNotice =  noticeDao.findById(noticeid);
        return happyHandNotice;
    }
}
