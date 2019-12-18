package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ming
 * @Date on 2019/12/9
 * @Description
 */
public class NormalBean extends BaseBean {

    /**
     * data : {"code":100,"msg":"Added"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 100
         * msg : Added
         */

        private int code;
        @SerializedName("msg")
        private String msgX;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }
    }
}
