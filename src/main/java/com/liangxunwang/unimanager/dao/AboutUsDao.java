package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandCompany;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("aboutUsDao")
public interface AboutUsDao {

    /**
     * 查询
     */
    List<HappyHandCompany> lists(Map<String, Object> map);

    //保存
    void save(HappyHandCompany aboutUs);



    /**
     * 更新
     * @param aboutUs
     */
    public void update(HappyHandCompany aboutUs);

}
