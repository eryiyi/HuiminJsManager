package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandNotice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("noticeDao")
public interface NoticeDao {
    /**
     * 查询公告
     */
    List<HappyHandNotice> lists(Map<String, Object> map);
    long count(Map<String, Object> map);

    //保存
    void save(HappyHandNotice notice);

    /**
     * 根据ID查找
     * @param mm_fuwu_id
     * @return
     */
    public HappyHandNotice findById(String mm_fuwu_id);

}
