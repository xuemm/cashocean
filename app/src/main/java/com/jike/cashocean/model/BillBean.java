package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillBean extends BaseBean {
    /**
     * data : {"code":100,"msg":"Gain success","datas":{"list":[{"app_name":"Opeso",
     * "logo_img":"http://img.cashmall24.com/images/191209115836c717916ebd49f2b9d032132848ddaf18
     * .png"}]}}
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
         * datas : {"list":[{"app_name":"Opeso","logo_img":"http://img.cashmall24
         * .com/images/191209115836c717916ebd49f2b9d032132848ddaf18.png"}]}
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
            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * app_name : Opeso
                 * logo_img : http://img.cashmall24
                 * .com/images/191209115836c717916ebd49f2b9d032132848ddaf18.png
                 */

                private String app_name;
                private String logo_img;

                public String getApp_name() {
                    return app_name;
                }

                public void setApp_name(String app_name) {
                    this.app_name = app_name;
                }

                public String getLogo_img() {
                    return logo_img;
                }

                public void setLogo_img(String logo_img) {
                    this.logo_img = logo_img;
                }
            }
        }
    }
}
