package test.bwie.com.mychatprogectitem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.utils.GlideCircleTransform;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/19.
 * function:
 */
public class MessageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<EMMessage> list;
    int myUserId;
    int userId;
    String friendImagePath;
    String myimagePath;
    public MessageAdapter(Context context, List<EMMessage> list,int myUserId,int userId,String friendImagePath,String myimagePath) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.myUserId=myUserId;
        this.userId=userId;
        this.friendImagePath=friendImagePath;
        this.myimagePath=myimagePath;
    }

    @Override
    public int getCount() {
        return  list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(position);

        ViewHolderLeftText viewHolderLeftText = null ;
        ViewHolderRightText viewHolderRightText = null ;

        if (convertView == null) {

            switch (type) {
                case 1:
                    //自己发送的文本
                    convertView = inflater.inflate(R.layout.text_right, viewGroup, false);
                    viewHolderRightText = new ViewHolderRightText(convertView);
                    convertView.setTag(viewHolderRightText);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.text_left, viewGroup, false);
                    viewHolderLeftText = new ViewHolderLeftText(convertView);
                    convertView.setTag(viewHolderLeftText);
                    break;
            }
        }else {
            switch (type) {
                case 1:
                    //自己发送的文本
                    viewHolderRightText = (ViewHolderRightText)convertView.getTag() ;
                    break;
                case 2:
                    viewHolderLeftText = (ViewHolderLeftText)convertView.getTag() ;
                    break;
            }
        }

        switch (type) {
            case 1:
                //自己发送的文本
                viewHolderRightText = (ViewHolderRightText)convertView.getTag() ;
                String s1 = list.get(position).getBody().toString();
                String substring1 = s1.substring(5,s1.length() - 1);
                viewHolderRightText.textRightImageText.setText(EaseSmileUtils.getSmiledText(context, substring1));
                viewHolderRightText.textRightImageText.setText(substring1);
                Glide.with(context).load(myimagePath).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).transform(new GlideCircleTransform(context)).into(viewHolderRightText.textRightImage);

                break;
            case 2:
                viewHolderLeftText = (ViewHolderLeftText)convertView.getTag() ;
                String s2 = list.get(position).getBody().toString();
                String substring2 = s2.substring(5,s2.length() - 1);
                viewHolderLeftText.textLeftImageText.setText(substring2);
                Glide.with(context).load(friendImagePath).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).transform(new GlideCircleTransform(context)).into(viewHolderLeftText.textLeftImage);
                break;
        }

        return convertView;
    }
    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getType() == EMMessage.Type.TXT) {

            if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
                return 1;
            } else {
                return 2;
            }
        }
        return  -1 ;
    }
    static class ViewHolderRightText {
        @BindView(R.id.text_right_image)
        ImageView textRightImage;
        @BindView(R.id.text_right_image_text)
        TextView textRightImageText;

        ViewHolderRightText(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderLeftText {
        @BindView(R.id.text_left_image_text)
        TextView textLeftImageText;
        @BindView(R.id.text_left_image)
        ImageView textLeftImage;

        ViewHolderLeftText(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
