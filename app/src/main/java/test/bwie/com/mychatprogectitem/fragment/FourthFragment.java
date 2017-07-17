package test.bwie.com.mychatprogectitem.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.MainThreeActivity;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.activity.SetActivity;
import test.bwie.com.mychatprogectitem.adapter.FourFragmentRecycler;
import test.bwie.com.mychatprogectitem.utils.MyGridSpacingItemDecoration;

public class FourthFragment extends Fragment {

    private Unbinder bind;

    public FourthFragment() {
    }
    private View view;
    @BindView(R.id.fourfragment_recyclerView_one)
    RecyclerView recyclerView;
    private String[] strings={"我的资料","诚信等级","我的相册","我的动态","征友条件",
                               "设置","会员中心","在线客服","新手福利","帮助&反馈","咪咕有缘阅读会","咪咕音乐会员专区"};
    private int [] ints={R.drawable.space_center_my_info_bg,R.drawable.space_center_integrity_level_bg,
                           R.drawable.space_center_album_bg,R.drawable.space_center_dynamic_bg,
                           R.drawable.space_center_condition_bg, R.drawable.space_center_setting_bg,
                           R.drawable.space_center_vip_bg,R.drawable.space_center_online_service_bg,
                          R.drawable.space_stern_welfare_bg,R.drawable.space_center_help_bg,
                          R.drawable.space_center_look_book_bg,R.drawable.worth_title_bg};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fourth, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences login = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean login1 = login.getBoolean("login", false);
        if (login1){
            initData();
        }else {
            startActivity(new Intent(getActivity(), MainThreeActivity.class));
        }

    }

    private void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.addItemDecoration(new MyGridSpacingItemDecoration(20,20,true));
        FourFragmentRecycler adapter=new FourFragmentRecycler(getActivity(),strings,ints);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FourFragmentRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), SetActivity.class));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
