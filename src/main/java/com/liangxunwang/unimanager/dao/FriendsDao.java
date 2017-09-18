package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Friends;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("friendsDao")
public interface FriendsDao {
    //保存
    void save(Friends friends);

    /**
     * 根据ID查找
     */
    public Friends findById(String friendsid);

    void update(Friends friends);

    List<Friends> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    void delete(Friends friendsid);

    void deleteById(Friends friendsid);

    void deleteByEmpId(Friends friendsid);
}
