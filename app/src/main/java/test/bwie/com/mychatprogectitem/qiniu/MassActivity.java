package test.bwie.com.mychatprogectitem.qiniu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.adapter.MassRecycleAdater;
import test.bwie.com.mychatprogectitem.bean.MassBean;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.utils.MyGridSpacingItemDecoration;

import static com.hyphenate.easeui.R.id.list;

public class MassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MassBean.ListBean> list;
    private List<MassBean.ListBean> massList=new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);
        initView();
        getGianzhong();


        System.out.print("massList=="+massList.size());
        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MyGridSpacingItemDecoration(20,20,true));
        MassRecycleAdater adater=new MassRecycleAdater(this,massList);
        recyclerView.setAdapter(adater);

        adater.setOnItemClickListener(new MassRecycleAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(MassActivity.this,PLVideoViewActivity.class);
                String publishUrl = massList.get(position).getPlayUrl();
                intent.putExtra("videoPath",publishUrl);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.mass_recycler);
    }

    public void getGianzhong(){
        final Map map = new HashMap<String, String>();
        map.put("live.type", 2 + "");
        map.put("user.sign", 1 + "");

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_live.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("8888888888888888888888888"+result);
                Gson gson=new Gson();
                MassBean massBean = gson.fromJson(result, MassBean.class);
                list = massBean.getList();
                massList.addAll(MassActivity.this.list);
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }
}
