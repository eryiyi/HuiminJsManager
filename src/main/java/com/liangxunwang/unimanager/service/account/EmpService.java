package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/8/12.
 */
@Service("empService")
public class EmpService implements ExecuteService,ListService,FindService,UpdateService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object execute(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String empid = (String) params[0];
        String password = (String) params[1];
        empDao.updatePass(empid, password);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        EmpQuery query = (EmpQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);
        if(!StringUtil.isNullOrEmpty(query.getIs_use())){
            map.put("is_use", query.getIs_use());
        }
        if(!StringUtil.isNullOrEmpty(query.getSex())){
            map.put("sex", query.getSex());
        }
        if(!StringUtil.isNullOrEmpty(query.getEducation())){
            map.put("education", query.getEducation());
        }
        if(!StringUtil.isNullOrEmpty(query.getMarriage())){
            map.put("marriage", query.getMarriage());
        }
        if(!StringUtil.isNullOrEmpty(query.getState())){
            map.put("state", query.getState());
        }
        if(!StringUtil.isNullOrEmpty(query.getRzstate1())){
            map.put("rzstate1", query.getRzstate1());
        }
        if(!StringUtil.isNullOrEmpty(query.getRzstate2())){
            map.put("rzstate2", query.getRzstate2());
        }
        if(!StringUtil.isNullOrEmpty(query.getRzstate3())){
            map.put("rzstate3", query.getRzstate3());
        }
        if(!StringUtil.isNullOrEmpty(query.getKeywords())){
            map.put("keywords", query.getKeywords());
        }
        if(!StringUtil.isNullOrEmpty(query.getCompany())){
            map.put("company", query.getCompany());
        }
        if(!StringUtil.isNullOrEmpty(query.getTjperson())){
            map.put("tjperson", query.getTjperson());
        }
        if(!StringUtil.isNullOrEmpty(query.getTjmobile())){
            map.put("tjmobile", query.getTjmobile());
        }
        List<Emp> lists = empDao.lists(map);
        if(lists != null){
            for(Emp member:lists){
                if(!StringUtil.isNullOrEmpty(member.getCover())){
                    if (member.getCover().startsWith("upload")) {
                        member.setCover(Constants.URL + member.getCover());
                    }else {
                        //处理七牛云图片，要压缩后的图片
                        member.setCover(Constants.QINIU_URL + member.getCover()+"-yasuoone");
                    }
                }
            }
        }
        long count = empDao.count(map);

        return new Object[]{lists, count};
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        Emp member = empDao.findById((String) object);
        return member;
    }

    @Override
    public Object update(Object object) {
        if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            String empid = (String) params[0];
            String is_use = (String) params[1];
            empDao.updateStatus(empid, is_use);
        }
        return null;
    }

}
