package com.simicart.core.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;
import com.simicart.MainActivity;
import com.simicart.core.adapter.ProductBaseAdapter;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.controller.ProductListListenerController;
import com.simicart.core.home.delegate.SpotProductDelegate;

public class SpotProductBlock extends SimiBlock implements SpotProductDelegate {

    protected ProductListListenerController listner;
    protected LinearLayout ll_layoutchild;
    protected LinearLayout ll_layout;
    protected ProgressBar progessBar;

    public SpotProductBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void onShowBestSeller(int postion, String title, ArrayList<ProductEntity> bestSellers) {
        showSpotProduct(postion, title, bestSellers);
    }

    @Override
    public void onShowNewlyUpdated(int postion, String title, ArrayList<ProductEntity> newlyUpdated) {
        showSpotProduct(postion, title, newlyUpdated);
    }

    @Override
    public void onShowRecentlyAdded(int postion, String title, ArrayList<ProductEntity> recentlyAdded) {
        showSpotProduct(postion, title, recentlyAdded);
    }

    @Override
    public void onShowFeature(int postion, String title, ArrayList<ProductEntity> feature) {
        showSpotProduct(postion, title, feature);
    }

    @Override
    public void createViewSport(int rowView) {
        ll_layout = (LinearLayout) mView.findViewById(Rconfig.getInstance().id("ll_spot"));
        for (int i = 0; i <= rowView; i++) {
            ll_layoutchild = new LinearLayout(mContext);
            ll_layout.addView(ll_layoutchild, i);
        }
    }

    @Override
    public void showLoadingSpot() {
        progessBar = (ProgressBar) mView.findViewById(Rconfig.getInstance().id("progressbarSpot"));
        progessBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoadingSpot() {
        progessBar = (ProgressBar) mView.findViewById(Rconfig.getInstance().id("progressbarSpot"));
        progessBar.setVisibility(View.GONE);
    }

    private void showSpotProduct(final int postion, final String title, final ArrayList<ProductEntity> products) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != products && products.size() > 0) {
                            View vll_spot = ll_layout.getChildAt(postion);
                            if (vll_spot instanceof LinearLayout) {
                                LinearLayout ll_spot = ((LinearLayout) vll_spot);

                                LinearLayout ll_sport_child = new LinearLayout(mContext);
                                ll_sport_child.setOrientation(LinearLayout.VERTICAL);

                                RelativeLayout.LayoutParams title_lp = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT,
                                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                                // name
                                TextView tv_name = new TextView(mContext);
                                tv_name.setLayoutParams(title_lp);
                                int height = 0;

                                if (DataLocal.isTablet) {
                                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                                } else {
                                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                                }
                                int padd = Utils.getValueDp(4);
                                tv_name.setPadding(Utils.getValueDp(6), Utils.getValueDp(10),
                                        Utils.getValueDp(7), Utils.getValueDp(10));
                                tv_name.setText(Config.getInstance().getText(
                                        title.toUpperCase()));
                                tv_name.setTextColor(Config.getInstance().getContent_color());
                                if (DataLocal.isTablet) {
                                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                                } else {
                                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                }
                                if (DataLocal.isLanguageRTL) {
                                    tv_name.setGravity(Gravity.RIGHT);
                                } else {
                                    tv_name.setGravity(Gravity.LEFT);
                                }
                                ll_sport_child.addView(tv_name);

                                // product list
                                LinearLayout llspot = new LinearLayout(mContext);
                                if (DataLocal.isTablet) {
                                    height = Utils.getValueDp(250);
                                    LinearLayout.LayoutParams spot_ll = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, height);
                                    llspot.setPadding(0, padd, 0, 0);
                                    llspot.setLayoutParams(spot_ll);
                                } else {
                                    height = Utils.getValueDp(165);
                                    LinearLayout.LayoutParams spot_ll = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT, height);
                                    llspot.setPadding(0, padd, 0, 0);
                                    llspot.setLayoutParams(spot_ll);
                                }
                                HorizontalListView listview_spotproduct = new HorizontalListView(
                                        mContext, null);
                                RelativeLayout.LayoutParams listh = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.MATCH_PARENT,
                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                                listview_spotproduct.setLayoutParams(listh);

                                ProductBaseAdapter productAdapter = new ProductBaseAdapter(
                                        mContext, products);
                                productAdapter.setIsHome(true);
                                listview_spotproduct.setAdapter(productAdapter);
                                listner = new ProductListListenerController();
                                listner.setProductList(products);
                                listview_spotproduct.setOnItemClickListener(listner
                                        .createTouchProductList());
                                llspot.addView(listview_spotproduct);
                                ll_sport_child.addView(llspot);
                                ll_spot.addView(ll_sport_child);
                            }
                        }
                    }
                });
            }
        });
        thread.start();
    }
}
