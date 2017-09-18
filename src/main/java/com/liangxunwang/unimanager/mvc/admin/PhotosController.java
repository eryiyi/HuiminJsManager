package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.query.PhotosQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/photos")
public class PhotosController extends ControllerConstants {

    @Autowired
    @Qualifier("photosService")
    private ListService photosServiceList;

    @Autowired
    @Qualifier("photosService")
    private SaveService photosServiceSave;

    @Autowired
    @Qualifier("photosService")
    private ExecuteService photosServiceExe;


    @Autowired
    @Qualifier("photosService")
    private DeleteService photosServiceDel;

    @Autowired
    @Qualifier("photosService")
    private UpdateService photosServiceUpdate;

    @Autowired
    @Qualifier("empService")
    private FindService empServiceFind;

    @RequestMapping("list")
    public String list(ModelMap map, PhotosQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) photosServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/photos/list";
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(HappyHandPhoto happyHandPhoto){
        photosServiceSave.save(happyHandPhoto);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(String photoid){
        photosServiceDel.delete(photoid);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/toDetail")
    public String toDetail(HttpSession session,ModelMap map, String id) throws Exception {
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        HappyHandPhoto photo = (HappyHandPhoto) photosServiceExe.execute(id);
        map.put("photo", photo);
        List<String> listPics = new ArrayList<String>();
        if(!StringUtil.isNullOrEmpty(photo.getPhotos())){
            String[] pics = new String[]{};
            if(photo!=null && photo.getPhotos()!= null){
                pics = photo.getPhotos().split(",");
            }
            for (int i=0; i<pics.length; i++){
                if (pics[i].startsWith("upload")) {
                    listPics.add(Constants.URL + pics[i]);
                }else {
                    listPics.add(Constants.QINIU_URL + pics[i]);
                }
            }
        }
        map.put("listPics", listPics);
        //查询会员信息
        Emp emp = (Emp) empServiceFind.findById(photo.getEmpid());
        map.put("emp", emp);
        return "/photos/detail";
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(HappyHandPhoto happyHandPhoto){
        photosServiceUpdate.update(happyHandPhoto);
        return toJSONString(SUCCESS);
    }

}
