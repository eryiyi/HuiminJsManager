package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobChatGroup;
import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("appEmpUpdateCard")
public class AppEmpUpdateCard implements UpdateService  ,ExecuteService ,FindService{

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("empKuDao")
    private EmpKuDao empKuDao;

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    @Autowired
    @Qualifier("empGroupsDao")
    private EmpGroupsDao empGroupsDao;

    @Autowired
    @Qualifier("hyrzDao")
    private HyrzDao hyrzDao;

    private EasemobChatGroup easemobChatGroup = new EasemobChatGroup();

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;
        if(emp == null){
            throw new ServiceException("null");
        }
        //todo
//        empDao.updateCard(emp.getEmpid(), emp.getCardpic());

        //先根据用户昵称和手机号查询该用户是否和库里的一致
        EmpKu empKu = empKuDao.findByMobile(emp.getMobile());
        if(empKu != null){
            if(!empKu.getNickname().equals(emp.getNickname())){
                throw new ServiceException("empkunull");
            }
        }else {
            throw new ServiceException("empkunull");
        }
        //先查询手机号是否存在
        Emp emp1 = empDao.findByMobile(emp.getMobile());
        if(emp1 == null){
            throw new ServiceException("null");
        }

        //------------更新会员到期日期和会员认证状态------------------
        if("0".equals(emp1.getRzstate2())){
            //说明是第一次认证身份  可以更新会员认证到临时状态
            empDao.updateRzstate2(emp1.getEmpid(), "2");

            Map<String, Object> maphyrz = new HashMap<>();
            maphyrz.put("empid", emp1.getEmpid());
            maphyrz.put("index", 0);
            maphyrz.put("size", 10);
            List<HappyHandHyrz> lists1 = hyrzDao.lists(maphyrz);
            if(lists1 != null && lists1.size()>0)
            {
                //说明数据库有认证的数据  更新
                HappyHandHyrz happyHandHyrz =lists1.get(0);
                //todo
                Object[] ob = DateUtil.getDayInterval(System.currentTimeMillis(), -30);//默认设置一天  正式上线改成30天
                long endtime = (long) ob[0];
                happyHandHyrz.setEndtime(String.valueOf(endtime));
                happyHandHyrz.setIs_use("1");
                hyrzDao.update(happyHandHyrz);
            }else{
                //添加会员认证数据
                HappyHandHyrz happyHandHyrz = new HappyHandHyrz();
                //todo
                Object[] ob = DateUtil.getDayInterval(System.currentTimeMillis(), -30);//默认设置一天  正式上线改成30天
                long endtime = (long) ob[0];
                happyHandHyrz.setEndtime(String.valueOf(endtime));
                happyHandHyrz.setIs_use("1");
                happyHandHyrz.setStarttime(System.currentTimeMillis() + "");
                happyHandHyrz.setEmpid(emp1.getEmpid());
                happyHandHyrz.setHyrzid(UUIDFactory.random());
                hyrzDao.save(happyHandHyrz);
            }

            //身份认证成功之后，发送系统消息
            //todo
            HappyHandMessage happyHandMessage = new HappyHandMessage();
            happyHandMessage.setMsgid(UUIDFactory.random());
            happyHandMessage.setDateline(System.currentTimeMillis() + "");
            happyHandMessage.setTitle("恭喜你认证成功，快来体验吧！真心欢迎您成为我们的会员，我们会努力做得更好！\n" +
                    "温馨提示：你浏览和交流的会员，均是其工作单位推荐。会员身份如有虚假，请随时向我们投诉和反馈。我们会与该会员工作单位沟通、核实，如确实违反服务条款，丫丫保健有权暂停或终止该会员的帐号，暂停或终止提供丫丫保健提供的全部或部分服务。请丫丫保健会员，遵守服务条款，真诚交友。让我们一起携手，共建一个真实、安全的婚恋交友环境！\n" +
                    "推荐并欢迎加入沈阳用户交流群。快去找寻真爱吧，祝你早日找到幸福！");

//            happyHandMessage.setTitle("恭喜你认证成功！可以免费体验一个月哦，快来体验吧！真心期待您成为我们的会员，我们会努力做得更好！\n" +
//                    "温馨提示：你浏览和交流的会员，均是其工作单位推荐。会员身份如有虚假，请随时向我们投诉和反馈。我们会与该会员工作单位沟通、核实，如确实违反服务条款，丫丫保健有权暂停或终止该会员的帐号，暂停或终止提供丫丫保健提供的全部或部分服务。请丫丫保健会员，遵守服务条款，真诚交友。让我们一起携手，共建一个真实、安全的婚恋交友环境！\n" +
//                    "推荐并欢迎加入沈阳用户交流群。快去找寻真爱吧，祝你早日找到幸福！");
            happyHandMessage.setEmpid(emp.getEmpid());
            messagesDao.save(happyHandMessage);

            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", "恭喜你认证成功，快来体验吧！真心欢迎您成为我们的会员，我们会努力做得更好！" +
                        "温馨提示：你浏览和交流的会员，均是其工作单位推荐。会员身份如有虚假，请随时向我们投诉和反馈。我们会与该会员工作单位沟通、核实，如确实违反服务条款，丫丫保健有权暂停或终止该会员的帐号，暂停或终止提供丫丫保健提供的全部或部分服务。请丫丫保健会员，遵守服务条款，真诚交友。让我们一起携手，共建一个真实、安全的婚恋交友环境！" +
                        "推荐并欢迎加入沈阳用户交流群。快去找寻真爱吧，祝你早日找到幸福！", "1", emp1.getChannelId());

//                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", "恭喜你认证成功！可以免费体验一个月哦，快来体验吧！真心期待您成为我们的会员，我们会努力做得更好！" +
//                        "温馨提示：你浏览和交流的会员，均是其工作单位推荐。会员身份如有虚假，请随时向我们投诉和反馈。我们会与该会员工作单位沟通、核实，如确实违反服务条款，丫丫保健有权暂停或终止该会员的帐号，暂停或终止提供丫丫保健提供的全部或部分服务。请丫丫保健会员，遵守服务条款，真诚交友。让我们一起携手，共建一个真实、安全的婚恋交友环境！" +
//                        "推荐并欢迎加入沈阳用户交流群。快去找寻真爱吧，祝你早日找到幸福！", "1", emp1.getChannelId());
            }

            //加群
            easemobChatGroup.addSingleUserToChatGroup(Constants.DEFAULT_GROUP_ID1, emp.getEmpid());
            EmpGroups empGroups = new EmpGroups();
            empGroups.setEmpgroupsid(UUIDFactory.random());
            empGroups.setDateline(System.currentTimeMillis() + "");
            empGroups.setGroupid(Constants.DEFAULT_GROUP_ID1);
            empGroups.setEmpid(emp.getEmpid());
            Map<String, Object> map = new HashMap<>();
            map.put("empid", empGroups.getEmpid());
            map.put("groupid", empGroups.getGroupid());
            List<EmpGroups> lists = empGroupsDao.findById(map);
            if(lists != null && lists.size()>0){
            }else {
                empGroupsDao.save(empGroups);
            }

        }

        return 200;
    }





    @Override
    public Object execute(Object object) throws Exception {

        List<HappyHandHyrz> listHy = hyrzDao.listVipEnd(System.currentTimeMillis()+"");
        hyrzDao.updateOverTime(System.currentTimeMillis()+"");
        //------------更新会员到期日期和会员认证状态------------------
        for(HappyHandHyrz emp1:listHy){
            if(emp1 != null){
                empDao.updateRzstate2(emp1.getEmpid(), "3");
            }
        }

        return 200;
    }


    @Override
    public Object findById(Object object) throws ServiceException {
        //查询即将到期的会员列表
        Map<String, Object> map = new HashMap<String, Object>();
        Object[] ob = DateUtil.getDayInterval(System.currentTimeMillis(), -7);
        long endtime = (long) ob[0];

        map.put("startTime",  DateUtil.getStartDay());
        map.put("endTime",  endtime);

        List<HappyHandHyrz> lists = hyrzDao.listsEmpEndDate(map);
        if(lists != null){
            for(HappyHandHyrz happyHandHyrz:lists){
                if(happyHandHyrz != null){
                    Emp emp1 = empDao.findById(happyHandHyrz.getEmpid());

                    if("2".equals(emp1.getRzstate2())){
                        HappyHandMessage happyHandMessage = new HappyHandMessage();
                        happyHandMessage.setMsgid(UUIDFactory.random());
                        happyHandMessage.setDateline(System.currentTimeMillis() + "");
                        happyHandMessage.setTitle("您好，免费体验即将到期，真心期待您成为我们的会员，我们会努力做得更好！");
                        happyHandMessage.setEmpid(happyHandHyrz.getEmpid());
                        messagesDao.save(happyHandMessage);

                        if(emp1 != null){
                            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", "您好，免费体验即将结束，真心期待您成为我们的会员，我们会努力做得更好！", "1", emp1.getChannelId());
                            }
                        }
                    }
                    if("1".equals(emp1.getRzstate2())){
                        HappyHandMessage happyHandMessage = new HappyHandMessage();
                        happyHandMessage.setMsgid(UUIDFactory.random());
                        happyHandMessage.setDateline(System.currentTimeMillis() + "");
                        happyHandMessage.setTitle(" 您好，会员服务即将到期，为了不影响您的使用，请及时续费！我们会努力做得更好！");
                        happyHandMessage.setEmpid(happyHandHyrz.getEmpid());
                        messagesDao.save(happyHandMessage);

                        if(emp1 != null){
                            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", " 您好，会员服务即将到期，为了不影响您的使用，请及时续费！我们会努力做得更好！", "1", emp1.getChannelId());
                            }
                        }
                    }

                }
            }
        }
        return 200;
    }
}
