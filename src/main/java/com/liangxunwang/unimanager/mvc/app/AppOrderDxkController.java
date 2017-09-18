package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2015/8/17.
 * 生成定向卡订单
 */
@Controller
public class AppOrderDxkController extends ControllerConstants {

    @Autowired
    @Qualifier("orderDxkService")
    private SaveService orderDxkServiceSave;

    @Autowired
    @Qualifier("empService")
    private FindService memberInfoServiceFind;

    /**
     *  生成定向卡订单
     * @return
     */
    @RequestMapping(value = "/appSaveDxkOrder", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveDxkOrder(Order order){
        if(StringUtil.isNullOrEmpty(order.getEmp_id())){
            return toJSONString(new ErrorTip(1, "操作失败！"));//会员ID为空
        }
        if(StringUtil.isNullOrEmpty(order.getSeller_emp_id())){
            return toJSONString(new ErrorTip(1, "操作失败！"));//卖家会员ID为空
        }
        //根据emp_id查询该会员是不是定向卡会员
        Emp memberVO = (Emp) memberInfoServiceFind.findById(order.getEmp_id());
        if(memberVO != null){
            if(memberVO.getIs_card_emp().equals("1")){
                try {
                    orderDxkServiceSave.save(order);
                    return toJSONString(SUCCESS);
                }catch (ServiceException e){
                    if (e.getMessage().equals("has_exist")){
                        return toJSONString(new ErrorTip(1, "操作失败！已经存在"));
                    }else if (e.getMessage().equals("has_exist_class")){
                        return toJSONString(new ErrorTip(1, "操作失败！"));
                    }else if (e.getMessage().equals("has_dxk_count_out")){
                        return toJSONString(new ErrorTip(1, "操作失败！超出店铺扫码总次数限制"));//超出店铺扫码总次数的限制
                    }else if (e.getMessage().equals("has_dxk_count_out_emp")){
                        return toJSONString(new ErrorTip(1, "操作失败！您在该店铺消费次数超出限制"));//超出个人（会员）在店铺消费次数限制，（充值后一年时间）
                    }
                    return toJSONString(new ErrorTip(1, "操作失败！"));
                }
            }else {
                return toJSONString(new ErrorTip(1, "操作失败！您不是定向卡会员"));//消费会员不是定向卡会员
            }

        }else{
            return toJSONString(new ErrorTip(1, "操作失败！"));
        }
    }

}
