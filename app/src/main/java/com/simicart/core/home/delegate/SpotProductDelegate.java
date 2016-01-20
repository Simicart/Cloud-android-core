package com.simicart.core.home.delegate;

import java.util.ArrayList;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

public interface SpotProductDelegate extends SimiDelegate {

    public void onShowBestSeller(int postion, String title, ArrayList<ProductEntity> bestSellers);

    public void onShowNewlyUpdated(int postion, String title, ArrayList<ProductEntity> newlyUpdated);

    public void onShowRecentlyAdded(int postion, String title, ArrayList<ProductEntity> recentlyAdded);

    public void onShowFeature(int postion, String title, ArrayList<ProductEntity> feature);

    public void createViewSport(int rowView);

    public void showLoadingSpot();

    public void dismissLoadingSpot();
}
