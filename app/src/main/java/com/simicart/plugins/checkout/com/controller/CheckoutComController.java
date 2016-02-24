package com.simicart.plugins.checkout.com.controller;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.checkout.CardValidator;
import com.checkout.CheckoutKit;
import com.checkout.exceptions.CardException;
import com.checkout.exceptions.CheckoutException;
import com.checkout.httpconnector.Response;
import com.checkout.models.Card;
import com.checkout.models.CardToken;
import com.checkout.models.CardTokenResponse;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.fragment.ThankyouFragment;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.plugins.checkout.com.block.CheckoutComBlock;
import com.simicart.plugins.checkout.com.model.GetPublicKeyModel;
import com.simicart.plugins.checkout.com.model.UpdatePaymentModel;

import java.io.IOException;

/**
 * Created by James Crabby on 1/15/2016.
 */
public class CheckoutComController extends SimiController {

    protected CheckoutComBlock mDelegate;
    OnClickListener onButtonCheckoutClick;
    Activity activity;
    protected EditText mName;
    protected EditText mNumber;
    protected EditText mCvv;
    protected Spinner mMonth;
    protected Spinner mYear;
    protected String publicKey = "";
    protected String orderID = "";
    protected String cartToken = "";
    protected OrderEntity mOrderEntity;
    protected OrderHisDetail mOrderHisDetail;

    public CheckoutComController(Activity mActivity) {
        this.activity = mActivity;
    }


    public OnClickListener getOnButtonCheckoutClick() {
        return onButtonCheckoutClick;
    }

    public void setDelegate(CheckoutComBlock mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {
        mDelegate.updateView(null);

        onButtonCheckoutClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCardToken();
            }
        };
    }

    @Override
    public void onResume() {

    }

    public void setmOrderEntity(OrderEntity mOrderEntity) {
        this.mOrderEntity = mOrderEntity;
    }

    public void setmOrderHisDetail(OrderHisDetail mOrderHisDetail) {
        this.mOrderHisDetail = mOrderHisDetail;
    }

    public void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setCvv(EditText cvv) {
        this.mCvv = cvv;
    }

    public void setMonth(Spinner month) {
        this.mMonth = month;
    }

    public void setName(EditText name) {
        this.mName = name;
    }

    public void setNumber(EditText number) {
        this.mNumber = number;
    }

    public void setYear(Spinner year) {
        this.mYear = year;
    }

    public void getCardToken() {
        mDelegate.showLoading();
        final GetPublicKeyModel model = new GetPublicKeyModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if(error != null)
                    SimiManager.getIntance().showNotify(error.getMessage().toString());
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                publicKey = model.getPublickey();
                Log.e(getClass().getName(), "Public key: " + publicKey);

                try {
                    new ConnectionTask().execute("");
                } catch (Exception e) {
                    e.printStackTrace();
                    goToError();
                }
            }
        });
        model.request();
    }

    public void updatePaymentMethod(String cartToken, String idOrder) {
        final UpdatePaymentModel model = new UpdatePaymentModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if(error != null)
                    SimiManager.getIntance().showNotify(error.getMessage().toString());
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                goToSuccess();
            }
        });
        model.addDataBody("order_id", idOrder);
        model.addDataBody("cart_token", cartToken);
        model.request();
    }

    private void goToError() {
        SimiManager.getIntance().showNotify(Config.getInstance().getText("Checkout Error. Please try again"));
    }

    private void goToSuccess() {
        ThankyouFragment fragment = ThankyouFragment.newInstance();
        fragment.setMessage(Config.getInstance().getText("Thank you for your purchase!"));
        fragment.setInvoice_number(String.valueOf(mOrderEntity.getSeqNo()));
        fragment.setOrderHisDetail(mOrderHisDetail);
        if (DataLocal.isTablet) {
            SimiManager.getIntance().replacePopupFragment(
                    fragment);
        } else {
            SimiManager.getIntance().replaceFragment(
                    fragment);
        }
    }

    class ConnectionTask extends AsyncTask<String, Void, String> {
        final int errorColor = Color.rgb(204, 0, 51);

        private boolean validateCardFields(final String name, final String number, final String month, final String year, final String cvv) {
            boolean error = false;
            clearFieldsError();

            if (!CardValidator.validateCardNumber(name)) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mName.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                    }
                });
                error = true;
            }

            if (!CardValidator.validateCardNumber(number)) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNumber.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                    }
                });
                error = true;
            }
            if (!CardValidator.validateExpiryDate(month, year)) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMonth.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                        mYear.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                    }
                });
                error = true;
            }
            if (cvv.equals("")) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCvv.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                    }
                });
                error = true;
            }
            return !error;
        }

        private void clearFieldsError() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCvv.getBackground().clearColorFilter();
                    mMonth.getBackground().clearColorFilter();
                    mYear.getBackground().clearColorFilter();
                    mNumber.getBackground().clearColorFilter();
                }
            });
        }

        @Override
        protected String doInBackground(String... urls) {
            if (validateCardFields(mName.getText().toString(), mNumber.getText().toString(), mMonth.getSelectedItem().toString()
                    , mYear.getSelectedItem().toString(), mCvv.getText().toString())) {
                clearFieldsError();
                try {
                    Card card = new Card(mNumber.getText().toString(), mNumber.getText().toString(), mMonth.getSelectedItem().toString(), mYear.getSelectedItem().toString(), mCvv.getText().toString());
                    CheckoutKit ck = CheckoutKit.getInstance(publicKey);
                    final Response<CardTokenResponse> resp = ck.createCardToken(card);
                    if (resp.hasError) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                goToError();
                            }
                        });
                    } else {
                        CardToken ct = resp.model.getCard();
                        //goToSuccess();
                        Log.e("GetCardToken", "++" + resp.model.getCardToken());
                        return resp.model.getCardToken();
                    }
                } catch (final CardException e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (e.getType().equals(CardException.CardExceptionType.INVALID_CVV)) {
                                mCvv.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                            } else if (e.getType().equals(CardException.CardExceptionType.INVALID_EXPIRY_DATE)) {
                                mMonth.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                                mYear.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                            } else if (e.getType().equals(CardException.CardExceptionType.INVALID_NUMBER)) {
                                mNumber.getBackground().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP);
                            }
                        }
                    });
                } catch (CheckoutException | IOException e2) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goToError();
                        }
                    });
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            String cartToken = s;
            updatePaymentMethod(cartToken, orderID);
        }
    }
}
