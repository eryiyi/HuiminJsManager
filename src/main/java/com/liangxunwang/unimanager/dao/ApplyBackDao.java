package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.ApplyBack;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("applyBackDao")
public interface ApplyBackDao {

    List<ApplyBack> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    void save(ApplyBack applyBack);

    void delete(String applyid);

    ApplyBack findById(String applyid);

    void update(ApplyBack applyBack);

    ApplyBack findByEmpId(String applyid);

}
