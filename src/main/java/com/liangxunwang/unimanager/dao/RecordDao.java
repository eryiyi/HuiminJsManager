package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("recordDao")
public interface RecordDao {

    List<RecordVO> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    void save(Record record);

    void delete(String record_id);

    RecordVO findById(String record_id);

    void update(Record record_id);

}
