package test.bwie.com.mychatprogectitem.bean;

import java.util.List;

public class UserInfoBean {
    /**
     * result_message : success
     * data : [{"area":"安徽省安庆市枞阳县","picWidth":720,"createtime":1500291713620,"picHeight":1084,"gender":"男","lng":116.299659,"introduce":"一句话描述一下自己","imagePath":"http://qhb.2dyt.com/MyInterface/images/3a4d49b3-21b5-42af-b98f-2f82a67addcf.jpg","userId":86,"relationtime":1500292996161,"yxpassword":"w93S0Ym0","relation":0,"password":"202cb962ac59075b964b07152d234b70","lasttime":1500292657918,"phone":"13934722582","nickname":"123","age":"22","lat":40.040441},{"area":"广东省-潮州市-潮安县","picWidth":720,"createtime":1500078234255,"picHeight":960,"gender":"男","lng":40.040426,"introduce":"111","imagePath":"http://qhb.2dyt.com/MyInterface/images/2bf3b140-81df-4e00-9837-d3c72bebfdf3.jpg","userId":3,"relationtime":1500292987485,"yxpassword":"73Y59hA4","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500292831211,"phone":"13945290108","nickname":"xun","age":"20","lat":116.299781}]
     * result_code : 200
     */

    private String result_message;
    private int result_code;
    private List<DataBean> data;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 安徽省安庆市枞阳县
         * picWidth : 720
         * createtime : 1500291713620
         * picHeight : 1084
         * gender : 男
         * lng : 116.299659
         * introduce : 一句话描述一下自己
         * imagePath : http://qhb.2dyt.com/MyInterface/images/3a4d49b3-21b5-42af-b98f-2f82a67addcf.jpg
         * userId : 86
         * relationtime : 1500292996161
         * yxpassword : w93S0Ym0
         * relation : 0
         * password : 202cb962ac59075b964b07152d234b70
         * lasttime : 1500292657918
         * phone : 13934722582
         * nickname : 123
         * age : 22
         * lat : 40.040441
         */

        private String area;
        private int picWidth;
        private long createtime;
        private int picHeight;
        private String gender;
        private double lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private long relationtime;
        private String yxpassword;
        private int relation;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private String age;
        private double lat;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getRelationtime() {
            return relationtime;
        }

        public void setRelationtime(long relationtime) {
            this.relationtime = relationtime;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
