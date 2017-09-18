package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpDianpu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("empDao")
public interface EmpDao {
    void save(Emp emp);

    void updatePass(@Param(value = "empid") String empid, @Param(value = "password") String password);

    List<Emp> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    long countDay(Map<String, Object> map);

    Emp findById(String empid);

    Emp findByMobile(String mobile);

    Emp findByCard(String yqnum);

    void updateStatus(@Param(value = "empid") String empid, @Param(value = "is_use") String is_use);

    void updateCover(@Param(value = "empid") String empid, @Param(value = "cover") String is_use);

    //前台更新
    void updateProfile(Emp emp);

    //后台管理员修改会员数据
    void updateManage(Emp emp);

    void updateMobile(@Param(value = "empid") String empid, @Param(value = "mobile") String is_use);

    void updatePassByMobile(@Param(value = "mobile") String empid, @Param(value = "password") String password);

    void updateRzstate2(@Param(value = "empid") String empid, @Param(value = "rzstate2") String password);
    void updateRzstate3(@Param(value = "empid") String empid, @Param(value = "rzstate3") String password);

    void saveList(List<Emp> list);

    /**
     * 根据ID更新pushId
     * {id, userId, channelId, type}
     */
    void updatePushId(@Param(value = "empid") String id, @Param(value = "userId") String userId, @Param(value = "channelId") String channelId, @Param(value = "deviceType")String deviceType);

    List<Emp> listsSearch(Map<String, Object> map);

    List<Emp> listsSearchNearby(Map<String, Object> map);

    void updateIsPush(@Param(value = "is_push") String empid, @Param(value = "password") String password);

    void updateLocation(Emp emp);

    List<Emp> listsSearchTuijian(Map<String, Object> map);

    void updateType(@Param(value = "empType") String is_fengqun, @Param(value = "emp_id") String emp_id);
    void updateEmpUp(@Param(value = "emp_id")String empId, @Param(value = "emp_up")String flag);

    //修改支付密码
    void resetPayPass(Emp member);
    //上传经纬度  个人的
    void resetEmpLocation(Emp member);

    Emp findInfoById(String empId);

    List<Emp> listAllFensi(Map<String,Object> map);
    List<Emp> listAllFensiEmp(Map<String,Object> map);

    List<Emp> findAll(Map<String,Object> map);

    //根据用户Id更新定向卡
    void updateMemberDxkById(Emp member);

    /**
     * 修改密码
     * @param empId
     * @param rePass
     */
    void resetPass(@Param(value = "empId") String empId, @Param(value = "rePass") String rePass);


    //查询店铺
    List<EmpDianpu> listDianPu(Map<String,Object> map);

    //性别
    void modifyMemberSex(Emp member);

    /**
     * 注册会员数量
     * @return
     */
    long memberCount();

    //定向卡会员
    long memberCountDxk();


    long countDayDxk(Map<String, Object> map);

    Emp findByNumber(String emp_number);//根据账号查找


}
