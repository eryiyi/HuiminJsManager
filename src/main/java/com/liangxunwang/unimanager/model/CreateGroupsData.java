package com.liangxunwang.unimanager.model;

/**
 * Created by zhl on 2017/5/2.
 */
public class CreateGroupsData {
//    {
//        "action": "post",
//            "application": "5c98bf00-1903-11e7-8c54-6f4871eb0a00",
//            "uri": "http://a1.easemob.com/826321978/yayabaojian/chatgroups",
//            "entities": [],
//        "data": {
//        "groupid": "14988326535170"
//    },
//        "timestamp": 1493692382513,
//            "duration": 1,
//            "organization": "826321978",
//            "applicationName": "yayabaojian"
//    }
    private String action;
    private String application;
    private String uri;
    private GroupidData data;
    private String timestamp;
    private String duration;
    private String organization;
    private String applicationName;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public GroupidData getData() {
        return data;
    }

    public void setData(GroupidData data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
