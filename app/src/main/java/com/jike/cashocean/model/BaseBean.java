package com.jike.cashocean.model;

public class BaseBean {

    /**
     * ret : 200
     * data : {"code":100,"msg":""}
     * msg :
     */

    private int ret;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
