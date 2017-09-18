package com.liangxunwang.unimanager.model;

/**
 * Created by zhl on 2017/4/16.
 */
public class Report {
    private String reportid;
    private String empid;
    private String nickname;//被举报人昵称
    private String mobile;//被举报人手机号
    private String content;
    private String is_read;
    private String dateline;
    private String empidBjbr;//被举报人ID 可以为空
    private String mobileBjbr;//被举报人ID 可以为空
    private String nicknameBjbr;//被举报人ID 可以为空
    private String empmobile;

    public String getEmpmobile() {
        return empmobile;
    }

    public void setEmpmobile(String empmobile) {
        this.empmobile = empmobile;
    }

    private String nicknameJbr;//举报人昵称
    private String mobileJbr;//举报人手机号

    public String getMobileBjbr() {
        return mobileBjbr;
    }

    public void setMobileBjbr(String mobileBjbr) {
        this.mobileBjbr = mobileBjbr;
    }

    public String getNicknameBjbr() {
        return nicknameBjbr;
    }

    public void setNicknameBjbr(String nicknameBjbr) {
        this.nicknameBjbr = nicknameBjbr;
    }

    public String getEmpidBjbr() {
        return empidBjbr;
    }

    public void setEmpidBjbr(String empidBjbr) {
        this.empidBjbr = empidBjbr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileJbr() {
        return mobileJbr;
    }

    public void setMobileJbr(String mobileJbr) {
        this.mobileJbr = mobileJbr;
    }

    public String getNicknameJbr() {
        return nicknameJbr;
    }

    public void setNicknameJbr(String nicknameJbr) {
        this.nicknameJbr = nicknameJbr;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}
