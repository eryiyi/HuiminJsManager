package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.LoginEmpDao;
import com.liangxunwang.unimanager.model.LoginEmp;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("appEmpThreeService")
public class AppEmpThreeService implements UpdateService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("loginEmpDao")
    private LoginEmpDao loginEmpDao;


    @Override
    public Object update(Object object) {
        String empid = (String) object;
        //更新退出状态
        LoginEmp loginEmp = new LoginEmp();
        loginEmp.setIs_login("1");
        loginEmp.setEmpid(empid);
        loginEmp.setLogindate(DateUtil.getDateAndTimeTwo1());
        loginEmpDao.update(loginEmp);
        return 200;
    }
}
