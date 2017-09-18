package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Report;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("reportDao")
public interface ReportDao {

    /**
     * 查询建议
     */
    List<Report> lists(Map<String, Object> map);
    long count(Map<String, Object> map);


    //保存
    void save(Report report);


    //删除
    void delete(String reportid);

    /**
     * 根据ID查找
     * @param reportid
     * @return
     */
    public Report findById(String reportid);

    void update(Report report);

}
