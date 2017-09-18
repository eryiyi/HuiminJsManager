package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.ReportDao;
import com.liangxunwang.unimanager.model.Report;
import com.liangxunwang.unimanager.query.ReportQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reportService")
public class ReportService implements ListService,SaveService,DeleteService,ExecuteService,UpdateService {
    @Autowired
    @Qualifier("reportDao")
    private ReportDao reportDao;

    @Override
    public Object list(Object object) throws ServiceException {
        ReportQuery query = (ReportQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getIs_read())){
            map.put("is_read", query.getIs_read());
        }
        List<Report> lists = reportDao.lists(map);
        long count = reportDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        Report report = (Report) object;
        report.setReportid(UUIDFactory.random());
        report.setDateline(System.currentTimeMillis() + "");
        report.setIs_read("0");
        reportDao.save(report);
        return 200;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String mm_suggest_id = (String) object;
        reportDao.delete(mm_suggest_id);
        return 200;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return reportDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        Report report = (Report) object;
        reportDao.update(report);
        return 200;
    }
}
