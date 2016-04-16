package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.model.UpdateBillingToQuoteModel;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialPaymentInformationAdapter;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialPaymentInformationManagerDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialPaymentInformationController extends SimiController {
    protected SimiDelegate mDelegate;
    protected MaterialPaymentInformationManagerDelegate mPaymentInformationManagerDelegate;
    protected ArrayList<PaymentMethod> mListPaymentMethod;
    protected MaterialPaymentInformationAdapter.OnItemClickListener onItemClick;

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setPaymentInformationManagerDelegate(MaterialPaymentInformationManagerDelegate mPaymentInformationManagerDelegate) {
        this.mPaymentInformationManagerDelegate = mPaymentInformationManagerDelegate;
    }

    public MaterialPaymentInformationAdapter.OnItemClickListener getOnItemClick() {
        return onItemClick;
    }

    @Override
    public void onStart() {
        onItemClick = new MaterialPaymentInformationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PaymentMethod item, int position) {
                mPaymentInformationManagerDelegate.selectedPaymentMethod(item);
            }
        };
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    public void getPaymentInformation(MyAddress mBillingAddress) {
        mDelegate.showDialogLoading();
        mModel = new UpdateBillingToQuoteModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
//                showPaymentMethod(null);
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    if (entity != null && entity.size() > 0) {
                        ArrayList<PaymentMethod> paymentMethodsArr = new ArrayList<PaymentMethod>();
                        for (SimiEntity simiEntity : entity) {
                            PaymentMethod paymentMethod = (PaymentMethod) simiEntity;
                            paymentMethodsArr.add(paymentMethod);
                        }

                        if (paymentMethodsArr.size() > 0) {
                            mListPaymentMethod = paymentMethodsArr;
                            mPaymentInformationManagerDelegate.showPaymentInformation(paymentMethodsArr);
                        }
                    }
                }
            }
        });

        JSONObject param = null;
        try {
            param = mBillingAddress.paramJsonRequest();
        } catch (JSONException e) {
            param = null;
        }

        if (param != null) {
            mModel.addDataBody("billing_address", param);
        }

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn(), "billing-address");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin(), "billing-address");
        }

        mModel.request();
    }
}
