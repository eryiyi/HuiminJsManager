package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.SuggestObj;
import com.liangxunwang.unimanager.mvc.vo.SuggestVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("suggestDao")
public interface SuggestDao {

    /**
     * 查询建议
     */
    List<SuggestVO> lists(Map<String, Object> map);
    long count(Map<String, Object> map);


    //保存
    void save(SuggestObj suggestObj);


    //删除
    void delete(String mm_suggest_id);

    /**
     * 根据ID查找
     * @param mm_suggest_id
     * @return
     */
    public SuggestVO findById(String mm_suggest_id);


    void update(SuggestObj suggestObj);

}
