package test.bwie.com.mychatprogectitem.utils;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/7.
 * function:
 */
public class Constants {

    public static final int RESIZE_PIC = 720 ;


    public static final String LOGIN_ACTION = "http://qhb.2dyt.com/MyInterface/userAction_login.action" ;

    //主机地址
    public static String HOST_URL = "http://qhb.2dyt.com/";
    //169.254.69.102:8080
//    public static String HOST_URL = "http://169.254.69.102:8080/";

    public static String HOST_URL_2 = "MyInterface/";


    //    必须
    //      user.phone , user.password ,  user.nickname, user.gender,
    //    user.area   user.age , user.introduce ,user.sign , user.secretkey
    //  可选 user.lat , user.lng
    // post
    public static String REGIST_URL = HOST_URL + HOST_URL_2 + "userAction_add.action";


    //    必须
    //    user.phone , user.password ,  user.sign , user.secretkey
    //    可选 user.lat , user.lng

    //2 登录地址(get)
    public static String LOGIN_URL = HOST_URL + HOST_URL_2  + "userAction_login.action";


    //relationship.friendId=3
    //&relationship.groupName=a
    //http://localhost:8080/MyInterface/
    //userAction_addFriends.action?
    //relationship.friendId=3
    // &relationship.groupName=a
    //3 添加好友地址(get)
    public static String ADD_FRIEND_URL = HOST_URL + HOST_URL_2  + "userAction_addFriends.action";


    //chat.userId=1
    //&chat.touserId=2
    //&chat.message=你好

    //4 发送聊天消息(get)
    public static String SEND_MESSAGE_URL = HOST_URL + HOST_URL_2  + "userAction_chatMessage.action";


    // chat.userId=1&
    // chat.touserId=2&
    // pageIndex=0&
    // pageSize=2

    //5 查看聊天记录(get)
    public static String SEARCH_CHAT_LOG_URL = HOST_URL + HOST_URL_2  + "userAction_selectChat.action";


    // pageIndex=0
    // &pageSize=2

    //6 查询附近的人(get)
    public static String FIND_NEAR_USER_URL = HOST_URL + HOST_URL_2  + "userAction_selectAllUserAndFriend.action";


    //    必须
    //   user.userId,user.sign

    //7 查询用户详细信息(get)
    public static String SEARCH_USER_INFO_URL = HOST_URL + HOST_URL_2  + "userAction_selectUserById.action";


    //    必须
    //    user.file  user.currenttimer  user.sign
    //    8 上传头像(post)
    public static String UPLOAD_PROFILE_URL = HOST_URL + HOST_URL_2  + "userAction_uploadImage.action";


    // user.phone=123&
    // user.userId=1&
    // user.gender=男&
    // user.area=145&
    // user.nickname=545&
    // user.introduce=121

    //9 修改用户基本信息(get)
    public static String UPDATE_USER_INFO_URL = HOST_URL + HOST_URL_2  + "userAction_updateUser.action";


    // 必须必须必须必须存cookie 否则无法上传!!!!!!!!!!!!!!!!
    // user.file    (必填，文件)  file类型

    //10 修改头像(post)
    public static String UPDATE__PROFILE_URL = HOST_URL + HOST_URL_2  + "userAction_updateuploadImage.action";


    // newPassword=666

    //11 修改密码(get)
    public static String UPDATE_PASSWORD_URL = HOST_URL + HOST_URL_2  + "userAction_updatePassword.action";

    //    必须
    //        user.file  user.currenttimer  user.sign

    //12 上传图片到相册(post)
    public static String UPLOAD_PHOTOES_URL = HOST_URL + HOST_URL_2  + "userAction_uploadPhotoAlbum.action";

    //数据
    public static  String ALL_USER = "http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action" ;



    //7 添加好友 必须登录
    public static String ADD_FRIEND="http://qhb.2dyt.com/MyInterface/userAction_addFriends.action";
//     user.sign , relationship.friendId

//    8  好友列表 必须登录
     public static  String ADD_FRIEND_LOGIN="http://qhb.2dyt.com/MyInterface/userAction_selectAllUserAndFriend.action";

//     user.sign , user.currenttimer

//   9 好友朋友圈
    public static String FRIEND_CIRCLE="http://localhost:8080/MyInterface/userAction_selectFriends.action?user.sign=1&dynamicinfor.dynamicTime=11111";

//    http://localhost:8080/MyInterface/userAction_live.action?user.sign=1&live.type=1
//
//    type
//    1  主播  url
//    2 获取直播列表   playUrl 查看主播
//    3 主播关闭房间

    //        message.setAttribute("state", "live"); 表示直播消息

//        message.setAttribute("type", "leave"); 离开聊天室
//        message.setAttribute("type", "join");   加入聊天室



}