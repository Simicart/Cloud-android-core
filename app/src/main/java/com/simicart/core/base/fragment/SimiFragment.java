package com.simicart.core.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;

public class SimiFragment extends Fragment {

	protected View rootView;
	protected boolean isShowPopup = false;
	protected String screenName = "";

	public static SimiFragment newInstance() {
		SimiFragment fragment = new SimiFragment();
		return fragment;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setShowPopup(boolean isShowPopup) {
		this.isShowPopup = isShowPopup;
	}

	public boolean isShowPopup() {
		return isShowPopup;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && getActivity() != null) {
			if (DataLocal.isTablet) {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}
	}

	public void setScreenName(String name) {
		this.screenName = name;
		// dispatch event for Google Analytics
		Context context = SimiManager.getIntance().getCurrentContext();
		Intent intent = new Intent("com.simicart.core.base.fragment.SimiFragment.setName");
		SimiEventFragmentEntity entity = new SimiEventFragmentEntity();
		entity.setFragmetn(this);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.ENTITY, entity);
		intent.putExtra(Constants.DATA, bundle);
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
