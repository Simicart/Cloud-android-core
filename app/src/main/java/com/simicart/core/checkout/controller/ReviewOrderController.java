package com.simicart.core.checkout.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.block.ManagePaymentMethodView;
import com.simicart.core.checkout.block.ManageShippingMethodView;
import com.simicart.core.checkout.delegate.PaymentDelegate;
import com.simicart.core.checkout.delegate.ReviewOrderDelegate;
import com.simicart.core.checkout.delegate.ShippingMethodDelegate;
import com.simicart.core.checkout.entity.Condition;
import com.simicart.core.checkout.entity.CouponCodeEntity;
import com.simicart.core.checkout.entity.CreditcardEntity;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.checkout.fragment.CreditCardFragment;
import com.simicart.core.checkout.fragment.ThankyouFragment;
import com.simicart.core.checkout.model.CheckOutGuestNewModel;
import com.simicart.core.checkout.model.CouponCodeModel;
import com.simicart.core.checkout.model.CreditCardModel;
import com.simicart.core.checkout.model.PaymentMethodModel;
import com.simicart.core.checkout.model.PlaceOrderModel;
import com.simicart.core.checkout.model.RemoveCouponCodeModel;
import com.simicart.core.checkout.model.ReviewOrderModel;
import com.simicart.core.checkout.model.ShippingMethodModel;
import com.simicart.core.checkout.model.UpdateBillingToQuoteModel;
import com.simicart.core.checkout.model.UpdateShippingToQuoteModel;
import com.simicart.core.common.SCDialog;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.controller.AutoSignInController;
import com.simicart.core.customer.entity.CustomerOrdersEntity;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;

@SuppressLint("ClickableViewAccessibility")
public class ReviewOrderController extends SimiController implements PaymentDelegate, ShippingMethodDelegate {

    protected ReviewOrderDelegate mDelegate;
    protected MyAddress mBillingAddress;
    protected MyAddress mShippingAddress;
    protected ArrayList<ShippingMethod> mShippingmethod;
    protected ArrayList<PaymentMethod> mPaymentMethods;
    protected QuoteEntity mtotalPrice;
    protected OnClickListener onPlaceNow;
    protected OnClickListener onChooseBillingAddress;
    protected OnClickListener onChooseShippingAddress;
    protected OnItemClickListener onViewProductDetail;
    protected OnEditorActionListener couponCodeListener;
    protected TextWatcher mCouponCodeChange;
    protected int mAfterControll;
    protected String couponCode = "";
    protected OnClickListener onRemoveCoupon;
    private boolean checkCouponCode = false;
    private boolean checkRequiedShipping = true;

    // frank : new payment
    protected PaymentMethod mCurrentPaymentMethod;
    // frank : new shipping
    protected ShippingMethod mCurrentShippingMethod;

    public void setAfterControll(int controll) {
        mAfterControll = controll;
    }

    protected ArrayList<Condition> mConditions;

    public OnClickListener getOnChoiceBillingAddress() {
        return onChooseBillingAddress;
    }

    public TextWatcher getCouponCodeChange() {
        return mCouponCodeChange;
    }

    public OnClickListener getOnRemoveCoupon() {
        return onRemoveCoupon;
    }

    public OnClickListener getOnChoiceShippingAddress() {
        return onChooseShippingAddress;
    }

    public OnClickListener getOnPlaceNow() {
        return onPlaceNow;
    }

    public OnItemClickListener getOnViewDetailProduct() {
        return onViewProductDetail;
    }

    public void setDelegate(ReviewOrderDelegate delegate) {
        this.mDelegate = delegate;
    }

    public void setBillingAddress(MyAddress address) {
        mBillingAddress = address;
    }

    public void setShippingAddress(MyAddress address) {
        mShippingAddress = address;
    }

    public OnEditorActionListener getCouponCodeListener() {
        return couponCodeListener;
    }

    @Override
    public void onStart() {
        initAction();

        if (mAfterControll == NewAddressBookFragment.NEW_CUSTOMER || mAfterControll == NewAddressBookFragment.NEW_AS_GUEST) {
            requestUpdateShipping();
            requestUpdateBilling();
            requestCheckoutGestNew();
        }
        onRequestData();
    }

    private void initAction() {
        setOnPlaceNow();
        setOnChoiceBillingAddress();
        setOnChooseShippingAddress();
        setCouponCodeListener();
        setViewProductDetail();
    }

    protected void onRequestData() {
        mDelegate.showLoading();
        mModel = new ReviewOrderModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.goneView();
                mDelegate.scrollCenter();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null && collection.getCollection().size() > 0) {
                    mDelegate.updateView(mModel.getCollection());
                    QuoteEntity entity = (QuoteEntity) collection.getCollection().get(0);
                    mDelegate.setTotalPrice(entity);
                    mtotalPrice = entity;
                    ArrayList<ProductEntity> products = entity.getProduct();
                    if (null != products && products.size() > 0) {
                        mDelegate.setInitViewListProduct(products);
                    }

                    ArrayList<CouponCodeEntity> coupons = entity.getCouponCode();
                    if (null != coupons && coupons.size() > 0) {
                        checkCouponCode = true;
                        mDelegate.setInitViewCouponCode(coupons.get(0).getCode());
                    }

                    if (entity.getShippingMethod() != null) {
//                        mReviewOrderShippingMethod = entity.getShippingMethod();
                        mCurrentShippingMethod = entity.getShippingMethod();
                    }


                    if (mAfterControll != NewAddressBookFragment.NEW_CUSTOMER && mAfterControll != NewAddressBookFragment.NEW_AS_GUEST) {
                        requestUpdateShipping();
                        requestUpdateBilling();
                    }
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        mModel.request();
    }

    private void requestUpdateShipping() {
        UpdateShippingToQuoteModel updateShippingToQuoteModel = new UpdateShippingToQuoteModel();
        updateShippingToQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                showShippingMethod(null);
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null && collection.getCollection().size() > 0) {
                    checkRequiedShipping = true;
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    if (entity != null && entity.size() > 0) {
                        ArrayList<ShippingMethod> shippingMethodsArr = new ArrayList<ShippingMethod>();
                        for (SimiEntity simiEntity : entity) {
                            ShippingMethod shippingMethod = (ShippingMethod) simiEntity;
                            shippingMethodsArr.add(shippingMethod);
                        }

                        if (shippingMethodsArr.size() > 0) {
                            if (mCurrentShippingMethod != null) {
                                String nameMethodSelected = mCurrentShippingMethod.getmShippingMethodCode();
                                for (int i = 0; i < shippingMethodsArr.size(); i++) {
                                    String name = shippingMethodsArr.get(i).getmShippingMethodCode();
                                    if (nameMethodSelected.equals(name)) {
                                        shippingMethodsArr.get(i).setIsSelected(true);
                                    }
                                }
                            }
                            mShippingmethod = shippingMethodsArr;
                            showShippingMethod(mShippingmethod);
                        }
                    }

                    mDelegate.setShipingAddress(mShippingAddress);
                } else {
                    checkRequiedShipping = false;
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
            updateShippingToQuoteModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn(), "shipping-address");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            updateShippingToQuoteModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin(), "shipping-address");
        }

        if (param != null) {
            updateShippingToQuoteModel.addDataBody("shipping_address", param);
        }

        updateShippingToQuoteModel.request();
    }

    private void requestUpdateBilling() {
        UpdateBillingToQuoteModel updateBillingToQuoteModel = new UpdateBillingToQuoteModel();
        updateBillingToQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                showPaymentMethod(null);
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (collection != null && collection.getCollection().size() > 0) {
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    if (entity != null && entity.size() > 0) {
                        ArrayList<PaymentMethod> paymentMethodsArr = new ArrayList<PaymentMethod>();
                        for (SimiEntity simiEntity : entity) {
                            PaymentMethod paymentMethod = (PaymentMethod) simiEntity;
                            paymentMethodsArr.add(paymentMethod);
                        }

                        if (paymentMethodsArr.size() > 0) {
                            mPaymentMethods = paymentMethodsArr;
                            showPaymentMethod(mPaymentMethods);
                        }

                        mDelegate.setBillingAddress(mBillingAddress);
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
            updateBillingToQuoteModel.addDataBody("billing_address", param);
        }

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            updateBillingToQuoteModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn(), "billing-address");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            updateBillingToQuoteModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin(), "billing-address");
        }

        updateBillingToQuoteModel.request();
    }

    private void requestCheckoutGestNew() {
        CheckOutGuestNewModel checkOutGuestNewModel = new CheckOutGuestNewModel();
        checkOutGuestNewModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                    SimiManager.getIntance().backPreviousFragment();
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);

                    CustomerOrdersEntity customer = quoteEntity.getCustomer();
                    if (null != customer) {
                        DataLocal.saveCustomerID(customer.getmCustomerID());
                        DataLocal.saveCustomer(customer.getmCustomerFirstName(), customer.getmCustomerLastName(), customer.getmCustomerEmail(), "", customer.getmCustomerID());
                    }
                }
            }
        });

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            checkOutGuestNewModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        checkOutGuestNewModel.addDataExtendURL("customer");


        String email = "";
        if (mShippingAddress != null && !mShippingAddress.getEmail().equals("")) {
            email = mShippingAddress.getEmail();
        }
        checkOutGuestNewModel.addDataBody("customer_email", email);

        JSONObject param = null;
        try {
            param = mShippingAddress.paramJsonRequest();
        } catch (JSONException e) {
            param = null;
        }

        if (param != null) {
            checkOutGuestNewModel.addDataBody("address", param);
        }

        if (mAfterControll == NewAddressBookFragment.NEW_CUSTOMER) {
            String password = DataLocal.getPassword();
            checkOutGuestNewModel.addDataBody("password", password);
            checkOutGuestNewModel.addDataBody("create_new_customer", "1");
        } else if (mAfterControll == NewAddressBookFragment.NEW_AS_GUEST) {
            checkOutGuestNewModel.addDataBody("create_new_customer", "0");
        }


        checkOutGuestNewModel.request();
    }


    private void setViewProductDetail() {
        onViewProductDetail = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ProductOrderDetailFragment fragment = new
                // ProductOrderDetailFragment();
                // fragment.setProduct(DataLocal.listCarts.get(mPosition));
                // SimiManager.getIntance().replacePopupFragment(fragment);


            }

        };
    }

    protected void setOnChooseShippingAddress() {
        onChooseShippingAddress = new OnClickListener() {

            @Override
            public void onClick(View v) {
                AddressBookCheckoutFragment fragment = AddressBookCheckoutFragment
                        .newInstance();
                fragment.setAddressFor(AddressBookCheckoutFragment.SHIPPING_ADDRESS);
                fragment.setBillingAddress(mBillingAddress);
                fragment.setShippingAddress(mShippingAddress);
                fragment.setAfterController(mAfterControll);
                SimiManager.getIntance().replacePopupFragment(fragment);
            }
        };

    }

    protected void setOnChoiceBillingAddress() {
        onChooseBillingAddress = new OnClickListener() {

            @Override
            public void onClick(View v) {
                AddressBookCheckoutFragment fragment = AddressBookCheckoutFragment
                        .newInstance();
                fragment.setAddressFor(AddressBookCheckoutFragment.BILLING_ADDRESS);
                fragment.setBillingAddress(mBillingAddress);
                fragment.setShippingAddress(mShippingAddress);
                fragment.setAfterController(mAfterControll);
                SimiManager.getIntance().replacePopupFragment(fragment);
            }
        };
    }

    protected void setCouponCodeListener() {
        couponCodeListener = new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String code = v.getText().toString().trim();
                    setCouponCode(code);
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    Utils.hideKeyboard(v);
                }
                return false;
            }
        };

        mCouponCodeChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals("")) {
                    mDelegate.showRemoveCoupon();
                    couponCode = s.toString().trim();
                } else {
                    mDelegate.hidenRemoveCoupon();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        onRemoveCoupon = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCouponCode == true) {
                    if (!couponCode.equals("")) {
                        removeCouponCode(couponCode);
                    }
                } else {
                    mDelegate.removeTextCouponCode();
                }
            }
        };
    }


    protected void removeCouponCode(String couponCode) {
        mDelegate.showDialogLoading();
        RemoveCouponCodeModel removeCouponCodeModel = new RemoveCouponCodeModel();
        removeCouponCodeModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showNotify(null, "Couponcode was removed", "Ok");
                checkCouponCode = false;
                mDelegate.removeTextCouponCode();
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                    mDelegate.setTotalPrice(quoteEntity);
                }
            }
        });
        removeCouponCodeModel.addDataExtendURL("remove");
        removeCouponCodeModel.addDataBody("coupon_code", couponCode);
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            removeCouponCodeModel.addDataBody("quote_id", Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            removeCouponCodeModel.addDataBody("quote_id", DataLocal.getQuoteCustomerNotSigin());
        }
        removeCouponCodeModel.request();
    }

    protected void setCouponCode(String code) {

        final CouponCodeModel model = new CouponCodeModel();
        mDelegate.showDialogLoading();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showNotify(null, "Couponcode was applied", "Ok");
                checkCouponCode = true;
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                    mDelegate.setTotalPrice(quoteEntity);
                }
            }
        });
        model.addDataExtendURL("apply");

        model.addDataBody("coupon_code", code);
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            model.addDataBody("quote_id", Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            model.addDataBody("quote_id", DataLocal.getQuoteCustomerNotSigin());
        }

        model.request();
    }


    protected void setOnPlaceNow() {
        onPlaceNow = new OnClickListener() {

            @Override
            public void onClick(View v) {
                SimiManager.getIntance().hideKeyboard();
                if (isCompleteRequired()) {
//                    PaymentMethod paymentMethod = getPaymentMethod(PaymentMethod
//                            .getInstance().getPlacePaymentMethod());
                    if (mCurrentPaymentMethod != null) {
                        requestPlaceOrder(mCurrentPaymentMethod);
                    }
                }
            }
        };
    }

    protected boolean isCompleteRequired() {
        if (null == mCurrentPaymentMethod) {
            Utils.expand(mDelegate.getLayoutPayment());
            mDelegate.scrollCenter();
            SimiManager.getIntance().showNotify(null,
                    "Please specify payment method", "Ok");
            return false;
        }

        if (checkRequiedShipping) {
            if (mShippingmethod != null && mShippingmethod.size() > 0) {
                if (null == mCurrentShippingMethod) {
                    Utils.expand(mDelegate.getLayoutShipping());
                    mDelegate.scrollCenter();
                    SimiManager.getIntance().showNotify(null,
                            "Please specify shipping method", "Ok");
                    return false;
                }
            }
        }

        // check condition
        if (Config.getInstance().getEnable_agreements() == 0
                && mConditions != null && mConditions.size() != 0) {
            mDelegate.scrollDown();
            SimiManager
                    .getIntance()
                    .showNotify(
                            null,
                            "Please agree to all the terms and conditions before placing the order.",
                            "Ok");
            return false;
        }
        return true;
    }

    protected void requestPlaceOrder(final PaymentMethod paymentmethod) {

        mDelegate.showLoading();
        mModel = new PlaceOrderModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                Config.getInstance().setQuoteCustomerSignIn("");
                DataLocal.saveQuoteCustomerNotSignIn("");
                ConfigCheckout.checkPaymentMethod = false;
                SimiManager.getIntance().onUpdateCartQty(null);
                //  PaymentMethod.getInstance().setPlacePaymentMethod("");

                if (mAfterControll == NewAddressBookFragment.NEW_CUSTOMER) {
                    AutoSignInController controller = new AutoSignInController();
                    controller.onStart();
                }

                if (collection != null && collection.getCollection().size() > 0) {
                    OrderEntity orderEntity = (OrderEntity) collection.getCollection().get(0);
                    processResultPlaceOrder(orderEntity, paymentmethod);
                }
            }
        });
        if (paymentmethod.getShow_type() == 4) {
            mModel.addDataBody(Constants.CARD_TYPE, ""
                    + paymentmethod.getPlace_cc_type());
            mModel.addDataBody(Constants.CARD_NUMBER, ""
                    + paymentmethod.getPlace_cc_number());
            mModel.addDataBody(Constants.EXPRIRED_MONTH, ""
                    + paymentmethod.getPlace_cc_exp_month());
            mModel.addDataBody(Constants.EXPRIRED_YEAR, ""
                    + paymentmethod.getPlace_cc_exp_year());

            String useCCV = paymentmethod.getData(Constants.USECCV);

            if (Utils.validateString(useCCV) && useCCV.equals("1")) {
                mModel.addDataBody(Constants.CC_ID, ""
                        + paymentmethod.getPlacecc_id());
            }
        }


        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn() + "?action=createorder");
        } else if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin() + "?action=createorder");
        }

        mModel.request();

    }

    private void processResultPlaceOrder(OrderEntity orderEntity, PaymentMethod paymentMethod) {
        int showtype = paymentMethod.getShow_type();
        switch (showtype) {
            case 2:
                showDialogDemo();
                //dispatchEventForPlaceOrder("com.simicart.paymentmethod.placeorder", orderEntity, paymentMethod);
                break;
            case 3:
                Log.e("ReviewOrderController", "type 3 " + paymentMethod.getMethodCode());
                showDialogDemo();
                //dispatchEventForPlaceOrder("com.simicart.after.placeorder.webview", orderEntity, paymentMethod);
                break;
            default:
                // type 1 & 4
                ThankyouFragment tkFragment = ThankyouFragment.newInstance();
                tkFragment.setMessage(Config.getInstance().getText("Thank you for your purchase!"));
                tkFragment.setInvoice_number(String.valueOf(orderEntity.getSeqNo()));
                OrderHisDetail orderHis = new OrderHisDetail();
                orderHis.setJSONObject(orderEntity.getJSONObject());
                orderHis.parse();
                tkFragment.setOrderHisDetail(orderHis);
                if (DataLocal.isTablet) {
                    SimiManager.getIntance().replacePopupFragment(
                            tkFragment);
                } else {
                    SimiManager.getIntance().replaceFragment(
                            tkFragment);
                }
                break;
        }
    }

    protected void showDialogDemo(){
        SCDialog scDialog = new SCDialog();
        scDialog.createDialog();
        scDialog.show();
    }

    private void dispatchEventForPlaceOrder(String nameEvent, OrderEntity orderEntity, PaymentMethod paymentMethod) {
        Intent intent = new Intent(nameEvent);
        Bundle bundle = new Bundle();
        SimiEventCheckoutEntity entity = new SimiEventCheckoutEntity();
        entity.setInvoiceNumber(((PlaceOrderModel) mModel)
                .getInvoiceNumber());
        entity.setOder(orderEntity);
        entity.setPaymentMethod(paymentMethod);
        OrderHisDetail orderHisDetail = new OrderHisDetail();
        orderHisDetail.setJSONObject(orderEntity.getJSONObject());
        orderHisDetail.parse();
        entity.setOrderHisDetail(orderHisDetail);
        bundle.putSerializable(Constants.ENTITY, entity);
        intent.putExtra(Constants.DATA, bundle);
        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).sendBroadcastSync(intent);
    }


    @Override
    public void onResume() {
        mDelegate.setShipingAddress(mShippingAddress);
        mDelegate.setBillingAddress(mBillingAddress);
        mDelegate.setConditions(mConditions);
        mDelegate.setTotalPrice(mtotalPrice);
        showPaymentMethod(mPaymentMethods);
        showShippingMethod(mShippingmethod);
        mDelegate.updateView(mModel.getCollection());
    }

    public void onDestroy() {
        ShippingMethod.refreshShipping();
        if (null != mShippingmethod && mShippingmethod.size() > 0) {
            for (ShippingMethod method : mShippingmethod) {
                method.setS_method_selected(false);
            }
        }
    }

    public void setActionArrowUp(int type) {
        if (type == 0) {
            mDelegate.setActionArrowUp(0);
        } else {
            mDelegate.setActionArrowUp(1);
        }
    }

    public void setActionArrowDown(int type) {
        if (type == 0) {
            mDelegate.setActionArrowDown(0);
        } else {
            mDelegate.setActionArrowDown(1);
        }
    }

    // frank: new payment
    private void showPaymentMethod(ArrayList<PaymentMethod> payments) {
        if (null == payments) {
            mDelegate.showPaymentMethod(null);
        } else {
            ManagePaymentMethodView paymentView = new ManagePaymentMethodView(payments, this);
            View view = paymentView.createView();
            if (null != view) {
                mDelegate.showPaymentMethod(view);
            }
        }
    }
    // implement method of PaymentMethodDelegate


    @Override
    public void updatePaymentChecked(PaymentMethod payment) {
        mCurrentPaymentMethod = payment;
        String name = mCurrentPaymentMethod.getName();
        mDelegate.setInitViewPaymentMethod(name);

        if (!payment.isCheck()) {
            requestSavePaymentMethod(mCurrentPaymentMethod);
            int show_type = payment.getShow_type();
            if (show_type == 4) {
                goCreditCardFragment(payment);
            }
        }
    }


    protected void requestSavePaymentMethod(PaymentMethod paymentMethod) {
        final PaymentMethodModel mModel = new PaymentMethodModel();
        mDelegate.showDialogLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                    //reviewOrder.setTotalPrice(quoteEntity);
                    mDelegate.setTotalPrice(quoteEntity);
                }
            }
        };

        mModel.setDelegate(delegate);

        JSONObject params = null;
        try {
            params = paramToRequest(paymentMethod);
        } catch (JSONException e) {
            params = null;
        }

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        if (params != null) {
            mModel.addDataBody("payment", params);
        }
        mModel.request();
    }

    private JSONObject paramToRequest(PaymentMethod paymentMethod) throws JSONException {
        JSONObject param = new JSONObject();
        param.put("title", paymentMethod.getTitle());
        param.put("method_code", paymentMethod.getMethodCode());
        return param;
    }


    private void goCreditCardFragment(PaymentMethod payment) {
        if (!isSavedCC(payment)) {
            CreditCardFragment fcreditCard = CreditCardFragment
                    .newInstance();
            fcreditCard.setIsCheckedMethod(true);
            fcreditCard.setPaymentMethod(payment);
            SimiManager.getIntance().replacePopupFragment(fcreditCard);
        } else {
            onSaveCreditcardToOrder(payment);
        }


    }

    private boolean isSavedCC(PaymentMethod payment) {
        HashMap<String, HashMap<String, CreditcardEntity>> hashMap = DataLocal
                .getHashMapCreditCart();
        if (hashMap != null && hashMap.size() != 0) {
            String email = DataLocal.getEmailCreditCart();
            if (Utils.validateString(email) && hashMap.containsKey(email)) {
                HashMap<String, CreditcardEntity> creditcard = hashMap
                        .get(email);
                String paymentMethodCode = payment.getMethodCode();
                if (creditcard.containsKey(paymentMethodCode)) {
                    return true;
                }
            }
        }

        return false;
    }


    // frank; new shipping method
    private void showShippingMethod(ArrayList<ShippingMethod> shippingMethods) {
        if (null == shippingMethods) {
            mDelegate.showShipingMethod(null);
        } else {
            ManageShippingMethodView shippingMethodView = new ManageShippingMethodView(shippingMethods, this);
            View view = shippingMethodView.initView();
            if (null != view) {
                mDelegate.showShipingMethod(view);
            }
        }

    }

    @Override
    public void updateShippingMehtod(ShippingMethod shippingMethod) {
        if (null != shippingMethod) {
            if (shippingMethod.getIsSelected()) {
                mCurrentShippingMethod = shippingMethod;
                String name = mCurrentShippingMethod.getServiceName();
                mDelegate.setInitViewShippingMethod(name);
            } else {
                mCurrentShippingMethod = shippingMethod;
                String name = mCurrentShippingMethod.getServiceName();
                mDelegate.setInitViewShippingMethod(name);
                requestSaveShippingMethod();
            }
        }

    }

    private void requestSaveShippingMethod() {
        final ShippingMethodModel mModel = new ShippingMethodModel();
        mDelegate.showDialogLoading();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                ConfigCheckout.checkShippingMethod = true;
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                    mDelegate.setTotalPrice(quoteEntity);
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        JSONObject param = null;
        try {
            param = paramToRequest(mCurrentShippingMethod);
        } catch (JSONException e) {
            param = null;
        }

        if (param != null) {
            mModel.addDataBody("shipping", param);
        }
        mModel.request();
    }

    private JSONObject paramToRequest(ShippingMethod shippingMethod) throws JSONException {
        JSONObject param = new JSONObject();
        param.put("cost", String.valueOf(shippingMethod.getPrice()));
        param.put("method_code", shippingMethod.getmShippingMethodCode());
        param.put("title", shippingMethod.getServiceName());
        if (Utils.validateString(String.valueOf(shippingMethod.getmShippingTaxPercent()))) {
            param.put("shiping_tax_percent", String.valueOf(shippingMethod.getmShippingTaxPercent()));
        } else {
            param.put("shiping_tax_percent", String.valueOf(0));
        }
        return param;
    }


    public void onSaveCreditcardToOrder(PaymentMethod paymentMethod) {
        mDelegate.showDialogLoading();
        CreditCardModel model = new CreditCardModel();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null)
                    SimiManager.getIntance().showToast(error.getMessage().toString());
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showToast(Config.getInstance().getText("Save Credit Card success"));
            }
        };
        model.setDelegate(delegate);

        String quote_id = "";
        if (DataLocal.isSignInComplete()) {
            quote_id = Config.getInstance().getQuoteCustomerSignIn();
        } else {
            quote_id = DataLocal.getQuoteCustomerNotSigin();
        }
        model.addDataBody("quote_id", quote_id);


        HashMap<String, HashMap<String, CreditcardEntity>> hashMap = DataLocal
                .getHashMapCreditCart();
        String email = DataLocal.getEmailCreditCart();
        HashMap<String, CreditcardEntity> hs_card = hashMap
                .get(email);
        String paymentMethodCode = paymentMethod.getMethodCode();
        CreditcardEntity cardEntity = hs_card.get(paymentMethodCode);

        String key = cardEntity.getKeyCardType();
        String code = cardEntity.getCodeCardType();
        String title = cardEntity.getTitleCardType();

        JSONArray cc_types = null;
        JSONObject card_type = new JSONObject();
        try {

            card_type.put("key", key);
            card_type.put("code", code);
            card_type.put("title", title);
        } catch (JSONException e) {

        }
        model.addDataBody("card_type", card_type);
        model.addDataBody("card_number", cardEntity.getPaymentNumber());
        model.addDataBody("card_name", cardEntity.getCardName());
        String ccid = "" + cardEntity.getPaymentCvv();
        model.addDataBody("card_digit", ccid);
        String card_name = "" + cardEntity.getCardName();
        model.addDataBody("card_name", card_name);
        model.request();
    }


}
