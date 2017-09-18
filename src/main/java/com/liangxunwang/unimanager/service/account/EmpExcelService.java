package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.util.CreateSimpleExcelToDisk;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Service("empExcelService")
public class EmpExcelService implements ExecuteService {
    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object execute(Object object) throws Exception {
        Object[] objects = (Object[]) object;
        String ids = (String) objects[0];
        HttpServletRequest request = (HttpServletRequest) objects[1];
        if(!StringUtil.isNullOrEmpty(ids)){
            String[] arrs = ids.split(",");
            //查询这些用户的数据
            List<Emp> empVOs = new ArrayList<Emp>();
            for(int i=0;i<arrs.length;i++){
                Emp empVO = empDao.findInfoById(arrs[i]);
                empVO.setDateline(DateUtil.getDate(empVO.getDateline(), "yyyy-MM-dd HH:mm"));
                empVOs.add(empVO);
            }
            String fileName = CreateSimpleExcelToDisk.toExcelEmp(empVOs,request);
            return fileName;
        }
        return null;
    }


}
