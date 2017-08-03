package test.bwie.com.mychatprogectitem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.activity.ChatActivityActivity;
import test.bwie.com.mychatprogectitem.activity.UserFriendChatActivity;
import test.bwie.com.mychatprogectitem.adapter.UserFriendAdapter;
import test.bwie.com.mychatprogectitem.base.BaseMvpFragment;
import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.UserInfoBean;
import test.bwie.com.mychatprogectitem.presenter.FirstFragmentPresenter;
import test.bwie.com.mychatprogectitem.presenter.FriendPresenter;
import test.bwie.com.mychatprogectitem.presenter.SecondFragmentPresenter;
import test.bwie.com.mychatprogectitem.utils.MyToast;
import test.bwie.com.mychatprogectitem.view.FirstFragmentView;
import test.bwie.com.mychatprogectitem.view.FridendView;
import test.bwie.com.mychatprogectitem.view.SecondFragmentView;


public class SecondFragment extends BaseMvpFragment<SecondFragmentView, SecondFragmentPresenter> implements SecondFragmentView {

    private Unbinder bind;

    @Override
    public SecondFragmentPresenter initPresenter() {
        return new SecondFragmentPresenter();
    }

    public SecondFragment() {
    }
    private View view;
    @BindView(R.id.secondfragment_recyclerview)
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.vaildInfor();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void registerSuccess(UserInfoBean userInfoBean) {
        MyToast.makeText(getActivity(),"数据请求成功", Toast.LENGTH_LONG);
        final List<UserInfoBean.DataBean> data = userInfoBean.getData();
        UserFriendAdapter adapter=new UserFriendAdapter(getActivity(),data);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        HorizontalDividerItemDecoration build = new HorizontalDividerItemDecoration.Builder(getActivity()).build();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(build);

        adapter.setOnItemClickListener(new UserFriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserInfoBean.DataBean dataBean = data.get(position);
                Intent intent=new Intent(getActivity(), UserFriendChatActivity.class);
                int userId = dataBean.getUserId();
                String nickname = dataBean.getNickname();
                String imagePath = dataBean.getImagePath();
                intent.putExtra("num",2);
                intent.putExtra("nickname",nickname);
                intent.putExtra("userId",userId);
                intent.putExtra("friendImagPath",imagePath);
                startActivity(intent);
            }
        });

    }

    @Override
    public void registerFailed() {
        MyToast.makeText(getActivity(),"数据请求失败", Toast.LENGTH_LONG);
    }
}
