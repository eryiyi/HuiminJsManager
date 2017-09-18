package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.RecordCommentDao;
import com.liangxunwang.unimanager.model.RecordComment;
import com.liangxunwang.unimanager.mvc.vo.RecordCommentVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appRecordCommentService")
public class AppRecordCommentService implements SaveService ,ListService{
    @Autowired
    @Qualifier("recordCommentDao")
    private RecordCommentDao recordCommentDao;

    @Override
    public Object list(Object object) throws ServiceException {
        RecordQuery query = (RecordQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getRecord_id())){
            map.put("record_id", query.getRecord_id());
        }

        List<RecordCommentVO> lists = recordCommentDao.lists(map);
        if(lists != null){
            for(RecordCommentVO info:lists){
                if(info != null){
                    if(!StringUtil.isNullOrEmpty(info.getComment_dateline())){
                        info.setComment_dateline(RelativeDateFormat.format(Long.valueOf(info.getComment_dateline())));
                    }
                    if(!StringUtil.isNullOrEmpty(info.getCommentCover())){
                        if (info.getCommentCover().startsWith("upload")) {
                            info.setCommentCover(Constants.URL + info.getCommentCover());
                        }else {
                            info.setCommentCover(Constants.QINIU_URL + info.getCommentCover()+"-yasuoone");
                        }
                    }
                }
            }
        }
        return lists;
    }


    @Override
    public Object save(Object object) throws ServiceException {
        RecordComment record = (RecordComment) object;
        record.setComment_id(UUIDFactory.random());
        record.setComment_dateline(System.currentTimeMillis() + "");
        recordCommentDao.save(record);
        return 200;
    }


}
