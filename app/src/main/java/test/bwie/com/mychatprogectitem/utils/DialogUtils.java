package test.bwie.com.mychatprogectitem.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import test.bwie.com.mychatprogectitem.R;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/7.
 * function:
 */
public class DialogUtils {
    /**
     * 创建自定义ProgressDialog
     *
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_loading_dialog, null); // 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view); // 加载布局
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog); // 创建自定义样式dialog
        loadingDialog.setCancelable(false); // 不可以用"返回键"取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }

}
