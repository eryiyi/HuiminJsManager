package com.liangxunwang.unimanager.service.app;


import com.liangxunwang.unimanager.dao.AppOrderMakeDao;
import com.liangxunwang.unimanager.dao.PaopaoGoodsDao;
import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.OrderInfoAndSign;
import com.liangxunwang.unimanager.model.ShoppingTrade;
import com.liangxunwang.unimanager.mvc.vo.OrderVo;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.query.OrdersQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
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
@Service("appOrderMakeService")
public class AppOrderMakeService implements SaveService,UpdateService,ListService,FindService {
//    private static Logger logger = LogManager.getLogger(AppOrderMakeService.class);
    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeSaveDao;

    @Autowired
    @Qualifier("paopaoGoodsDao")
    private PaopaoGoodsDao paopaoGoodsDao;


    //保存订单
    @Override
    public Object save(Object object) throws ServiceException {
        List<Order> lists = (List<Order>) object;
        //先判断商品剩余数量，是否大于要购买的数量
        for(Order order:lists){
//            order.getGoods_count();//订单数量
            if(!StringUtil.isNullOrEmpty(order.getGoods_count())){
                //根据商品ID查询商品数量
                PaopaoGoodsVO vo = paopaoGoodsDao.findGoodsVO(order.getGoods_id());
                if(vo != null){
                    if(!StringUtil.isNullOrEmpty(vo.getCount())){
                        if(Integer.parseInt(vo.getCount()) < Integer.parseInt(order.getGoods_count())){
                            throw new ServiceException("outOfNum");//超出数量限制
                        }
                    }
                }
            }
        }
        Double doublePrices = 0.0;
        if(lists!=null && lists.size() > 0){
            for(Order order:lists){
                doublePrices += order.getPayable_amount();
            }
        }
        doublePrices = Double.parseDouble(String.format("%.2f",doublePrices));
        //商品总额，用于插入订单金额
        String out_trade_no = UUIDFactory.random();//订单总金额的id
        ShoppingTrade shoppingTrade = new ShoppingTrade();
        shoppingTrade.setOut_trade_no(out_trade_no);
        shoppingTrade.setPay_status("0");
        shoppingTrade.setDateline(System.currentTimeMillis() + "");
        shoppingTrade.setTrade_prices(String.valueOf(doublePrices));

        //保存总订单--和支付宝一致
        appOrderMakeSaveDao.saveTrade(shoppingTrade);
        int is_dxk_order = 0;
        //构造订单列表
        if(lists!=null && lists.size() > 0){
            for(Order order:lists){
                if("0".equals(order.getIs_dxk_order())){
                    is_dxk_order = 0;//普通订单
                }
                if("2".equals(order.getIs_dxk_order())){
                    is_dxk_order = 2;//充值定向卡的
                }
                order.setOrder_no(UUIDFactory.random());
                order.setCreate_time(System.currentTimeMillis() + "");
                order.setStatus("1");//1生成订单
                order.setPay_status("0");//0未支付
                order.setOut_trade_no(out_trade_no);
                order.setIsAccount("0");
                //自动签收时间--accept_time
//                order.setAccept_time(DateUtil.beforLongDate(System.currentTimeMillis(),7));
            }
        }
        //保存订单
        for(Order order:lists){
            appOrderMakeSaveDao.saveList(order);
        }

        //商品数量要减去已购买的数量
        for(Order order:lists){
//            order.getGoods_count();//订单数量
            if(!StringUtil.isNullOrEmpty(order.getGoods_id())){
                PaopaoGoodsVO paopaoGoods = paopaoGoodsDao.findGoodsVO(order.getGoods_id());
                if(paopaoGoods != null){
                    paopaoGoodsDao.updateCountById(order.getGoods_id(), order.getGoods_count(), order.getGoods_count());
                }
            }
        }

        String notify_url = "";
        if(is_dxk_order == 0){
            notify_url =  Constants.ZFB_NOTIFY_URL;
        }else if(is_dxk_order == 2){
            //定向卡充值
            notify_url = Constants.ZFB_NOTIFY_URL_DXK;
        }else{
            notify_url = Constants.ZFB_NOTIFY_URL;
        }

        //todo
        String orderInfo = StringUtil.getOrderInfo(out_trade_no, "yayabaojian", "yayabaojian_pay", String.valueOf(doublePrices), notify_url);
        // 对订单做RSA 签名
        String sign = StringUtil.sign(orderInfo);

        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
            return new OrderInfoAndSign(orderInfo, sign, out_trade_no);
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("ISWRONG");
        }
    }

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
                        appOrderMakeSaveDao.updateTradeById(order.getOut_trade_no());
                        order.setPay_time(System.currentTimeMillis() + "");
                        appOrderMakeSaveDao.updateOrderById(order);
                    }
                }
            }
        }else {
            //支付单一订单成功，更新分订单状态
            String order_no = (String) object;
            String pay_time =  System.currentTimeMillis() + "";
            appOrderMakeSaveDao.updateOrderBySingle(order_no,pay_time);
        }
        return null;
    }

    //查询订单列表
    @Override
    public Object list(Object object) throws ServiceException {
        OrdersQuery query = (OrdersQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getEmpId())) {
            map.put("emp_id", query.getEmpId());
        }
        if (!StringUtil.isNullOrEmpty(query.getStatus())) {
            map.put("status", query.getStatus());
        }
        if("0".equals(query.getEmptype())){
            //会员查询自己的订单
//            private String completion_time;//订单完成时间

            List<OrderVo> list = appOrderMakeSaveDao.listOrders(map);
            for (OrderVo record : list){
                if(!StringUtil.isNullOrEmpty(record.getCreate_time())){
                    record.setCreate_time(RelativeDateFormat.format(Long.parseLong(record.getCreate_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getPay_time())){
                    record.setPay_time(RelativeDateFormat.format(Long.parseLong(record.getPay_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getAccept_time())){
                    record.setAccept_time(RelativeDateFormat.format(Long.parseLong(record.getAccept_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getSend_time())){
                    record.setSend_time(RelativeDateFormat.format(Long.parseLong(record.getSend_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getCompletion_time())){
                    record.setCompletion_time(RelativeDateFormat.format(Long.parseLong(record.getCompletion_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getEmpCover())){
                    if (record.getEmpCover().startsWith("upload")) {
                        record.setEmpCover(Constants.URL + record.getEmpCover());
                    }else {
                        record.setEmpCover(Constants.QINIU_URL + record.getEmpCover());
                    }
                }
                if(!StringUtil.isNullOrEmpty(record.getGoodsCover())){
                    if (record.getGoodsCover().startsWith("upload")) {
                        record.setGoodsCover(Constants.URL + record.getGoodsCover());
                    }else {
                        record.setGoodsCover(Constants.QINIU_URL + record.getGoodsCover());
                    }
                }

            }
            return list;
        }
        if("2".equals(query.getEmptype())){
            //商家查询自己管理的订单
            List<OrderVo> list = appOrderMakeSaveDao.listOrdersSj(map);
            for (OrderVo record : list){
                if(!StringUtil.isNullOrEmpty(record.getCreate_time())){
                    record.setCreate_time(RelativeDateFormat.format(Long.parseLong(record.getCreate_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getPay_time())){
                    record.setPay_time(RelativeDateFormat.format(Long.parseLong(record.getPay_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getAccept_time())){
                    record.setAccept_time(RelativeDateFormat.format(Long.parseLong(record.getAccept_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getSend_time())){
                    record.setSend_time(RelativeDateFormat.format(Long.parseLong(record.getSend_time())));
                }
                if(!StringUtil.isNullOrEmpty(record.getCompletion_time())){
                    record.setCompletion_time(RelativeDateFormat.format(Long.parseLong(record.getCompletion_time())));
                }
                if (record.getEmpCover().startsWith("upload")) {
                    record.setEmpCover(Constants.URL + record.getEmpCover());
                }else {
                    record.setEmpCover(Constants.QINIU_URL + record.getEmpCover());
                }
                record.setGoodsCover(Constants.URL + record.getGoodsCover());
            }
            return list;
        }
        return null;
    }


    @Override
    public Object findById(Object object) throws ServiceException {
        String order_no = (String) object;
        OrderVo record = appOrderMakeSaveDao.findOrderByOrderNo(order_no);

        if(!StringUtil.isNullOrEmpty(record.getCreate_time())){
            record.setCreate_time(RelativeDateFormat.format(Long.parseLong(record.getCreate_time())));
        }
        if(!StringUtil.isNullOrEmpty(record.getPay_time())){
            record.setPay_time(RelativeDateFormat.format(Long.parseLong(record.getPay_time())));
        }
        if(!StringUtil.isNullOrEmpty(record.getAccept_time())){
            record.setAccept_time(RelativeDateFormat.format(Long.parseLong(record.getAccept_time())));
        }
        if(!StringUtil.isNullOrEmpty(record.getSend_time())){
            record.setSend_time(RelativeDateFormat.format(Long.parseLong(record.getSend_time())));
        }
        if(!StringUtil.isNullOrEmpty(record.getCompletion_time())){
            record.setCompletion_time(RelativeDateFormat.format(Long.parseLong(record.getCompletion_time())));
        }

        if (record.getEmpCover().startsWith("upload")) {
            record.setEmpCover(Constants.URL + record.getEmpCover());
        }else {
            record.setEmpCover(Constants.QINIU_URL + record.getEmpCover());
        }

        if (record.getGoodsCover().startsWith("upload")) {
            record.setGoodsCover(Constants.URL + record.getGoodsCover());
        }else {
            record.setGoodsCover(Constants.QINIU_URL + record.getGoodsCover());
        }

        return record;
    }
}
