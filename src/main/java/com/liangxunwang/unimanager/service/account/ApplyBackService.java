package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.ApplyBackDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.ApplyBack;
import com.liangxunwang.unimanager.query.ApplyBackQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("applyBackService")
public class ApplyBackService implements ListService,SaveService,DeleteService,ExecuteService,UpdateService {
    @Autowired
    @Qualifier("applyBackDao")
    private ApplyBackDao applyBackDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object list(Object object) throws ServiceException {
        ApplyBackQuery query = (ApplyBackQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getIs_read())){
            map.put("is_read", query.getIs_read());
        }
        List<ApplyBack> lists = applyBackDao.lists(map);
        long count = applyBackDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        ApplyBack report = (ApplyBack) object;
        ApplyBack applyBack = applyBackDao.findByEmpId(report.getEmpid());
        if(applyBack != null){
            return 1;
        }else {
            report.setApplyid(UUIDFactory.random());
            report.setApplytime(System.currentTimeMillis() + "");
            report.setIs_check("0");
            applyBackDao.save(report);
            return 200;
        }

    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String applyid = (String) object;
        applyBackDao.delete(applyid);
        return 200;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return applyBackDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        ApplyBack applyBack = (ApplyBack) object;
        ApplyBack applyBack1 = applyBackDao.findById(applyBack.getApplyid());

        applyBack.setDonetime(System.currentTimeMillis()+"");
        applyBackDao.update(applyBack);


        return 200;
    }
}
