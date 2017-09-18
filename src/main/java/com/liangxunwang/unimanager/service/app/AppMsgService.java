package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.dao.NoticeDao;
import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.model.HappyHandNotice;
import com.liangxunwang.unimanager.model.MsgCount;
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

@Service("appMsgService")
public class AppMsgService implements ListService{

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;
    @Autowired
    @Qualifier("noticeDao")
    private NoticeDao noticeDao;


    @Override
    public Object list(Object object) throws ServiceException {
        String empid = (String) object;
        //系统消息
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("empid", empid);
        List<HappyHandMessage> lists1 = messagesDao.lists(map1);
        for(HappyHandMessage happyHandMessage:lists1){
            if(!StringUtil.isNullOrEmpty(happyHandMessage.getDateline())){
                happyHandMessage.setDateline(RelativeDateFormat.format(Long.parseLong(happyHandMessage.getDateline())));
            }
        }

        //活动公告
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("index", 0);
        map3.put("size", 10);
        List<HappyHandNotice> lists3 = noticeDao.lists(map3);

        return new MsgCount(lists1, lists3);
    }



}
