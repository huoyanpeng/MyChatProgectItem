package test.bwie.com.mychatprogectitem.base;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/6/19.
 * function:
 */
public abstract class BasePresenter<T> {
    public T view ;


    public void attach(T view){

        this.view = view ;

    }

    public void detach(){

        this.view = null ;

    }

}
