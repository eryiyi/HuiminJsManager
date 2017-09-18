package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandPhoto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("photosDao")
public interface PhotosDao {

    /**
     * 查询相册
     */
    List<HappyHandPhoto> lists(Map<String, Object> map);
    long count(Map<String, Object> map);

    //保存
    void save(HappyHandPhoto happyHandPhoto);

    //删除
    void delete(String photoid);

    /**
     * 根据ID查找
     */
    public HappyHandPhoto findById(String photoid);

    void update(HappyHandPhoto happyHandPhoto);

    List<HappyHandPhoto> findByEmpid(Map<String, Object> map);

    List<HappyHandPhoto> listsAll(Map<String, Object> map);
}
