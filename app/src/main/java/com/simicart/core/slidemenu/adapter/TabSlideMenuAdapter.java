package com.simicart.core.slidemenu.adapter;

import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Config;
import com.simicart.core.slidemenu.fragment.PhoneSlideMenuFragment;
import com.simicart.core.style.PagerSlidingTabStrip;

public class TabSlideMenuAdapter extends FragmentStatePagerAdapter {
	protected ArrayList<SimiFragment> mListFragment;
	protected ArrayList<String> mListTitle;
	protected ViewPager mViewPager;
	protected FragmentManager fmanager;
	PagerSlidingTabStrip titletab;
	public TabSlideMenuAdapter(FragmentManager fm,
			ArrayList<SimiFragment> simiFragments, ViewPager mPager, PagerSlidingTabStrip title_tab) {
		super(fm);
		fmanager = fm;
		mListFragment = simiFragments;
		mListTitle = new ArrayList<String>();
		mViewPager = mPager;
		titletab = title_tab;
		addTitle();
	}

	public TabSlideMenuAdapter(FragmentManager fm ,ArrayList<SimiFragment> simiFragments) {
		super(fm);
		mListFragment = simiFragments;
		mListTitle = new ArrayList<String>();
		addTitle();
	}

	@Override
	public SimiFragment getItem(int position) {
		SimiFragment fragment = mListFragment.get(position);
		if(fragment instanceof PhoneSlideMenuFragment){
			((PhoneSlideMenuFragment) fragment).setTabSlideMenuAdapter(this);
			((PhoneSlideMenuFragment) fragment).setViewPager(mViewPager);
			((PhoneSlideMenuFragment) fragment).setFm(fmanager);
			((PhoneSlideMenuFragment) fragment).setListFragment(mListFragment);
			((PhoneSlideMenuFragment) fragment).setTitletab(titletab);
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return mListFragment.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mListTitle.get(position);
	}

	private void addTitle() {
		mListTitle.add(Config.getInstance().getText("Menu"));
		mListTitle.add(Config.getInstance().getText("Category"));
	}
}
