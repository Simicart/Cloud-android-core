package com.simicart.core.catalog.product.options;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.delegate.OptionProductDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.entity.productEnity.VariantsAttributeEntity;
import com.simicart.core.catalog.product.options.bundleitem.ManageBundleItemView;
import com.simicart.core.catalog.product.options.customoption.ManageCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.groupitem.GroupItemView;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupDelegate;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupEntity;
import com.simicart.core.catalog.product.options.variants.ManageVariantAttributeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 11/12/2015.
 */
public class ManageOptionView implements ProductVariantDelegate, ManageOptionDelegate, ItemGroupDelegate {
    protected ProductEntity mProduct;
    ManageCustomOptionView manageCustomOptionView;
    ManageVariantAttributeView manageVariantView;
    protected VariantEntity mVariantEntity;
    protected ManageBundleItemView bundleItemView;
    GroupItemView groupView;
    protected OptionProductDelegate mDelegate;

    public void setDelegate(OptionProductDelegate delegate) {
        mDelegate = delegate;
    }

    public ManageOptionView(ProductEntity product) {
        mProduct = product;
    }

    public View createOptionView() {
        String type = mProduct.getType();
        if (type.equals("grouped")) {
            return createGroupedView();
        } else if (type.equals("bundle")) {
            return createBundleItemView();
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_Option = new LinearLayout(SimiManager.getIntance().getCurrentContext());
        ll_Option.setOrientation(LinearLayout.VERTICAL);

        View viewCustom = createCustomOptionView();
        if (null != viewCustom) {
            ll_Option.addView(viewCustom, params);
        }


        View viewVariant = createVariantView();
        if (null != viewVariant) {
            ll_Option.addView(viewVariant, params);
        }
        return ll_Option;
    }

    protected View createCustomOptionView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_Option = new LinearLayout(SimiManager.getIntance().getCurrentContext());
        ll_Option.setOrientation(LinearLayout.VERTICAL);
        ArrayList<CustomOptionEntity> listCustom = mProduct.getListCustom();
        if (null != listCustom && listCustom.size() > 0) {
            manageCustomOptionView = new ManageCustomOptionView(listCustom);
            manageCustomOptionView.setDelegate(this);
            View view = manageCustomOptionView.createView();
            if (null != view) {
                ll_Option.addView(view, params);
            }
        }

        return ll_Option;
    }

    protected View createVariantView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_Option = new LinearLayout(SimiManager.getIntance().getCurrentContext());
        ll_Option.setOrientation(LinearLayout.VERTICAL);

        ArrayList<VariantEntity> listVariant = mProduct.getVariants();
        ArrayList<VariantsAttributeEntity> listAttribute = mProduct.getVariantAttribute();
        if (null != listVariant && listVariant.size() > 0 && null != listAttribute && listAttribute.size() > 0) {
            manageVariantView = new ManageVariantAttributeView(listAttribute, listVariant, this);
            View view = manageVariantView.createView();
            if (null != view) {
                ll_Option.addView(view, params);
            }
        }

        return ll_Option;
    }

    protected View createBundleItemView() {
        ArrayList<CustomOptionEntity> listBundleItem = mProduct.getListBundleItem();
        bundleItemView = new ManageBundleItemView(listBundleItem);
        bundleItemView.setDelegate(this);
        return bundleItemView.createView();
    }

    protected View createGroupedView() {
        ArrayList<ItemGroupEntity> listItemGroup = mProduct.getListItemGroup();
        groupView = new GroupItemView(listItemGroup);
        groupView.setDelegate(this);
        return groupView.createView();
    }


    public JSONObject getDataForCheckout() {
        JSONObject json = new JSONObject();
        if (null == mVariantEntity) {
            ArrayList<VariantEntity> variants = mProduct.getVariants();
            if (null != variants && variants.size() > 0) {
                mVariantEntity = variants.get(0);
            }
        }
        if (null != mVariantEntity) {
            String variant_id = mVariantEntity.getID();
            try {
                json.put("variant_id", variant_id);
                json.put("qty", "1");
                if (null != manageCustomOptionView) {
                    JSONArray array = manageCustomOptionView.getDataForCheckout();
                    json.put("customs_detail", array);
                }
                if (null != bundleItemView) {
                    JSONArray array = bundleItemView.getDataForCheckout();
                    json.put("bundle_detail", array);
                }
                if (null != groupView) {
                    JSONArray array = groupView.getDataForCheckout();
                    json.put("group_items", array);
                }
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }
        return null;
    }

    @Override
    public void onUpdateVariantSelected(VariantEntity variantEntity) {
        mVariantEntity = variantEntity;
        mDelegate.updatePriceWithVariant(variantEntity, true);
    }

    public boolean isComplete() {
        if (null != manageVariantView) {
            if (null == mVariantEntity) {

                Log.e("ManageOptionView ", "Variant False");

                return false;
            }
        }

        if (null != manageCustomOptionView) {
            if (!manageCustomOptionView.isComplete()) {
                return false;
            }
        } else {
            ArrayList<CustomOptionEntity> listCustom = mProduct.getListCustom();
            if (null != listCustom && listCustom.size() > 0) {
                return false;
            }
        }

        if (null != groupView) {
            if (!groupView.isComplete()) {
                Log.e("ManageOptionView ", "Group False");
                return false;
            }

        } else {
            String type = mProduct.getType();
            if (type.equals("grouped")) {
                return false;
            }
        }


        if (null != bundleItemView) {
            if (!bundleItemView.isComplete()) {
                Log.e("ManageOptionView ", "Bundle False");
                return false;
            }
        } else {
            String type = mProduct.getType();
            if (type.equals("bundle")) {
                return false;
            }
        }
        Log.e("ManageOptionView ", "-----------> TRUE");
        return true;
    }


    @Override
    public void updatePrice(ValueCustomOptionEntity option, boolean isAdd) {
        Log.e("ManageOptionView ", "updatePrice ");
        mDelegate.updatePriceWithCustomOption(option, isAdd);
    }

    @Override
    public void updatePriceForItemGroup(ItemGroupEntity item, boolean isAdd) {
        Log.e("ManageOptionView ", "updatePriceForItemGroup ");
        mDelegate.updatePriceWithItemGroup(item, isAdd);
    }
}
