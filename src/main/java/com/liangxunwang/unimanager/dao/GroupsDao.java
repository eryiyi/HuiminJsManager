package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("groupsDao")
public interface GroupsDao {

    List<HappyHandGroup> lists(Map<String, Object> map);


    long count(Map<String, Object> map);


    //保存
    void save(HappyHandGroup happyHandGroup);


    //删除
    void delete(String groupid);

    /**
     * 根据ID查找
     */
    public HappyHandGroup findById(String groupid);


    void update(HappyHandGroup happyHandGroup);

    public HappyHandGroup findByLikeId(String likeid);

    List<HappyHandGroup> listsByLikeIds(Map<String, Object> map);

    List<HappyHandGroup> listsPublic(Map<String, Object> map);

    List<HappyHandGroup> listsSearch(Map<String, Object> map);
}
