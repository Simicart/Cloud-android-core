package com.simicart.core.checkout.controller;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.simicart.MainActivity;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.delegate.PaymentMethodDelegate;
import com.simicart.core.checkout.delegate.ReviewOrderDelegate;
import com.simicart.core.checkout.delegate.SelectedShippingMethodDelegate;
import com.simicart.core.checkout.delegate.ShippingDelegate;
import com.simicart.core.checkout.entity.Condition;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.checkout.entity.TotalPrice;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.checkout.fragment.ThankyouFragment;
import com.simicart.core.checkout.model.CheckOutGuestNewModel;
import com.simicart.core.checkout.model.CouponCodeModel;
import com.simicart.core.checkout.model.PaymentMethodModel;
import com.simicart.core.checkout.model.PlaceOrderModel;
import com.simicart.core.checkout.model.RemoveCouponCodeModel;
import com.simicart.core.checkout.model.ReviewOrderModel;
import com.simicart.core.checkout.model.ShippingMethodModel;
import com.simicart.core.checkout.model.UpdateBillingToQuoteModel;
import com.simicart.core.checkout.model.UpdateShippingToQuoteModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.controller.AutoSignInController;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.event.checkout.CheckoutData;
import com.simicart.core.event.checkout.EventCheckout;
import com.simicart.core.notification.NotificationActivity;
import com.simicart.core.notification.entity.NotificationEntity;

@SuppressLint("ClickableViewAccessibility")
public class ReviewOrderController extends SimiController implements
        SelectedShippingMethodDelegate {

    protected ReviewOrderDelegate mDelegate;
    protected PaymentMethodDelegate mPaymentMethodDelegate;
    protected ShippingDelegate mShippingDelegate;

    protected MyAddress mBillingAddress;
    protected MyAddress mShippingAddress;
    protected ArrayList<ShippingMethod> mShippingmethod;
    protected ArrayList<PaymentMethod> mPaymentMethods;
    protected ShippingMethod mReviewOrderShippingMethod;
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
        setOnPlaceNow();
        setOnChoiceBillingAddress();
        setOnChooseShippingAddress();
        setCouponCodeListener();
        setViewProductDetail();

        if(mAfterControll == NewAddressBookFragment.NEW_CUSTOMER || mAfterControll == NewAddressBookFragment.NEW_AS_GUEST){
            requestUpdateShipping();
            requestUpdateBilling();
            requestCheckoutGestNew();
        }
        onRequestData();
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
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    mDelegate.setTotalPrice(cart);
                    mtotalPrice = cart;
                    if (cart.getProduct() != null && cart.getProduct().size() > 0) {
                        mDelegate.setInitViewListProduct(cart.getProduct());
                    }

                    if (cart.getCouponCode() != null && cart.getCouponCode().size() > 0) {
                        checkCouponCode = true;
                        mDelegate.setInitViewCouponCode(cart.getCouponCode().get(0).getCode());
                    }

                    if (cart.getShippingMethod() != null) {
                        mReviewOrderShippingMethod = cart.getShippingMethod();
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
                mShippingDelegate.goneView();
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
                            if (mReviewOrderShippingMethod != null) {
                                for (int i = 0; i < shippingMethodsArr.size(); i++) {
                                    if (mReviewOrderShippingMethod.getmShippingMethodCode().equals(shippingMethodsArr.get(i).getmShippingMethodCode())) {
                                        shippingMethodsArr.get(i).setIsSelected(true);
                                    }
                                }
                            }
                            mShippingmethod = shippingMethodsArr;
                            mShippingDelegate.setShippingMethods(shippingMethodsArr);
                        }
                    }

                    mDelegate.setShipingAddress(mShippingAddress);
                }else{
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
                mPaymentMethodDelegate.goneView();
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
                            mPaymentMethodDelegate.setPaymentMethods(paymentMethodsArr);
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

    private void requestCheckoutGestNew(){
        CheckOutGuestNewModel checkOutGuestNewModel = new CheckOutGuestNewModel();
        checkOutGuestNewModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                    SimiManager.getIntance().backPreviousFragment();
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if(collection != null && collection.getCollection().size() > 0){
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                    if(quoteEntity.getCustomer() != null){
                        DataLocal.saveCustomerID(quoteEntity.getCustomer().getmCustomerID());
                        DataLocal.saveCustomer(quoteEntity.getCustomer().getmCustomerFirstName(), quoteEntity.getCustomer().getmCustomerLastName(), quoteEntity.getCustomer().getmCustomerEmail(), "",quoteEntity.getCustomer().getmCustomerID());
                    }
                }
            }
        });

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            checkOutGuestNewModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        if(mAfterControll == NewAddressBookFragment.NEW_CUSTOMER){
            checkOutGuestNewModel.addDataExtendURL("customer");
        }

        String email = DataLocal.getEmail();
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

        if(mAfterControll == NewAddressBookFragment.NEW_CUSTOMER){
            String password = DataLocal.getPassword();
            checkOutGuestNewModel.addDataBody("password", password);
            checkOutGuestNewModel.addDataBody("create_new_customer", "1");
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
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showNotify(null, "Couponcode was removed", "Ok");
                checkCouponCode = false;
                mDelegate.removeTextCouponCode();
                if(collection != null && collection.getCollection().size() > 0){
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
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showNotify(null, "Couponcode was applied", "Ok");
                checkCouponCode = true;
                if(collection != null && collection.getCollection().size() > 0){
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
					PaymentMethod paymentMethod = getPaymentMethod(PaymentMethod
							.getInstance().getPlacePaymentMethod());
					if (paymentMethod != null) {
                        requestPlaceOrder(paymentMethod);
					}
                }
            }
        };
    }

    protected void requestPlaceOrder(final PaymentMethod paymentmethod) {

        mDelegate.showLoading();
        mModel = new PlaceOrderModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                Config.getInstance().setQuoteCustomerSignIn("");
                DataLocal.saveQuoteCustomerNotSignIn("");
//                PaymentMethod.getInstance().setPlacePaymentMethod("");
//                PaymentMethod.getInstance().setPlacePaymentMethod("");
//                PaymentMethod.getInstance().setPlace_cc_number("");
//                PaymentMethod.getInstance().setPlacecc_id("");
                ConfigCheckout.checkPaymentMethod = false;
                SimiManager.getIntance().onUpdateCartQty(null);
                PaymentMethod.getInstance().setPlacePaymentMethod("");

                if (mAfterControll == NewAddressBookFragment.NEW_CUSTOMER) {
                    AutoSignInController controller = new AutoSignInController();
                    controller.onStart();
                }

                if(collection != null && collection.getCollection().size() > 0){
                    OrderEntity orderEntity = (OrderEntity) collection.getCollection().get(0);
                    ThankyouFragment fragment = ThankyouFragment.newInstance();


                    int showtype = paymentmethod.getShow_type();
                    switch (showtype) {
                        case 1:
                            fragment.setMessage(Config.getInstance().getText("Thank you for your purchase!"));
                            fragment.setInvoice_number(String.valueOf(orderEntity.getSeqNo()));
                            OrderHisDetail orderHisDetail = new OrderHisDetail();
                            orderHisDetail.setJSONObject(orderEntity.getJSONObject());
                            orderHisDetail.parse();
                            fragment.setOrderHisDetail(orderHisDetail);
                            if (DataLocal.isTablet) {
                                SimiManager.getIntance().replacePopupFragment(
                                        fragment);
                            } else {
                                SimiManager.getIntance().replaceFragment(
                                        fragment);
                            }
                            break;
                        case  2:
                            // event call paypal server.
                            CheckoutData _CheckoutData2 = new CheckoutData();
                            _CheckoutData2
                                    .setInvoice_number(((PlaceOrderModel) mModel)
                                            .getInvoiceNumber());
                            _CheckoutData2.setOder(orderEntity);
                            _CheckoutData2.setPaymentMethod(paymentmethod);
                            EventCheckout event2 = new EventCheckout();
                            event2.dispatchEvent(
                                    "com.simicart.paymentmethod.placeorder",
                                    _CheckoutData2);
                            // end event
                            break;
                    }
                }
            }
        });
        if (paymentmethod.getShow_type() == 4) {
            mModel.addDataBody(Constants.CARD_TYPE, ""
                    + PaymentMethod.getInstance().getPlace_cc_type());
            mModel.addDataBody(Constants.CARD_NUMBER, ""
                    + PaymentMethod.getInstance().getPlace_cc_number());
            mModel.addDataBody(Constants.EXPRIRED_MONTH, ""
                    + PaymentMethod.getInstance().getPlace_cc_exp_month());
            mModel.addDataBody(Constants.EXPRIRED_YEAR, ""
                    + PaymentMethod.getInstance().getPlace_cc_exp_year());
            if (paymentmethod.getData(Constants.USECCV).equals("1")) {
                mModel.addDataBody(Constants.CC_ID, ""
                        + PaymentMethod.getInstance().getPlacecc_id());
            }
        }
//		if (Config.getInstance().getEnable_agreements() != 0) {
//			mModel.addDataBody(Constants.CONDITION, ""
//					+ Config.getInstance().getEnable_agreements());
//		}

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn() + "?action=createorder");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin() + "?action=createorder");
        }

        mModel.request();

    }

    protected boolean isCompleteRequired() {
        if (PaymentMethod.getInstance().getPlacePaymentMethod().equals("")) {
            Utils.expand(mDelegate.getLayoutPayment());
            mDelegate.scrollCenter();
            SimiManager.getIntance().showNotify(null,
                    "Please specify payment method", "Ok");
            return false;
        }

        if(checkRequiedShipping){
            if (mShippingmethod != null && mShippingmethod.size() > 0) {
                if (!isCheckShippingMethod()) {
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

    private void recieveNotification(NotificationEntity notificationData) {
        Intent i = new Intent(MainActivity.context, NotificationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("NOTIFICATION_DATA", notificationData);
        MainActivity.context.startActivity(i);
    }

    protected void updateView() {
        ReviewOrderModel model = (ReviewOrderModel) mModel;
        mConditions = (model.getConditions());
        mDelegate.setConditions(model.getConditions());

        QuoteEntity quoteEntity = (QuoteEntity) model.getCollection().getCollection().get(0);

        mDelegate.setTotalPrice(quoteEntity);
        mPaymentMethods = model.getPaymentMethods();
        if (mPaymentMethods != null && mPaymentMethods.size() > 0) {
            mPaymentMethodDelegate.setPaymentMethods(mPaymentMethods);
            mShippingDelegate.setPaymentMethod(mPaymentMethods);
        }
        mShippingmethod = model.getShippingMethods();
        if (mShippingmethod != null && mShippingmethod.size() > 0) {
            mShippingDelegate.setShippingMethods(mShippingmethod);
            mPaymentMethodDelegate.setListShippingMethod(mShippingmethod);
        }
        mDelegate.updateView(mModel.getCollection());

        if (mShippingmethod == null || mShippingmethod.size() <= 0) {
            mDelegate.setBillingAddress(mBillingAddress);
        } else {
            mDelegate.setShipingAddress(mShippingAddress);
            mDelegate.setBillingAddress(mBillingAddress);
        }

        // if (!checkAutoSelectShipping()) {
        // checkAutoSelectPaymentMehtod();
        // }

    }

    @Override
    public void onResume() {
        mDelegate.setShipingAddress(mShippingAddress);
        mDelegate.setBillingAddress(mBillingAddress);
        mDelegate.setConditions(mConditions);
        mDelegate.setTotalPrice(mtotalPrice);
        mPaymentMethodDelegate.setPaymentMethods(mPaymentMethods);
        mShippingDelegate.setShippingMethods(mShippingmethod);
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

    public void setDelegate(PaymentMethodDelegate paymentMethodDelegate) {
        this.mPaymentMethodDelegate = paymentMethodDelegate;
    }

    public void setShippingDelegate(ShippingDelegate shippingDelegate) {
        this.mShippingDelegate = shippingDelegate;
    }

    @Override
    public void updateTotalPrice(TotalPrice totalPrice) {

    }

    @Override
    public void updatePaymentMethod(ArrayList<PaymentMethod> paymentMethod) {
        mPaymentMethods = paymentMethod;
        mPaymentMethodDelegate.setPaymentMethods(mPaymentMethods);
    }

    public void updateListShipping(ArrayList<ShippingMethod> listShippingMethos) {
        mShippingDelegate.setShippingMethods(listShippingMethos);
    }

    @Override
    public void updateShippingMethod(ShippingMethod shippingMethod) {
        ArrayList<ShippingMethod> listShipping = ((ReviewOrderModel) mModel)
                .getShippingMethods();
        for (int i = 0; i < listShipping.size(); i++) {
            ShippingMethod shippingMethod2 = listShipping.get(i);
            String id = shippingMethod2.getS_method_id();
            if (id.equals(shippingMethod.getS_method_id())) {
                listShipping.set(i, shippingMethod);
            }
        }
        ((ReviewOrderModel) mModel).setShippingMethods(listShipping);
        // mDelegate.setShipingMethods(listShipping);
        mShippingDelegate.setShippingMethods(listShipping);

    }

    protected PaymentMethod getPaymentMethod(String payment_name) {
        for (PaymentMethod paymentMethod : mPaymentMethods) {
            if (paymentMethod.getMethodCode().equals(payment_name)) {
                return paymentMethod;
            }
        }
        return null;
    }

    protected boolean isCheckShippingMethod() {
        for (ShippingMethod shippingMethod : mShippingmethod) {
            if (shippingMethod.isS_method_selected()) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkAutoSelectShipping() {
        if (null != mShippingmethod && mShippingmethod.size() > 0) {
            ShippingMethod currentMethod = null;
            for (ShippingMethod shippingMethod : mShippingmethod) {
                if (shippingMethod.isS_method_selected()) {
                    currentMethod = shippingMethod;
                }
            }
            if (currentMethod == null) {
                autoSelectShipping();
                return true;
            }
        }
        return false;
    }

    protected void autoSelectShipping() {
        if (null != mShippingmethod) {
            mDelegate.showLoading();
            final ShippingMethod firstShippingMethod = mShippingmethod.get(0);
            String code = firstShippingMethod.getS_method_code();
            String id = firstShippingMethod.getS_method_id();
            final ShippingMethodModel mModel_shipping = new ShippingMethodModel();
            mModel_shipping.setDelegate(new ModelDelegate() {
                @Override
                public void onFail(SimiError error) {

                }

                @Override
                public void onSuccess(SimiCollection collection) {

                }
            });
            mModel_shipping.addDataBody("s_method_id", id);
            mModel_shipping.addDataBody("s_method_code", code);
            mModel_shipping.request();
        }
    }

    protected void checkAutoSelectPaymentMehtod() {
        if (null != mPaymentMethods && mPaymentMethods.size() > 0) {
            for (int i = 0; i < mPaymentMethods.size(); i++) {
                PaymentMethod paymentMethod = mPaymentMethods.get(i);
                int show_type = paymentMethod.getShow_type();
                if (show_type == 0
                        && !paymentMethod.getData(Constants.CONTENT).equals("")) {
                    if (Config.getInstance().isReload_payment_method()) {
                        PaymentMethod.getInstance().setPlacePaymentMethod(
                                paymentMethod.getPayment_method());
                        autoSelectPaymentMethod(paymentMethod);
                        return;
                    }
                }
            }
        }
    }

    protected void autoSelectPaymentMethod(final PaymentMethod paymentMethod) {
        final PaymentMethodModel model = new PaymentMethodModel();
        mDelegate.showDialogLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        };
        model.setDelegate(delegate);
        model.addDataBody("payment_method", paymentMethod.getPayment_method());
        Log.e("ReviewOrderController ", "AutoSelectPaymentMethod "
                + paymentMethod.getPayment_method());
        model.request();
    }

    public void setSavePaymentMethod(QuoteEntity totalPrice) {
        mtotalPrice = totalPrice;
//        mDelegate.setTotalPrice(totalPrice);
    }

    public void setInitViewShipping(String shippingName) {
        mDelegate.setInitViewShippingMethod(shippingName);
    }

    public void setInitViewPayment(String paymentName) {
        mDelegate.setInitViewPaymentMethod(paymentName);
    }

    public void setTotalPrice(QuoteEntity quoteEntity){
        mDelegate.setTotalPrice(quoteEntity);
    }

    public void setActionArrowUp(int type){
        if(type == 0){
            mDelegate.setActionArrowUp(0);
        }else{
            mDelegate.setActionArrowUp(1);
        }
    }

    public void setActionArrowDown(int type){
        if(type == 0){
            mDelegate.setActionArrowDown(0);
        }else{
            mDelegate.setActionArrowDown(1);
        }
    }
}
