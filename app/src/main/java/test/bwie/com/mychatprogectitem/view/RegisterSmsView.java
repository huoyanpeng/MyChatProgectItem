package test.bwie.com.mychatprogectitem.view;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/5.
 * function:
 */

public interface RegisterSmsView {
    /**
     * 1：手机号码不为空
     * 2：手机号码不合法
     */
    public void phoneError(int num);
    /**
     * 显示倒计时
     */
    public void showTimer();
    /**
     * 跳转下一个页面
     */
    public void nextPager();
    public void SMSError();
}
