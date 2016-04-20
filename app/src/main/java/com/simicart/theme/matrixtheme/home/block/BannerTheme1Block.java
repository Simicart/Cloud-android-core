package com.simicart.theme.matrixtheme.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.simicart.core.banner.delegate.BannerDelegate;
import com.simicart.core.banner.entity.BannerEntity;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.matrixtheme.home.adapter.Theme1PagerAdapter;

import org.json.JSONObject;

public class BannerTheme1Block extends SimiBlock implements BannerDelegate {

	protected ViewPager vp_bannerTop;
	protected ArrayList<BannerEntity> mListBanner;
	protected Theme1PagerAdapter mAdapter;
	protected FragmentManager fragmentManager;

	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}

	public BannerTheme1Block(View view, Context context) {
		super(view, context);
	}

	@Override
	public void initView() {
		super.initView();
	}



	private int getResolutionScreen() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE); // the results will
															// be higher
															// than using the
															// activity
															// context object or
															// the
															// getWindowManager()
															// shortcut
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		// int screenWidth = displayMetrics.widthPixels;
		int screenHeight = displayMetrics.heightPixels;
		return screenHeight;
	}

	@Override
	public void drawView(SimiCollection collection) {
		mListBanner = new ArrayList<BannerEntity>();
		ArrayList<SimiEntity> listBanner = collection.getCollection();
		int count = listBanner.size();
		if (count == 0) {
			drawBannerFake();
			return;
		}

		for (int i = 0; i < count; i++) {
			BannerEntity bannerEntity = new BannerEntity();
            SimiEntity simiEntity = listBanner.get(i);
            JSONObject json = simiEntity.getJSONObject();
            bannerEntity.setJSONObject(json);
            bannerEntity.parse();
			mListBanner.add(bannerEntity);
		}

		if (mListBanner.size() > 0) {
			drawBanner();
		}
	}


    protected void drawBanner() {
        vp_bannerTop = (ViewPager) mView.findViewById(Rconfig.getInstance().id(
                "viewpager_banner_top"));
        mAdapter = new Theme1PagerAdapter(mContext, fragmentManager,
                mListBanner);
        mAdapter.setViewPager(vp_bannerTop);
        vp_bannerTop.setAdapter(mAdapter);
        vp_bannerTop.setOnPageChangeListener(mAdapter);
        vp_bannerTop.setCurrentItem((mListBanner.size() * 20) / 2);
        vp_bannerTop.setOffscreenPageLimit(3);
        int margin = Integer.parseInt(mContext.getString(Rconfig.getInstance()
                .string("pagermargin")));
        String brand = android.os.Build.BRAND;
        String message = mContext.getString(Rconfig.getInstance().string(
                "values"));

        Log.e("BannerTheme1Block ", "MESSAGE " + message);
        int height = getResolutionScreen();

        Log.e("BannerTheme1Block ", "HEIGHT " + height);

        if (message.equals("sw800dp")) {
            margin = -1200;
            if (height > 700) {
                margin = -750;
            }

            if (brand.equals("samsung")) {
                margin = -800;
            }
        }
        if (height < 700) {
            margin = -550;
        }

        Log.e("BannerTheme1Block ", "MARGIN " + margin);

        vp_bannerTop.setPageMargin(margin);
    }

	private void drawBannerFake() {
		mView.setBackgroundResource(Rconfig.getInstance().drawable(
				"fake_banner"));
	}
}
