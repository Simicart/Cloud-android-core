package com.simicart.core.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.devsmart.android.ui.HorizontalListView;
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

    public SpotProductBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void onShowBestSeller(ArrayList<ProductEntity> bestSellers) {
        Log.e("SpotProductBlock", "onShowBestSeller 001");
        LinearLayout ll_best_seller = (LinearLayout) mView.findViewById(Rconfig.getInstance().id("ll_best_seller"));
        String title = Config.getInstance().getText("Best-Seller");
        showSpotProduct(ll_best_seller, title, bestSellers);
        Log.e("SpotProductBlock", "onShowBestSeller " + bestSellers.size());
    }

    @Override
    public void onShowNewlyUpdated(ArrayList<ProductEntity> newlyUpdated) {
        Log.e("SpotProductBlock", "onShowNewlyUpdated 001");
        LinearLayout ll_newly_updated = (LinearLayout) mView.findViewById(Rconfig.getInstance().id("ll_newly_updated"));
        Log.e("SpotProductBlock","onShowNewlyUpdated 002");
        String title = Config.getInstance().getText("Newly Updated");
        Log.e("SpotProductBlock","onShowNewlyUpdated 003");
        showSpotProduct(ll_newly_updated, title, newlyUpdated);
    }

    @Override
    public void onShowRecentlyAdded(ArrayList<ProductEntity> recentlyAdded) {
        Log.e("SpotProductBlock", "onShowRecentlyAdded 001");
        LinearLayout ll_recently_added = (LinearLayout) mView.findViewById(Rconfig.getInstance().id("ll_recently_added"));
        String title = Config.getInstance().getText("Recently Addred");
        showSpotProduct(ll_recently_added, title, recentlyAdded);
    }

    @Override
    public void onShowFeature(ArrayList<ProductEntity> feature) {
        Log.e("SpotProductBlock", "onShowFeature 001");
        LinearLayout ll_feature = (LinearLayout) mView.findViewById(Rconfig.getInstance().id("ll_feature"));
        String title = Config.getInstance().getText("Feature");
        showSpotProduct(ll_feature, title, feature);
    }

    private void showSpotProduct(LinearLayout ll_spot, String title, ArrayList<ProductEntity> products) {
        Log.e("SpotProductBlock","showSpotProduct 001");
        if (null != products && products.size() > 0) {
            Log.e("SpotProductBlock","showSpotProduct 002");
            ll_spot.setVisibility(View.VISIBLE);

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
            ll_spot.addView(tv_name);

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
            ll_spot.addView(llspot);
//			if (i != count - 1) {
//				View view = new View(mContext);
//				view.setBackgroundColor(Config.getInstance().getLine_color());
//				LinearLayout.LayoutParams lp_view = new LinearLayout.LayoutParams(
//						RelativeLayout.LayoutParams.MATCH_PARENT, 1);
//				ll_listProduct.addView(view, lp_view);
//			}
            YoYo.with(Techniques.Shake).duration(2000)
                    .playOn(listview_spotproduct);
        } else {
            Log.e("SpotProductBlock","showSpotProduct 003");
            ll_spot.setVisibility(View.GONE);
        }
    }

}
