package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppRecordsController extends ControllerConstants {

    @Autowired
    @Qualifier("appRecordService")
    private ListService appRecordServiceList;

    @Autowired
    @Qualifier("appRecordService")
    private SaveService appRecordServiceSave;

    @Autowired
    @Qualifier("appRecordService")
    private ExecuteService appRecordServiceExe;

    @Autowired
    @Qualifier("appRecordService")
    private DeleteService appRecordServiceDel;

    @RequestMapping(value = "/appRecords", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecords(RecordQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());

            List<RecordVO> lists = (List<RecordVO>) appRecordServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    @RequestMapping(value = "/appRecordById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordById(String record_id){
        try {
            RecordVO recordVO = (RecordVO) appRecordServiceExe.execute(record_id);
            DataTip tip = new DataTip();
            tip.setData(recordVO);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    @RequestMapping(value = "/appRecordSave", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordSave(Record record){
        try {
            appRecordServiceSave.save(record);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "保存数据失败，请稍后重试！"));
        }
    }

    @RequestMapping(value = "/appRecordDelete", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordDelete(String record_id){
        try {
            RecordVO recordVO = (RecordVO) appRecordServiceDel.delete(record_id);
            DataTip tip = new DataTip();
            tip.setData(recordVO);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
