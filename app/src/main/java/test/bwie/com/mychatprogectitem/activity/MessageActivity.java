package test.bwie.com.mychatprogectitem.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.base.IActivity;
import test.bwie.com.mychatprogectitem.fragment.FirstFragment;
import test.bwie.com.mychatprogectitem.fragment.FourthFragment;
import test.bwie.com.mychatprogectitem.fragment.SecondFragment;
import test.bwie.com.mychatprogectitem.fragment.ThirdFragment;
import test.bwie.com.mychatprogectitem.widget.ButtomLayout;

public class MessageActivity extends IActivity implements ButtomLayout.OnSelectListener {

    ButtomLayout buttomLayout;
    private List<Fragment> fragments=new ArrayList<>();
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        fragmentManager = getSupportFragmentManager();
        createFragment(savedInstanceState);


        buttomLayout= (ButtomLayout) findViewById(R.id.message_buttomLayout);
        buttomLayout.setOnSelectListenter(this);
        switchFragment(0);
    }

    private void switchFragment(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragments.get(i).isAdded()){
            transaction.add(R.id.message_frame,fragments.get(i),fragments.get(i).getClass().getSimpleName());
        }
        for (int j = 0; j <fragments.size() ; j++) {
            if (j==i){
                transaction.show(fragments.get(i));
            }else {
                transaction.hide(fragments.get(j));
            }
        }
        transaction.commit();
    }

    private void createFragment(Bundle savedInstanceState) {
        FirstFragment firstFragment = (FirstFragment) fragmentManager.findFragmentByTag("FirstFragment");
        FourthFragment fourthFragment = (FourthFragment) fragmentManager.findFragmentByTag("FourthFragment");
        SecondFragment secondFragment = (SecondFragment) fragmentManager.findFragmentByTag("SecondFragment");
        ThirdFragment thirdFragment = (ThirdFragment) fragmentManager.findFragmentByTag("ThirdFragment");

        if (firstFragment==null){
            firstFragment=new FirstFragment();
        }
        if (fourthFragment==null){
            fourthFragment=new FourthFragment();
        }
        if (secondFragment==null){
            secondFragment=new SecondFragment();
        }
        if (thirdFragment==null){
            thirdFragment=new ThirdFragment();
        }
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(fourthFragment);

    }


    @Override
    public void onSelect(int index) {
        switchFragment(index);
    }

}
