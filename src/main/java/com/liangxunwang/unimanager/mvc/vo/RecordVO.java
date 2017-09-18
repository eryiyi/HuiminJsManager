package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.Record;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordVO extends Record {
    private String mobile;
    private String nickname;
    private String cover;

    private int commentNum;
    private int favourNum;

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getFavourNum() {
        return favourNum;
    }

    public void setFavourNum(int favourNum) {
        this.favourNum = favourNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
