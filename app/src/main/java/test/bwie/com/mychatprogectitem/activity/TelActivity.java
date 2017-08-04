package test.bwie.com.mychatprogectitem.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMCallSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.bwie.com.mychatprogectitem.R;

public class TelActivity extends AppCompatActivity {

    private String uid;
    private String type;

    public static void startTelActivity(String type, String uid, Context context) {

        Intent intent = new Intent(context, TelActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("uid", uid);
        context.startActivity(intent);

    }
    @BindView(R.id.tel_surfaceView_one)
    EMCallSurfaceView surfaceView_one;
    @BindView(R.id.tel_surfaceView_two)
    EMCallSurfaceView  surfaceView_two;
//    @BindView(R.id.tel_surfaceView_one)
//    SurfaceView surfaceView_one;
//    @BindView(R.id.tel_surfaceView_two)
//    SurfaceView surfaceView_two;
    @BindView(R.id.tel_ring_off_one)
    ImageView ring_off_one;
    @BindView(R.id.tel_ring_off_two)
    ImageView ring_off_two;
    @BindView(R.id.tel_ring_dial)
    ImageView ring_dial;
    @BindView(R.id.tel_relative_one)
    RelativeLayout relative_one;
    @BindView(R.id.tel_relative_two)
    RelativeLayout relative_two;
    @BindView(R.id.tel_text_title)
    TextView telActivityHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel);
        ButterKnife.bind(this);

        uid = getIntent().getExtras().getString("uid");
        type = getIntent().getExtras().getString("type");
        //让自己图像 显示在上面
        surfaceView_two.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceView_two.setZOrderOnTop(true);
        if(type.equals("1")){
            //拨打电话
            relative_one.setVisibility(View.VISIBLE);
            relative_two.setVisibility(View.GONE);
            //拨打视频通话
            try {
                EMClient.getInstance().callManager().makeVideoCall(uid);
            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }

        } else {

//            接听电话
            relative_one.setVisibility(View.VISIBLE);
            relative_two.setVisibility(View.GONE);

        }

        EMClient.getInstance().callManager().setSurfaceView(surfaceView_two,surfaceView_one);
        connectState();

    }

    private void connectState() {
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                telActivityHint.setText("正在连接");
                                relative_one.setVisibility(View.GONE);
                                relative_two.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case CONNECTED: // 双方已经建立连接

                        break;

                    case ACCEPTED: // 电话接通成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                telActivityHint.setText("通话中");
                                telActivityHint.setVisibility(View.GONE);
                                relative_one.setVisibility(View.GONE);
                                relative_two.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case DISCONNECTED: // 电话断了

                        finish();

                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                        }else{
                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        break;
                    default:
                        break;
                }
            }
        });
    }
    @OnClick({R.id.tel_ring_off_one, R.id.tel_ring_off_two, R.id.tel_ring_dial})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tel_ring_dial:
                try {
                    EMClient.getInstance().callManager().answerCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tel_ring_off_two:
                try {
                    //挂断
                    EMClient.getInstance().callManager().endCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }
                finish();
                break;
            case R.id.tel_ring_off_one:
                try {
                    EMClient.getInstance().callManager().rejectCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
