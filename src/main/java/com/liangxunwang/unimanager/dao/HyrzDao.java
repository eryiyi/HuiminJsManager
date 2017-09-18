package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandHyrz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("hyrzDao")
public interface HyrzDao {

    /**
     * 查询
     */
    List<HappyHandHyrz> lists(Map<String, Object> map);
    long count(Map<String, Object> map);
    //保存
    void save(HappyHandHyrz happyHandHyrz);

    /**
     * 更新
     * @param happyHandHyrz
     */
    public void update(HappyHandHyrz happyHandHyrz);


    public void updateOverTime(String nowTime);
    public  List<HappyHandHyrz>  listVipEnd(String nowTime);

    List<HappyHandHyrz> listsEmpEndDate(Map<String, Object> map);

    long countDay(Map<String, Object> map);

}
