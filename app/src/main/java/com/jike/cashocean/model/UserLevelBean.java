package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ming
 * @Date on 2019/12/11
 * @Description
 */
public class UserLevelBean extends BaseBean {

    /**
     * data : {"code":100,"msg":"","datas":{"level":1}}
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
         * msg :
         * datas : {"level":1}
         */

        private int code;
        @SerializedName("msg")
        private String msgX;
        private DatasBean datas;

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

        public DatasBean getDatas() {
            return datas;
        }

        public void setDatas(DatasBean datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * level : 1
             */

            private int level;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }
        }
    }
}
