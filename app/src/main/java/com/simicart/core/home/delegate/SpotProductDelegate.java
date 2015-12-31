package com.simicart.core.home.delegate;

import java.util.ArrayList;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

public interface SpotProductDelegate extends SimiDelegate {

    public void onShowBestSeller(ArrayList<ProductEntity> bestSellers);

    public void onShowNewlyUpdated(ArrayList<ProductEntity> newlyUpdated);

    public void onShowRecentlyAdded(ArrayList<ProductEntity> recentlyAdded);

    public void onShowFeature(ArrayList<ProductEntity> feature);
}
