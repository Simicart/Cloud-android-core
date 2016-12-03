package com.simicart.core.searchvoice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.simicart.MainActivity;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class SearchVoice {

	private View mRootView;
	private final int REQ_CODE_SPEECH_INPUT = 100;

	private final String SEARCH_URL = "urlSearch";
	private final String SEARCH_CATE_ID = "cateId";
	private final String SEARCH_CATE_NAME = "cateName";
	private final String SEARCH_TAG = "tagSearch";

	private String cateId = null, cateName = null, tagSearch = null;

	public SearchVoice(String method, JSONObject json, View view) {
		mRootView = view;
		// getListLanguageSupport
		if (InstanceVoice.LISTLANGUAGESUPPORT.size() == 0) {
			getListLanguageSupport();
		}

		if (method.equals("addIconSearchVoice")) {
			if(null != json){
				addIconSearchVoiceProductList(json);
			}
			else{
				addIconSearchHome();
			}
		}
//		if (method.equals("addiconsearchtablet")) {
//			View rootView = cacheBlock.getView();
//			RelativeLayout rlt_layout = (RelativeLayout) rootView
//					.findViewById(Rconfig.getInstance().id("rlt_right_menutop"));
//			if (DataLocal.isTablet) {
//				addSearchVoiceTablet(rlt_layout);
//			}
//		}
	}

	public SearchVoice( String text) {
		handleSearchVoice(text);
	}

	private void addSearchVoiceTablet(RelativeLayout rootView) {
		RelativeLayout relativeLayout = rootView;
		LinearLayout layoutlogo = (LinearLayout) rootView.findViewById(Rconfig
				.getInstance().id("layoutlogo"));
		RelativeLayout layout_cart = (RelativeLayout) rootView
				.findViewById(Rconfig.getInstance().id("layout_cart"));

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				100, LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.LEFT_OF,
				Rconfig.getInstance().id("layout_cart"));
		layoutParams.addRule(RelativeLayout.RIGHT_OF,
				Rconfig.getInstance().id("layoutlogo"));
		RelativeLayout layout = new RelativeLayout(SimiManager.getIntance()
				.getCurrentContext());
		layout.setPadding(0, 0, 0, 14);
		layout.setLayoutParams(layoutParams);
		layoutlogo.addView(layout);

		ImageView imageView = new ImageView(SimiManager.getIntance()
				.getCurrentContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				Utils.getValueDp(25), Utils.getValueDp(25));
		params.setMargins(0, 0, 5, 0);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		imageView.setLayoutParams(params);
		imageView.setImageResource(Rconfig.getInstance().drawable(
				"ic_search"));
		imageView.setColorFilter(Color.parseColor("#ffffff"));
		layout.addView(imageView);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateData(null, null, null);
				promptSpeechInput();
			}
		});

	}

	private void getListLanguageSupport() {
		if (Locale.getAvailableLocales().length > 0) {
			for (Locale locale : Locale.getAvailableLocales()) {
				InstanceVoice.LISTLANGUAGESUPPORT.add(locale.toString());
			}
		}
	}

	private void handleSearchVoice(String textSearch) {
		String tagSearch = null, categoryName = null, categoryId = null;
		categoryId = InstanceVoice.SEARCH_CATE_ID;
		tagSearch = InstanceVoice.SEARCH_TAG;
		categoryName = InstanceVoice.SEARCH_CATE_NAME;
		searchProduct(categoryId, tagSearch, categoryName, textSearch);
	}

	private void addIconSearchHome() {
		LinearLayout layoutSearch = (LinearLayout) mRootView
				.findViewById(Rconfig.getInstance().id("ll_search"));
		RelativeLayout rlt_layout = (RelativeLayout) mRootView
				.findViewById(Rconfig.getInstance().id("rlt_layout"));
		rlt_layout.setVisibility(View.GONE);

		LayoutParams param_view = new LayoutParams(4, LayoutParams.MATCH_PARENT);
		View view = new View(SimiManager.getIntance().getCurrentContext());
		view.setBackgroundColor(Color.parseColor("#ffffff"));
		view.setLayoutParams(param_view);
		layoutSearch.addView(view);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				100, LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		RelativeLayout layout = new RelativeLayout(SimiManager.getIntance()
				.getCurrentContext());
		layout.setLayoutParams(layoutParams);
		layoutSearch.addView(layout);

		ImageView imageView = new ImageView(SimiManager.getIntance()
				.getCurrentContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				Utils.getValueDp(22), Utils.getValueDp(22));
		params.setMargins(0, 0, 5, 0);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		imageView.setLayoutParams(params);
		imageView.setImageResource(Rconfig.getInstance().drawable(
				"ic_search_voice"));
		layout.addView(imageView);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateData(null, null, null);
				promptSpeechInput();
			}
		});
	}

	private void addIconSearchVoiceProductList(JSONObject object) {

		LinearLayout layoutSearch = (LinearLayout) mRootView
				.findViewById(Rconfig.getInstance().id("ll_search"));
		RelativeLayout rlt_layout = (RelativeLayout) mRootView
				.findViewById(Rconfig.getInstance().id("rlt_layout"));
		rlt_layout.setVisibility(View.GONE);

		LayoutParams param_view = new LayoutParams(4, LayoutParams.MATCH_PARENT);
		View view = new View(SimiManager.getIntance().getCurrentContext());
		view.setBackgroundColor(Color.parseColor("#ffffff"));
		view.setLayoutParams(param_view);
		layoutSearch.addView(view);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				100, LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		RelativeLayout layout = new RelativeLayout(SimiManager.getIntance()
				.getCurrentContext());
		layout.setLayoutParams(layoutParams);
		layoutSearch.addView(layout);

		ImageView imageView = new ImageView(SimiManager.getIntance()
				.getCurrentContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				Utils.getValueDp(22), Utils.getValueDp(22));
		params.setMargins(0, 0, 5, 0);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		imageView.setLayoutParams(params);
		imageView.setImageResource(Rconfig.getInstance().drawable(
				"ic_search_voice"));
		layout.addView(imageView);

		try {
			if (object != null) {
				if (object.has(SEARCH_CATE_ID)) {
					cateId = object.getString(SEARCH_CATE_ID);
				}
				if (object.has(SEARCH_CATE_NAME)) {
					cateName = object.getString(SEARCH_CATE_NAME);
				}
				if (object.has(SEARCH_TAG)) {
					tagSearch = object.getString(SEARCH_TAG);
				}
			}
		} catch (Exception e) {
		}
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateData(cateId, cateName, tagSearch);
				promptSpeechInput();
			}
		});
	}

	private void promptSpeechInput() {
		// updateData(url, cateId, cateName, tagSearch);
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		if (checkLanguageSupport()) {
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Config
					.getInstance().getLocale_identifier());
		} else {
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
					Locale.getDefault());
		}
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");
		try {
			SimiManager.getIntance().getCurrentActivity()
					.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(SimiManager.getIntance().getCurrentContext(),
					"Sorry! Your device doesn\'t support speech input",
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean checkLanguageSupport() {
		if (InstanceVoice.LISTLANGUAGESUPPORT.size() > 0
				&& Utils.validateString(Config.getInstance()
						.getLocale_identifier())) {
			String locale = Config.getInstance().getLocale_identifier();
			System.out.println(locale);
			for (String language : InstanceVoice.LISTLANGUAGESUPPORT) {
				if (language
						.equals(Config.getInstance().getLocale_identifier())) {
					return true;
				}
			}
		}
		return false;
	}

	private void updateData(String cateId, String cateName, String tagsearch) {
		InstanceVoice.SEARCH_CATE_ID = cateId;
		InstanceVoice.SEARCH_CATE_NAME = cateName;
		InstanceVoice.SEARCH_TAG = tagsearch;
	}

	private void searchProduct(String cateID, String tagSearch,
			String cateName, String query) {

		CategoryDetailFragment fragment = CategoryDetailFragment.newInstance();
		fragment.setQuerySearch(query);
		fragment.setTag_search(tagSearch);
		fragment.setUrlSearch("products");
		fragment.setTypeSearch(TagSearch.TYPE_SEARCH_ALL);
		SimiManager.getIntance().addFragment(fragment);

//		SimiManager.getIntance().showToast(query);
//		if (tagSearch == null) {
//			tagSearch = TagSearch.TAG_LISTVIEW;
//		}
//		ListProductFragment fragment = ListProductFragment.newInstance(
//				Constants.SEARCH_PRODUCTS, cateID, tagSearch, null, cateName,
//				query, null, null);
//		SimiManager.getIntance().addFragment(fragment);
	}

}