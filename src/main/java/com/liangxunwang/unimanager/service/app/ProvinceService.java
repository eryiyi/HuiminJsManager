package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.ProvinceDao;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("provinceService")
public class ProvinceService implements ListService {

    @Autowired
    @Qualifier("provinceDao")
    private ProvinceDao provinceDao;

    @Override
    public Object list(Object object) throws ServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Province> lists = provinceDao.list();
        for(int i=0;i<lists.size();i++){
            lists.get(i).setPname(lists.get(i).getPname().trim());
        }
        return lists;
    }


}
