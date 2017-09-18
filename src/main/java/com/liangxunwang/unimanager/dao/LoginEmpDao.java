package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.LoginEmp;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository("loginEmpDao")
public interface LoginEmpDao {

    //保存
    void save(LoginEmp loginEmp);

    /**
     * 更新
     */
    public void update(LoginEmp loginEmp);


    long count(Map<String, Object> map);

    public void updateLogout(LoginEmp loginEmp);


}
