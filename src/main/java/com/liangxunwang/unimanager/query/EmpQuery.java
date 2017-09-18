package com.liangxunwang.unimanager.query;

/**
 * Created by zhl on 2015/1/31.
 */
public class EmpQuery {
    private int index;
    private int size;
    private String is_use;
    private String sex;
    private String education;
    private String marriage;
    private String state;
    private String rzstate1;
    private String rzstate2;
    private String rzstate3;
    private String empid;
    private String keywords;
    private String company;
    private String tjperson;
    private String tjmobile;
    private String is_select;//是否选项到期一个月以上的认证会员 0否 1是

    public String getTjperson() {
        return tjperson;
    }

    public void setTjperson(String tjperson) {
        this.tjperson = tjperson;
    }

    public String getTjmobile() {
        return tjmobile;
    }

    public void setTjmobile(String tjmobile) {
        this.tjmobile = tjmobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIs_select() {
        return is_select;
    }

    public void setIs_select(String is_select) {
        this.is_select = is_select;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRzstate1() {
        return rzstate1;
    }

    public void setRzstate1(String rzstate1) {
        this.rzstate1 = rzstate1;
    }

    public String getRzstate2() {
        return rzstate2;
    }

    public void setRzstate2(String rzstate2) {
        this.rzstate2 = rzstate2;
    }

    public String getRzstate3() {
        return rzstate3;
    }

    public void setRzstate3(String rzstate3) {
        this.rzstate3 = rzstate3;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
