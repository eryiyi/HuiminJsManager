package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("memberUpdatePwrByIdService")
public class MemberUpdatePwrByIdService implements UpdateService {
    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object update(Object object) {
        Emp member = (Emp) object;
        if(!StringUtil.isNullOrEmpty(member.getPassword())){
            empDao.resetPass(member.getEmpid(), member.getPassword());
        }
        if(!StringUtil.isNullOrEmpty(member.getEmp_pay_pass())){
            empDao.resetPayPass(member);
        }
        return null;
    }

}
