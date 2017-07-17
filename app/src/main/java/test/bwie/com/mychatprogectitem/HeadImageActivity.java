package test.bwie.com.mychatprogectitem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.RuntimePermissions;
import test.bwie.com.mychatprogectitem.activity.LoginActivity;
import test.bwie.com.mychatprogectitem.base.IActivity;
import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.bean.UploadPhotoBean;
import test.bwie.com.mychatprogectitem.core.JNICore;
import test.bwie.com.mychatprogectitem.core.SortUtils;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.utils.Constants;
import test.bwie.com.mychatprogectitem.utils.ImageResizeUtils;
import test.bwie.com.mychatprogectitem.utils.MyToast;
import test.bwie.com.mychatprogectitem.utils.SDCardUtils;
import permissions.dispatcher.PermissionRequest;
import android.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static test.bwie.com.mychatprogectitem.R.id.haed_button_up;
import static test.bwie.com.mychatprogectitem.R.id.haed_image_title;
import static test.bwie.com.mychatprogectitem.utils.ImageResizeUtils.copyStream;


@RuntimePermissions
public class HeadImageActivity extends IActivity {

    private Unbinder bind;
    @BindView(R.id.haed_image_arrow)
    ImageView arrow;
    @BindView(haed_button_up)
    Button button_up;
    @BindView(R.id.haed_button_photo)
    Button button_phto;
    @BindView(R.id.haed_text_finish)
    TextView finish;
//    @BindView(haed_image_title)
//    ImageView title;
    @BindView(R.id.haed_image_title)
    CircleImageView title;

    static final int INTENTFORCAMERA = 1 ;
    static final int INTENTFORPHOTO = 2 ;
    public String LocalPhotoName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);
    }

    @OnClick({haed_image_title,R.id.haed_button_photo, haed_button_up,R.id.haed_image_arrow,R.id.haed_text_finish})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.haed_button_photo:
                openCamera();
                break;
            case haed_button_up:
                toPhoto();
                break;
            case R.id.haed_image_arrow:
                finish();
                break;
            case R.id.haed_text_finish:
                startActivity(new Intent(HeadImageActivity.this, LoginActivity.class));
                break;
            case haed_image_title:
                break;
        }

    }

    private void toPhoto() {
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, INTENTFORPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return  LocalPhotoName ;
    }

    public void openCamera() {
        HeadImageActivityPermissionsDispatcher.toCameraWithCheck(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HeadImageActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void onDenied(){
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

    }
    /**
     * 打开系统相机
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA} )
    public void toCamera(){
        try {
            Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
            startActivityForResult(intentNow, INTENTFORCAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void onNeverAsyAgain(){
        Toast.makeText(this, "不再提示", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENTFORPHOTO:
                //相册
                try {
                    // 必须这样处理，不然在4.4.2手机上会出问题
                    Uri originalUri =  data.getData();
                    File f = null;
                    if (originalUri != null) {
                        f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor =  this.getContentResolver().query(originalUri, proj, null, null, null);
                        if (null == actualimagecursor) {
                            if (originalUri.toString().startsWith("file:")) {
                                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                if(!file.exists()){
                                    //地址包含中文编码的地址做utf-8编码
                                    originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(),"UTF-8"));
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        } else {
                            // 系统图库
                            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            String img_path = actualimagecursor.getString(actual_image_column_index);
                            if (img_path == null) {
                                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            } else {
                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }

                        }
                        Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                        if (bitmap != null) {
                            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                                fos.close();
                                fos.flush();
                            }
                            if (!bitmap.isRecycled()) {
                                bitmap.isRecycled();
                            }
                            uploadFile(f,width,height,bitmap);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case INTENTFORCAMERA:
//                相机
                try {
                    //file 就是拍照完 得到的原始照片
                    File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), Constants.RESIZE_PIC);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }
                        uploadFile(file,width,height,bitmap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void uploadFile(File file, int bitmapWith, int bitmapHeight, final Bitmap bitmap) {
        if (!file.exists()) {
            MyToast.makeText(this, " 照片不存在", Toast.LENGTH_SHORT);
            return;
        }
        String[] arr = file.getAbsolutePath().split("/");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        Map<String, String> map = new HashMap<>();
        map.put("user.currenttimer", System.currentTimeMillis() + "");
        map.put("user.picWidth", bitmapWith+"");
        map.put("user.picHeight", bitmapHeight+"");

        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);


        MultipartBody body = new MultipartBody.Builder().addFormDataPart("image", arr[arr.length - 1], requestFile).build();

        RetrofitManager.uploadPhoto(body, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                try {
                    Gson gson = new Gson();
                    UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
                    if (bean.getResult_code() == 200) {
                        MyToast.makeText(IApplication.getApplication(), "上传成功", Toast.LENGTH_SHORT);
                        title.setImageBitmap(bitmap);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void onFailed(int code) {
                MyToast.makeText(IApplication.getApplication(), "" + code, Toast.LENGTH_SHORT);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void showRationaleForCameras(final PermissionRequest request) {

        new AlertDialog.Builder(this)
                .setMessage("需要打开您的相机来上传照片并保存照片")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        request.proceed();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }
}
