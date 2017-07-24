package test.bwie.com.mychatprogectitem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.bean.DataBean;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.utils.AMapUtils;
import test.bwie.com.mychatprogectitem.utils.DeviceUtils;
import test.bwie.com.mychatprogectitem.utils.DisanceUtils;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/12.
 * function:
 */
public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<DataBean> list;
    private Context context;
    private int tag = 1; // 1 先行布局 2 瀑布流
    private int itemWidth ;

    public IndexAdapter(Context context) {
        this.context = context;
        //当前屏幕 的宽度 除以3
        itemWidth = DeviceUtils.getDisplayInfomation(context).x / 3 ;
    }
    public void setData(IndexBean bean, int page) {
        if (list == null) {
            list = new ArrayList<DataBean>();
        }
        if(page == 1){

            list.clear();
        }

        list.addAll(bean.getData());
        notifyDataSetChanged();
        System.out.println("list:============="+list.size());
    }
    /**
     * 2 瀑布流
     * 1 线性布局
     *
     * @param type
     */
    public void dataChange(int type) {
        this.tag = type;

    }

    //声明接口
    private IndexAdapter.OnItemClickListener mOnItemClickListener = null;
    //自定义接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    //暴露给外面的调用者，定义一个设置Listener的方法（）
    public void setOnItemClickListener(IndexAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.indexfragment_verticaladapter, parent, false);
            VerticalViewHolder verticalViewHolder = new VerticalViewHolder(view);


            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return verticalViewHolder;

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.indexfragment_pinterest, parent, false);
            PinterestViewHolder pinterestViewHolder = new PinterestViewHolder(view);

            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return pinterestViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VerticalViewHolder) {

            //列表的形式展示
            VerticalViewHolder verticalViewHolder = (VerticalViewHolder) holder;

            verticalViewHolder.indexfragmentNickname.setText(list.get(position).getNickname());

            verticalViewHolder.indexfragmentDes.setText(list.get(position).getIntroduce());
            Glide.with(context).load(list.get(position).getImagePath()).error(R.drawable.face_error).into(verticalViewHolder.indexfragmentFace);

            String lat =  PreferencesUtils.getValueByKey(context, AMapUtils.LAT,"");
            String lng = PreferencesUtils.getValueByKey(context,AMapUtils.LNG,"");

            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng() ;


            if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0){

                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);
                DPoint dPoint = new DPoint(dlat,dlng);
                DPoint oPoint = new DPoint(olat,olng);

                //计算两点之间的距离
                float dis =  CoordinateConverter.calculateLineDistance(dPoint,oPoint);

                verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getAge() + "岁 , " + list.get(position).getGender() + " , " + DisanceUtils.standedDistance(dis));

                //将position保存在itemView的Tag中，以便点击时进行获取
                holder.itemView.setTag(position);

            } else {

                verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getAge() + "岁 , " + list.get(position).getGender());

                //将position保存在itemView的Tag中，以便点击时进行获取
                holder.itemView.setTag(position);
            }
        } else {
            PinterestViewHolder staggeredViewHolder = (PinterestViewHolder) holder;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) staggeredViewHolder.indexfragmentStagger.getLayoutParams() ;

            float scale =  (float) itemWidth / (float) list.get(position).getPicWidth()  ;
            params.width = itemWidth;
            params.height = (int)( (float)scale * (float)list.get(position).getPicHeight()) ;

            System.out.println("params.scale = " + scale);
            System.out.println("params.width = " + params.width + " " + list.get(position).getPicWidth());
            System.out.println("params.height = " + params.height + "  " + list.get(position).getPicHeight());


            staggeredViewHolder.indexfragmentStagger.setLayoutParams(params);


            staggeredViewHolder.name.setText(list.get(position).getNickname());
            Glide.with(context).load(list.get(position).getImagePath()).error(R.drawable.face_error).into(staggeredViewHolder.indexfragmentStagger);

            //将position保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);

        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();

    }
    @Override
    public int getItemViewType(int position) {
        if (tag == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    static class VerticalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.indexfragment_nickname)
        TextView indexfragmentNickname;
        @BindView(R.id.indexfragment_agesex)
        TextView indexfragmentAgesex;
        @BindView(R.id.indexfragment_des)
        TextView indexfragmentDes;
        @BindView(R.id.indexfragment_face)
        ImageView indexfragmentFace;

        public VerticalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    static class PinterestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.indexfragment_stagger)
        ImageView indexfragmentStagger;
        @BindView(R.id.indexfragment_name)
        TextView name;

        public PinterestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
