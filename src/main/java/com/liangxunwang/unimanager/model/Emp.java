package com.liangxunwang.unimanager.model;

/**
 * Created by zhl on 2017/4/3.
 */
public class Emp
{
    private String empid;
    private String mobile;
    private String password;
    private String nickname;
    private String cover;

    private String provinceid;
    private String cityid;
    private String areaid;
    private String emp_sex;

    private String rzstate2;//0否 1是

    private String is_use;
    private String dateline;
    private String userId;
    private String channelId;

    private String rolestate;//角色  0技师 1会员

    private String deviceType;
    private String is_push;//是否允许推送 默认0是 1否
    private String tjempid;//推荐人唯一ID
    private String tjnickname;//推荐人昵称
    private String tjmobile;//推荐人手机号
    private String yqnum;//技师的邀请码
    private String topnum;//排序数字 越大越靠前
    private String lat;
    private String lng;
    private String sign;


    //城市
    private String pname;
    private String cityName;
    private String is_manager;//是否是管理员 0否 1是  默认否

    private String emp_number;
    private String empType;


    private String levelName;
    private String level_zhe;

    private String emp_mobile_up;
    private String emp_name_up;

    private String lx_dxk_name;//定向卡等级
    private String depth;//深度


    private String level_id;//等级
    private String emp_erweima;//二维码
    private String emp_up;//上级

    private String emp_up_mobile;
    private String emp_pay_pass;
    private String lx_attribute_id;//分销等级ID，返利用； 默认0是普通等级，1是普通返利会员  2是店长
    private String is_card_emp;//是否是定向卡会员  默认0否  1是
    private String package_money;//零钱
    private String jfcount;//积分
    private String lx_dxk_level_id;//定向卡等级ID--后台设置

    public String getEmp_up_mobile() {
        return emp_up_mobile;
    }

    public void setEmp_up_mobile(String emp_up_mobile) {
        this.emp_up_mobile = emp_up_mobile;
    }

    public String getPackage_money() {
        return package_money;
    }

    public void setPackage_money(String package_money) {
        this.package_money = package_money;
    }

    public String getJfcount() {
        return jfcount;
    }

    public void setJfcount(String jfcount) {
        this.jfcount = jfcount;
    }

    public String getIs_card() {
        return is_card;
    }

    public void setIs_card(String is_card) {
        this.is_card = is_card;
    }

    private String is_card;//是否是定向卡商家 默认0否  1是


    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevel_zhe() {
        return level_zhe;
    }

    public void setLevel_zhe(String level_zhe) {
        this.level_zhe = level_zhe;
    }

    public String getEmp_mobile_up() {
        return emp_mobile_up;
    }

    public void setEmp_mobile_up(String emp_mobile_up) {
        this.emp_mobile_up = emp_mobile_up;
    }

    public String getEmp_name_up() {
        return emp_name_up;
    }

    public void setEmp_name_up(String emp_name_up) {
        this.emp_name_up = emp_name_up;
    }

    public String getLx_dxk_name() {
        return lx_dxk_name;
    }

    public void setLx_dxk_name(String lx_dxk_name) {
        this.lx_dxk_name = lx_dxk_name;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getEmp_sex() {
        return emp_sex;
    }

    public void setEmp_sex(String emp_sex) {
        this.emp_sex = emp_sex;
    }

    public String getEmp_number() {
        return emp_number;
    }

    public void setEmp_number(String emp_number) {
        this.emp_number = emp_number;
    }

    public String getEmp_pay_pass() {
        return emp_pay_pass;
    }

    public void setEmp_pay_pass(String emp_pay_pass) {
        this.emp_pay_pass = emp_pay_pass;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getEmp_erweima() {
        return emp_erweima;
    }

    public void setEmp_erweima(String emp_erweima) {
        this.emp_erweima = emp_erweima;
    }

    public String getEmp_up() {
        return emp_up;
    }

    public void setEmp_up(String emp_up) {
        this.emp_up = emp_up;
    }

    public String getLx_attribute_id() {
        return lx_attribute_id;
    }

    public void setLx_attribute_id(String lx_attribute_id) {
        this.lx_attribute_id = lx_attribute_id;
    }

    public String getIs_card_emp() {
        return is_card_emp;
    }

    public void setIs_card_emp(String is_card_emp) {
        this.is_card_emp = is_card_emp;
    }

    public String getLx_dxk_level_id() {
        return lx_dxk_level_id;
    }

    public void setLx_dxk_level_id(String lx_dxk_level_id) {
        this.lx_dxk_level_id = lx_dxk_level_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTjmobile() {
        return tjmobile;
    }

    public void setTjmobile(String tjmobile) {
        this.tjmobile = tjmobile;
    }

    public String getTjnickname() {
        return tjnickname;
    }

    public void setTjnickname(String tjnickname) {
        this.tjnickname = tjnickname;
    }

    public String getTopnum() {
        return topnum;
    }

    public void setTopnum(String topnum) {
        this.topnum = topnum;
    }

    public String getYqnum() {
        return yqnum;
    }

    public void setYqnum(String yqnum) {
        this.yqnum = yqnum;
    }

    public String getTjempid() {

        return tjempid;
    }

    public void setTjempid(String tjempid) {
        this.tjempid = tjempid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getRolestate() {
        return rolestate;
    }

    public void setRolestate(String rolestate) {
        this.rolestate = rolestate;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getRzstate2() {
        return rzstate2;
    }

    public void setRzstate2(String rzstate2) {
        this.rzstate2 = rzstate2;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIs_push() {
        return is_push;
    }

    public void setIs_push(String is_push) {
        this.is_push = is_push;
    }



    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(String is_manager) {
        this.is_manager = is_manager;
    }
}
