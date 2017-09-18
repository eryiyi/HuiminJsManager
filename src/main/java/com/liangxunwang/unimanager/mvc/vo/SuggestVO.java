package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.SuggestObj;

/**
 * Created by Administrator on 2016/2/14.
 */
public class SuggestVO extends SuggestObj{
    private String nickname;
    private String mobile;

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
}
