package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ming
 * @Date on 2019/12/10
 * @Description
 */
public class BillAppBean extends BaseBean {

    /**
     * data : {"code":100,"msg":"Gain success","datas":{"list":[{"id":1,"user_id":9,
     * "app_name":"0","repay_money":"12.35","repay_time":-157795200,"created_at":"09-12-2019",
     * "updated_at":null,"status":1,"repay_date":"01-01-1965","diff_time":20066},{"id":2,
     * "user_id":9,"app_name":"0","repay_money":"12.35","repay_time":-157795200,
     * "created_at":"09-12-2019","updated_at":null,"status":1,"repay_date":"01-01-1965",
     * "diff_time":20066},{"id":3,"user_id":9,"app_name":"0","repay_money":"3.67",
     * "repay_time":-157795200,"created_at":"09-12-2019","updated_at":null,"status":1,
     * "repay_date":"01-01-1965","diff_time":20066},{"id":7,"user_id":9,"app_name":"0",
     * "repay_money":"6469.00","repay_time":0,"created_at":"09-12-2019","updated_at":null,
     * "status":1,"repay_date":"01-01-1970","diff_time":18240},{"id":5,"user_id":9,
     * "app_name":"0","repay_money":"21644.00","repay_time":1575907200,"created_at":"09-12-2019",
     * "updated_at":null,"status":1,"repay_date":"10-12-2019","diff_time":0},{"id":8,"user_id":9,
     * "app_name":"0","repay_money":"66499.00","repay_time":1575993599,"created_at":"09-12-2019",
     * "updated_at":null,"status":1,"repay_date":"10-12-2019","diff_time":0},{"id":6,"user_id":9,
     * "app_name":"0","repay_money":"61664.00","repay_time":1575993600,"created_at":"09-12-2019",
     * "updated_at":null,"status":1,"repay_date":"11-12-2019","diff_time":1},{"id":9,"user_id":9,
     * "app_name":"0","repay_money":"6464464.00","repay_time":1576252799,
     * "created_at":"10-12-2019","updated_at":null,"status":1,"repay_date":"13-12-2019",
     * "diff_time":3},{"id":4,"user_id":9,"app_name":"0","repay_money":"147258.00",
     * "repay_time":1577721600,"created_at":"09-12-2019","updated_at":null,"status":1,
     * "repay_date":"31-12-2019","diff_time":21}]}}
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
         * datas : {"list":[{"id":1,"user_id":9,"app_name":"0","repay_money":"12.35",
         * "repay_time":-157795200,"created_at":"09-12-2019","updated_at":null,"status":1,
         * "repay_date":"01-01-1965","diff_time":20066},{"id":2,"user_id":9,"app_name":"0",
         * "repay_money":"12.35","repay_time":-157795200,"created_at":"09-12-2019",
         * "updated_at":null,"status":1,"repay_date":"01-01-1965","diff_time":20066},{"id":3,
         * "user_id":9,"app_name":"0","repay_money":"3.67","repay_time":-157795200,
         * "created_at":"09-12-2019","updated_at":null,"status":1,"repay_date":"01-01-1965",
         * "diff_time":20066},{"id":7,"user_id":9,"app_name":"0","repay_money":"6469.00",
         * "repay_time":0,"created_at":"09-12-2019","updated_at":null,"status":1,
         * "repay_date":"01-01-1970","diff_time":18240},{"id":5,"user_id":9,"app_name":"0",
         * "repay_money":"21644.00","repay_time":1575907200,"created_at":"09-12-2019",
         * "updated_at":null,"status":1,"repay_date":"10-12-2019","diff_time":0},{"id":8,
         * "user_id":9,"app_name":"0","repay_money":"66499.00","repay_time":1575993599,
         * "created_at":"09-12-2019","updated_at":null,"status":1,"repay_date":"10-12-2019",
         * "diff_time":0},{"id":6,"user_id":9,"app_name":"0","repay_money":"61664.00",
         * "repay_time":1575993600,"created_at":"09-12-2019","updated_at":null,"status":1,
         * "repay_date":"11-12-2019","diff_time":1},{"id":9,"user_id":9,"app_name":"0",
         * "repay_money":"6464464.00","repay_time":1576252799,"created_at":"10-12-2019",
         * "updated_at":null,"status":1,"repay_date":"13-12-2019","diff_time":3},{"id":4,
         * "user_id":9,"app_name":"0","repay_money":"147258.00","repay_time":1577721600,
         * "created_at":"09-12-2019","updated_at":null,"status":1,"repay_date":"31-12-2019",
         * "diff_time":21}]}
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
                 * id : 1
                 * user_id : 9
                 * app_name : 0
                 * repay_money : 12.35
                 * repay_time : -157795200
                 * created_at : 09-12-2019
                 * updated_at : null
                 * status : 1
                 * repay_date : 01-01-1965
                 * diff_time : 20066
                 */

                private int id;
                private int user_id;
                private String app_name;
                private String repay_money;
                private int repay_time;
                private String created_at;
                private Object updated_at;
                private int status;

                public String getApp_logo() {
                    return app_logo;
                }

                public void setApp_logo(String app_logo) {
                    this.app_logo = app_logo;
                }

                private String app_logo;
                private String repay_date;
                private int diff_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getApp_name() {
                    return app_name;
                }

                public void setApp_name(String app_name) {
                    this.app_name = app_name;
                }

                public String getRepay_money() {
                    return repay_money;
                }

                public void setRepay_money(String repay_money) {
                    this.repay_money = repay_money;
                }

                public int getRepay_time() {
                    return repay_time;
                }

                public void setRepay_time(int repay_time) {
                    this.repay_time = repay_time;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public Object getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(Object updated_at) {
                    this.updated_at = updated_at;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getRepay_date() {
                    return repay_date;
                }

                public void setRepay_date(String repay_date) {
                    this.repay_date = repay_date;
                }

                public int getDiff_time() {
                    return diff_time;
                }

                public void setDiff_time(int diff_time) {
                    this.diff_time = diff_time;
                }
            }
        }
    }
}
