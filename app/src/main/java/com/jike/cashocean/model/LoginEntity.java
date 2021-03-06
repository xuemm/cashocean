package com.jike.cashocean.model;

/**
 * Created by Yey on 2018/1/9.
 */

public class LoginEntity {

    /**
     * data : {"code":100,"datas":{"token":"71d140ece7d2f431aa1d7db6d90a929328"},"msg":"登录成功"}
     * msg :
     * ret : 200
     */

    private DataBean data;
    private String msg;
    private int ret;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public static class DataBean {
        /**
         * code : 100
         * datas : {"token":"71d140ece7d2f431aa1d7db6d90a929328"}
         * msg : 登录成功
         */

        private int code;
        private DatasBean datas;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DatasBean getDatas() {
            return datas;
        }

        public void setDatas(DatasBean datas) {
            this.datas = datas;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static class DatasBean {
            /**
             * token : 71d140ece7d2f431aa1d7db6d90a929328
             */

            private String token;

            public String getIs_auth() {
                return is_auth;
            }

            public void setIs_auth(String is_auth) {
                this.is_auth = is_auth;
            }

            private String is_auth;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
