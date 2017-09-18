package com.liangxunwang.unimanager.util;


import com.liangxunwang.unimanager.alipay.AlipayConfig;
import com.liangxunwang.unimanager.alipay.SignUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: zhl
 * Date: 2014/7/29
 * Time: 18:11
 * 类的功能、说明写在此处.
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    //生成随机数字和字母,
    public static String getStringRandom() {
        StringBuilder str=new StringBuilder();//定义变长字符串
        Random random=new Random();
//随机生成数字，并添加到字符串
        for(int i=0;i< 8;i++){
            str.append(random.nextInt(10));
        }
//将字符串转换为数字并输出
//        int num=Integer.parseInt(str.toString());
        return str.toString();
    }

    public static boolean isEmail(String str) {
        String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(pattern1);
        Matcher mat = pattern.matcher(str);
        return !mat.find();
    }

    /**
     * 取出一段字符串中的一个img标签
     */
    public static String selsrcSingle(String str) {
        String ss = "";
        Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        Matcher m = p.matcher(str);
        while (m.find()) {
            ss = m.group(1);
        }
        return ss;
    }

    /**
     * 判断是否为手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 校验是否为正整数
     * @param intStr
     * @return
     */
    public static final boolean isValidInteger(String intStr) {
        return (intStr == null) ? false : intStr.matches("\\d+");
    }

    /**
     * 查询文件夹下面所有文件名
     * path:文件夹地址  例："C://Documents and Settings//yinxm//デスクトップ//TestFile"
     * */
    public static String[] getFile(String path){
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        String[] arrayTitle = new String[array.length];
        for(int i=0;i<array.length;i++){
            if(array[i].isFile()){
                // only take file name
//                System.out.println("^^^^^" + array[i].getName());
                arrayTitle[i] = array[i].getName();
                // take file path and name
//                System.out.println("#####" + array[i]);
                // take file path and name
//                System.out.println("*****" + array[i].getPath());
            }else if(array[i].isDirectory()){
                getFile(array[i].getPath());
            }
        }
        return arrayTitle;
    }
    /**
     * 删除某个文件夹下的所有文件夹和文件
     */
    public static boolean deletefile(String delpath)
            throws FileNotFoundException, IOException {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
//                                System.out.println("不是文件夹");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
//                                                System.out.println("path=" + delfile.getPath());
//                                                System.out.println("absolutepath="
//                                                                + delfile.getAbsolutePath());
//                                                System.out.println("name=" + delfile.getName());
                        delfile.delete();
//                                                System.out.println("删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                    }
                }
//                                file.delete();//这个是删除该文件夹的
            }
        } catch (FileNotFoundException e) {
//                        System.out.println("deletefile()   Exception:" + e.getMessage());
        }
        return true;
    }



    /**
     * create the order info. 创建订单信息
     *
     */
    public static String getOrderInfo(String outTradeNo, String subject, String body, String price, String notify_url) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + AlipayConfig.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + AlipayConfig.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";


        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, AlipayConfig.RSA_PRIVATE,false);
    }



}
