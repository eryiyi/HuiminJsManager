package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.RecordFavour;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordFavourVO extends RecordFavour {
    private String favourNickname;
    private String favourCover;

    public String getFavourNickname() {
        return favourNickname;
    }

    public void setFavourNickname(String favourNickname) {
        this.favourNickname = favourNickname;
    }

    public String getFavourCover() {
        return favourCover;
    }

    public void setFavourCover(String favourCover) {
        this.favourCover = favourCover;
    }
}
