package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.query.DianpuFavourQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("fensiService")
public class FensiService implements ListService,ExecuteService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object list(Object object) throws ServiceException {
        DianpuFavourQuery query = (DianpuFavourQuery) object;
        Map<String, Object> map1 = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmp_id())){
            map1.put("param1", query.getEmp_id());
        }
        empDao.listAllFensi(map1);
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();


        String contion = " tmpLst,lx_emp where tmpLst.emp_id=lx_emp.emp_id";
        map.put("param1", query.getIndex() );
        map.put("param2", size);
        map.put("param3", "tmpLst.*,lx_emp.*");
        map.put("param4", contion);
        map.put("param5", "");
        map.put("param6", "@RECODE");

        List<Emp> lists = empDao.findAll(map);
        if(lists != null){
            for(Emp member:lists){
                if (member.getCover().startsWith("upload")) {
                    member.setCover(Constants.URL + member.getCover());
                }else {
                    member.setCover(Constants.QINIU_URL + member.getCover());
                }
            }
        }
//        long count = memberDao.countFensi(map);

        Map<String,Object> mapFensi = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmp_id())){
            mapFensi.put("param1", query.getEmp_id());
        }
        List<Emp> listFensi =  empDao.listAllFensi(mapFensi);
        long count = 0;
        if(listFensi != null){
            count = listFensi.size();
        }
        return new Object[]{lists, count};
    }


    @Override
    public Object execute(Object object) throws Exception {
        String emp_id = (String) object;
        Map<String, Object> map1 = new HashMap<String, Object>();
        Emp member = empDao.findById(emp_id);
        List<Emp>  list = new ArrayList<Emp>();
        if("0".equals(member.getIs_card_emp())){
            //不是定向卡会员
            if(!StringUtil.isNullOrEmpty(emp_id)){
                map1.put("emp_up", emp_id);
            }
            list =  empDao.listAllFensiEmp(map1);
        }else{
            //定向卡会员
            if(!StringUtil.isNullOrEmpty(emp_id)){
                map1.put("param1", emp_id);
            }
            list =  empDao.listAllFensi(map1);
        }

        return list;
    }
}
