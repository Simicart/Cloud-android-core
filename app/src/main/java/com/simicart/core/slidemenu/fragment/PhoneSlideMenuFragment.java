package com.simicart.core.slidemenu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.slidemenu.adapter.TabSlideMenuAdapter;
import com.simicart.core.slidemenu.block.PhoneSlideMenuBlock;
import com.simicart.core.slidemenu.controller.PhoneSlideMenuController;
import com.simicart.core.slidemenu.delegate.CloseSlideMenuDelegate;
import com.simicart.core.style.PagerSlidingTabStrip;

import java.util.ArrayList;

public class PhoneSlideMenuFragment extends SimiFragment {

	protected PhoneSlideMenuController mController;
	protected PhoneSlideMenuBlock mBlock;
	protected CloseSlideMenuDelegate mCloseDelegate;
	protected TabSlideMenuAdapter tabSlideMenuAdapter;
	protected ViewPager mViewPager;
	protected ArrayList<SimiFragment> mListFragment;
	protected FragmentManager fm;
	PagerSlidingTabStrip titletab;

	public void setCloseDelegate(CloseSlideMenuDelegate delegate) {
		mCloseDelegate = delegate;
	}

	public void setTabSlideMenuAdapter(TabSlideMenuAdapter tabSlideMenuAdapter) {
		this.tabSlideMenuAdapter = tabSlideMenuAdapter;
	}

	public void setViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
	}

	public void setFm(FragmentManager fm) {
		this.fm = fm;
	}

	public void setTitletab(PagerSlidingTabStrip titletab) {
		this.titletab = titletab;
	}

	public void setListFragment(ArrayList<SimiFragment> mListFragment) {
		this.mListFragment = mListFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("PhoneSlideMenuFragment", "onCreateView 001");
		View view = inflater.inflate(
				Rconfig.getInstance().layout("core_phone_slide_menu"), null,
				false);
		view.setBackgroundColor(Config.getInstance().getMenu_background());
		Log.e("PhoneSlideMenuFragment", "onCreateView 002");
		Context context = getActivity();
		mBlock = new PhoneSlideMenuBlock(view, context);
		mBlock.initView();
		Log.e("PhoneSlideMenuFragment", "onCreateView 003");
		mController = new PhoneSlideMenuController(mBlock, context);
		mController.setCloseDelegate(mCloseDelegate);
		if(DataLocal.isTablet) {
			mController.setTabSlideMenuAdapter(tabSlideMenuAdapter);
			mController.setViewPager(mViewPager);
			mController.setListFragment(mListFragment);
			mController.setFmanager(fm);
			mController.setTitletab(titletab);
		}
		mController.create();
		Log.e("PhoneSlideMenuFragment", "onCreateView 004");
		mBlock.setListener(mController.getListener());
		mBlock.setClickerPersonal(mController.getOnClickPersonal());
		Log.e("PhoneSlideMenuFragment", "onCreateView 005");
		SimiManager.getIntance().setSlideMenuController(mController);
		Log.e("PhoneSlideMenuFragment", "onCreateView 006");
		return view;
	}
}
