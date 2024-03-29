package com.simicart.core.checkout.controller;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.checkout.fragment.ReviewOrderFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.controller.AddressBookController;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.customer.model.AddressBookModel;

@SuppressLint("ClickableViewAccessibility")
public class AddressBookCheckoutController extends AddressBookController {

    protected int addressFor;
    protected MyAddress mBillingAddress;
    protected MyAddress mShippingAddress;
    protected int mAfterController;
    protected QuoteEntity mQuote;

    public void setAddressFor(int addressFor) {
        this.addressFor = addressFor;
    }

    public void setBillingAddress(MyAddress mBillingAddress) {
        this.mBillingAddress = mBillingAddress;
    }

    public void setShippingAddress(MyAddress mShippingAddress) {
        this.mShippingAddress = mShippingAddress;
    }

    public void setAfterController(int afterController) {
        mAfterController = afterController;
    }

    @Override
    public void onStart() {
        request();

        mListener = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NewAddressBookFragment fragment = NewAddressBookFragment
                        .newInstance();
                fragment.setAfterControler(NewAddressBookFragment.NEW_ADDRESS_CHECKOUT);
                fragment.setAddressFor(addressFor);
                fragment.setBillingAddress(mBillingAddress);
                fragment.setShippingAddress(mShippingAddress);
                // fragment.setAfterControler(mAfterController);
                SimiManager.getIntance().replacePopupFragment(fragment);
                return false;
            }
        };

        mClicker = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectItem(position);
            }

        };

    }

    private void request() {
        mDelegate.showLoading();
        mModel = new AddressBookModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                mDelegate.updateView(collection);
            }
        });
        mModel.addDataExtendURL(DataLocal.getCustomerID());
        mModel.request();
    }

    @Override
    protected void selectItem(int position) {
        MyAddress address;
        try {
            if (mModel.getCollection() != null && mModel.getCollection().getCollection().size() > 0) {
                ProfileEntity profileEntity = (ProfileEntity) mModel.getCollection().getCollection().get(0);
                address = profileEntity.getAddress().get(position);
                if (Utils.validateString(profileEntity.getEmail())) {
                    address.setEmail(profileEntity.getEmail());
                }
                ReviewOrderFragment fragment = ReviewOrderFragment.newInstance();
                switch (addressFor) {
                    case AddressBookCheckoutFragment.ALL_ADDRESS:
                        fragment.setBilingAddress(address);
                        fragment.setShippingAddress(address);
                        break;
                    case AddressBookCheckoutFragment.BILLING_ADDRESS:
                        fragment.setBilingAddress(address);
                        fragment.setShippingAddress(mShippingAddress);
                        break;
                    case AddressBookCheckoutFragment.SHIPPING_ADDRESS:
                        fragment.setBilingAddress(mBillingAddress);
                        fragment.setShippingAddress(address);
                        break;
                    default:
                        break;
                }

                SimiManager.getIntance().removeDialog();
                SimiManager.getIntance().replaceFragment(fragment);
            }
        } catch (Exception e) {
            Log.e("Error",
                    e.getMessage());
        }
    }
}
