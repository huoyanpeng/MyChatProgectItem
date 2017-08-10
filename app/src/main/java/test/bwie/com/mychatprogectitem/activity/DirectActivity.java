package test.bwie.com.mychatprogectitem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.bean.AnchorBean;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.qiniu.MassActivity;
import test.bwie.com.mychatprogectitem.qiniu.SWCameraStreamingActivity;

public class DirectActivity extends AppCompatActivity {

    private Unbinder bind;
    @BindView(R.id.direct_button_anchor)
    Button button_anchor;
    @BindView(R.id.direct_button_audience)
    Button button_audience;
    private String url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.direct_button_audience,R.id.direct_button_anchor})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.direct_button_anchor:
                getZhibo();
                Log.e("url==",""+url);
                break;
            case R.id.direct_button_audience:
                Intent intent1=new Intent(DirectActivity.this,MassActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void getZhibo(){

        final Map map = new HashMap<String, String>();
        map.put("live.type", 1 + "");
        map.put("user.sign", 1 + "");

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_live.action ", map, new BaseObserver() {
        @Override
        public void onSuccess(String result) {
            Log.e("result",""+result);
            Gson gson=new Gson();
            AnchorBean anchorBean = gson.fromJson(result, AnchorBean.class);
            url = anchorBean.getUrl();
            Intent intent=new Intent(DirectActivity.this,SWCameraStreamingActivity.class);
            intent.putExtra("stream_json_str",url);
            startActivity(intent);
        }
        @Override
        public void onFailed(int code) {

        }
    });
}

}
