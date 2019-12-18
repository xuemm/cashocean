package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ming
 * @Date on 2019/12/9
 * @Description
 */
public class UserBillInfoBean extends BaseBean {

    /**
     * data : {"code":100,"msg":"Gain success","datas":{"counts":8,"total_money":"303562.37"}}
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
         * msg : Gain success
         * datas : {"counts":8,"total_money":"303562.37"}
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

        public String  getMsgX() {
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
             * counts : 8
             * total_money : 303562.37
             */

            private int counts;
            private String total_money;

            public int getCounts() {
                return counts;
            }

            public void setCounts(int counts) {
                this.counts = counts;
            }

            public String getTotal_money() {
                return total_money;
            }

            public void setTotal_money(String total_money) {
                this.total_money = total_money;
            }
        }
    }
}
