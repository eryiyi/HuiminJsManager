package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/17.
 */
@Controller
public class AppPayWxController1 extends ControllerConstants {

    /**
     * 微信支付回调-会员认证
     * @return
     */
    @RequestMapping(value = "/payWxHyNotifyAction",   produces = "text/html;charset=UTF-8", method={RequestMethod.POST})
    @ResponseBody
    public String payWxHyNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

            // 解析结果存储在HashMap
            Map<String, String> map = new HashMap<String, String>();
            InputStream inputStream = request.getInputStream();

            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }

//            JSONObject  = JSONObject.fromObject(map);
            String json = ControllerConstants.toJSONString(map);

            System.out.println("===消息通知的结果：" + json.toString() + "==========================");
            System.out.println("===return_code===" + map.get("return_code"));
            System.out.println("===return_msg===" + map.get("return_msg"));
            System.out.println("===out_trade_no===" + map.get("out_trade_no"));

            //验证签名的过程

            //判断是否支付成功
            if(map.get("return_code").equals("SUCCESS")) {

                /**
                 *支付成功之后的业务处理
                 */
                String out_trade_no = map.get("out_trade_no") ;
                if(!StringUtil.isNullOrEmpty(out_trade_no)){
                    synchronized (this){
                        updateOrder(out_trade_no);
                    }
                }

                // 释放资源
                inputStream.close();
                inputStream = null;
                //bis.close();
                return "SUCCESS";
            }

            if (map.get("return_code").equals("FAIL")) {

                /**
                 *支付失败后的业务处理
                 */

                // 释放资源
                inputStream.close();

                inputStream = null;

                return "SUCCESS";
            }

            // 释放资源
            inputStream.close();
            inputStream = null;

            return "SUCCESS";

    }

    @Autowired
    @Qualifier("appOrderMakeService1")
    private UpdateService appOrderMakeService1;


    void updateOrder(String out_trade_no){
        Order order = new Order();
        order.setOut_trade_no(out_trade_no);
        appOrderMakeService1.update(order);
    }

//
//    /**
//     * 微信支付回调-诚信认证
//     * @return
//     */
//    @RequestMapping(value = "/payWxCxNotifyAction",   produces = "text/html;charset=UTF-8", method={RequestMethod.POST})
//    @ResponseBody
//    public String payWxCxNotifyAction(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
//
//        // 解析结果存储在HashMap
//        Map<String, String> map = new HashMap<String, String>();
//        InputStream inputStream = request.getInputStream();
//
//        // 读取输入流
//        SAXReader reader = new SAXReader();
//        Document document = reader.read(inputStream);
//        // 得到xml根元素
//        Element root = document.getRootElement();
//        // 得到根元素的所有子节点
//        List<Element> elementList = root.elements();
//
//        // 遍历所有子节点
//        for (Element e : elementList) {
//            map.put(e.getName(), e.getText());
//        }
//
////            JSONObject  = JSONObject.fromObject(map);
//        String json = ControllerConstants.toJSONString(map);
//
//        System.out.println("===消息通知的结果：" + json.toString() + "==========================");
//        System.out.println("===return_code===" + map.get("return_code"));
//        System.out.println("===return_msg===" + map.get("return_msg"));
//        System.out.println("===out_trade_no===" + map.get("out_trade_no"));
//
//        //验证签名的过程
//
//        //判断是否支付成功
//        if(map.get("return_code").equals("SUCCESS")) {
//
//            /**
//             *支付成功之后的业务处理
//             */
//            String out_trade_no = map.get("out_trade_no") ;
//            if(!StringUtil.isNullOrEmpty(out_trade_no)){
//                synchronized (this){
//                    updateOrderCx(out_trade_no);
//                }
//            }
//
//            // 释放资源
//            inputStream.close();
//            inputStream = null;
//            //bis.close();
//            return "SUCCESS";
//        }
//
//        if (map.get("return_code").equals("FAIL")) {
//
//            /**
//             *支付失败后的业务处理
//             */
//
//            // 释放资源
//            inputStream.close();
//
//            inputStream = null;
//
//            return "SUCCESS";
//        }
//
//        // 释放资源
//        inputStream.close();
//        inputStream = null;
//
//        return "SUCCESS";
//
//    }
//
//
//    @Autowired
//    @Qualifier("orderUpdateWxService")
//    private UpdateService orderUpdateWxService;
//
//
//    void updateOrderCx(String out_trade_no){
//        Order order = new Order();
//        order.setOut_trade_no(out_trade_no);
//        orderUpdateWxService.update(order);
//    }



}
