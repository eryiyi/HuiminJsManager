package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.RecordComment;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordCommentVO extends RecordComment{
    private String commentNickname;
    private String commentCover;

    public String getCommentNickname() {
        return commentNickname;
    }

    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }

    public String getCommentCover() {
        return commentCover;
    }

    public void setCommentCover(String commentCover) {
        this.commentCover = commentCover;
    }
}
