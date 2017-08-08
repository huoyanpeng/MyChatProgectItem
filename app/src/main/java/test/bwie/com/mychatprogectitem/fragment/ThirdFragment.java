package test.bwie.com.mychatprogectitem.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import test.bwie.com.mychatprogectitem.MainThreeActivity;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.activity.DirectActivity;
import test.bwie.com.mychatprogectitem.activity.LoginActivity;


public class ThirdFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button share_text;
    private Button share_streaming;

    public ThirdFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_third, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences login = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean login1 = login.getBoolean("login", false);
        if (login1){

        }else {
            startActivity(new Intent(getActivity(), MainThreeActivity.class));
        }

        initView();

    }

    private void initView() {
        share_text = (Button) getActivity().findViewById(R.id.fragment_third_share_text);
        share_streaming = (Button) getActivity().findViewById(R.id.fragment_third_share_streaming);

        share_text.setOnClickListener(this);
        share_streaming.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_third_share_streaming:
                startActivity(new Intent(getActivity(), DirectActivity.class));
                break;
            case R.id.fragment_third_share_text:
                break;
        }
    }
}
