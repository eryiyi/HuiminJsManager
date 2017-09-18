package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.ApplyBack;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.UpdateService;
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
@RequestMapping("/recordController")
public class RecordController extends ControllerConstants {

    @Autowired
    @Qualifier("recordService")
    private ListService recordServiceList;

    @Autowired
    @Qualifier("recordService")
    private UpdateService recordServiceUpdate;

    @Autowired
    @Qualifier("recordService")
    private ExecuteService recordServiceExe;

    @RequestMapping("list")
    public String list(HttpSession session,ModelMap map, RecordQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) recordServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/record/list";
    }


    @RequestMapping("update")
    @ResponseBody
    public String update(HttpSession session,Record record){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        recordServiceUpdate.update(record);
        return toJSONString(SUCCESS);
    }


    @RequestMapping("toEdit")
    public String toEdit(ModelMap map, String record_id) throws Exception {
        RecordVO recordVO = (RecordVO) recordServiceExe.execute(record_id);
        map.put("recordVO", recordVO);
        List<String> listPics  =  new ArrayList<String>();
        if(recordVO != null){
            if(!StringUtil.isNullOrEmpty(recordVO.getRecord_pic())){
                String[] arras = recordVO.getRecord_pic().split(",");
                if(arras != null){
                    for(int i=0;i<arras.length;i++){
                        if (arras[i].startsWith("upload")) {
                            arras[i] = Constants.URL + arras[i];
                        }else {
                            arras[i] = Constants.QINIU_URL + arras[i];
                        }
                        listPics.add(arras[i]);
                    }
                }
            }
            map.put("listPics", listPics);
        }
        return "/record/edit";
    }

}
