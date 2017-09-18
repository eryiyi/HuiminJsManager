package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.MD5Util;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by zhl on 2015/8/12.
 */
@Service("empUpdatePwrService")
public class EmpUpdatePwrService implements UpdateService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;
        if(!StringUtil.isNullOrEmpty(emp.getPassword())){
            emp.setPassword(new MD5Util().getMD5ofStr(emp.getPassword()));//密码加密
        }
        empDao.updatePass(emp.getEmpid(), emp.getPassword());
        return 200;
    }

}
