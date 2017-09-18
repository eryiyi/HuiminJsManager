package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Area;
import com.liangxunwang.unimanager.model.City;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
public class AppAreaController extends ControllerConstants {

    @Autowired
    @Qualifier("provinceService")
    private ListService provinceService;

    @RequestMapping(value = "/appProvinces", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appProvinces(){
        try {
            List<Province> lists = (List<Province>) provinceService.list("");
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    @Autowired
    @Qualifier("cityService")
    private ListService cityService;

    @RequestMapping(value = "/appCitys", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appCitys(String provinceid){
        try {
            List<City> lists = (List<City>) cityService.list(provinceid);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @RequestMapping("/getAllCitys")
    @ResponseBody
    public String getAllCitys(String provinceid,HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<City> listCitysAll = (List<City>) cityService.list(provinceid);
        try {
            DataTip tip = new DataTip();
            tip.setData(listCitysAll);
            return reBack(toJSONString(tip), request, response);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


    @Autowired
    @Qualifier("appAreaService")
    private ListService appAreaListService;
    /**
     * 获得所有的地区，根据城市id
     * @return
     */
    @RequestMapping(value = "/appGetArea", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appGetArea(String cityid){
        try {
            List<Area> list = (List<Area>) appAreaListService.list(cityid);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "获得数据失败！"));
        }
    }

    //后台获得地区列表
    @RequestMapping("listAreas")
    public String listAreas(HttpSession session,ModelMap map, String id){
        List<Area> list = (List<Area>) appAreaListService.list(id);
        map.put("list", list);
        //日志记录
        return "/province/listarea";
    }


}
