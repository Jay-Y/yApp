package org.yapp.core.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yapp.core.presenter.BasePresenter;
import org.yapp.core.ui.abase.BasePartsApi;
import org.yapp.core.ui.inject.annotation.ContentInject;
import org.yapp.ex.ApplicationException;
import org.yapp.utils.Log;

/**
 * ClassName: BaseFragment <br> 
 * Description: 公共Fragment 通一编码规范. <br> 
 * Date: 2015-6-15 下午5:44:09 <br> 
 * 
 * @author ysj 
 * @version  1.0
 * @since JDK 1.7
 */
public abstract class BaseV4Fragment<P extends BasePresenter> extends Fragment implements BasePartsApi {
	/**
	 * the presenter of this Activity
	 */
	protected P mPresenter;

	public P getPresenter() {
		return mPresenter;
	}

	/**
	 * init presenter
	 */
	@Override
	public void initPresenter(){
		try {
			Class<?> cls = Class.forName(mPresenter.getClass().getName());
			mPresenter = (P)cls.newInstance();
			if (null == mPresenter) {
				throw new ApplicationException("Do you need to init mPresenter in " + this.getClass().getSimpleName() + " initPresenter() method.");
			}
		} catch (Exception e) {
			Log.e(e.getMessage(), e);
			throw new ApplicationException("Presenter mapping failure.");
		}
	}

	@Override
	public int getLayoutId() {
		Class<?> clazz = this.getClass();
		ContentInject inject = clazz.getAnnotation(ContentInject.class);
		try {
			return inject.value();
		}catch (Exception e){
			Log.e(e.getMessage(),e);
			throw new ApplicationException("Please setting id of "+clazz.getSimpleName()+" by @ViewInject.");
		}
	}

	/**
	 * Fragment创建UI
	 * @see android.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState){
		View view = inflater.inflate(getLayoutId(), container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPresenter();
		mPresenter.onBuild(getActivity());
		mPresenter.onInit();
	}

	@Override
	public void onDestroyView() {
		mPresenter.onDestroy();
		mPresenter = null;
		super.onDestroyView();
	}
}
