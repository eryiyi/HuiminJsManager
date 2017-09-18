package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandNews;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("newsDao")
public interface NewsDao {
    /**
     * 查询公告
     */
    List<HappyHandNews> lists(Map<String, Object> map);
    long count(Map<String, Object> map);

    //保存
    void save(HappyHandNews notice);

    /**
     * 根据ID查找
     * @param mm_fuwu_id
     * @return
     */
    public HappyHandNews findById(String mm_fuwu_id);

}
