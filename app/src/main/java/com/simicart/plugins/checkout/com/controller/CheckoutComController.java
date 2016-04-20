package com.simicart.plugins.checkout.com.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.checkout.CardValidator;
import com.checkout.CheckoutKit;
import com.checkout.exceptions.CardException;
import com.checkout.exceptions.CheckoutException;
import com.checkout.httpconnector.Response;
import com.checkout.models.Card;
import com.checkout.models.CardToken;
import com.checkout.models.CardTokenResponse;
import com.simicart.MainActivity;
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
import com.simicart.plugins.checkout.com.CheckoutCom;
import com.simicart.plugins.checkout.com.block.CheckoutComBlock;
import com.simicart.plugins.checkout.com.entity.CheckoutComEntity;
import com.simicart.plugins.checkout.com.model.CheckOutCancelModel;
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
                CheckoutComEntity entity = mDelegate.getCheckoutComData();
                if(entity != null) {
                    if(validateCardFields(entity.getName(), entity.getCardNumber(), entity.getExpiredMonth()
                            , entity.getExpiredYear(), entity.getCvv())) {
                        getCardToken();
                    }
                }

            }
        };
    }

    @Override
    public void onResume() {

    }

    public View.OnKeyListener getOnKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        showDialog();
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private void showDialog() {
        new AlertDialog.Builder(SimiManager.getIntance().getCurrentActivity())
                .setMessage(
                        Config.getInstance()
                                .getText(
                                        "Are you sure that you want to cancel the order?"))
                .setPositiveButton(Config.getInstance().getText("Yes"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                requestCancelOrder();
                                showToastMessage("Your order has been canceled");
                                SimiManager.getIntance().backToHomeFragment();
                            }
                        })
                .setNegativeButton(Config.getInstance().getText("No"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                            }
                        }).show();

    }

    private void requestCancelOrder() {
        CheckOutCancelModel checkoutCancelModel = new CheckOutCancelModel();
        checkoutCancelModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    showToastMessage(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        });
        checkoutCancelModel.addDataExtendURL(orderID);
        checkoutCancelModel.addDataExtendURL("cancel");
        checkoutCancelModel.request();
    }

    public void showToastMessage(String message) {
        Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
                .getText(message), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void setmOrderEntity(OrderEntity mOrderEntity) {
        this.mOrderEntity = mOrderEntity;
    }

    public void setmOrderHisDetail(OrderHisDetail mOrderHisDetail) {
        this.mOrderHisDetail = mOrderHisDetail;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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

    private boolean validateCardFields(final String name, final String number, final String month, final String year, final String cvv) {
        boolean error = false;
        clearFieldsError();

//        Log.e("abc", name);
//        if (name == null || name.equals("")) {
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mDelegate.setNameFiledError();
//                }
//            });
//            error = true;
//        }

        if (!CardValidator.validateCardNumber(number)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDelegate.setNumberFieldError(true);
                }
            });
            error = true;
        }
        if (!CardValidator.validateExpiryDate(month, year)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDelegate.setSpinMonthError(true);
                    mDelegate.setSpinYearError(true);
                }
            });
            error = true;
        }
        if (cvv.equals("")) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDelegate.setCvvFieldError(true);
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
                mDelegate.setCvvFieldError(false);
                mDelegate.setSpinMonthError(false);
                mDelegate.setSpinYearError(false);
                mDelegate.setNumberFieldError(false);
            }
        });
    }

    class ConnectionTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            CheckoutComEntity entity = mDelegate.getCheckoutComData();
            if(entity != null) {
                if (validateCardFields(entity.getName(), entity.getCardNumber(), entity.getExpiredMonth()
                        , entity.getExpiredYear(), entity.getCvv())) {
                    clearFieldsError();
                    try {
                        Card card = new Card(entity.getCardNumber(), entity.getName(), entity.getExpiredMonth()
                                , entity.getExpiredYear(), entity.getCvv());
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
                                    mDelegate.setCvvFieldError(true);
                                } else if (e.getType().equals(CardException.CardExceptionType.INVALID_EXPIRY_DATE)) {
                                    mDelegate.setSpinMonthError(true);
                                    mDelegate.setSpinYearError(true);
                                } else if (e.getType().equals(CardException.CardExceptionType.INVALID_NUMBER)) {
                                    mDelegate.setNumberFieldError(true);
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
