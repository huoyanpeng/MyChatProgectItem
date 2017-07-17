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
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.activity.ParticularsActivity;
import test.bwie.com.mychatprogectitem.adapter.IndexAdapter;
import test.bwie.com.mychatprogectitem.base.BaseMvpFragment;
import test.bwie.com.mychatprogectitem.bean.DataBean;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.presenter.FirstFragmentPresenter;
import test.bwie.com.mychatprogectitem.view.FirstFragmentView;


public class FirstFragment extends BaseMvpFragment<FirstFragmentView, FirstFragmentPresenter> implements FirstFragmentView{

    private Unbinder bind;

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
    private int page = 1 ;
    private  IndexAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
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

        presenter.getData(page);


        springviewIndexfragment.setHeader(new DefaultHeader(getActivity()));
        springviewIndexfragment.setFooter(new DefaultFooter(getActivity()));

        springviewIndexfragment.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("onRefresh = " );
                page = 1 ;
                presenter.getData(page);
            }

            @Override
            public void onLoadmore() {
                System.out.println("onLoadmore = " );
                presenter.getData(++page);

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
    public void success(final IndexBean indexBean, int page) {
        System.out.println(indexBean.toString());
        adapter.setData(indexBean,page);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new IndexAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position>20){
                    position=position-20;
                }
                Intent intent=new Intent(getActivity(), ParticularsActivity.class);
                Bundle bundle=new Bundle();
                DataBean dataBean = indexBean.getData().get(position);
                int userId = dataBean.getUserId();
                bundle.putSerializable("dataBean",dataBean);
                intent.putExtras(bundle);
                intent.putExtra("userId",userId);
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
