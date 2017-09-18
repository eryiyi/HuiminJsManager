package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.LoginEmpDao;
import com.liangxunwang.unimanager.model.LoginEmp;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by zhl on 2015/8/12.
 */
@Service("empLoginService")
public class EmpLoginService implements ExecuteService {
    @Autowired
    @Qualifier("loginEmpDao")
    private LoginEmpDao loginEmpDao;

    @Override
    public Object execute(Object object) throws Exception {
        LoginEmp loginEmp = new LoginEmp();
        loginEmp.setLogindate(DateUtil.getDateAndTimeTwo2());
        loginEmp.setIs_login("1");
        loginEmpDao.updateLogout(loginEmp);
        return 200;
    }
}
