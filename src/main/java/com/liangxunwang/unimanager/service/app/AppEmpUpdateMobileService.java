package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("appEmpUpdateMobileService")
public class AppEmpUpdateMobileService implements UpdateService,ExecuteService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;


    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;

        if(emp == null){
            throw new ServiceException("null");
        }
        if(StringUtil.isNullOrEmpty(emp.getEmpid())){
            throw new ServiceException("empidnull");
        }
        empDao.updateMobile(emp.getEmpid(), emp.getMobile());
        return 200;
    }

    @Override
    public Object execute(Object object) throws Exception {
        Emp emp = (Emp) object;
        if(StringUtil.isNullOrEmpty(emp.getEmpid())){
            throw new ServiceException("empidnull");
        }
        if(StringUtil.isNullOrEmpty(emp.getLat())){
            throw new ServiceException("lcoationnull");
        }
        if(StringUtil.isNullOrEmpty(emp.getLng())){
            throw new ServiceException("lcoationnull");
        }
        empDao.updateLocation(emp);
        return 200;
    }
}
