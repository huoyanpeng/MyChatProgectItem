package test.bwie.com.mychatprogectitem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.emoji.LQREmotionKit;
import com.lqr.emoji.MoonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.adapter.MessageAdapter;
import test.bwie.com.mychatprogectitem.speex.SpeexRecorder;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;
import test.bwie.com.mychatprogectitem.utils.SDCardUtils;
import test.bwie.com.mychatprogectitem.widget.KeyBoardHelper;

import static android.R.attr.key;
import static android.content.Context.MODE_PRIVATE;
//implements IEmotionSelectedListener
public class ChatActivityActivity extends AppCompatActivity  {
//    @BindView(R.id.llContent)
//    LinearLayout mLlContent;//内容
//
//    @BindView(R.id.ivAudio)
//    ImageView mIvAudio;//语音
//    @BindView(R.id.btnAudio)
//    Button mBtnAudio;//摁住说话
//    @BindView(R.id.etContent)
//    EditText mEtContent;//输入框
//    @BindView(R.id.ivEmo)
//    ImageView mIvEmo;//表情
//    @BindView(R.id.ivMore)
//    ImageView mIvMore;//添加按钮
//    @BindView(R.id.btnSend)
//    Button mBtnSend;//发送
//
//    @BindView(R.id.flEmotionView)
//    FrameLayout mFlEmotionView;//表情容器
//    @BindView(R.id.elEmotion)
//    EmotionLayout mElEmotion;//表情包
//    @BindView(R.id.llMore)
//    LinearLayout mLlMore;//其他
//    @BindView(R.id.userfriend_chat_title_text)
//    TextView text_title;//标题
//    @BindView(R.id.userfriend_chat_image_back)
//    ImageView image_back;//返回按钮
//    @BindView(R.id.chat_listview)
//    ListView chat_listView;//对话框
//    private EmotionKeyboard mEmotionKeyboard;
//    private EMMessageListener msgListener;
//    private List<EMMessage> emMessageList=new ArrayList<>();;
//    private MessageAdapter adapter;
//    private int userId;
//
//    Handler handler = new Handler(){
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch ( msg.what ){
//                case 1:
//                    break;
//                case 2:
//                    adapter.notifyDataSetChanged();
//                    break;
//            }
//        }
//    } ;
//    private String nickname;
//    private SharedPreferences login;
//    private int myuserId;
//    private String friendImagPath;
//    private String fileName;
//    private SpeexRecorder speexRecorder;
//    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);
        ButterKnife.bind(this);


//
//        Intent intent = getIntent();
//        int num = intent.getIntExtra("num",0);
//        if (num == 2) {
//            userId = intent.getIntExtra("userId",0);
//            nickname = intent.getStringExtra("nickname");
//            friendImagPath = intent.getStringExtra("friendImagPath");
//        }else if (num==1){
//            userId = intent.getIntExtra("userId",0);
//            nickname = intent.getStringExtra("nickname");
//            friendImagPath = intent.getStringExtra("friendImagPath");
//        }
//        login = getSharedPreferences("login", MODE_PRIVATE);
//        myuserId = login.getInt("myuserId", 0);
//        String myImagePath = login.getString("myImagePath", null);
//        text_title.setText(nickname);
//
////        adapter = new MessageAdapter(this,emMessageList,myuserId,userId,friendImagPath,myImagePath);
////        chat_listView.setAdapter(adapter);
//
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////        chat_listView.setLayoutManager(linearLayoutManager);
////        adapter = new MessageAdapter(this, emMessageList);
////        chat_listView.setAdapter(adapter);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


//        initView();
//        initListener();
//        receive();
//        mBtnAudio.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
//                        System.out.println("filePath:" + filePath);
//                        File file = new File(filePath + "/");
//                        System.out.println("file:" + file);
//                        if (!file.exists()) {
//                            file.mkdirs();
//                        }
////                        fileName = file + File.separator + currentTimeMillis1 + ".spx";
//                        System.out.println("保存文件名：＝＝ " + fileName);
//                        speexRecorder = new SpeexRecorder(fileName, handler);
//                        Thread th = new Thread(speexRecorder);
//                        th.start();
//                        speexRecorder.setRecording(true);
//                        break;
//                    case MotionEvent.ACTION_UP:
////                        currentTimeMillis2 = System.currentTimeMillis();
//                        //    filePath为语音文件路径，length为录音时间(秒)
//                        speexRecorder.setRecording(false);
//                        long length = new File(fileName).length();
//                        EMMessage message = EMMessage.createVoiceSendMessage(filePath, 36, userId+"");
//                        //如果是群聊，设置chattype，默认是单聊
//                        EMClient.getInstance().chatManager().sendMessage(message);
//                        break;
//                }
//
//                return false;
//            }
//        });
//        mElEmotion.setEmotionSelectedListener(new IEmotionSelectedListener() {
//            @Override
//            public void onEmojiSelected(String key) {
//                if (mEtContent == null)
//                    return;
//                Editable editable = mEtContent.getText();
//                if (key.equals("/DEL")) {
//                    mEtContent.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
//                } else {
//                    int start = mEtContent.getSelectionStart();
//                    int end = mEtContent.getSelectionEnd();
//                    start = (start < 0 ? 0 : start);
//                    end = (start < 0 ? 0 : end);
//                    editable.replace(start, end, key);
//
//                    int editEnd = mEtContent.getSelectionEnd();
//                    MoonUtils.replaceEmoticons(LQREmotionKit.getContext(), editable, 0, editable.toString().length());
//                    mEtContent.setSelection(editEnd);
//                }
//            }
//            @Override
//            public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
//
//            }
//        });
//
//    }
//    private void setTextMessage() {
//        final EMMessage emMessage =  EMMessage.createTxtSendMessage(mEtContent.getText().toString().trim(),userId+"");
//        EMClient.getInstance().chatManager().sendMessage(emMessage);
//        emMessage.setMessageStatusCallback(new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                System.out.println("emMessage getMsgId= " + emMessage.getMsgId());
//                System.out.println("emMessage = onSuccess ");
//            }
//            @Override
//            public void onError(int i, String s) {
//
//            }
//            @Override
//            public void onProgress(int i, String s) {
//
//            }
//        });
//        emMessageList.add(emMessage);
//        adapter.notifyDataSetChanged();
//    }
//    private void receive() {
//        //收到消息
//        //收到透传消息
//        //收到已读回执
//        //收到已送达回执
//        //消息状态变动
//        msgListener = new EMMessageListener() {
//            @Override
//            public void onMessageReceived(List<EMMessage> messages) {
//                //收到消息
//                System.out.println("onMessageReceived messages = " + messages);
//
//                emMessageList.addAll(messages);
//                handler.sendEmptyMessage(2);
//            }
//            @Override
//            public void onCmdMessageReceived(List<EMMessage> messages) {
//                //收到透传消息
//                System.out.println("onCmdMessageReceived messages = " + messages);
//            }
//            @Override
//            public void onMessageRead(List<EMMessage> messages) {
//                //收到已读回执
//                System.out.println("onMessageRead messages = " + messages);
//
//            }
//
//            @Override
//            public void onMessageDelivered(List<EMMessage> message) {
//                //收到已送达回执
//                System.out.println("onMessageDelivered messages = " + message);
//            }
//            @Override
//            public void onMessageChanged(EMMessage message, Object change) {
//                //消息状态变动
//                System.out.println("onMessageChanged messages = " + message);
//            }
//        };
//        EMClient.getInstance().chatManager().addMessageListener(msgListener);
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mEtContent.clearFocus();
//    }
//
//
//    public void initView() {
//        mElEmotion.attachEditText(mEtContent);
//        initEmotionKeyboard();
//    }
//
//    public void initListener() {
//        mElEmotion.setEmotionSelectedListener(this);
//        mElEmotion.setEmotionAddVisiable(true);
//        mElEmotion.setEmotionSettingVisiable(true);
//        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
//            @Override
//            public void onEmotionAddClick(View view) {
//                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onEmotionSettingClick(View view) {
//                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mLlContent.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        closeBottomAndKeyboard();
//                        break;
//                }
//                return false;
//            }
//        });
//        mIvAudio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mBtnAudio.isShown()) {
//                    hideAudioButton();
//                    mEtContent.requestFocus();
//                    if (mEmotionKeyboard != null) {
//                        mEmotionKeyboard.showSoftInput();
//                    }
//                } else {
//                    showAudioButton();
//                    hideEmotionLayout();
//                    hideMoreLayout();
//                }
//            }
//        });
//
//        mEtContent.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mEtContent.getText().toString().trim().length() > 0) {
//                    mBtnSend.setVisibility(View.VISIBLE);
//                    mIvMore.setVisibility(View.GONE);
//                } else {
//                    mBtnSend.setVisibility(View.GONE);
//                    mIvMore.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        mBtnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                setTextMessage();
//                mEtContent.setText("");
//                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void initEmotionKeyboard() {
//        mElEmotion.attachEditText(mEtContent);
//        mEmotionKeyboard = EmotionKeyboard.with(this);
//        mEmotionKeyboard.bindToEditText(mEtContent);
//        mEmotionKeyboard.bindToContent(mLlContent);
//        mEmotionKeyboard.setEmotionLayout(mFlEmotionView);
//        mEmotionKeyboard.bindToEmotionButton(mIvEmo, mIvMore);
//        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
//            @Override
//            public boolean onEmotionButtonOnClickListener(View view) {
//                switch (view.getId()) {
//                    case R.id.ivEmo:
//                        if (!mElEmotion.isShown()) {
//                            if (mLlMore.isShown()) {
//                                showEmotionLayout();
//                                hideMoreLayout();
//                                hideAudioButton();
//                                return true;
//                            }
//                        } else if (mElEmotion.isShown() && !mLlMore.isShown()) {
//                            mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
//                            return false;
//                        }
//                        showEmotionLayout();
//                        hideMoreLayout();
//                        hideAudioButton();
//                        break;
//                    case R.id.ivMore:
//                        if (!mLlMore.isShown()) {
//                            if (mElEmotion.isShown()) {
//                                showMoreLayout();
//                                hideEmotionLayout();
//                                hideAudioButton();
//                                return true;
//                            }
//                        }
//                        showMoreLayout();
//                        hideEmotionLayout();
//                        hideAudioButton();
//                        break;
//                }
//                return false;
//            }
//        });
//    }
//
//    private void showAudioButton() {
//        mBtnAudio.setVisibility(View.VISIBLE);
//        mEtContent.setVisibility(View.GONE);
//        mIvAudio.setImageResource(R.mipmap.ic_cheat_keyboard);
//
//        if (mFlEmotionView.isShown()) {
//            if (mEmotionKeyboard != null) {
//                mEmotionKeyboard.interceptBackPress();
//            }
//        } else {
//            if (mEmotionKeyboard != null) {
//                mEmotionKeyboard.hideSoftInput();
//            }
//        }
//    }
//
//    private void hideAudioButton() {
//        mBtnAudio.setVisibility(View.GONE);
//        mEtContent.setVisibility(View.VISIBLE);
//        mIvAudio.setImageResource(R.mipmap.ic_cheat_voice);
//    }
//
//    private void showEmotionLayout() {
//        mElEmotion.setVisibility(View.VISIBLE);
//        mIvEmo.setImageResource(R.mipmap.ic_cheat_keyboard);
//    }
//
//    private void hideEmotionLayout() {
//        mElEmotion.setVisibility(View.GONE);
//        mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
//    }
//
//    private void showMoreLayout() {
//        mLlMore.setVisibility(View.VISIBLE);
//    }
//
//    private void hideMoreLayout() {
//        mLlMore.setVisibility(View.GONE);
//    }
//
//    private void closeBottomAndKeyboard() {
//        mElEmotion.setVisibility(View.GONE);
//        mLlMore.setVisibility(View.GONE);
//        if (mEmotionKeyboard != null) {
//            mEmotionKeyboard.interceptBackPress();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mElEmotion.isShown() || mLlMore.isShown()) {
//            mEmotionKeyboard.interceptBackPress();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public void onEmojiSelected(String key) {
//        Log.e("CSDN_LQR", "onEmojiSelected : " + key);
//    }
//
//    @Override
//    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
//        Toast.makeText(getApplicationContext(), stickerBitmapPath, Toast.LENGTH_SHORT).show();
//        Log.e("CSDN_LQR", "stickerBitmapPath : " + stickerBitmapPath);

    }
}
