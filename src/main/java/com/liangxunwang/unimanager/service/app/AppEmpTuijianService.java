package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.query.PersonQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("appEmpTuijianService")
public class AppEmpTuijianService implements ListService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PersonQuery query = (PersonQuery) object;
        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("index", 0);
//        map.put("size", 10);

        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getKeywords())){
            map.put("keywords", query.getKeywords());
        }

        if(!StringUtil.isNullOrEmpty(query.getProvinceid())){
            map.put("provinceid", query.getProvinceid());
        }
        if(!StringUtil.isNullOrEmpty(query.getCityid())){
            map.put("cityid", query.getCityid());
        }
        if(!StringUtil.isNullOrEmpty(query.getRolestate())){
            if("0".equals(query.getRolestate())){
                //是技师 我们要查询会员
                map.put("rolestate", "1");
            }
            if("1".equals(query.getRolestate())){
                //是会员 我们要查询技师
                map.put("rolestate", "0");
            }
        }

        if (!StringUtil.isNullOrEmpty(query.getLat())) {
            map.put("lat", query.getLat());
        }
        if (!StringUtil.isNullOrEmpty(query.getLng())) {
            map.put("lng", query.getLng());
        }

        List<Emp> list1 = empDao.listsSearchTuijian(map);


        if(list1 != null){
            for(Emp member:list1){
                if(!StringUtil.isNullOrEmpty(member.getCover())){
                    if (member.getCover().startsWith("upload")) {
                        member.setCover(Constants.URL + member.getCover());
                    }else {
                        member.setCover(Constants.QINIU_URL + member.getCover()+"-yasuoone");
                    }
                }
                if(!StringUtil.isNullOrEmpty(member.getPname())){
                    member.setPname(member.getPname().trim());
                }
            }
        }
        return list1;
    }

}
