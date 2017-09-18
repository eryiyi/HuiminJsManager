package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.RecordComment;
import com.liangxunwang.unimanager.mvc.vo.RecordCommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("recordCommentDao")
public interface RecordCommentDao {

    List<RecordCommentVO> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    void save(RecordComment recordComment);

}
