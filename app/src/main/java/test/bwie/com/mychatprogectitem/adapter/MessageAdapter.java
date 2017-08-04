package test.bwie.com.mychatprogectitem.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.speex.SpeexPlayer;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/19.
 * function:
 */

//implements View.OnClickListener
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    List<EMMessage> list;
    int myUserId;
    int userId;
    String friendImagePath;
    String myimagePath;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

//    //声明接口
//    private MessageAdapter.OnItemClickListener mOnItemClickListener = null;
//
//    @Override
//    public void onClick(View view) {
//        if (mOnItemClickListener != null) {
//            //注意这里使用getTag方法获取position
//            mOnItemClickListener.onItemClick(view,(int)view.getTag());
//        }
//    }
//
//    //自定义接口
//    public static interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//    //暴露给外面的调用者，定义一个设置Listener的方法（）
//    public void setOnItemClickListener(MessageAdapter.OnItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }

    public MessageAdapter(Context context, List<EMMessage> list, int myUserId, int userId, String friendImagePath, String myimagePath) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.myUserId = myUserId;
        this.userId = userId;
        this.friendImagePath = friendImagePath;
        this.myimagePath = myimagePath;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View view = inflater.inflate(R.layout.text_left, parent, false);
            ViewHolderLeftText viewHolderLeftText = new ViewHolderLeftText(view);
            return viewHolderLeftText;
        }else if (viewType==2){
            View view = inflater.inflate(R.layout.text_right, parent, false);
            ViewHolderRightText viewHolderRightText = new ViewHolderRightText(view);
            return viewHolderRightText;
        }else if (viewType==3){
            View view = inflater.inflate(R.layout.rigth_voice, parent, false);
            ViewHolderRigthVoice rigthVoice=new ViewHolderRigthVoice(view);


            //将创建的View注册点击事件
//            view.setOnClickListener(this);
            return rigthVoice;
        }else if (viewType==4){
            View view = inflater.inflate(R.layout.left_voice, parent, false);
            ViewHolderLeftVoice leftVoice=new ViewHolderLeftVoice(view);

            //将创建的View注册点击事件
//            view.setOnClickListener(this);
            return leftVoice;
        }else if (viewType==5){
            View view = inflater.inflate(R.layout.rigth_image, parent, false);
            ViewHolderRigthImage viewHolderRigthImage=new ViewHolderRigthImage(view);

            return viewHolderRigthImage;
        }else if (viewType==6){
            View view = inflater.inflate(R.layout.left_image, parent, false);
            ViewHolderLeftImage viewHolderLeftImage=new ViewHolderLeftImage(view);

            return viewHolderLeftImage;
        }else if (viewType==7){
            View view = inflater.inflate(R.layout.rigth_location, parent, false);
            ViewHolderRigthLocation viewHolderRigthLocation=new ViewHolderRigthLocation(view);
            return viewHolderRigthLocation;
        }else if (viewType==8){
            View view = inflater.inflate(R.layout.left_location, parent, false);
            ViewHolderLeftLocation viewHolderLeftLocation=new ViewHolderLeftLocation(view);

            return viewHolderLeftLocation;
        }

        return null;
    }


        @Override
        public void onBindViewHolder (RecyclerView.ViewHolder holder, final int position){
            if (holder instanceof ViewHolderLeftText) {
                ViewHolderLeftText leftholder = (ViewHolderLeftText) holder;
                String txt = list.get(position).getBody().toString();
                String string = txt.substring(5, txt.length() - 1);
                leftholder.textLeftImageText.setText(EaseSmileUtils.getSmiledText(context, string));

            } else if (holder instanceof ViewHolderRightText) {
                ViewHolderRightText rightholder = (ViewHolderRightText) holder;
                String Msg = list.get(position).getBody().toString();
                String sendMsg = Msg.substring(5, Msg.length() - 1);
                rightholder.textRightImageText.setText(EaseSmileUtils.getSmiledText(context, sendMsg));

            } else if (holder instanceof ViewHolderRigthVoice){
                ViewHolderRigthVoice viewHolderRigthVoice= (ViewHolderRigthVoice) holder;

                //将position保存在itemView的Tag中，以便点击时进行获取
//                viewHolderRigthVoice.itemView.setTag(position);

                viewHolderRigthVoice.rigth_voice_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EMVoiceMessageBody body =  (EMVoiceMessageBody)list.get(position).getBody();
                        SpeexPlayer player = new SpeexPlayer(body.getLocalUrl(),handler);
                        player.startPlay();
                    }
                });


            }else if (holder instanceof ViewHolderLeftVoice){

                ViewHolderLeftVoice viewHolderLeftVoice= (ViewHolderLeftVoice) holder;
                //将position保存在itemView的Tag中，以便点击时进行获取
//                viewHolderLeftVoice.itemView.setTag(position);

                viewHolderLeftVoice.left_voice_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EMVoiceMessageBody body =  (EMVoiceMessageBody)list.get(position).getBody();
                        SpeexPlayer player = new SpeexPlayer(body.getLocalUrl(),handler);
                        player.startPlay();
                    }
                });

            }else if (holder instanceof ViewHolderRigthImage) {

                    ViewHolderRigthImage viewHolderRigthImage = (ViewHolderRigthImage) holder;
                    EMImageMessageBody body = (EMImageMessageBody) list.get(position).getBody();
                    String s = body.thumbnailLocalPath();

                    System.out.println("图片路径：" + s);
                    Glide.with(context).load(s).error(R.drawable.face_error).into(viewHolderRigthImage.rigth_me_image);

                } else if (holder instanceof ViewHolderLeftImage) {
                    ViewHolderLeftImage viewHolderLeftImage = (ViewHolderLeftImage) holder;

                    EMImageMessageBody body = (EMImageMessageBody) list.get(position).getBody();
                    String s = body.thumbnailLocalPath();
                    System.out.println("图片路径：" + s);
                    Glide.with(context).load(s).error(R.drawable.face_error).into(viewHolderLeftImage.left_friend_image);

            }else if (holder instanceof ViewHolderRigthLocation){
                ViewHolderRigthLocation viewHolderRigthLocation= (ViewHolderRigthLocation) holder;
                EMLocationMessageBody body = (EMLocationMessageBody) list.get(position).getBody();
                String address = body.getAddress();
                viewHolderRigthLocation.rigth_location_text.setText(address);
            }else if (holder instanceof ViewHolderLeftLocation){
                ViewHolderLeftLocation viewHolderLeftLocation= (ViewHolderLeftLocation) holder;
                EMLocationMessageBody body = (EMLocationMessageBody) list.get(position).getBody();
                String address = body.getAddress();
                viewHolderLeftLocation.left_friend_location_text.setText(address);
            }
        }

        @Override
        public int getItemCount () {
            return list == null ? 0 : list.size();
        }

        @Override
        public long getItemId ( int i){
            return i;
        }

        @Override
        public int getItemViewType ( int position){

            if (list.get(position).getType() == EMMessage.Type.TXT) {

                if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
                    return 2;
                } else {
                    return 1;
                }
            } else if (list.get(position).getType() == EMMessage.Type.VOICE) {

                if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
                    return 4;  //接收
                } else {
                    return 3;   //发送
                }
            }else if (list.get(position).getType() == EMMessage.Type.IMAGE){

                if (list.get(position).direct()==EMMessage.Direct.RECEIVE){
                    return 6;

                }else {
                    return 5;
                }

            }else if (list.get(position).getType()==EMMessage.Type.LOCATION){
                if(list.get(position).direct()==EMMessage.Direct.RECEIVE){
                    return 8;
                }else {
                    return 7;
                }

            }


            return -1;

        }
        class ViewHolderRightText extends RecyclerView.ViewHolder {
            @BindView(R.id.text_right_image)
            ImageView textRightImage;
            @BindView(R.id.text_right_image_text)
            TextView textRightImageText;

            public ViewHolderRightText(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        class ViewHolderLeftText extends RecyclerView.ViewHolder {

            @BindView(R.id.text_left_image_text)
            TextView textLeftImageText;
            @BindView(R.id.text_left_image)
            ImageView textLeftImage;

            public ViewHolderLeftText(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    class ViewHolderLeftVoice extends RecyclerView.ViewHolder {

        @BindView(R.id.left_voice_button)
        Button left_voice_button;
        @BindView(R.id.left_voice_im)
        ImageView left_voice_im;

        public ViewHolderLeftVoice(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    class ViewHolderRigthVoice extends RecyclerView.ViewHolder {

        @BindView(R.id.rigth_voice_button)
        Button rigth_voice_button;
        @BindView(R.id.rigth_voice_im)
        ImageView rigth_voice_im;

        public ViewHolderRigthVoice(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    class ViewHolderRigthImage extends RecyclerView.ViewHolder{

        @BindView(R.id.rigth_me_image)
        ImageView rigth_me_image;
        @BindView(R.id.rigth_me_image_title)
        ImageView rigth_me_image_title;

        public ViewHolderRigthImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    class ViewHolderLeftImage extends RecyclerView.ViewHolder{

        @BindView(R.id.left_friend_image)
        ImageView left_friend_image;
        @BindView(R.id.left_friend_image_title)
        ImageView left_friend_image_title;

        public ViewHolderLeftImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    class ViewHolderRigthLocation extends RecyclerView.ViewHolder{

        @BindView(R.id.rigth_me_location_title)
        ImageView rigth_me_location_title;
        @BindView(R.id.rigth_location_text)
        TextView rigth_location_text;
        public ViewHolderRigthLocation(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class ViewHolderLeftLocation extends RecyclerView.ViewHolder{


        @BindView(R.id.left_friend_location_title)
        ImageView left_friend_location_title;
        @BindView(R.id.left_friend_location_text)
        TextView left_friend_location_text;
        public ViewHolderLeftLocation(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
