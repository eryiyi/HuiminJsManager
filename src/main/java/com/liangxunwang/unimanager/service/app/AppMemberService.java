package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpDianpu;
import com.liangxunwang.unimanager.query.MemberQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/31.
 */
@Service("appMemberService")
public class AppMemberService implements ListService,UpdateService,FindService {
    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object list(Object object) throws ServiceException {
        MemberQuery query = (MemberQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);

        if (!StringUtil.isNullOrEmpty(query.getEmp_typeid())) {
            map.put("emp_typeid", query.getEmp_typeid());
        }
        if (!StringUtil.isNullOrEmpty(query.getSchool_id())) {
            map.put("school_id", query.getSchool_id());
        }
        if (!StringUtil.isNullOrEmpty(query.getKeyWords())) {
            map.put("keyWords", query.getKeyWords());
        }
        if (!StringUtil.isNullOrEmpty(query.getEmp_id())) {
            map.put("emp_id", query.getEmp_id());
        }
        if (!StringUtil.isNullOrEmpty(query.getLat())) {
            map.put("lat", query.getLat());
        }
        if (!StringUtil.isNullOrEmpty(query.getLng())) {
            map.put("lng", query.getLng());
        }
        List<EmpDianpu> list = empDao.listDianPu(map);
        for(EmpDianpu empDianpu : list){
            if(!StringUtil.isNullOrEmpty(empDianpu.getCover())){
                if (empDianpu.getCover().startsWith("upload")) {
                    empDianpu.setCover(Constants.URL + empDianpu.getCover());
                }else {
                    empDianpu.setCover(Constants.QINIU_URL + empDianpu.getCover());
                }
            }
               if(!StringUtil.isNullOrEmpty(empDianpu.getCompany_pic())){
                   if (empDianpu.getCompany_pic().startsWith("upload")) {
                       empDianpu.setCompany_pic(Constants.URL + empDianpu.getCompany_pic());
                   }else {
                       empDianpu.setCompany_pic(Constants.QINIU_URL + empDianpu.getCompany_pic());
                   }
               }
        }
        return list;
    }


    @Override
    public Object update(Object object) {
        Emp member = (Emp) object;
        empDao.modifyMemberSex(member);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        return empDao.findById((String) object);
    }
}
