package com.simicart.core.simivideo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.simicart.core.config.Rconfig;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements
		YouTubePlayer.OnInitializedListener {
	  String VIDEO_ID ="";
	public static final String DEVELOPER_KEY = "AIzaSyD8GPps7zSu8OMY6KiiJAhHhFVQnFTbPJs";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Rconfig.getInstance().layout("plugins_youtube_item_activity_layout"));

		VIDEO_ID = getIntent().getStringExtra("KeyYoutube");
		System.out.println(VIDEO_ID);
		YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(Rconfig
				.getInstance().id("youtubeplayerview"));
		youTubePlayerView.initialize(DEVELOPER_KEY, this);
	} 

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		Toast.makeText(getApplicationContext(), "onInitializationFailure()",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
			player.loadVideo(VIDEO_ID);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.e("Activity", "finish");
		finish();
	}
}
