package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/31.
 */
@Service("memberFensiAllService")
public class MemberFensiAllService implements FindService {
    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object findById(Object object) throws ServiceException {
        String emp_id = (String) object;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("param1", emp_id);
        List<Emp> lists =  empDao.listAllFensi(map);
        return lists;
    }
}
