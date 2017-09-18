package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.CityDao;
import com.liangxunwang.unimanager.model.City;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("cityService")
public class CityService implements ListService {

    @Autowired
    @Qualifier("cityDao")
    private CityDao cityDao;

    @Override
    public Object list(Object object) throws ServiceException {
        String provinceid = (String) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(provinceid)){
            map.put("areaid", provinceid);
        }
        List<City> lists = cityDao.list(map);
        return lists;
    }


}
