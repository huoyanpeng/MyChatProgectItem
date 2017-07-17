package test.bwie.com.mychatprogectitem.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import test.bwie.com.mychatprogectitem.R;

/**
 * Created by muhanxi on 17/6/19.
 */

public class ButtomLayout extends LinearLayout {


    public ButtomLayout(Context context) {
        this(context,null);
    }

    public ButtomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ButtomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


//       View view =  LayoutInflater.from(context).inflate(R.layout.tab_layout,null,false);
//        View view =  View.inflate(context,R.layout.tab_layout,null);

        View view =  LayoutInflater.from(context).inflate(R.layout.tab_layout,this);

        RadioGroup radioGroup = (RadioGroup)  view.findViewById(R.id.tab_radiogroup);

        RadioButton radioButtonFirst = (RadioButton) view.findViewById(R.id.radiobutton_home);
        RadioButton radioButtonSecond = (RadioButton) view.findViewById(R.id.radiobutton_discover);
        RadioButton radioButtonThird = (RadioButton) view.findViewById(R.id.radiobutton_feed);
        RadioButton radioButtonFourth = (RadioButton) view.findViewById(R.id.radiobutton_me);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {

                    case R.id.radiobutton_home:

                        setListener(0);
                        break;

                    case R.id.radiobutton_discover:
                        setListener(1);
                        break;

                    case R.id.radiobutton_feed:
                        setListener(2);
                        break;

                    case R.id.radiobutton_me:
                        setListener(3);
                        break;

                }
            }
        });
    }
    public void setListener(int index){

        if(listener != null){
            listener.onSelect(index);
        }
    }




    public OnSelectListener listener;
    public interface OnSelectListener{
    public void onSelect(int index);
}
public void setOnSelectListenter(OnSelectListener listener){
    this.listener=listener;
}
}
