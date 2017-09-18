package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("appEmpSecondService")
public class AppEmpSecondService implements UpdateService {

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
        empDao.updateProfile(emp);

        Emp emp1 = empDao.findById(emp.getEmpid());
        if(!StringUtil.isNullOrEmpty(emp1.getCover())){
            if (emp1.getCover().startsWith("upload")) {
                emp1.setCover(Constants.URL + emp1.getCover());
            }else {
                emp1.setCover(Constants.QINIU_URL + emp1.getCover());
            }
        }
        if(!StringUtil.isNullOrEmpty(emp1.getPname())){
            emp1.setPname(emp1.getPname().trim());
        }
        return emp1;
    }
}
