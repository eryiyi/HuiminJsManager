package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.HyrzDao;
import com.liangxunwang.unimanager.model.HappyHandHyrz;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/8/12.
 */
@Service("empHyrzService")
public class EmpHyrzService implements ListService {

    @Autowired
    @Qualifier("hyrzDao")
    private HyrzDao hyrzDao;

    @Override
    public Object list(Object object) throws ServiceException {
        EmpQuery query = (EmpQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);
        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }if(!StringUtil.isNullOrEmpty(query.getIs_use())){
            map.put("is_use", query.getIs_use());
        }
        if(!StringUtil.isNullOrEmpty(query.getIs_select())){
            if("1".equals(query.getIs_select())){
                //到期一个月以上的会员认证
//                当前日期减去30天的毫秒值 大于到期日期
                Object[] ob = DateUtil.getDayInterval(System.currentTimeMillis(), 30);//默认设置3天  正式上线改成30
                long endtime = (long) ob[0];
                map.put("endtime", String.valueOf(endtime));
            }
        }
        List<HappyHandHyrz> lists = hyrzDao.lists(map);
        long count = hyrzDao.count(map);
        return new Object[]{lists, count};
    }


}
