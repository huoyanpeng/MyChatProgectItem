package test.bwie.com.mychatprogectitem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.MainThreeActivity;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.activity.LoginActivity;
import test.bwie.com.mychatprogectitem.activity.ParticularsActivity;
import test.bwie.com.mychatprogectitem.adapter.IndexAdapter;
import test.bwie.com.mychatprogectitem.base.BaseMvpFragment;
import test.bwie.com.mychatprogectitem.bean.DataBean;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.presenter.FirstFragmentPresenter;
import test.bwie.com.mychatprogectitem.view.FirstFragmentView;


public class FirstFragment extends BaseMvpFragment<FirstFragmentView, FirstFragmentPresenter> implements FirstFragmentView{

    private Unbinder bind;
    private long currentTimeMillis;

    @Override
    public FirstFragmentPresenter initPresenter() {
        return new FirstFragmentPresenter();
    }

    public FirstFragment() {
    }

    private View view;
    @BindView(R.id.firstfragment_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.firstfragment_text_pattern)
    TextView pattern;
    @BindView(R.id.springview_indexfragment)
    SpringView springviewIndexfragment;
    @BindView(R.id.firstfragment_image_back)
    ImageView image_back;
    private int page = 1 ;
    private  IndexAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    List<DataBean> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainThreeActivity.class));
            }
        });

        currentTimeMillis = System.currentTimeMillis();

        presenter.getData(0,currentTimeMillis);

        initView();
    }

    private void initView() {

        pattern.setTag(1);
        pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag() ;
                if(tag == 1){
                    pattern.setTag(2);
                    toStaggeredGridLayoutManager();


                } else {
                    pattern.setTag(1);
                    toLinearLayoutManager();
                }
            }
        });
        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).build();
        adapter = new IndexAdapter(getActivity());
        toLinearLayoutManager();



        springviewIndexfragment.setHeader(new DefaultHeader(getActivity()));
        springviewIndexfragment.setFooter(new DefaultFooter(getActivity()));

        springviewIndexfragment.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("onRefresh = " );
                presenter.getData(1,currentTimeMillis);
            }

            @Override
            public void onLoadmore() {
                System.out.println("onLoadmore = " );
                long lasttime = list.get(list.size() - 1).getLasttime();

                presenter.getData(1,lasttime);

            }
        });

    }

    private void toLinearLayoutManager() {
        if(linearLayoutManager == null){
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        adapter.dataChange(1);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(horizontalDividerItemDecoration);
    }

    private void   toStaggeredGridLayoutManager() {
        if(staggeredGridLayoutManager == null){
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        }
        adapter.dataChange(2);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.removeItemDecoration(horizontalDividerItemDecoration);

    }

    @Override
    public void success(final IndexBean indexBean, int page,long currttimer) {

        System.out.println(indexBean.toString());
        list=indexBean.getData();
        adapter.setData(indexBean,page);

        adapter.notifyDataSetChanged();


        adapter.setOnItemClickListener(new IndexAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(getActivity(), ParticularsActivity.class);
                Bundle bundle=new Bundle();
                DataBean dataBean = indexBean.getData().get(position);
                bundle.putSerializable("dataBean",dataBean);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void failed(int code, int page) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
