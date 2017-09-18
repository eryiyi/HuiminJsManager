package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.chat.impl.EasemobIMUsers;
import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("appEmpService")
public class AppEmpService implements ExecuteService,SaveService,UpdateService,ListService,FindService {

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    private EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    @Autowired
    @Qualifier("loginEmpDao")
    private LoginEmpDao loginEmpDao;

    @Override
    public Object execute(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String mobile = (String) params[0];
        String password = (String) params[1];
        Emp member = empDao.findByMobile(mobile);
        if (member == null){
            throw new ServiceException("NotFound");
        }
        if(StringUtil.isNullOrEmpty(member.getPassword())){
            //密码为空
            throw new ServiceException("PassNull");
        }
        if (!new MD5Util().getMD5ofStr(password).equals(member.getPassword())){
            throw new ServiceException("PassError");
        }
        if ("0".equals(member.getIs_use())){
            throw new ServiceException("NotUse");
        }
//        if("2".equals(member.getIs_use())){
//            throw new ServiceException("NotUpdateZiliao");
//        }
        if(!StringUtil.isNullOrEmpty(member.getCover())){
            if (member.getCover().startsWith("upload")) {
                member.setCover(Constants.URL + member.getCover());
            }else {
                member.setCover(Constants.QINIU_URL + member.getCover()+"-yasuoone");
            }
        }

        if(!StringUtil.isNullOrEmpty(member.getPname())){
            member.setPname(member.getPname().trim());
        }
        //统计登录
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("empid", member.getEmpid());
        map1.put("logindate", DateUtil.getDateAndTimeTwo1());
        long l1 = loginEmpDao.count(map1);
        if(l1 == 0){
            //说明今天没有登录过，可以统计新的登录
            LoginEmp loginEmp = new LoginEmp();
            loginEmp.setLogin_id(UUIDFactory.random());
            loginEmp.setDateline(System.currentTimeMillis() + "");
            loginEmp.setEmpid(member.getEmpid());
            loginEmp.setIs_login("0");
            loginEmp.setLogindate(DateUtil.getDateAndTimeTwo1());
            loginEmpDao.save(loginEmp);
        }else{
            //说明今天登陆过了 更新为登录状态
            LoginEmp loginEmp = new LoginEmp();
            loginEmp.setEmpid(member.getEmpid());
            loginEmp.setIs_login("0");
            loginEmp.setLogindate(DateUtil.getDateAndTimeTwo1());
            loginEmpDao.update(loginEmp);
        }

        return member;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        Emp emp = (Emp) object;
        //查询该手机号是否已经注册
        Emp emp1 = empDao.findByMobile(emp.getMobile());
        if(emp1 != null){
            throw new ServiceException("MobileHasExist");
        }
        emp.setEmpid(UUIDFactory.random());
        emp.setDateline(System.currentTimeMillis() + "");
        emp.setIs_use("1");//0否 1是
        if(!StringUtil.isNullOrEmpty(emp.getPassword())){
            emp.setPassword(new MD5Util().getMD5ofStr(emp.getPassword()));//密码加密
        }
        emp.setRolestate("1");//0技师  1会员 前台注册的都是普通会员
        emp.setNickname(emp.getMobile().substring(7,11));
        emp.setCover(Constants.PHOTOURLS[new Random().nextInt(61)]);//头像
        String yqnum = emp.getYqnum();//获得该注册用户填写的邀请码
        //根据邀请码查询技师信息
        if(!StringUtil.isNullOrEmpty(yqnum)){
            Emp emp2 = empDao.findByCard(yqnum);
            if(emp2 != null){
                emp.setTjempid(emp2.getEmpid());//设置推荐人ID
            }
        }
        emp.setEmp_sex("0");//默认女
        emp.setLevel_id(Constants.DEFAULT_LEVEL);//默认青铜会员
        if(StringUtil.isNullOrEmpty(emp.getEmp_up_mobile())){
            emp.setEmp_up_mobile("10000000000");
        }
        String emp_number = "";
        //生成账号相关
        emp_number = StringUtil.getStringRandom();
        Emp member1 = empDao.findByNumber(emp_number);
        if(member1 != null){
            //说明改账号存在了
            emp_number = StringUtil.getStringRandom();
            Emp member2 = empDao.findByNumber(emp_number);
            if(member2 != null){
                //说明该账号存在了
                //todo
            }else {
                emp.setEmp_number(emp_number);
            }
        }else{
            emp.setEmp_number(emp_number);
        }

        //查询上级
        Emp memberUp = empDao.findByMobile(emp.getEmp_up_mobile());
        if(memberUp != null){
            //说明有上级
            emp.setEmp_up(memberUp.getEmpid());
        }else{
            emp.setEmp_up(Constants.MOBILE_UP_DEFAULT_id);
        }

        empDao.save(emp);
        //同步在环信注册该用户
        RegisterUsers users = new RegisterUsers();
//        User user = new User().username(emp.getEmpid() + new Random().nextInt(500)).password("123456");
        User user = new User().username(emp.getEmpid()).password("123456");
        users.add(user);
        Object result = easemobIMUsers.createNewIMUserSingle(users);
        Assert.assertNotNull(result);

        //钱包表
        MinePackage minePackage = new MinePackage();
        minePackage.setPackage_id(UUIDFactory.random());
        minePackage.setEmp_id(emp.getEmpid());
        minePackage.setPackage_money("0");
        minePackageDao.save(minePackage);
        //积分表
        Count count = new Count();
        count.setId(UUIDFactory.random());
        count.setEmpId(emp.getEmpid());
        count.setCount(String.valueOf(0));
        countDao.save(count);


        //注册成功之后 系统通知
        HappyHandMessage happyHandMessage = new HappyHandMessage();
        happyHandMessage.setMsgid(UUIDFactory.random());
        happyHandMessage.setDateline(System.currentTimeMillis() + "");
        happyHandMessage.setTitle("欢迎并感谢注册、使用丫丫保健APP！请使用搜索功能，查找、添加会员，浏览会员信息。");
        happyHandMessage.setEmpid(emp.getEmpid());
        messagesDao.save(happyHandMessage);
        return emp;
    }

    @Autowired
    @Qualifier("countDao")
    private CountDao countDao;

    @Autowired
    @Qualifier("minePackageDao")
    private MinePackageDao minePackageDao;

    @Autowired
    @Qualifier("photosDao")
    private PhotosDao photosDao;

    @Override
    public Object update(Object object) {
        Emp emp = (Emp) object;
        if(!StringUtil.isNullOrEmpty(emp.getEmpid()) && !StringUtil.isNullOrEmpty(emp.getCover())){
            //更新头像
            empDao.updateCover(emp.getEmpid(), emp.getCover());
            //添加头像文件到相册
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("empid", emp.getEmpid());
            List<HappyHandPhoto> lists = photosDao.findByEmpid(map);

            if(lists != null && lists.size() > 0){
                //说明存在了 更新
                HappyHandPhoto photo = lists.get(0);//原先的相册
                photo.setPhotos(photo.getPhotos()+","+ emp.getCover());
                photosDao.update(photo);
            }else {
                //添加
                HappyHandPhoto photo = new  HappyHandPhoto();
                photo.setPhotoid(UUIDFactory.random());
                photo.setDateline(System.currentTimeMillis() + "");
                photo.setEmpid(emp.getEmpid());
                photo.setPhotos(emp.getCover());
                photosDao.save(photo);
            }
        }
        return 200;
    }

    //查询推荐人
    @Override
    public Object list(Object object) throws ServiceException {
        Map<String, String> map1 = (Map<String, String>) object;
        String empid = map1.get("empid");
        String size = map1.get("size");
        String sex = map1.get("sex");

        List<Emp> list = new ArrayList<Emp>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index", size);
        map.put("size", 10);

        List<Emp> list1 = empDao.lists(map);

        return list;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String empid = (String) object;
        Emp member = empDao.findById(empid);
        if(member != null){
            if(!StringUtil.isNullOrEmpty(member.getCover())){
                if (member.getCover().startsWith("upload")) {
                    member.setCover(Constants.URL + member.getCover());
                }else {
                    member.setCover(Constants.QINIU_URL + member.getCover()+"-yasuoone");
                }
            }
            if(!StringUtil.isNullOrEmpty(member.getPname())){
                member.setPname(member.getPname().trim());
            }
        }
        return member;
    }
}
