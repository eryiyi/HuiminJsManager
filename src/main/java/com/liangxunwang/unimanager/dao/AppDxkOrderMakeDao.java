package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.DxkOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/10.
 */
@Repository("appDxkOrderMakeDao")
public interface AppDxkOrderMakeDao {

    //保存定向卡
    void saveDxkOrder(DxkOrder order);


    //查询该会员是否刷过今天的定向卡订单了
    DxkOrder findIsExist(Map<String, Object> map);

 }
