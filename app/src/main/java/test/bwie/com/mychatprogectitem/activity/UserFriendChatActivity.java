package test.bwie.com.mychatprogectitem.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.RuntimePermissions;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.adapter.MessageAdapter;
import test.bwie.com.mychatprogectitem.custom.EditTextPreIme;
import test.bwie.com.mychatprogectitem.emojicon.EaseSmileUtils;
import test.bwie.com.mychatprogectitem.speex.SpeexRecorder;

import test.bwie.com.mychatprogectitem.utils.Constants;
import test.bwie.com.mychatprogectitem.utils.ImageResizeUtils;
import test.bwie.com.mychatprogectitem.utils.MyDecoration;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;
import test.bwie.com.mychatprogectitem.utils.SDCardUtils;
import test.bwie.com.mychatprogectitem.widget.KeyBoardHelper;

import static android.R.attr.bitmap;
import static android.app.Activity.RESULT_OK;
import static test.bwie.com.mychatprogectitem.utils.ImageResizeUtils.copyStream;

public class UserFriendChatActivity extends AppCompatActivity implements KeyBoardHelper.OnKeyBoardStatusChangeListener, EditTextPreIme.EditTextListener {

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
    @BindView(R.id.re_listview)
    RecyclerView re_listview;
    @BindView(R.id.linear_voice)
    LinearLayout linear_voice;
    @BindView(R.id.keyboard_one)
    ImageView keyboard_one;
    @BindView(R.id.button_seepx)
    Button button_seepx;
    @BindView(R.id.buttom_layout_view)
    LinearLayout buttomLayoutView;//表情占位布局
    @BindView(R.id.userfriend_chat_image_back)
    ImageView image_back;
    @BindView(R.id.linear_dialogue)
    LinearLayout linear_dialogue;
    @BindView(R.id.buttom_layout_plus)
    LinearLayout buttom_layout_plus;
    @BindView(R.id.image_video_call)
    ImageView image_video_call;
    @BindView(R.id.ivAlbum)
    ImageView ivAlbum;//上传图片
    @BindView(R.id.ivLocation)
    ImageView ivLocation;
    private EMMessageListener msgListener;
    private List<EMMessage> emMessageList = new ArrayList<>();
    ;
    private MessageAdapter adapter;
    private int userId;
    //键盘高度
    private int kh;
    EaseEmojiconMenuBase emojiconMenu;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    //显示最新消息
                    re_listview.scrollToPosition(adapter.getItemCount() - 1);
                    break;
            }
        }
    };
    private String nickname;
    private SharedPreferences login;
    private int myuserId;
    private String friendImagPath;
    private String myImagePath;
    private String fileName;
    private long currentTimeMillis01;
    private String filePath;
    private long currentTimeMillis02;
    private SpeexRecorder speexRecorder;
    public AMapLocationListener mLocationListener = null;
    static final int INTENTFORCAMERA = 1;
    static final int INTENTFORPHOTO = 2;
    public String LocalPhotoName;

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
        int num = intent.getIntExtra("num", 0);
        if (num == 2) {
            userId = intent.getIntExtra("userId", 0);
            nickname = intent.getStringExtra("nickname");
            friendImagPath = intent.getStringExtra("friendImagPath");
        } else if (num == 1) {
            userId = intent.getIntExtra("userId", 0);
            nickname = intent.getStringExtra("nickname");
            friendImagPath = intent.getStringExtra("friendImagPath");
        }
        login = getSharedPreferences("login", MODE_PRIVATE);
        myuserId = login.getInt("myuserId", 0);
        myImagePath = login.getString("myImagePath", null);
        text_title.setText(nickname);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        re_listview.setLayoutManager(linearLayoutManager);
        re_listview.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        adapter = new MessageAdapter(this, emMessageList, myuserId, userId, friendImagPath, myImagePath);
        re_listview.setAdapter(adapter);

        kh = PreferencesUtils.getValueByKey(this, "kh", 0);
        EaseEmojiconMenu.LayoutParams params = (EaseEmojiconMenu.LayoutParams) buttomLayoutView.getLayoutParams();
        params.height = kh;
        buttomLayoutView.setLayoutParams(params);

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) buttom_layout_plus.getLayoutParams();
        params1.height = kh;
        buttom_layout_plus.setLayoutParams(params1);


        setKeyBoardModelResize();
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);

        ed_text.setListener(this);
        ed_text.addTextChangedListener(mTextWatcher);  // 输入文本框监听字数变化
        ed_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (buttomLayoutView.getVisibility() == View.VISIBLE) {
                    showKeyBoard();
                } else {
                    setKeyBoardModelResize();
                    buttomLayoutView.setVisibility(View.VISIBLE);
                    buttom_layout_plus.setVisibility(View.GONE);
                }
                ed_text.setListener(UserFriendChatActivity.this);
                return false;
            }
        });
        // 1 表示表情  2 表示弹键
        image_face.setTag(1);
        receive();
        initEmoje(null);
        initLisitener();
        button_seepx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button_seepx.setFocusable(true);
                        downStart();
                        break;
                    case MotionEvent.ACTION_UP:
                        button_seepx.setFocusable(false);
                        upStop();
                        break;
                }
                return false;
            }
        });


    }

    private void upStop() {
        currentTimeMillis02 = System.currentTimeMillis();
        long l = (currentTimeMillis02 - currentTimeMillis01) / 1000;
        int l1 = (int) l;
        speexRecorder.setRecording(false);
        EMMessage message = EMMessage.createVoiceSendMessage(fileName, l1, userId + "");
        //如果是群聊，设置chattype，默认是单聊
        EMClient.getInstance().chatManager().sendMessage(message);
        emMessageList.add(message);
        adapter.notifyDataSetChanged();
        re_listview.scrollToPosition(adapter.getItemCount() - 1);

    }

    private void downStart() {
        currentTimeMillis01 = System.currentTimeMillis();
        filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
        File file = new File(filePath + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        fileName = file + File.separator + System.currentTimeMillis() + ".spx";
        speexRecorder = new SpeexRecorder(fileName, handler);
        Thread th = new Thread(speexRecorder);
        th.start();
        speexRecorder.setRecording(true);


    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            image_plus.setVisibility(View.GONE);
            buttom_layout_plus.setVisibility(View.GONE);
            text_send.setVisibility(View.VISIBLE);

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @OnClick({R.id.userfriend_chat_image_face, R.id.userfriend_chat_text_send, R.id.userfriend_chat_ed_text, R.id.userfriend_chat_image_back,
            R.id.userfriend_chat_image_plus, R.id.userfriend_chat_image_voice, R.id.keyboard_one,
            R.id.image_video_call, R.id.ivAlbum, R.id.ivLocation})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userfriend_chat_image_face://表情图片按钮
                ed_text.setListener(null);
                setKeyBoardModelPan();
                int tag = (int) image_face.getTag();
                if (tag == 1) {
                    //表情
                    //表情按钮
                    buttomLayoutView.setVisibility(View.VISIBLE);
                    buttom_layout_plus.setVisibility(View.GONE);
                    image_face.setTag(2);
                    hidenKeyBoard();
                } else {
                    //键盘
                    image_face.setTag(1);
                    buttom_layout_plus.setVisibility(View.GONE);
                    linear_dialogue.setVisibility(View.GONE);
                    change();
                }
                break;
            case R.id.userfriend_chat_text_send:
                setTextMessage();
                ed_text.setText("");
                image_plus.setVisibility(View.VISIBLE);
                break;
            case R.id.userfriend_chat_image_back:
                finish();
                break;
            case R.id.userfriend_chat_image_voice:
                linear_dialogue.setVisibility(View.GONE);
                linear_voice.setVisibility(View.VISIBLE);
                buttomLayoutView.setVisibility(View.GONE);
                buttom_layout_plus.setVisibility(View.GONE);
                hidenKeyBoard();
                break;
            case R.id.keyboard_one:
                linear_dialogue.setVisibility(View.VISIBLE);
                linear_voice.setVisibility(View.GONE);
                buttom_layout_plus.setVisibility(View.GONE);
                buttomLayoutView.setVisibility(View.VISIBLE);
                change();
                showKeyBoard();
                break;
            case R.id.userfriend_chat_image_plus:
                buttom_layout_plus.setVisibility(View.VISIBLE);
                linear_dialogue.setVisibility(View.VISIBLE);
                linear_voice.setVisibility(View.GONE);
                buttomLayoutView.setVisibility(View.GONE);
                hidenKeyBoard();
                break;
            case R.id.image_video_call:
                TelActivity.startTelActivity(1 + "", userId + "", this);
                break;
            case R.id.ivAlbum:
                sendImage();
                break;
            case R.id.ivLocation:
                sendLocation();
                break;

        }
    }

    private void sendLocation() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                String address = aMapLocation.getAddress();
                System.out.println("latitude:" + latitude + ",,,longitude:" + longitude);
                EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, address, userId + "");
                //如果是群聊，设置chattype，默认是单聊
                EMClient.getInstance().chatManager().sendMessage(message);
                emMessageList.add(message);
                adapter.notifyDataSetChanged();
                //显示最新消息
                re_listview.scrollToPosition(adapter.getItemCount() - 1);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e("TAG->onresult", "ActivityResult resultCode error");
            return;
        }
        switch (requestCode) {
            case INTENTFORPHOTO:
                //相册
                try {
                    // 必须这样处理，不然在4.4.2手机上会出问题
                    Uri originalUri = data.getData();
                    File f = null;
                    if (originalUri != null) {
                        f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
                        if (null == actualimagecursor) {
                            if (originalUri.toString().startsWith("file:")) {
                                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                if (!file.exists()) {
                                    //地址包含中文编码的地址做utf-8编码
                                    originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        } else {
                            // 系统图库
                            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            String img_path = actualimagecursor.getString(actual_image_column_index);
                            if (img_path == null) {
                                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            } else {
                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }

                        }
                        Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);
                        FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                        if (bitmap != null) {
                            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                                fos.close();
                                fos.flush();
                            }
                            if (!bitmap.isRecycled()) {
                                bitmap.isRecycled();
                            }
                        }
                        System.out.println("path?????" + f.getAbsolutePath());
                        EMMessage imageSendMessage = EMMessage.createImageSendMessage(f.getAbsolutePath(), false, userId + "");
                        //如果是群聊，设置chattype，默认是单聊
                        emMessageList.add(imageSendMessage);
                        adapter.notifyDataSetChanged();
                        //显示最新消息
                        re_listview.scrollToPosition(adapter.getItemCount() - 1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case INTENTFORCAMERA:
                //相机
                try {
                    //file 就是拍照完 得到的原始照片
                    File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), Constants.RESIZE_PIC);
                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }
                    }
                    System.out.println("path??????//" + file.getAbsolutePath());
                    EMMessage imageSendMessage = EMMessage.createImageSendMessage(file.getAbsolutePath(), false, userId + "");
                    //如果是群聊，设置chattype，默认是单聊
                    EMClient.getInstance().chatManager().sendMessage(imageSendMessage);
                    emMessageList.add(imageSendMessage);
                    adapter.notifyDataSetChanged();
                    //显示最新消息
                    re_listview.scrollToPosition(adapter.getItemCount() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void sendImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择方式")
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            createLocalPhotoName();
                            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                            getAlbum.setType("image/*");
                            startActivityForResult(getAlbum, INTENTFORPHOTO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }).setNegativeButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
                    startActivityForResult(intentNow, INTENTFORCAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).show();
    }

    public String createLocalPhotoName() {

        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return LocalPhotoName;
    }

    //键盘显示（反之隐藏）
    private void change() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setTextMessage() {

        final EMMessage emMessage = EMMessage.createTxtSendMessage(ed_text.getText().toString().trim(), userId + "");
        if (emMessage == null) {
            return;
        }
        EMClient.getInstance().chatManager().sendMessage(emMessage);
        emMessageList.add(emMessage);
        adapter.notifyDataSetChanged();
        //显示最新消息
        re_listview.scrollToPosition(adapter.getItemCount() - 1);

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
                System.out.println(" onMessageReceivedmessages = " + messages);
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
        handler.sendEmptyMessageAtTime(1, 200);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
    }


    //设置输入法模式 pan
    public void setKeyBoardModelPan() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    //设置输入法模式 resize
    public void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    //隐藏键盘
    public void hidenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_text.getWindowToken(), 0);
    }

    // 显示键盘
    public void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ed_text, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void onBack() {
        ed_text.setListener(null);
        setKeyBoardModelResize();
        buttomLayoutView.setVisibility(View.GONE);
        image_face.setTag(1);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (buttomLayoutView.getVisibility() == View.VISIBLE) {
                buttomLayoutView.setVisibility(View.GONE);
                image_face.setTag(1);
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initEmoje(List<EaseEmojiconGroupEntity> emojiconGroupList) {
        if (emojiconMenu == null) {
            emojiconMenu = (EaseEmojiconMenu) View.inflate(UserFriendChatActivity.this, R.layout.ease_layout_emojicon_menu, null);
            //动态修改底部view 的高度 (表情 符号 view 的高度)
            //  buttomLayoutView.setVisibility(View.GONE);
            if (emojiconGroupList == null) {
                emojiconGroupList = new ArrayList<>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(R.drawable.ee_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            }
            ((EaseEmojiconMenu) emojiconMenu).init(emojiconGroupList);

        }
        buttomLayoutView.addView(emojiconMenu);
    }

    private void initLisitener() {
        // emojicon menu
        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuBase.EaseEmojiconMenuListener() {

            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if (emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION) {
                    if (emojicon.getEmojiText() != null) {
                        ed_text.append(EaseSmileUtils.getSmiledText(UserFriendChatActivity.this, emojicon.getEmojiText()));
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                if (!TextUtils.isEmpty(ed_text.getText())) {
                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                    image_face.dispatchKeyEvent(event);
                }
            }
        });
    }

}
