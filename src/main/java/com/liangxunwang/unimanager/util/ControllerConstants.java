package com.liangxunwang.unimanager.util;

import com.google.gson.Gson;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.model.tip.SuccessTip;
import com.liangxunwang.unimanager.model.tip.Tip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;

public class ControllerConstants {

    public static final String QINIU_AK = "Gtm_frmskgjbyj6FIKD6emEQnDqelQrITyqKi-40";
    public static final String QINIU_SK = "7aH9ICF4XefQZL5l-s7IRnoOH5PEK7EPKej2RgkX";

    public static final String ACCOUNT_KEY = "account";
    public static final String MEMBER_KEY = "member";
    public static final String PERMISSIONS = "powers";
    public static final String ADMIN_KEY ="admin";
    public static final String VERIFY_CODE = "verify_code";
//    public static final String USER_KEY = "user";
//    public static final String PERMISSIONS = "powers";

    public static final int PAGE_SIZE = 20;

    public static final Tip TIMEOUT = new ErrorTip(-1, "响应超时！");
    public static final Tip SUCCESS = new SuccessTip();



    private static final Gson JSON_HELPER = new Gson();

    public static String toJSONString(Object object) {
        return JSON_HELPER.toJson(object);
    }

    public static Object fromJson(String json, Type clazz){
        return JSON_HELPER.fromJson(json, clazz);
    }

    public static long calculatePageCount(int size, long count) {
        if (count == 0){
            return 1;
        }
        return count % size == 0 ? count / size : count / size + 1;
    }

    public static String  reBack(String str,HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");

        PrintWriter out = response.getWriter();
        out.println(str);
        out.close();
        return null;
    }


}
