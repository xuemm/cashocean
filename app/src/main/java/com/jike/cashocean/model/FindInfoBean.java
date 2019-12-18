package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ming
 * @Date on 2019/12/10
 * @Description
 */
public class FindInfoBean extends BaseBean {
    /**
     * data : {"code":100,"msg":"","datas":{"id":1,"title":"测试","read_num":2858,
     * "logo_img":"http://img.cashmall24.com/images/1912091149306ae4bee8b402ce76507b857283f80691
     * .png","master_img":"http://img.cashmall24
     * .com/images/191209114930e5837e553ed818ca3199e946d1346192.png","content":"<p>测试<\/p>",
     * "created_at":"2019-12-09 11:49:31","updated_at":"2019-12-09 11:49:31","deleted_at":null,
     * "is_use":1,"pv":0,"uv":0}}
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
         * datas : {"id":1,"title":"测试","read_num":2858,"logo_img":"http://img.cashmall24
         * .com/images/1912091149306ae4bee8b402ce76507b857283f80691.png","master_img":"http://img
         * .cashmall24.com/images/191209114930e5837e553ed818ca3199e946d1346192.png",
         * "content":"<p>测试<\/p>","created_at":"2019-12-09 11:49:31","updated_at":"2019-12-09
         * 11:49:31","deleted_at":null,"is_use":1,"pv":0,"uv":0}
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
             * id : 1
             * title : 测试
             * read_num : 2858
             * logo_img : http://img.cashmall24
             * .com/images/1912091149306ae4bee8b402ce76507b857283f80691.png
             * master_img : http://img.cashmall24
             * .com/images/191209114930e5837e553ed818ca3199e946d1346192.png
             * content : <p>测试</p>
             * created_at : 2019-12-09 11:49:31
             * updated_at : 2019-12-09 11:49:31
             * deleted_at : null
             * is_use : 1
             * pv : 0
             * uv : 0
             */

            private int id;
            private String title;
            private int read_num;
            private String logo_img;
            private String master_img;
            private String content;
            private String created_at;
            private String updated_at;
            private Object deleted_at;
            private int is_use;
            private int pv;
            private int uv;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getRead_num() {
                return read_num;
            }

            public void setRead_num(int read_num) {
                this.read_num = read_num;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getMaster_img() {
                return master_img;
            }

            public void setMaster_img(String master_img) {
                this.master_img = master_img;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public Object getDeleted_at() {
                return deleted_at;
            }

            public void setDeleted_at(Object deleted_at) {
                this.deleted_at = deleted_at;
            }

            public int getIs_use() {
                return is_use;
            }

            public void setIs_use(int is_use) {
                this.is_use = is_use;
            }

            public int getPv() {
                return pv;
            }

            public void setPv(int pv) {
                this.pv = pv;
            }

            public int getUv() {
                return uv;
            }

            public void setUv(int uv) {
                this.uv = uv;
            }
        }
    }
}
