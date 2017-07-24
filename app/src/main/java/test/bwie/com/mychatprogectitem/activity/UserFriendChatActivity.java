package test.bwie.com.mychatprogectitem.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.adapter.MessageAdapter;
import test.bwie.com.mychatprogectitem.custom.EditTextPreIme;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;
import test.bwie.com.mychatprogectitem.widget.KeyBoardHelper;



public class UserFriendChatActivity extends AppCompatActivity implements KeyBoardHelper.OnKeyBoardStatusChangeListener , EditTextPreIme.EditTextListener{

    private Unbinder bind;
    @BindView(R.id.userfriend_chat_ed_text)
    EditTextPreIme ed_text;//输入文本
    @BindView(R.id.userfriend_chat_image_face)
    ImageView image_face;//表情按钮
    @BindView(R.id.userfriend_chat_image_plus)
    ImageView image_plus;//加号按钮
    @BindView(R.id.userfriend_chat_image_voice)
    ImageView image_voice;//语音按钮
    @BindView(R.id.userfriend_chat_text_send)
    Button text_send;//发送按钮
    @BindView(R.id.userfriend_chat_title_text)
    TextView text_title;
    @BindView(R.id.chat_listview)
    ListView chat_listView;
    @BindView(R.id.buttom_layout_view)
    LinearLayout buttomLayoutView;//表情占位布局
    @BindView(R.id.userfriend_chat_image_back)
    ImageView image_back;

    private EMMessageListener msgListener;
    private List<EMMessage> emMessageList=new ArrayList<>();;
    private MessageAdapter adapter;
    private int userId;
    //键盘高度
    private int kh;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ( msg.what ){
                case 1:
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    } ;
    private String nickname;
    private SharedPreferences login;
    private int myuserId;
    private String friendImagPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friend_chat);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);
        Intent intent = getIntent();
        int num = intent.getIntExtra("num",0);
        if (num == 2) {
            userId = intent.getIntExtra("userId",0);
            nickname = intent.getStringExtra("nickname");
            friendImagPath = intent.getStringExtra("friendImagPath");
        }else if (num==1){
            userId = intent.getIntExtra("userId",0);
            nickname = intent.getStringExtra("nickname");
            friendImagPath = intent.getStringExtra("friendImagPath");
        }
        login = getSharedPreferences("login", MODE_PRIVATE);
        myuserId = login.getInt("myuserId", 0);
        String myImagePath = login.getString("myImagePath", null);
        text_title.setText(nickname);

        adapter = new MessageAdapter(this,emMessageList,myuserId,userId,friendImagPath,myImagePath);
        chat_listView.setAdapter(adapter);

        kh = PreferencesUtils.getValueByKey(this, "kh", 0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttomLayoutView.getLayoutParams();
        params.height = kh;
        buttomLayoutView.setLayoutParams(params);
        setKeyBoardModelResize();
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this) ;
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);
        ed_text.setListener(this);
        ed_text.addTextChangedListener(mTextWatcher);  // 输入文本框监听字数变化
        ed_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(buttomLayoutView.getVisibility() == View.VISIBLE){
                    showKeyBoard();
                } else {
                    setKeyBoardModelResize();
                    buttomLayoutView.setVisibility(View.VISIBLE);
                }
                ed_text.setListener(UserFriendChatActivity.this);
                return false;
            }
        });
        // 1 表示表情  2 表示弹键
        image_face.setTag(1);
        receive();

    }







    TextWatcher mTextWatcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            image_plus.setVisibility(View.GONE);
            text_send.setVisibility(View.VISIBLE);
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

//    R.id.userfriend_chat_image_face,
    @OnClick({ R.id.userfriend_chat_image_face,R.id.userfriend_chat_text_send,R.id.userfriend_chat_ed_text,R.id.userfriend_chat_image_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userfriend_chat_image_face:
                ed_text.setListener(null);
                setKeyBoardModelPan();
                int tag = (int) image_face.getTag() ;
                if(tag == 1){
                    //表情
                    //表情按钮
                    buttomLayoutView.setVisibility(View.VISIBLE);
                    image_face.setTag(2);
                    hidenKeyBoard();
                } else {
                    //键盘
                    image_face.setTag(1);
                    change();
                }
                break;
            case R.id.userfriend_chat_text_send:
                setTextMessage();
                ed_text.setText("");
                break;
            case R.id.userfriend_chat_image_back:
                finish();
                break;
            case R.id.userfriend_chat_ed_text:

                break;
        }
    }

    private void change() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setTextMessage() {
        final EMMessage emMessage =  EMMessage.createTxtSendMessage(ed_text.getText().toString().trim(),userId+"");
        EMClient.getInstance().chatManager().sendMessage(emMessage);
        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("emMessage getMsgId= " + emMessage.getMsgId());

                System.out.println("emMessage = onSuccess ");
            }
            @Override
            public void onError(int i, String s) {

            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
        emMessageList.add(emMessage);
        adapter.notifyDataSetChanged();

    }

    private void receive() {
        //收到消息
        //收到透传消息
        //收到已读回执
        //收到已送达回执
        //消息状态变动
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                System.out.println("onMessageReceived messages = " + messages);

                emMessageList.addAll(messages);
                handler.sendEmptyMessage(2);

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息

                System.out.println("onCmdMessageReceived messages = " + messages);

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                System.out.println("onMessageRead messages = " + messages);

            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                System.out.println("onMessageDelivered messages = " + message);
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                System.out.println("onMessageChanged messages = " + message);

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        handler.sendEmptyMessageAtTime(1,200);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
    }


    //设置输入法模式 pan
    public  void setKeyBoardModelPan() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    //设置输入法模式 resize
    public  void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    //隐藏键盘
    public void hidenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_text.getWindowToken(), 0);
    }
    // 显示键盘
    public void showKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ed_text,InputMethodManager.SHOW_FORCED);
    }



    @Override
    public void onBack() {
        ed_text.setListener(null);

        System.out.println("chatTitle = onBack" );

        setKeyBoardModelResize();
        buttomLayoutView.setVisibility(View.GONE);
        image_face.setTag(1);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK ){
            System.out.println("chatTitle = onBack KEYCODE_BACK" );
            if(buttomLayoutView.getVisibility() == View.VISIBLE){
                buttomLayoutView.setVisibility(View.GONE);
                image_face.setTag(1);

                return  false;
            }else {
                return super.onKeyDown(keyCode, event);
            }
        } else{
            return super.onKeyDown(keyCode, event);
        }
    }
}
