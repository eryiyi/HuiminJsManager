package com.liangxunwang.unimanager.model;

/**
 * Created by Administrator on 2016/2/22.
 */
public class SuggestObj {
    private String mm_suggest_id;
    private String mm_suggest_cont;
    private String mm_emp_id;
    private String dateline;
    private String is_check;

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    public String getMm_suggest_id() {
        return mm_suggest_id;
    }

    public void setMm_suggest_id(String mm_suggest_id) {
        this.mm_suggest_id = mm_suggest_id;
    }

    public String getMm_suggest_cont() {
        return mm_suggest_cont;
    }

    public void setMm_suggest_cont(String mm_suggest_cont) {
        this.mm_suggest_cont = mm_suggest_cont;
    }

    public String getMm_emp_id() {
        return mm_emp_id;
    }

    public void setMm_emp_id(String mm_emp_id) {
        this.mm_emp_id = mm_emp_id;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
}
