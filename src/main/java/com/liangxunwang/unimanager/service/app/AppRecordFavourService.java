package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.RecordFavourDao;
import com.liangxunwang.unimanager.model.RecordFavour;
import com.liangxunwang.unimanager.mvc.vo.RecordFavourVO;
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

@Service("appRecordFavourService")
public class AppRecordFavourService implements SaveService ,ListService{
    @Autowired
    @Qualifier("recordFavourDao")
    private RecordFavourDao recordFavourDao;

    @Override
    public Object save(Object object) throws ServiceException {
        RecordFavour record = (RecordFavour) object;
        //先查询该用户是否已经点过赞了
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("record_id", record.getRecord_id());
        map.put("favour_empid", record.getFavour_empid());

        List<RecordFavourVO> lists = recordFavourDao.findByEmpId(map);
        if(lists != null && lists.size() > 0){
            //说明赞过了
            throw new ServiceException("hasFavour");
        }
        record.setFavour_id(UUIDFactory.random());
        record.setFavour_dateline(System.currentTimeMillis() + "");
        recordFavourDao.save(record);
        return 200;
    }

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

        List<RecordFavourVO> lists = recordFavourDao.lists(map);
        if(lists != null){
            for(RecordFavourVO info:lists){
                if(info != null){
                    if(!StringUtil.isNullOrEmpty(info.getFavour_dateline())){
                        info.setFavour_dateline(RelativeDateFormat.format(Long.valueOf(info.getFavour_dateline())));
                    }
                    if(!StringUtil.isNullOrEmpty(info.getFavourCover())){
                        if (info.getFavourCover().startsWith("upload")) {
                            info.setFavourCover(Constants.URL + info.getFavourCover());
                        }else {
                            info.setFavourCover(Constants.QINIU_URL + info.getFavourCover() + "-yasuoone");
                        }
                    }
                }
            }
        }
        return lists;
    }

}
