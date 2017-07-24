package test.bwie.com.mychatprogectitem.bean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/22.
 * function:
 */
public class LoginBean {

    /**
     * result_message : success
     * data : {"area":"安徽省-安庆市-枞阳县","createtime":1500541315007,"gender":"男","lng":0,"introduce":"发达的说法都是发达的","imagePath":"http://qhb.2dyt.com/MyInterface/images/80531abe-83e0-4708-b50a-89f484b96f26.jpg","userId":150,"yxpassword":"R6u431VC","password":"e10adc3949ba59abbe56e057f20f883e","lasttime":1500686578126,"phone":"13888888888","nickname":"哦哦哦","lat":0}
     * result_code : 200
     */

    private String result_message;
    private DataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public static class DataBean {
        /**
         * area : 安徽省-安庆市-枞阳县
         * createtime : 1500541315007
         * gender : 男
         * lng : 0
         * introduce : 发达的说法都是发达的
         * imagePath : http://qhb.2dyt.com/MyInterface/images/80531abe-83e0-4708-b50a-89f484b96f26.jpg
         * userId : 150
         * yxpassword : R6u431VC
         * password : e10adc3949ba59abbe56e057f20f883e
         * lasttime : 1500686578126
         * phone : 13888888888
         * nickname : 哦哦哦
         * lat : 0
         */

        private String area;
        private long createtime;
        private String gender;
        private int lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private String yxpassword;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private int lat;

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

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
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

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
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

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }
    }
}
