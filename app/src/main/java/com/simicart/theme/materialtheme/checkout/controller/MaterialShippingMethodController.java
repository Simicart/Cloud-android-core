package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.checkout.model.UpdateShippingToQuoteModel;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialShippingMethodAdapter;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialShippingMethodManagerDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sony on 4/13/2016.
 */
public class MaterialShippingMethodController extends SimiController {
    protected SimiDelegate mDelegate;
    protected MaterialShippingMethodManagerDelegate mShippingMethodManagerDelegate;
    protected ArrayList<ShippingMethod> mListShippingmethod;
    protected MaterialShippingMethodAdapter.OnItemClickListener onItemClick;

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setShippingMethodManagerDelegate(MaterialShippingMethodManagerDelegate mShippingMethodManagerDelegate) {
        this.mShippingMethodManagerDelegate = mShippingMethodManagerDelegate;
    }

    public MaterialShippingMethodAdapter.OnItemClickListener getOnItemClick() {
        return onItemClick;
    }

    @Override
    public void onStart() {
        onItemClick = new MaterialShippingMethodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShippingMethod item, int position) {
                mShippingMethodManagerDelegate.selectedShippingMethod(item);
            }
        };
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    public void requestGetShippingMethod(MyAddress mShippingAddress){
        mDelegate.showDialogLoading();
        mModel = new UpdateShippingToQuoteModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
//                showShippingMethod(null);
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    if (entity != null && entity.size() > 0) {
                        ArrayList<ShippingMethod> shippingMethodsArr = new ArrayList<ShippingMethod>();
                        for (SimiEntity simiEntity : entity) {
                            ShippingMethod shippingMethod = (ShippingMethod) simiEntity;
                            shippingMethodsArr.add(shippingMethod);
                        }

                        if (shippingMethodsArr.size() > 0) {
//                            if (mCurrentShippingMethod != null) {
//                                String nameMethodSelected = mCurrentShippingMethod.getmShippingMethodCode();
//                                for (int i = 0; i < shippingMethodsArr.size(); i++) {
//                                    String name = shippingMethodsArr.get(i).getmShippingMethodCode();
//                                    if (nameMethodSelected.equals(name)) {
//                                        shippingMethodsArr.get(i).setIsSelected(true);
//                                    }
//                                }
//                            }
                            mListShippingmethod = shippingMethodsArr;
                            mShippingMethodManagerDelegate.showShippingMethod(shippingMethodsArr);
                        }
                    }
                }
            }
        });

        JSONObject param = null;
        try {
            param = mShippingAddress.paramJsonRequest();
        } catch (JSONException e) {
            param = null;
        }

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn(), "shipping-address");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin(), "shipping-address");
        }

        if (param != null) {
            mModel.addDataBody("shipping_address", param);
        }

        mModel.request();
    }
}
