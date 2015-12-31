package com.simicart.core.catalog.product.controller;

import android.text.TextUtils;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.Attributes;
import com.simicart.core.catalog.product.model.TechSpecsModel;
import com.simicart.core.common.Utils;

import java.util.ArrayList;

/**
 * Created by Sony on 12/9/2015.
 */
public class TechSpecsController extends SimiController {
    protected SimiDelegate mDelegate;
    protected ArrayList<Attributes> mAttributes;

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setAttributes(ArrayList<Attributes> mAttributes) {
        this.mAttributes = mAttributes;
    }

    @Override
    public void onStart() {
        mDelegate.showLoading();
        mModel = new TechSpecsModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (collection != null) {
                    mDelegate.updateView(collection);
                }
            }
        });

        String listID = "";
        ArrayList<String> listIDArr = new ArrayList<String>();
        for (int i = 0; i < mAttributes.size(); i++){
            if(Utils.validateString(mAttributes.get(i).getID())){
                listIDArr.add(mAttributes.get(i).getID());
                if(listIDArr.size() > 0) {
                    listID = TextUtils.join(",", listIDArr);
                }
            }
        }

        if(!listID.equals("")){
            mModel.addDataParameter("ids", listID);
        }
        mModel.request();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }
}
