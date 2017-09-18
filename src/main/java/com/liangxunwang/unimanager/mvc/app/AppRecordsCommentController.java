package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.RecordComment;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.mvc.vo.RecordCommentVO;
import com.liangxunwang.unimanager.query.RecordQuery;
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
public class AppRecordsCommentController extends ControllerConstants {

    @Autowired
    @Qualifier("appRecordCommentService")
    private SaveService appRecordCommentServiceSave;

    @Autowired
    @Qualifier("appRecordCommentService")
    private ListService appRecordCommentServiceList;


    @RequestMapping(value = "/appRecordCommentSave", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordCommentSave(RecordComment record){
        try {
            appRecordCommentServiceSave.save(record);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    @RequestMapping(value = "/appRecordsComment", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appRecordsComment(RecordQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());

            List<RecordCommentVO> lists = (List<RecordCommentVO>) appRecordCommentServiceList.list(query);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }


}
