package com.liangxunwang.unimanager.service.app;


import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.mvc.vo.OrderVo;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/15.
 */
@Service("appOrderService")
public class AppOrderService implements UpdateService,SaveService,ExecuteService {
//    private static Logger logger = LogManager.getLogger(AppOrderService.class);
    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeSaveDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;


    @Override
    public Object update(Object object) {
        Map<String, String> map = (Map<String, String>) object;
        String order_no = map.get("order_no");
        String status = map.get("status");
        String kuaidi_company_code = map.get("kuaidi_company_code");
        String kuaidi_order = map.get("kuaidi_order");
        if("3".equals(status)){
            // 取消订单
            appOrderMakeSaveDao.cancelOrderById(order_no);
        }
        if("4".equals(status)){
            //删除订单
            appOrderMakeSaveDao.deleteOrderById(order_no);
        }
        if("5".equals(status)){
            //买家确认收货，完成订单
            Order order = new Order();
            order.setOrder_no(order_no);
            order.setCompletion_time(System.currentTimeMillis() + "");
            order.setAccept_time(System.currentTimeMillis() + "");
            appOrderMakeSaveDao.sureOrder(order);

            //根据订单ID查询订单详情
            OrderVo ordersVO = appOrderMakeSaveDao.findOrderByOrderNo(order_no);

            //返利
            if(ordersVO != null){
                //--------------------给卖家返利------------------------------
                //销售总额
                Float payable_amount = ordersVO.getPayable_amount();
                //利润
                String pv_amount = ordersVO.getPv_amount()==null?"0":ordersVO.getPv_amount();

                Float jf = payable_amount - Float.valueOf(pv_amount);

                //把订单的钱给卖家
                //增加他的积分
                countDao.updateScore(ordersVO.getSeller_emp_id(), String.valueOf(jf));
                //添加积分变动记录
                CountRecord countRecordSeller = new CountRecord();
                countRecordSeller.setLx_count_record_id(UUIDFactory.random());
                countRecordSeller.setDateline(System.currentTimeMillis() + "");
                countRecordSeller.setEmp_id(ordersVO.getSeller_emp_id());
                countRecordSeller.setLx_count_record_count("+" + String.valueOf(jf));
                countRecordSeller.setLx_count_record_cont(ordersVO.getEmpName()+ "("+ordersVO.getEmpMobile()+")消费订单" + jf + "元");
                countRecordDao.save(countRecordSeller);
                //-----------------------------------------------------
                //查询消费者ID
                String emp_id = ordersVO.getEmp_id();
//                String pv_amount = ordersVO.getPv_amount();//商品的pv在订单中的总和 这是返利用到的（商品pv,pv即利润）
                //根据会员ID查询会员详情
                Emp memberVO = (Emp) empDao.findInfoById(emp_id);
                if(memberVO != null && !StringUtil.isNullOrEmpty(memberVO.getEmp_up())){
                    //根据emp_id查询他的分销返利上级路线
                    //1.查询消费者的--所有上级返利关系 直到店长
                    lists.clear();
                    List<Emp> lists = getListMemberVo(memberVO.getEmp_up());
                    if(lists != null && !StringUtil.isNullOrEmpty(pv_amount) && !"0".equals(pv_amount) && !"0.0".equals(pv_amount)){
                        List<Emp> listsArea = new ArrayList<Emp>();//县级会员 只能放一个
                        List<Emp> listsCity = new ArrayList<Emp>();//市级代理 只能放一个
                        List<Emp> listsProvince = new ArrayList<Emp>();//省级代理 只能放一个
                        List<Emp> listsDianzhang = new ArrayList<Emp>();//店长 只能放一个
                        for(Emp memberVO1:lists){
                            if(listsDianzhang.size()>0){
                                //说明已经返利到店长级别了 返回即可
                                break;
                            }else{
                                //分销等级ID，返利用； 默认0是普通等级，1是普通返利会员  2是店长
                                switch (Integer.parseInt(memberVO1.getLx_attribute_id())){
                                    case 0:
                                    {
                                        //返利会员：省 市 县
                                        if(listsProvince.size()>0){
                                            //说明省会员有了
                                            if(listsCity.size()>0){
                                                //说明有市级会员了
                                                if(listsArea.size()>0){
                                                    //说明有县级会员 不能继续返利了
                                                }else {
                                                    //没有县级会员 可以添加
                                                    LxAttribute lxAttribute = lxAttributeDao.findByNick("3");
                                                    if (!StringUtil.isNullOrEmpty(lxAttribute.getLx_attribute_rate())) {
                                                        Double jifenCount = Double.parseDouble(pv_amount) * (Double.parseDouble(lxAttribute.getLx_attribute_rate()) * 0.01);//增加的积分
                                                        //增加他的积分
                                                        countDao.updateScore(memberVO1.getEmpid(), String.valueOf(jifenCount));
                                                        //添加积分变动记录
                                                        CountRecord countRecord = new CountRecord();
                                                        countRecord.setLx_count_record_id(UUIDFactory.random());
                                                        countRecord.setDateline(System.currentTimeMillis()+"");
                                                        countRecord.setEmp_id(memberVO1.getEmpid());
                                                        countRecord.setLx_count_record_count("+" + String.valueOf(jifenCount));
                                                        countRecord.setLx_count_record_cont(memberVO.getNickname() + "购物消费利润pv" + pv_amount + "元");
                                                        countRecordDao.save(countRecord);
                                                    }
                                                    listsArea.add(memberVO1);
                                                }
                                            }else {
                                                //没有市级会员 可以添加
                                                LxAttribute lxAttribute = lxAttributeDao.findByNick("2");
                                                if (!StringUtil.isNullOrEmpty(lxAttribute.getLx_attribute_rate())) {
                                                    Double jifenCount = Double.parseDouble(pv_amount) * (Double.parseDouble(lxAttribute.getLx_attribute_rate()) * 0.01);//增加的积分
                                                    //增加他的积分
                                                    countDao.updateScore(memberVO1.getEmpid(), String.valueOf(jifenCount));
                                                    //添加积分变动记录
                                                    CountRecord countRecord = new CountRecord();
                                                    countRecord.setLx_count_record_id(UUIDFactory.random());
                                                    countRecord.setDateline(System.currentTimeMillis()+"");
                                                    countRecord.setEmp_id(memberVO1.getEmpid());
                                                    countRecord.setLx_count_record_count("+" + String.valueOf(jifenCount));
                                                    countRecord.setLx_count_record_cont(memberVO.getNickname() + "购物消费利润pv" + pv_amount + "元");
                                                    countRecordDao.save(countRecord);
                                                }
                                                listsCity.add(memberVO1);
                                            }
                                        }else{
                                            //说明没有省会员 可以添加
                                            LxAttribute lxAttribute = lxAttributeDao.findByNick("1");
                                            if (!StringUtil.isNullOrEmpty(lxAttribute.getLx_attribute_rate())) {
                                                Double jifenCount = Double.parseDouble(pv_amount) * (Double.parseDouble(lxAttribute.getLx_attribute_rate()) * 0.01);//增加的积分
                                                //增加他的积分
                                                countDao.updateScore(memberVO1.getEmpid(), String.valueOf(jifenCount));
                                                //添加积分变动记录
                                                CountRecord countRecord = new CountRecord();
                                                countRecord.setLx_count_record_id(UUIDFactory.random());
                                                countRecord.setDateline(System.currentTimeMillis()+"");
                                                countRecord.setEmp_id(memberVO1.getEmpid());
                                                countRecord.setLx_count_record_count("+" + String.valueOf(jifenCount));
                                                countRecord.setLx_count_record_cont(memberVO.getNickname() + "购物消费利润pv" + pv_amount + "元");
                                                countRecordDao.save(countRecord);
                                            }
                                            listsProvince.add(memberVO1);
                                        }
                                    }
                                    break;
                                    case 2:
                                    {
                                        //店长
                                        if(listsDianzhang.size() > 0){
                                            //说明返利到店长级别了
                                            break;
                                        }else {
                                            //说明还没有返利到店长 可以继续返利
                                            LxAttribute lxAttribute = lxAttributeDao.findByNick("4");
                                            if (!StringUtil.isNullOrEmpty(lxAttribute.getLx_attribute_rate())) {
                                                Double jifenCount = Double.parseDouble(pv_amount) * (Double.parseDouble(lxAttribute.getLx_attribute_rate()) * 0.01);//增加的积分
                                                //增加他的积分
                                                countDao.updateScore(memberVO1.getEmpid(), String.valueOf(jifenCount));
                                                //添加积分变动记录
                                                CountRecord countRecord = new CountRecord();
                                                countRecord.setLx_count_record_id(UUIDFactory.random());
                                                countRecord.setDateline(System.currentTimeMillis()+"");
                                                countRecord.setEmp_id(memberVO1.getEmpid());
                                                countRecord.setLx_count_record_count("+" + String.valueOf(jifenCount));
                                                countRecord.setLx_count_record_cont(memberVO.getNickname() + "购物消费利润pv" + pv_amount + "元");
                                                countRecordDao.save(countRecord);
                                            }
                                            listsDianzhang.add(memberVO1);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //根据订单号  查询订单
//            OrderVo record = appOrderMakeSaveDao.findOrderByOrderNo(order_no);
            //通知卖家  已收货
//            Member member =  memberDao.findById(record.getSeller_emp_id());
            //保存相关信息
//            Relate relate1 = new Relate();
//            relate1.setId(UUIDFactory.random());
//            relate1.setEmpId(record.getEmp_id());
//            relate1.setEmpTwoId(record.getSeller_emp_id());
//            relate1.setOrderId(record.getOrder_no());
//            relate1.setTypeId("2");
//            relate1.setDateline(System.currentTimeMillis() + "");
//            relate1.setCont( "订单号：" + order_no+ "，买家已收货");
//            relateDao.save(relate1);
//
//            String pushId =member.getPushId();
//            String type = member.getDeviceType();
//            pushMsg(pushId, type,"订单号：" + order_no+ "，买家已收货");
        }
        if("6".equals(status)){
            //卖家确认发货
            Order order = new Order();
            order.setOrder_no(order_no);
            order.setDistribution_status("1");
            order.setSend_time(System.currentTimeMillis() + "");
            order.setKuaidi_order(kuaidi_order);
            order.setKuaidi_company_code(kuaidi_company_code);
            appOrderMakeSaveDao.sendOrderSj(order);
            //根据订单号  查询订单
//            OrderVo record = appOrderMakeSaveDao.findOrderByOrderNo(order_no);

            //发通知给买家：卖家已发货
//            Member member =  memberDao.findById(record.getEmp_id());
//            //保存相关信息
//            Relate relate1 = new Relate();
//            relate1.setId(UUIDFactory.random());
//            relate1.setEmpId(record.getSeller_emp_id());
//            relate1.setEmpTwoId(record.getEmp_id());
//            relate1.setOrderId(record.getOrder_no());
//            relate1.setTypeId("3");
//            relate1.setDateline(System.currentTimeMillis() + "");
//            relate1.setCont( "订单号：" + order_no+ "，卖家已发货");
//            relateDao.save(relate1);
//
//            String pushId =member.getPushId();
//            String type = member.getDeviceType();
//            pushMsg(pushId, type,"订单号：" + order_no+ "，卖家已发货");
        }
        if("7".equals(status)){
            //退货 退款
            String returnMsg =  map.get("returnMsg");//退货原因
            String returnOrder =  map.get("returnOrder");//退货单号
            Order order = new Order();
            order.setOrder_no(order_no);
            order.setReturnMsg(returnMsg);
            order.setReturnOrder(returnOrder);
            appOrderMakeSaveDao.updateOrderReturn(order);
        }
        return null;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        Map<String,String> map = (Map<String, String>) object;
        String order_no = map.get("order_no");
        String doublePrices = map.get("doublePrices");
        //生成sign签名
        // 订单
        //todo
        String orderInfo = StringUtil.getOrderInfo(order_no, "yayabaojian", "yayabaojian_single_order", String.valueOf(doublePrices), Constants.ZFB_NOTIFY_URL);

        // 对订单做RSA 签名
        String sign = StringUtil.sign(orderInfo);


            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//            return new OrderInfoAndSign(orderInfo, sign, order_no);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
            return new OrderInfoAndSign(orderInfo, sign, order_no);
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("ISWRONG");
        }
    }

    //查询订单数量
    @Override
    public Object execute(Object object) throws ServiceException {
        Map<String,Object> mapOld = (Map<String, Object>) object;
        Map<String,Object> map1 = new HashMap<String, Object>();
        Map<String,Object> map2 = new HashMap<String, Object>();
        Map<String,Object> map3 = new HashMap<String, Object>();
        Map<String,Object> map4 = new HashMap<String, Object>();
        String empId = (String) mapOld.get("emp_id");//用户id
        String time_status = (String) mapOld.get("time_status");//时间0 今天  1代表总的  2查询今天的收入
        if("0".equals(time_status)){
            //查询今日订单总数量，全部状态的
            map1.put("emp_id", empId);
            map1.put("start", DateUtil.getStartDay()+"");
            map1.put("end", DateUtil.getEndDay()+"");
            long number = appOrderMakeSaveDao.selectOrderNumByDay(map1);
            return String.valueOf(number);
        }else if("1".equals(time_status)){
            //查询总的订单数量
            map2.put("emp_id", empId);
            long number = appOrderMakeSaveDao.selectOrderNum(map2);
            return String.valueOf(number);
        }else if("2".equals(time_status)){
            //查询今天收入
            map3.put("emp_id", empId);
            map3.put("start", DateUtil.getStartDay()+"");
            map3.put("end", DateUtil.getEndDay()+"");
            String floatSum = appOrderMakeSaveDao.selectSum(map3);
            return floatSum;
        }else if("3".equals(time_status)){
            //查询总收入
            map4.put("emp_id", empId);
            String floatSum = appOrderMakeSaveDao.selectSum(map4);
            return floatSum;
        }
        return null;
    }


    @Autowired
    @Qualifier("lxAttributeDao")
    private LxAttributeDao lxAttributeDao;

    static int i = 0;
    List<Emp> lists = new ArrayList<Emp>();
    //根据会员ID查询他的上级所有关系-直到找到店长
    List<Emp> getListMemberVo(String emp_id){
        //emp_id： A充值的话，emp_id就是A的上级B
        Emp memberVO = (Emp) empDao.findInfoById(emp_id);
        lists.add(memberVO);
        if(!memberVO.getLx_attribute_id().equals("2")){
            getListMemberVo(memberVO.getEmp_up());
        }else {
            return lists;
        }
        return lists;
    }

    @Autowired
    @Qualifier("countDao")
    private CountDao countDao;

    @Autowired
    @Qualifier("countRecordDao")
    private CountRecordDao countRecordDao;

}
