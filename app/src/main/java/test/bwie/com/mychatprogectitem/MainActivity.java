package test.bwie.com.mychatprogectitem;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.activity.MessageActivity;


public class MainActivity extends AppCompatActivity {

    private Timer timer;
    @BindView(R.id.mian_image)
    ImageView imageView;
    private AnimationDrawable drawable;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        bind = ButterKnife.bind(this);

        imageView.setImageResource(R.drawable.animation1);
        drawable = (AnimationDrawable) imageView.getDrawable();
        drawable.start();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       startActivity(new Intent(MainActivity.this,MessageActivity.class));
                       drawable.stop();
                       timer.cancel();
                   }
               });
            }
        },2000,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
