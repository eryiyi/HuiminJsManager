package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.MD5Util;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("appEmpUpdatePwrById")
public class AppEmpUpdatePwrById implements UpdateService {

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
        empDao.updatePass(emp.getEmpid(), new MD5Util().getMD5ofStr(emp.getPassword()));
        return 200;
    }
}
