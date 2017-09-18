package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("appEmpUpdatePwrByMobile")
public class AppEmpUpdatePwrByMobile implements UpdateService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;

        if(emp == null){
            throw new ServiceException("null");
        }
        //先查询手机号是否存在
        Emp emp1 = empDao.findByMobile(emp.getMobile());
        if(emp1 == null){
            throw new ServiceException("null");
        }
        empDao.updatePassByMobile(emp.getMobile(), new MD5Util().getMD5ofStr(emp.getPassword()));
        return 200;
    }
}
