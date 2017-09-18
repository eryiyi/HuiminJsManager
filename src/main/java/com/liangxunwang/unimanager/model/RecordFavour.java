package com.liangxunwang.unimanager.model;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordFavour {
    private String favour_id;
    private String record_id;
    private String favour_empid;
    private String favour_dateline;

    public String getFavour_id() {
        return favour_id;
    }

    public void setFavour_id(String favour_id) {
        this.favour_id = favour_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getFavour_empid() {
        return favour_empid;
    }

    public void setFavour_empid(String favour_empid) {
        this.favour_empid = favour_empid;
    }

    public String getFavour_dateline() {
        return favour_dateline;
    }

    public void setFavour_dateline(String favour_dateline) {
        this.favour_dateline = favour_dateline;
    }
}
