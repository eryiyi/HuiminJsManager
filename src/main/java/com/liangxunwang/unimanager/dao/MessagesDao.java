package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("messagesDao")
public interface MessagesDao {
    /**
     * 查询
     */
    List<HappyHandMessage> lists(Map<String, Object> map);
    long count(Map<String, Object> map);

    //保存
    void save(HappyHandMessage happyHandMessage);

    /**
     * 根据ID查找
     * @param msgid
     * @return
     */
    public HappyHandMessage findById(String msgid);

    void updateRead(String msgid);


}
