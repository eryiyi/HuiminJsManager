package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.RecordDao;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.*;
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

@Service("appRecordService")
public class AppRecordService implements ListService,SaveService,DeleteService,ExecuteService {
    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

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
        if(lists != null){
            for(RecordVO info:lists){
                if(info != null){
                    if(!StringUtil.isNullOrEmpty(info.getRecord_dateline())){
                        info.setRecord_dateline(RelativeDateFormat.format(Long.valueOf(info.getRecord_dateline())));
                    }
                    if(!StringUtil.isNullOrEmpty(info.getCover())){
                        if (info.getCover().startsWith("upload")) {
                            info.setCover(Constants.URL + info.getCover());
                        }else {
                            info.setCover(Constants.QINIU_URL + info.getCover()+"-yasuoone");
                        }
                    }
                    if(!StringUtil.isNullOrEmpty(info.getRecord_pic())){
                        String[] arras = info.getRecord_pic().split(",");
                        if(arras != null){
                            for(int i=0;i<arras.length;i++){
                                if (arras[i].startsWith("upload")) {
                                    arras[i] = Constants.URL + arras[i];
                                }else {
                                    arras[i] = Constants.QINIU_URL + arras[i];
                                }
                            }
                            StringBuffer stringBuffer = new StringBuffer();
                            for(String str:arras){
                                stringBuffer.append(str+",");
                            }
                            info.setRecord_pic(stringBuffer.toString());
                        }

                    }
                }
            }
        }
        return lists;
    }


    @Override
    public Object save(Object object) throws ServiceException {
        Record record = (Record) object;
        record.setRecord_id(UUIDFactory.random());
        record.setRecord_dateline(System.currentTimeMillis() + "");
        recordDao.save(record);
        return 200;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String record_id = (String) object;
        recordDao.delete(record_id);
        return 200;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
       RecordVO info = recordDao.findById((String) object);
        if(info != null){
            if(!StringUtil.isNullOrEmpty(info.getRecord_dateline())){
                info.setRecord_dateline(RelativeDateFormat.format(Long.valueOf(info.getRecord_dateline())));
            }
            if(!StringUtil.isNullOrEmpty(info.getCover())){
                if (info.getCover().startsWith("upload")) {
                    info.setCover(Constants.URL + info.getCover());
                }else {
                    info.setCover(Constants.QINIU_URL + info.getCover()+"-yasuoone");
                }
            }
            if(!StringUtil.isNullOrEmpty(info.getRecord_pic())){
                String[] arras = info.getRecord_pic().split(",");
                if(arras != null){
                    for(int i=0;i<arras.length;i++){
                        if (arras[i].startsWith("upload")) {
                            arras[i] = Constants.URL + arras[i];
                        }else {
                            arras[i] = Constants.QINIU_URL + arras[i];
                        }
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    for(String str:arras){
                        stringBuffer.append(str+",");
                    }
                    info.setRecord_pic(stringBuffer.toString());
                }

            }
        }
        return info;
    }

}
