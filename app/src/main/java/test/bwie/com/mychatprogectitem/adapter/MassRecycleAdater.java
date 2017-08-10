package test.bwie.com.mychatprogectitem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.zip.Inflater;

import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.bean.MassBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/8/9.
 * function:
 */
public class MassRecycleAdater extends RecyclerView.Adapter<MassRecycleAdater.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<MassBean.ListBean> list;

    public MassRecycleAdater(Context context, List<MassBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    //声明接口
    private MassRecycleAdater.OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View view) {

        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    //自定义接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    //暴露给外面的调用者，定义一个设置Listener的方法（）
    public void setOnItemClickListener(MassRecycleAdater.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public MassRecycleAdater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mass_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);


        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MassRecycleAdater.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getLivepic()).error(R.drawable.face_error).into(holder.imageView);
        holder.textView.setText(list.get(position).getNickName());

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.mass_item_image);
            textView = (TextView) itemView.findViewById(R.id.mass_item_text);

        }
    }
}
