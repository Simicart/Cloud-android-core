package com.simicart.core.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.product.entity.Attributes;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.BasicInforFragment;
import com.simicart.core.catalog.product.fragment.DescriptionFragment;
import com.simicart.core.catalog.product.fragment.RelatedProductFragment;
import com.simicart.core.catalog.product.fragment.TechSpecsFragment;
import com.simicart.core.common.price.ProductDetailPriceView;
import com.simicart.core.config.Config;

public class TabAdapterFragment extends FragmentStatePagerAdapter {
    protected ProductEntity mProduct;
    protected ArrayList<SimiFragment> mListFragment;
    protected ArrayList<String> mListTitle;
    protected ProductDetailPriceView priceViewBasic;

    public void setPriceViewBasic(ProductDetailPriceView priceView) {
        priceViewBasic = priceView;
    }

    public TabAdapterFragment(FragmentManager fm, ProductEntity product,ProductDetailPriceView priceView) {
        super(fm);
        this.mProduct = product;
        mListFragment = new ArrayList<SimiFragment>();
        mListTitle = new ArrayList<String>();
        priceViewBasic = priceView;
        addFragment();
        addTitle();
        EventTabFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitle.get(position);
    }

    private void addFragment() {
        BasicInforFragment fragment_basic = BasicInforFragment.newInstance();
        if(null != priceViewBasic)
        {
            Log.e("Tab Adapter Fragment ", "set Price View Basic");
            fragment_basic.setPriceViewBasic(priceViewBasic);
        }
        fragment_basic.setProduct(mProduct);

        mListFragment.add(fragment_basic);

        DescriptionFragment fragment_description = DescriptionFragment
                .newInstance();
        fragment_description.setDescription(mProduct.getDescription());

        mListFragment.add(fragment_description);

        ArrayList<Attributes> attributes = mProduct.getAttibutes();

        if (null != attributes && !attributes.isEmpty()) {
            TechSpecsFragment fragment_tech = TechSpecsFragment.newInstance();
            fragment_tech.setAttributes(attributes);
            mListFragment.add(fragment_tech);
        }

//		if (mProduct.getRate() > 0 && mProduct.getReviewNumber() > 0) {
//			CustomerReviewFragment fragment_review = CustomerReviewFragment
//					.newInstance();
//			fragment_review.setProductID(mProduct.getId());
//			fragment_review.setRatingStar(mProduct.getStar());
//			fragment_review.setProduct(mProduct);
//			mListFragment.add(fragment_review);
//		}

        if (!mProduct.getRelatedProduct().isEmpty()) {
            if (mProduct.getRelatedProduct().size() > 0) {
                RelatedProductFragment fragment_related = RelatedProductFragment
                        .newInstance();
                fragment_related.setListProductRelated(mProduct.getRelatedProduct());
                mListFragment.add(fragment_related);
            }
        }
    }

    private void addTitle() {
        mListTitle.add(Config.getInstance().getText("Basic Info"));
        mListTitle.add(Config.getInstance().getText("Description"));
        ArrayList<Attributes> attributes = mProduct.getAttibutes();
        if (null != attributes && !attributes.isEmpty()) {
            mListTitle.add(Config.getInstance().getText("Tech Specs"));
        }
//		if (mProduct.getRate() > 0 && mProduct.getReviewNumber() > 0) {
//			mListTitle.add(Config.getInstance().getText("Review"));
//		}
        if (!mProduct.getRelatedProduct().isEmpty()) {
            if (mProduct.getRelatedProduct().size() > 0) {
                mListTitle.add(Config.getInstance().getText("Related Products"));
            }
        }
    }

    public void EventTabFragment() {
//        EventBlock event = new EventBlock();
//        CacheBlock cacheBlock = new CacheBlock();
//        cacheBlock.setListFragment(mListFragment);
//        cacheBlock.setListName(mListTitle);
//        cacheBlock.setSimiEntity(mProduct);
//        event.dispatchEvent("com.simicart.core.adapter.TabAdapterFragment", cacheBlock);
    }
}
