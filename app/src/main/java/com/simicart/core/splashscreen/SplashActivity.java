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
import com.simicart.core.splashscreen.block.SplashBlock;
import com.simicart.core.splashscreen.controller.SplashController;

public class SplashActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context = this;
		SimiManager.getIntance().setCurrentContext(context);
		SimiManager.getIntance().setCurrentActivity(this);
		setContentView(Rconfig.getInstance().getId(context,
				"core_splash_screen", "layout"));
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
		SplashController controller = new SplashController( context);
		controller.create();
	}





}
