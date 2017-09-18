package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandJw;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("jiaowangDao")
public interface JiaowangDao {
    //保存
    void save(HappyHandJw happyHandJw);

    /**
     * 根据ID查找
     */
    public HappyHandJw findById(String jwid);

    void update(HappyHandJw happyHandJw);


    List<HappyHandJw> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    void delete(HappyHandJw happyHandJw);
    void deleteById(HappyHandJw happyHandJw);

    void updateOther(HappyHandJw happyHandJw);
}
