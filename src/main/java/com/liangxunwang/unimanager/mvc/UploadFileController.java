package com.liangxunwang.unimanager.mvc;

import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhl on 2015/2/4.
 */
public class UploadFileController extends ControllerConstants {
    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadImage(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
        String newFileName = null;
        if(fileName != null && !"".equals(fileName)) {
            String type = fileName.substring(fileName.lastIndexOf("."), fileName.length()); //上传文件类型
            //判断上传文件类型
            if(!".mp3".equals(type)) {
                return toJSONString(new ErrorTip(1, "上传文件格式不正确！"));
            }
            newFileName = String.valueOf(System.currentTimeMillis()) + fileName.subSequence(fileName.lastIndexOf("."), fileName.length());
            File targetFile = new File(path, newFileName);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            //保存
            try {
                file.transferTo(targetFile);
            } catch (Exception e) {
                return toJSONString(new ErrorTip(1, "上传文件失败！"));//上传文件失败，
            }
        } else {
            return toJSONString(new ErrorTip(1, "上传图片为空！"));//上传图片为空
        }
        DataTip dataTip = new DataTip();
        dataTip.setData("upload/" + newFileName);
        return toJSONString(dataTip);
    }

    /**
     * 通过上下文来取工程路径
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByContext(HttpSession session) throws Exception {

        String webPath = session.getServletContext().getRealPath("/upload/"+getDatePath());

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
     * 通过类路径来取工程路径
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByClass(HttpSession session) throws Exception {
        String webPath = this.getClass().getResource("/upload").getPath().replaceAll("^\\/", "");
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
        URL url = session.getServletContext().getResource("/upload");
        String path = new File(url.toURI()).getAbsolutePath();
        if (!path.endsWith("\\") && !path.endsWith("/")) {
            path += File.separator;
        }
        return path;
    }

    public String init(HttpSession session) throws ServletException {
        String webPath = null;
        try {
            webPath = getAbsolutePathByContext(session);
        } catch (Exception e) {
        }

        // 在weblogic 11g 上可能无法从上下文取到工程物理路径，所以改为下面的
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

//        System.out.println(webPath);
        return webPath;
    }

    private String getDatePath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }
}
