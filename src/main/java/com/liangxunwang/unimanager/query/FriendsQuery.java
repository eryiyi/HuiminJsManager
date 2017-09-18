package com.liangxunwang.unimanager.query;

/**
 * Created by zhl on 2015/1/31.
 */
public class FriendsQuery {
    private int index;
    private int size;

    private String empid1;
    private String empid2;
    private String is_check;
    private String starttime;
    private String endtime;
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public String getEmpid1() {
        return empid1;
    }

    public void setEmpid1(String empid1) {
        this.empid1 = empid1;
    }

    public String getEmpid2() {
        return empid2;
    }

    public void setEmpid2(String empid2) {
        this.empid2 = empid2;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
