package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.JiaowangDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.dao.NewsDao;
import com.liangxunwang.unimanager.dao.NoticeDao;
import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.query.MessagesQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appMessagesService")
public class AppMessagesService implements ListService,FindService{

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    @Autowired
    @Qualifier("newsDao")
    private NewsDao newsDao;

    @Autowired
    @Qualifier("noticeDao")
    private NoticeDao noticeDao;

    @Autowired
    @Qualifier("jiaowangDao")
    private JiaowangDao jiaowangDao;

    @Override
    public Object list(Object object) throws ServiceException {
        MessagesQuery query = (MessagesQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
//        int index = (query.getIndex() - 1) * query.getSize();
//        int size = query.getSize();
//        map.put("index", index);
//        map.put("size", size);
        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }
        List<HappyHandMessage> lists = messagesDao.lists(map);
        for(HappyHandMessage happyHandMessage:lists){
            if(!StringUtil.isNullOrEmpty(happyHandMessage.getDateline())){
                happyHandMessage.setDateline(RelativeDateFormat.format(Long.parseLong(happyHandMessage.getDateline())));
            }
        }
        return lists;
    }


    @Override
    public Object findById(Object object) throws ServiceException {
        String noticeid= (String) object;
        HappyHandMessage happyHandMessage =  messagesDao.findById(noticeid);
        return happyHandMessage;
    }

}
