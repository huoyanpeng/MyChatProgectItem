package test.bwie.com.mychatprogectitem.bean;

import java.io.Serializable;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/17.
 * function:
 */
public class FriendBean implements Serializable{

    /**
     * addUser : {"age":"20","area":"安徽省-安庆市-枞阳县","createtime":1500278161671,"gender":"男","imagePath":"http://qhb.2dyt.com/MyInterface/images/a0114334-f24b-4d26-b8e2-5b9f0a14de2a.jpg","introduce":"dfasfasdfadsfasdf","lasttime":1500278161671,"lat":0,"lng":0,"nickname":"kkkk","password":"e10adc3949ba59abbe56e057f20f883e","phone":"13500000000","picHeight":540,"picWidth":720,"relation":0,"userId":72,"yxpassword":"J861M693"}
     * result_code : 200
     * result_message : 添加好友成功
     */

    private AddUserBean addUser;
    private int result_code;
    private String result_message;

    public AddUserBean getAddUser() {
        return addUser;
    }

    public void setAddUser(AddUserBean addUser) {
        this.addUser = addUser;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public static class AddUserBean {
        /**
         * age : 20
         * area : 安徽省-安庆市-枞阳县
         * createtime : 1500278161671
         * gender : 男
         * imagePath : http://qhb.2dyt.com/MyInterface/images/a0114334-f24b-4d26-b8e2-5b9f0a14de2a.jpg
         * introduce : dfasfasdfadsfasdf
         * lasttime : 1500278161671
         * lat : 0
         * lng : 0
         * nickname : kkkk
         * password : e10adc3949ba59abbe56e057f20f883e
         * phone : 13500000000
         * picHeight : 540
         * picWidth : 720
         * relation : 0
         * userId : 72
         * yxpassword : J861M693
         */

        private String age;
        private String area;
        private long createtime;
        private String gender;
        private String imagePath;
        private String introduce;
        private long lasttime;
        private int lat;
        private int lng;
        private String nickname;
        private String password;
        private String phone;
        private int picHeight;
        private int picWidth;
        private int relation;
        private int userId;
        private String yxpassword;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }
    }
}
