package com.jike.cashocean.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ming
 * @Date on 2019/12/10
 * @Description
 */
public class FindHomeBean extends BaseBean {

    /**
     * data : {"code":100,"msg":"Gain success","datas":{"banner_list":[{"banner_type":88,
     * "banner_value":"123","banner_image":"http://img.cashmall24
     * .com/images/191210035556b66e80258d302b827865c1c2216f8549.png"}],"list":[{"id":1,
     * "title":"测试","read_num":2858,"logo_img":"http://img.cashmall24
     * .com/images/1912091149306ae4bee8b402ce76507b857283f80691.png","created_at":"2019-12-09
     * 11:49:31"}]}}
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
         * datas : {"banner_list":[{"banner_type":88,"banner_value":"123",
         * "banner_image":"http://img.cashmall24
         * .com/images/191210035556b66e80258d302b827865c1c2216f8549.png"}],"list":[{"id":1,
         * "title":"测试","read_num":2858,"logo_img":"http://img.cashmall24
         * .com/images/1912091149306ae4bee8b402ce76507b857283f80691.png","created_at":"2019-12-09
         * 11:49:31"}]}
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
            private List<BannerListBean> banner_list;
            private List<ListBean> list;

            public List<BannerListBean> getBanner_list() {
                return banner_list;
            }

            public void setBanner_list(List<BannerListBean> banner_list) {
                this.banner_list = banner_list;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class BannerListBean {
                /**
                 * banner_type : 88
                 * banner_value : 123
                 * banner_image : http://img.cashmall24
                 * .com/images/191210035556b66e80258d302b827865c1c2216f8549.png
                 */

                private int banner_type;
                private String banner_value;
                private String banner_image;

                public int getBanner_type() {
                    return banner_type;
                }

                public void setBanner_type(int banner_type) {
                    this.banner_type = banner_type;
                }

                public String getBanner_value() {
                    return banner_value;
                }

                public void setBanner_value(String banner_value) {
                    this.banner_value = banner_value;
                }

                public String getBanner_image() {
                    return banner_image;
                }

                public void setBanner_image(String banner_image) {
                    this.banner_image = banner_image;
                }
            }

            public static class ListBean {
                /**
                 * id : 1
                 * title : 测试
                 * read_num : 2858
                 * logo_img : http://img.cashmall24
                 * .com/images/1912091149306ae4bee8b402ce76507b857283f80691.png
                 * created_at : 2019-12-09 11:49:31
                 */

                private int id;
                private String title;
                private int read_num;
                private String logo_img;
                private String created_at;

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

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }
            }
        }
    }
}
