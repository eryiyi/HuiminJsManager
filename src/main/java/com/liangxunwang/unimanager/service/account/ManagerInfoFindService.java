package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.AdminDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.ManagerInfoDao;
import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.ManagerInfo;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by zhl on 2015/8/30.
 */
@Service("managerInfoFindService")
public class ManagerInfoFindService implements FindService,UpdateService {

    @Autowired
    @Qualifier("managerInfoDao")
    private ManagerInfoDao managerInfoDao;
    @Override
    public Object findById(Object object) throws ServiceException {
        if (object instanceof String){
            String id = (String) object;
            ManagerInfo managerInfo = managerInfoDao.findByInfoId(id);
            if(!StringUtil.isNullOrEmpty(managerInfo.getDateline())){
                managerInfo.setDateline(DateUtil.getDate(managerInfo.getDateline(), "yyyy-MM-dd HH:mm"));
            }
            return managerInfo;
        }
        return null;
    }


    @Autowired
    @Qualifier("adminDao")
    private AdminDao adminDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object update(Object object) {
        if (object instanceof ManagerInfo){
            ManagerInfo info = (ManagerInfo) object;
            managerInfoDao.updateCheck(info);
            if(!StringUtil.isNullOrEmpty(info.getIs_check()) && "1".equals(info.getIs_check()) && !StringUtil.isNullOrEmpty(info.getEmp_id())){
                //审核通过 添加管理员

                //1现根据emp_id查询emp对象
                Emp member = empDao.findById(info.getEmp_id());
                 if(member != null){
                     Admin admin = new Admin();
                     admin.setManager_id(UUIDFactory.random());
                     admin.setEmp_id(info.getEmp_id());
                     admin.setIs_use("0");
                     admin.setIs_daili("0");
                     admin.setManager_pass(member.getPassword());
                     admin.setManager_admin(member.getMobile());
                     admin.setManager_cover(member.getCover());
                     //然后根据empid查询后台是否已经有这个管理员了
                     Admin admin1 = adminDao.findByEmpId(info.getEmp_id());
                     if(admin1 == null){
                         //说明不存在
                         adminDao.add(admin);
                     }
                 }
                //更新用户emptype
                empDao.updateType("1", info.getEmp_id());
            }
        }
        return null;
    }
}
