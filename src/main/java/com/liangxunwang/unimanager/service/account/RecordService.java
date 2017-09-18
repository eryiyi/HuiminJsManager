package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.RecordDao;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("recordService")
public class RecordService implements ListService,DeleteService,ExecuteService,UpdateService {
    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object list(Object object) throws ServiceException {
        RecordQuery query = (RecordQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getRecord_use())){
            map.put("record_use", query.getRecord_use());
        }
        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }
        if(!StringUtil.isNullOrEmpty(query.getRecord_type())){
            map.put("record_type", query.getRecord_type());
        }
        List<RecordVO> lists = recordDao.lists(map);
        long count = recordDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object delete(Object object) throws ServiceException {
        String record_id = (String) object;
        recordDao.delete(record_id);
        return 200;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return recordDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        Record record = (Record) object;
        recordDao.update(record);
        return 200;
    }
}
