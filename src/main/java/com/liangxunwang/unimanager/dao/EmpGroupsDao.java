package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.EmpGroups;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("empGroupsDao")
public interface EmpGroupsDao {
    /**
     * 查询
     */
    List<EmpGroups> lists(Map<String, Object> map);
    //保存
    void save(EmpGroups aboutUs);

    List<EmpGroups> findById(Map<String, Object> map);

    void delete(EmpGroups empGroups);
}
