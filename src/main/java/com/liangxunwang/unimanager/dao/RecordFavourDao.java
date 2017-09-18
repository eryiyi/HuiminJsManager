package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.RecordFavour;
import com.liangxunwang.unimanager.mvc.vo.RecordFavourVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("recordFavourDao")
public interface RecordFavourDao {

    List<RecordFavourVO> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    void save(RecordFavour recordFavour);

    List<RecordFavourVO> findByEmpId(Map<String, Object> map);

}
