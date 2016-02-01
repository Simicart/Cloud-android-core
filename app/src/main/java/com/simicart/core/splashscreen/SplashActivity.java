package com.simicart.core.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.activity.CacheActivity;
import com.simicart.core.shortcutbadger.ShortcutBadgeException;
import com.simicart.core.shortcutbadger.ShortcutBadger;
import com.simicart.core.splashscreen.block.SplashBlock;
import com.simicart.core.splashscreen.controller.SplashController;
import com.simicart.core.splashscreen.delegate.SplashDelegate;

public class SplashActivity extends Activity implements SplashDelegate {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Frank comment 1

//		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));
		Context context = this;
		SimiManager.getIntance().setCurrentContext(context);
		SimiManager.getIntance().setCurrentActivity(this);
		setContentView(Rconfig.getInstance().getId(context,
				"core_splash_screen", "layout"));
//		GCMIntentService.notificationData = null;
		DataLocal.isTablet = Utils.isTablet(context);
		if (DataLocal.isTablet) {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		View view = findViewById(Rconfig.getInstance().getId(
				getApplicationContext(), "core_splash_screen", "id"));

		SplashBlock block = new SplashBlock(view, context);
		block.initView();
		SplashController controller = new SplashController(this, context);
		controller.create();

		// dispatch event for sent google analytic
		CacheActivity cacheActivity = new CacheActivity();
		cacheActivity.setActivity(this);
//		EventActivity dispacth = new EventActivity();
//		dispacth.dispatchEvent("com.simicart.splashscreen.onCreate",
//				cacheActivity);
		// end dispatch

		// Update badge
		try {
			ShortcutBadger.setBadge(context, 0);
		} catch (ShortcutBadgeException e) {

		}
	}

	@Override
	public void creatMain() {
		SimiManager.getIntance().toMainActivity();
	}

	@Override
	public void onBackPressed() {
	}

}
