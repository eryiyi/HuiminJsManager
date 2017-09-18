package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.EmpKu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("empKuDao")
public interface EmpKuDao {
    void add(EmpKu emp);

    List<EmpKu> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    EmpKu findByMobile(String mobile);

    void saveList(List<EmpKu> list);

    void delete(String empkuid);
}
