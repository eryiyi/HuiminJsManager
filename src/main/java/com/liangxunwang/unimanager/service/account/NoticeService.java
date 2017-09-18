package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.NoticeDao;
import com.liangxunwang.unimanager.model.HappyHandNotice;
import com.liangxunwang.unimanager.query.NoticeQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.BaiduPush;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("noticeService")
public class NoticeService implements ListService,SaveService, ExecuteService {

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
        long count = noticeDao.count(map);
        return new Object[]{lists, count};
    }

    @Override
    public Object save(Object object) throws ServiceException {
        HappyHandNotice notice = (HappyHandNotice) object;
        notice.setNoticeid(UUIDFactory.random());
        notice.setDateline(DateUtil.getDateAndTime());
        noticeDao.save(notice);
        //发通告
        BaiduPush.PushMsgToAll(3, "有新的活动公告信息", notice.getTitle(), "3");
        BaiduPush.PushMsgToAll(4, "有新的活动公告信息", notice.getTitle(), "3");
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return noticeDao.findById((String) object);
    }

}
