package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.alipay.AlipayConfig;
import com.liangxunwang.unimanager.alipay.OrderInfoUtil2_0;
import com.liangxunwang.unimanager.dao.AppOrderMakeDao;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.HyrzDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.*;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/14.
 */
@Service("appOrderMakeService1")
public class AppOrderMakeService1 implements UpdateService,FindService {
    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeSaveDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;


    @Autowired
    @Qualifier("hyrzDao")
    private HyrzDao hyrzDao;

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    //更新订单状态
    @Override
    public Object update(Object object) {
        if (object instanceof Order){
            //跟新主订单和分订单状态
            Order order = (Order) object;
            List<Order> orders = appOrderMakeSaveDao.findOrderByTradeNo(order.getOut_trade_no());
            if(orders != null && orders.size() > 0){
                Order order1 = orders.get(0);
                if(order1 != null){
                    if(!"2".equals(order1.getStatus())){
                        //更新主订单
                        appOrderMakeSaveDao.updateTradeById(order.getOut_trade_no());
                        order.setPay_time(System.currentTimeMillis() + "");
                        //更新分订单
                        appOrderMakeSaveDao.updateOrderById(order);
                        //更新会员到期日期和会员认证状态
                        empDao.updateRzstate2(order1.getEmp_id(), "1");

                        Map<String, Object> maphyrz = new HashMap<>();
                        maphyrz.put("empid", order1.getEmp_id());
                        maphyrz.put("index", 0);
                        maphyrz.put("size", 10);
                        List<HappyHandHyrz> lists = hyrzDao.lists(maphyrz);
                        if(lists != null && lists.size()>0)
                        {
                            //说明数据库有认证的数据  更新
                            HappyHandHyrz happyHandHyrz =lists.get(0);
                            Object[] ob = DateUtil.getDayInterval(System.currentTimeMillis(), -365);
                            long endtime = (long) ob[0];
                            happyHandHyrz.setEndtime(String.valueOf(endtime));

                            happyHandHyrz.setIs_use("1");
                            hyrzDao.update(happyHandHyrz);
                        }else{
                            //添加会员认证数据
                            HappyHandHyrz happyHandHyrz = new HappyHandHyrz();
                            Object[] ob = DateUtil.getDayInterval(System.currentTimeMillis(), -365);
                            long endtime = (long) ob[0];
                            happyHandHyrz.setEndtime(String.valueOf(endtime));

                            happyHandHyrz.setIs_use("1");
                            happyHandHyrz.setStarttime(System.currentTimeMillis() + "");
                            happyHandHyrz.setEmpid(order1.getEmp_id());
                            happyHandHyrz.setHyrzid(UUIDFactory.random());
                            hyrzDao.save(happyHandHyrz);
                        }
                        //会员认证成功之后，发送系统消息
                        //todo
                        Emp emp = empDao.findById(order1.getEmp_id());
                        if(emp != null){
                            HappyHandMessage happyHandMessage = new HappyHandMessage();
                            happyHandMessage.setMsgid(UUIDFactory.random());
                            happyHandMessage.setDateline(System.currentTimeMillis() + "");
                            happyHandMessage.setTitle("恭喜你成为认证会员!");
                            happyHandMessage.setEmpid(order1.getEmp_id());
                            messagesDao.save(happyHandMessage);

                            if(!StringUtil.isNullOrEmpty(emp.getChannelId())){
                                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp.getDeviceType()), "系统消息", "恭喜你成为认证会员!", "1", emp.getChannelId());
                            }
                        }
                    }
                }
            }

        }
        return null;
    }


    @Override
    public Object findById(Object object) throws ServiceException {
        String order_no = (String) object;
        Order record = appOrderMakeSaveDao.findOrderByOrderNo(order_no);

        if(!StringUtil.isNullOrEmpty(record.getCreate_time())){
            record.setCreate_time(RelativeDateFormat.format(Long.parseLong(record.getCreate_time())));
        }
        if(!StringUtil.isNullOrEmpty(record.getPay_time())){
            record.setPay_time(RelativeDateFormat.format(Long.parseLong(record.getPay_time())));
        }
        return record;
    }
}
