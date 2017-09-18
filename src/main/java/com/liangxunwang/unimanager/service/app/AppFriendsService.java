package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobIMUsers;
import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.FriendsDao;
import com.liangxunwang.unimanager.dao.JiaowangDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.Friends;
import com.liangxunwang.unimanager.model.HappyHandJw;
import com.liangxunwang.unimanager.model.HappyHandMessage;
import com.liangxunwang.unimanager.query.FriendsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appFriendsService")
public class AppFriendsService implements SaveService,ListService,UpdateService,ExecuteService {
    @Autowired
    @Qualifier("friendsDao")
    private FriendsDao friendsDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    @Override
    public Object save(Object object) throws ServiceException {
        Friends friends = (Friends) object;
        if(StringUtil.isNullOrEmpty(friends.getEmpid1())){
            throw new ServiceException("empId1Null");
        }
        if(StringUtil.isNullOrEmpty(friends.getEmpid2())){
            throw new ServiceException("empId2Null");
        }
        //先判断是否是黑名单中的
        String str1 = (String) easemobIMUsers.getBlackList(friends.getEmpid1());

        JSONObject jo1 = null;
        JSONArray ja1 = null;
        try {
            jo1 = new JSONObject(str1);
            ja1 = jo1.getJSONArray("data");
            String empids1 = "";
            for (int i = 0; i < ja1.length(); i++) {
                String jo = (String) ja1.get(i);
                empids1 += jo+",";
            }
            if(empids1.contains(friends.getEmpid2())){
                //说明empid1的黑名单中有empid2,对方在您的黑名单中
                throw new ServiceException("itinyourbk");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String str2 = (String) easemobIMUsers.getBlackList(friends.getEmpid2());
        JSONObject jo2 = null;
        JSONArray ja2 = null;
        try {
            jo2 = new JSONObject(str2);
            ja2 = jo2.getJSONArray("data");
            String empids2 = "";
            for (int i = 0; i < ja2.length(); i++) {
                String jo11 = (String) ja2.get(i);
                empids2 += jo11+",";
            }
            if(empids2.contains(friends.getEmpid1())){
                //你在对方的黑名单中
                throw new ServiceException("youinitbk");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //先判断是否存在好友关系
        Map<String, Object> map = new HashMap<>();
        map.put("empid1", friends.getEmpid1() );
        map.put("empid2", friends.getEmpid2() );
        map.put("is_check", "1" );
        List<Friends> list1 = friendsDao.lists(map);
        if(list1 != null && list1.size() > 0){
            throw new ServiceException("isFriends");//已经是好友了
        }

        //一天只能发一次加好友请求
        Map<String, Object> map1 = new HashMap<>();
        map1.put("empid1", friends.getEmpid1());
        map1.put("empid2", friends.getEmpid2());

        map1.put("starttime",  DateUtil.getStartDay());
        map1.put("endtime",  DateUtil.getEndDay());

        long l1= friendsDao.count(map1);
        if(l1 > 0){
            throw new ServiceException("hasApply");//今天已经申请过了
        }

        //一共是三次请求加好友机会
        Map<String, Object> map2 = new HashMap<>();
        map2.put("empid1", friends.getEmpid1());
        map2.put("empid2", friends.getEmpid2());
        long l2 = friendsDao.count(map2);
        if(l2 > 2){
            throw new ServiceException("applyTooMuch");//申请超过三次
        }

        friends.setApplytime(System.currentTimeMillis()+"");
        friends.setIs_check("0");
        friends.setFriendsid(UUIDFactory.random());
        friendsDao.save(friends);

        Emp emp2= empDao.findById(friends.getEmpid2());
        if(!StringUtil.isNullOrEmpty(emp2.getChannelId())){
            BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp2.getDeviceType()), "好友请求", "有新的好友请求，点此查看", "5", emp2.getChannelId());
        }

        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        FriendsQuery query = (FriendsQuery) object;
        //先判断是否存在好友关系
        Map<String, Object> map = new HashMap<>();
        if(!StringUtil.isNullOrEmpty(query.getEmpid1())){
            map.put("empid1", query.getEmpid1());
        }
        if(!StringUtil.isNullOrEmpty(query.getEmpid2())){
            map.put("empid2", query.getEmpid2());
        }
        if(!StringUtil.isNullOrEmpty(query.getIs_check())){
            map.put("is_check", query.getIs_check());
        }
        if(!StringUtil.isNullOrEmpty(query.getKeywords())){
            map.put("keywords", query.getKeywords());
        }
        List<Friends> list = friendsDao.lists(map);
        for(Friends member:list){
            if(member != null){
                if(!StringUtil.isNullOrEmpty(member.getEmpid1Cover())){
                    if (member.getEmpid1Cover().startsWith("upload")) {
                        member.setEmpid1Cover(Constants.URL + member.getEmpid1Cover());
                    }else {
                        member.setEmpid1Cover(Constants.QINIU_URL + member.getEmpid1Cover()+"-yasuoone");
                    }
                }
                if(!StringUtil.isNullOrEmpty(member.getEmpid2Cover())){
                    if (member.getEmpid2Cover().startsWith("upload")) {
                        member.setEmpid2Cover(Constants.URL + member.getEmpid2Cover());
                    }else {
                        member.setEmpid2Cover(Constants.QINIU_URL + member.getEmpid2Cover()+"-yasuoone");
                    }
                }
            }
        }
        return list;
    }


    //接受好友申请
    @Override
    public Object update(Object object) {
        Friends friends = (Friends) object;
        if(StringUtil.isNullOrEmpty(friends.getIs_check())){
            throw new ServiceException("ischecknull");
        }
        if(StringUtil.isNullOrEmpty(friends.getFriendsid())){
            throw new ServiceException("friendsidnull");
        }
        //先查看是否已经是好友了
        Friends friends11 = friendsDao.findById(friends.getFriendsid());
        if("1".equals(friends11.getIs_check())){
            friendsDao.deleteById(friends);
            //说明已经是好友了
//            throw new ServiceException("hasFriends");
        }
        //加一个判断 看看是否已经是好友了
        Map<String, Object> map1 = new HashMap<>();
        map1.put("empid1", friends.getEmpid1() );
        map1.put("empid2", friends.getEmpid2() );
        map1.put("is_check", "1" );
        List<Friends> list1 = friendsDao.lists(map1);
        if(list1 != null && list1.size() > 0){
            //删除该请求
            friendsDao.deleteById(friends);
            return 200;
//            throw new ServiceException("hasFriends");//已经是好友了
        }

        Map<String, Object> map2 = new HashMap<>();
        map2.put("empid1", friends.getEmpid2() );
        map2.put("empid2", friends.getEmpid1() );
        map2.put("is_check", "1" );
        List<Friends> list12 = friendsDao.lists(map2);
        if(list12 != null && list12.size() > 0){
            friendsDao.deleteById(friends);
            return 200;
//            throw new ServiceException("hasFriends");//已经是好友了
        }

        friends.setAccepttime(System.currentTimeMillis()+"");
        friendsDao.update(friends);
        //删除其他请求
        Friends friends111 = new Friends();
        friends111.setEmpid1(friends.getEmpid1());
        friends111.setEmpid2(friends.getEmpid2());
        friends111.setIs_check("0");
        friendsDao.deleteByEmpId(friends111);
        Friends friends112 = new Friends();
        friends112.setEmpid1(friends.getEmpid2());
        friends112.setEmpid2(friends.getEmpid1());
        friends112.setIs_check("0");
        friendsDao.deleteByEmpId(friends112);

        if("1".equals(friends.getIs_check())){
            //说明接受申请了
            Friends friends1 = new Friends();
            friends1.setFriendsid(UUIDFactory.random());
            friends1.setIs_check("1");
            friends1.setAccepttime(System.currentTimeMillis() + "");
            friends1.setApplytime(System.currentTimeMillis()+"");
            friends1.setEmpid1(friends.getEmpid2());
            friends1.setEmpid2(friends.getEmpid1());
            friendsDao.save(friends1);

            //添加系统消息
            Emp emp1= empDao.findById(friends.getEmpid1());
            Emp emp2= empDao.findById(friends.getEmpid2());

            HappyHandMessage happyHandMessage = new HappyHandMessage();
            happyHandMessage.setMsgid(UUIDFactory.random());
            happyHandMessage.setDateline(System.currentTimeMillis() + "");
            happyHandMessage.setTitle(emp2.getNickname()+"已经同意你的好友请求");
            happyHandMessage.setEmpid(emp1.getEmpid());
            messagesDao.save(happyHandMessage);

            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", emp2.getNickname()+"已经同意你的好友请求", "9", emp1.getChannelId());
            }
        }else{
            //说明拒绝了
            Emp emp1= empDao.findById(friends.getEmpid1());
            Emp emp2= empDao.findById(friends.getEmpid2());

            HappyHandMessage happyHandMessage = new HappyHandMessage();
            happyHandMessage.setMsgid(UUIDFactory.random());
            happyHandMessage.setDateline(System.currentTimeMillis() + "");
            happyHandMessage.setTitle(emp2.getNickname()+"已经拒绝你的好友请求");
            happyHandMessage.setEmpid(emp1.getEmpid());
            messagesDao.save(happyHandMessage);

            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", emp2.getNickname()+"已经拒绝你的好友请求", "10", emp1.getChannelId());
            }
        }
        return 200;
    }

    @Autowired
    @Qualifier("jiaowangDao")
    private JiaowangDao jiaowangDao;

    @Override
    public Object execute(Object object) throws Exception {
        Friends friends = (Friends) object;
        if(StringUtil.isNullOrEmpty(friends.getEmpid1())){
            throw new ServiceException("empidisnull");
        }
        if(StringUtil.isNullOrEmpty(friends.getEmpid2())){
            throw new ServiceException("empidisnull");
        }
        Map<String, Object> map = new HashMap<>();
        String empid1 = friends.getEmpid1();
        String empid2 = friends.getEmpid2();

        //先判断是否已经有两个人的交往
        Map<String, Object> mapjw = new HashMap<>();
        mapjw.put("empid1", empid1 );
        mapjw.put("empid2", empid2);
        mapjw.put("is_check", "1" );
        List<HappyHandJw> list1 = jiaowangDao.lists(mapjw);
        if(list1 != null && list1.size() > 0){
            throw new Exception("isjwing");//交往中
        }



        Emp emp1 = empDao.findById(empid1);
        Emp emp2 = empDao.findById(empid2);//被删除人

        map.put("empid1" , empid1);
        map.put("empid2" , empid2);
        map.put("is_check" ,"1");

        List<Friends> listss1 = friendsDao.lists(map);
        if(listss1 != null && listss1.size()>0){
            friendsDao.delete(friends);

            HappyHandMessage happyHandMessage1 = new HappyHandMessage();
            happyHandMessage1.setMsgid(UUIDFactory.random());
            happyHandMessage1.setDateline(System.currentTimeMillis() + "");
            happyHandMessage1.setTitle("你已经把" + emp2.getNickname()+"从好友列表中删除");
            happyHandMessage1.setEmpid(emp1.getEmpid());
            messagesDao.save(happyHandMessage1);

            if(!StringUtil.isNullOrEmpty(emp1.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp1.getDeviceType()), "系统消息", "你已经把" + emp2.getNickname()+"从好友列表中删除", "8", emp1.getChannelId());
            }


        }else {
//            throw new ServiceException("noexist");
        }

        map.put("empid1" , empid2);
        map.put("empid2" , empid1);
        List<Friends> listss2 = friendsDao.lists(map);


        if(listss2 != null && listss2.size()>0){
            friends.setEmpid1(empid2);
            friends.setEmpid2(empid1);
            friendsDao.delete(friends);

            HappyHandMessage happyHandMessage2 = new HappyHandMessage();
            happyHandMessage2.setMsgid(UUIDFactory.random());
            happyHandMessage2.setDateline(System.currentTimeMillis() + "");
            happyHandMessage2.setTitle(emp1.getNickname()+" 已经把你从好友列表中删除");
            happyHandMessage2.setEmpid(emp2.getEmpid());
            messagesDao.save(happyHandMessage2);

            if(!StringUtil.isNullOrEmpty(emp2.getChannelId())){
                BaiduPush.PushMsgToSingleDevice(Integer.parseInt(emp2.getDeviceType()), "系统消息", emp1.getNickname()+" 已经把你从好友列表中删除", "8", emp2.getChannelId());
            }

        }else {
//            throw new ServiceException("noexist");
        }

        return 200;
    }
}
