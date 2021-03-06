package org.yapp.core.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.yapp.core.Application;
import org.yapp.core.ui.abase.BaseFragmentPresenterApi;
import org.yapp.core.ui.activity.BaseAppCompatActivity;
import org.yapp.core.ui.fragment.BaseFragment;
import org.yapp.core.ui.inject.ViewInjector;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.y;

/**
 * Description: Fragemnt主持层抽象基类. <br>
 * Date: 2016/3/14 16:03 <br>
 * Author: ysj
 */
public abstract class BaseFragmentPresenter<T extends BaseAppCompatActivity, F extends BaseFragment, A extends Application>
        extends BasePresenter<T, A> implements BaseFragmentPresenterApi<F> {
    protected F mFragment;

    /**
     * 构建
     *
     * @param context activity context
     */
    @Override
    public void onBuild(Context context){
        super.onBuild(context);
        Log.w("Please override the onBuild(Context context, "+this.getClass().getSimpleName()+" fragment) method in " + this.getClass().getSimpleName() + ".");
    }

    /**
     * 构建
     *
     * @param context
     * @param fragment
     */
    @Override
    public void onBuild(Context context, F fragment) {
        super.onBuild(context);
        this.mFragment = fragment;
        ViewInjector.inject(fragment, this);
    }

    @Override
    public F getFragment() {
        return mFragment;
    }

    @Override
    public View getContextView(){
        return mFragment.getView();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void go(Class<?> clazz) {
        Intent intent = new Intent(getContext(), clazz);
        getFragment().startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void go(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getFragment().startActivity(intent);
    }

    /**
     * startActivity with bundle and delayed
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void go(Class<?> clazz, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragment().startActivity(intent);
            }
        }, delayed * 1000);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void goThenKill(Class<?> clazz) {
        Intent intent = new Intent(getContext(), clazz);
        getFragment().startActivity(intent);
        getContext().finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public void goThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getFragment().startActivity(intent);
        getContext().finish();
    }

    /**
     * startActivity with bundle and delayed then finish
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void goThenKill(Class<?> clazz, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragment().startActivity(intent);
                getContext().finish();
            }
        }, delayed * 1000);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    public void goForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        getFragment().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void goForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getFragment().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle and delayed
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void goForResult(Class<?> clazz, final int requestCode, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFragment().startActivityForResult(intent, requestCode);
            }
        }, delayed * 1000);
    }

    @Override
    public void showMsg(String msg) {
        getContext().getPresenter().showMsg(msg);
    }

    @Override
    public void showError(Throwable throwable) {
        getContext().getPresenter().showError(throwable);
    }

    /**
     * 弹出Dialog
     *
     * @param msg
     * @param title
     * @param callback
     */
    @Override
    public void showDialog(String msg, String title, Callback.DialogCallback callback) {
        if (null != getContext().getPresenter())
            getContext().getPresenter().showDialog(title, msg, callback);
    }

    @Override
    public void closeDialog() {
        if (null != getContext().getPresenter()) getContext().getPresenter().closeDialog();
    }

    @Override
    public void showLoading() {
        if (null != getContext().getPresenter()) getContext().getPresenter().showLoading();
    }

    @Override
    public boolean isLoading() {
        if (null != getContext().getPresenter()) {
            return getContext().getPresenter().isLoading();
        }
        return false;
    }

    @Override
    public void closeLoading() {
        if (null != getContext().getPresenter()) getContext().getPresenter().closeLoading();
    }
}