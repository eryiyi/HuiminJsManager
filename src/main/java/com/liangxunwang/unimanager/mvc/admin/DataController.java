package com.liangxunwang.unimanager.mvc.admin;


import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URL;

@Controller
@RequestMapping("/data")
public class DataController extends ControllerConstants {
    @Autowired
    @Qualifier("dataService")
    private SaveService dataSaveService;

    @RequestMapping("toAdd")
    public String toAdd(){
        return "data/add";
    }

//    @RequestMapping(value = "/resolve", produces = "text/plain;charset=UTF-8")
//    @ResponseBody
//    public String resolve(String fileName, HttpServletRequest request){
//        try {
//            String path = init(request.getSession());
//            String filePath = path+fileName;
//            dataSaveService.save(filePath);
//            return toJSONString(SUCCESS);
//        } catch (Exception e) {
//            String msg = e.getMessage();
//            if(!StringUtil.isNullOrEmpty(msg)) {
//                if (msg.equals("SAVE_ERROR")) {
//                    return toJSONString(new ErrorTip(1, "导入失败，请检查文件！"));
//                }
//            }
//            return toJSONString(new ErrorTip(1, "导入失败，请检查文件！"));
//        }
//    }


    @Autowired
    @Qualifier("empSaveService")
    private SaveService empSaveServiceSave;

    @RequestMapping("toAddEmp")
    public String toAddEmp(){
        return "data/addEmp";
    }

    @RequestMapping(value = "/resolveEmp", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String resolveEmp(String fileName, HttpServletRequest request){
        try {
            String path = init(request.getSession());
            String filePath = path+fileName;
            empSaveServiceSave.save(filePath);
            return toJSONString(SUCCESS);
        } catch (Exception e) {
            String msg = e.getMessage();
            if(!StringUtil.isNullOrEmpty(msg)){
                if (msg.equals("SAVE_ERROR")){
                    return toJSONString(new ErrorTip(1, "导入失败，请检查文件！"));
                }
            }

            return toJSONString(new ErrorTip(1, "导入失败，请检查文件！"));
        }
    }

    public String init(HttpSession session) throws ServletException {
        String webPath = null;
        try {
            webPath = getAbsolutePathByContext(session);
        } catch (Exception e) {
        }

        if (webPath == null) {
            try {
                webPath = getAbsolutePathByClass(session);
            } catch (Exception e) {
            }
        }

        if (webPath == null) {
            try {
                webPath = getAbsolutePathByResource(session);
            } catch (Exception e) {
            }
        }
        return webPath;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByContext(HttpSession session) throws Exception {

        String webPath = session.getServletContext().getRealPath("/");

        webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        webPath = webPath.replaceAll("%20", " ");

        if (webPath.matches("^[a-zA-Z]:.*?$")) {

        } else {
            webPath = "/" + webPath;
        }

        webPath += "/";
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        return webPath;

    }

    /**
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByClass(HttpSession session) throws Exception {
        String webPath = this.getClass().getResource("/").getPath().replaceAll("^\\/", "");
        webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        webPath = webPath.replaceAll("%20", " ");

        if (webPath.matches("^[a-zA-Z]:.*?$")) {

        } else {
            webPath = "/" + webPath;
        }

        webPath += "/";
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");

        return webPath;
    }
    private String getAbsolutePathByResource(HttpSession session) throws Exception {
        URL url = session.getServletContext().getResource("/");
        String path = new File(url.toURI()).getAbsolutePath();
        if (!path.endsWith("\\") && !path.endsWith("/")) {
            path += File.separator;
        }
        return path;
    }

}
