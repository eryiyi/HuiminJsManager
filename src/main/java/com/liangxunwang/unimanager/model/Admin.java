package com.liangxunwang.unimanager.model;

/**
 */
public class Admin {
    private String manager_id;
    private String manager_admin;
    private String manager_cover;
    private String manager_pass;
    private String is_use;
    private String emp_id;
    private String permissions;

    private String rname;

    private String is_pingtai;//是否是平台账号  默认0否 1是
    private String is_daili;//是否是代理  0否 1是  默认0

    public String getIs_pingtai() {
        return is_pingtai;
    }

    public void setIs_pingtai(String is_pingtai) {
        this.is_pingtai = is_pingtai;
    }

    public String getIs_daili() {
        return is_daili;
    }

    public void setIs_daili(String is_daili) {
        this.is_daili = is_daili;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_admin() {
        return manager_admin;
    }

    public void setManager_admin(String manager_admin) {
        this.manager_admin = manager_admin;
    }

    public String getManager_cover() {
        return manager_cover;
    }

    public void setManager_cover(String manager_cover) {
        this.manager_cover = manager_cover;
    }

    public String getManager_pass() {
        return manager_pass;
    }

    public void setManager_pass(String manager_pass) {
        this.manager_pass = manager_pass;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
