package com.liangxunwang.unimanager.model.tip;

public class ErrorTip implements Tip {
    private boolean success;
    private int code;
    private String message;

    public ErrorTip(int code, String message) {
        super();
        this.success = false;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
