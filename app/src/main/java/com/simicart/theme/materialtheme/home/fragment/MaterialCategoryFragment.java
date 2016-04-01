package com.simicart.theme.materialtheme.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.theme.materialtheme.home.block.MaterialCategoryBlock;
import com.simicart.theme.materialtheme.home.controller.MaterialCategoryController;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryFragment extends SimiFragment {
    protected  MaterialCategoryBlock mBlock;
    protected MaterialCategoryController mController;
    protected  SpotProductEntity sportProduct;

    public void setSportProduct(SpotProductEntity sportProduct) {
        this.sportProduct = sportProduct;
    }

    public static MaterialCategoryFragment newInstance(){
        MaterialCategoryFragment fragment = new MaterialCategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_category_layout"), container, false);

        Context mContext = getActivity();
        mBlock = new MaterialCategoryBlock(rootView, mContext);
        mBlock.setActivity(getActivity());
        mBlock.initView();
        if(mController == null){
            mController = new MaterialCategoryController();
            mController.setDelegate(mBlock);
            mController.setSportProduct(sportProduct);
            mController.onStart();
        }else{
            mController.setDelegate(mBlock);
            mController.setSportProduct(sportProduct);
            mController.onResume();
        }
        return rootView;
    }
}
