package test.bwie.com.mychatprogectitem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import test.bwie.com.mychatprogectitem.R;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/14.
 * function:
 */
public class FourFragmentRecycler extends RecyclerView.Adapter<FourFragmentRecycler.ViewHolder> implements View.OnClickListener{

    private Context context;
    private String[] strings;
    private int[] ints;

    public FourFragmentRecycler(Context context, String[] strings, int[] ints) {
        this.context = context;
        this.strings = strings;
        this.ints = ints;
        System.out.println("strings========="+strings.length);
        System.out.println("ints========="+ints.length);
    }
    //声明接口
    private FourFragmentRecycler.OnItemClickListener mOnItemClickListener = null;
    //自定义接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    //暴露给外面的调用者，定义一个设置Listener的方法（）
    public void setOnItemClickListener(FourFragmentRecycler.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public FourFragmentRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fourfragment_recycler_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);


        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FourFragmentRecycler.ViewHolder holder, int position) {

        holder.imageView.setImageResource(ints[position]);
        holder.textView.setText(strings[position]);

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fourfragment_recyclerView_item_image);
            textView = (TextView) itemView.findViewById(R.id.fourfragment_recyclerView_item_text);

        }
    }
}
