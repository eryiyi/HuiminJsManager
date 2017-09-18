package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.EmpGroupManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("empGroupsManagerDao")
public interface EmpGroupsManagerDao {
    /**
     * 查询
     */
    List<EmpGroupManager> lists(Map<String, Object> map);

    //保存
    void save(EmpGroupManager empGroupManager);

    void delete(EmpGroupManager empGroupManager);

    void deleteByGroupid(String groupid);

    EmpGroupManager findById(String groupid);
}
