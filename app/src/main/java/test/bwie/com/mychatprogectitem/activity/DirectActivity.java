package test.bwie.com.mychatprogectitem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.qiniu.SWCameraStreamingActivity;

public class DirectActivity extends AppCompatActivity {

    private Unbinder bind;
    @BindView(R.id.direct_button_anchor)
    Button button_anchor;
    @BindView(R.id.direct_button_audience)
    Button button_audience;

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
                Intent intent=new Intent(DirectActivity.this,SWCameraStreamingActivity.class);
                intent.putExtra("stream_json_str","rtmp://pili-publish.2dyt.com/1503d/streamkey?e=1502093100&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:BfxD_8YaZvKCncgiX9FKSczLZsw=");
                startActivity(intent);
                break;
            case R.id.direct_button_audience:
                Intent intent1=new Intent(DirectActivity.this,SWCameraStreamingActivity.class);
                intent1.putExtra("videoPath","rtmp://pili-live-rtmp.2dyt.com/1503d/streamkey");
                startActivity(intent1);
                break;
        }
    }
}
