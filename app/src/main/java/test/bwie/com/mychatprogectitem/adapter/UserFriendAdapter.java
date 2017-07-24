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

import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.bean.UserInfoBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/17.
 * function:
 */
public class UserFriendAdapter extends RecyclerView.Adapter<UserFriendAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<UserInfoBean.DataBean> list;

    public UserFriendAdapter(Context context, List<UserInfoBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    //声明接口
    private UserFriendAdapter.OnItemClickListener mOnItemClickListener = null;
    //自定义接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    //暴露给外面的调用者，定义一个设置Listener的方法（）
    public void setOnItemClickListener(UserFriendAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public UserFriendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userfridend_item, parent, false);
        MyViewHolder viewHolder=new MyViewHolder(view);



        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserFriendAdapter.MyViewHolder holder, int position) {
          holder.fridend_agesex.setText(list.get(position).getAge()+"");
          holder.fridend_des.setText(list.get(position).getArea());
          holder.fridend_nickname.setText(list.get(position).getNickname());
          Glide.with(context).load(list.get(position).getImagePath()).error(R.drawable.face_error).into(holder.fridend_face);


        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView fridend_nickname;
        private final TextView fridend_des;
        private final TextView fridend_agesex;
        private final ImageView fridend_face;

        public MyViewHolder(View itemView) {
            super(itemView);
            fridend_nickname = (TextView) itemView.findViewById(R.id.user_fridend_nickname);
            fridend_des = (TextView) itemView.findViewById(R.id.user_fridend_des);
            fridend_agesex = (TextView) itemView.findViewById(R.id.user_fridend_agesex);
            fridend_face = (ImageView) itemView.findViewById(R.id.user_fridend_face);

        }
    }
}
