package com.simicart.core.checkout.entity;

import android.util.Log;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.customer.entity.CustomerOrdersEntity;

public class QuoteEntity extends SimiEntity {
    protected String mID;
    protected String mSesstionID;
    protected String mWareHouseID;
    protected String mQuoteCurrency;
    protected String mBaseCurrency;
    protected String mStoreCurrency;
    protected String mCurrencyTemplate;
    protected String mOrigOrderID;
    protected float mTaxPercent;
    protected float mTaxAmount;
    protected float mBaseTaxAmount;
    protected float mDiscountAmount;
    protected float mBaseDiscountAmount;
    protected float mShppingAmount;
    protected float mBaseShippingAmount;
    protected float mAdditionalFee;
    protected float mShippingTaxAmount;
    protected float mBaseShippingTaxAmount;
    protected float mSubTotal;
    protected float mBaseSubTotal;
    protected float mGrandTotal;
    protected float mBaseGrandTotal;
    protected String mClientID;
    protected int mSeqNo;
    protected String mUpdateAt;
    protected String mCreateAt;
    protected ArrayList<ProductEntity> mProduct;
    protected int mCartQty;
    protected float mPaymentAmount;
    protected String mBillName;
    protected ArrayList<CouponCodeEntity> mCouponCode;
    protected ShippingMethod mShippingMethod;
    protected CustomerOrdersEntity mCustomer;

    private String _id = "_id";
    private String session_id = "session_id";
    private String warehouse_id = "warehouse_id";
    private String quote_currency_code = "quote_currency_code";
    private String base_currency_code = "base_currency_code";
    private String store_currency_code = "store_currency_code";
    private String currency_template = "currency_template";
    private String orig_order_id = "orig_order_id";
    private String tax_percent = "tax_percent";
    private String tax_amount = "tax_amount";
    private String base_tax_amount = "base_tax_amount";
    private String discount_amount = "discount_amount";
    private String base_discount_amount = "base_discount_amount";
    private String shipping_amount = "shipping_amount";
    private String base_shipping_amount = "base_shipping_amount";
    private String additional_fee = "additional_fee";
    private String totals = "totals";
    private String coupon = "coupon";
    private String client_id = "client_id";
    private String seq_no = "seq_no";
    private String updated_at = "updated_at";
    private String created_at = "created_at";
    private String items = "items";
    private String shipping_tax_amount = "shipping_tax_amount";
    private String base_shipping_tax_amount = "base_shipping_tax_amount";
    private String subtotal = "subtotal";
    private String base_subtotal = "base_subtotal";
    private String grand_total = "grand_total";
    private String base_grand_total = "base_grand_total";
    private String cart_qty = "cart_qty";
    private String comments = "comments";
    private String payment_amount = "payment_amount";
    private String bill_name = "bill_name";
    private String shipping = "shipping";
    private String customer = "customer";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mID = getData(_id);
            }

            if(mJSON.has(session_id)) {
                mSesstionID = getData(session_id);
            }

            if(mJSON.has(warehouse_id)){
                mWareHouseID = getData(warehouse_id);
            }

            if(mJSON.has(quote_currency_code)){
                mQuoteCurrency = getData(quote_currency_code);
            }

            if(mJSON.has(base_currency_code)){
                mBaseCurrency = getData(base_currency_code);
            }

            if(mJSON.has(store_currency_code)){
                mStoreCurrency = getData(store_currency_code);
            }

            if(mJSON.has(currency_template)){
                mCurrencyTemplate = getData(currency_template);
            }

            if(mJSON.has(orig_order_id)){
                mOrigOrderID = getData(orig_order_id);
            }

            if(mJSON.has(tax_percent)){
                String taxPercentValue = getData(tax_percent);
                if(Utils.validateString(taxPercentValue)){
                    mTaxPercent  = Float.parseFloat(taxPercentValue);
                }
            }

            if(mJSON.has(tax_amount)){
                String taxAmountValue = getData(tax_amount);
                if(Utils.validateString(taxAmountValue)){
                    mTaxAmount = Float.parseFloat(taxAmountValue);
                }
            }

            if(mJSON.has(base_tax_amount)){
                String baseTaxAmountValue = getData(base_tax_amount);
                if(Utils.validateString(baseTaxAmountValue)){
                    mBaseTaxAmount = Float.parseFloat(baseTaxAmountValue);
                }
            }

            if(mJSON.has(discount_amount)){
                String discountAmountValue = getData(discount_amount);
                if(Utils.validateString(discountAmountValue)){
                    mDiscountAmount = Float.parseFloat(discountAmountValue);
                }
            }

            if(mJSON.has(base_discount_amount)){
                String baseDiscountValue = getData(base_discount_amount);
                if(Utils.validateString(baseDiscountValue)){
                    mBaseDiscountAmount = Float.parseFloat(baseDiscountValue);
                }
            }

            if(mJSON.has(shipping_amount)){
                String shippingAmountValue = getData(shipping_amount);
                if(Utils.validateString(shippingAmountValue)){
                    mShppingAmount = Float.parseFloat(shippingAmountValue);
                }
            }

            if(mJSON.has(base_shipping_amount)){
                String baseShippingAmountValue = getData(base_shipping_amount);
                if(Utils.validateString(baseShippingAmountValue)){
                    mBaseShippingAmount = Float.parseFloat(baseShippingAmountValue);
                }
            }

            if(mJSON.has(additional_fee)){
                String additionalFeeValue = getData(additional_fee);
                if(Utils.validateString(additionalFeeValue)){
                    mAdditionalFee = Float.parseFloat(additionalFeeValue);
                }
            }

            if(mJSON.has(client_id)){
                mClientID = getData(client_id);
            }

            if(mJSON.has(seq_no)){
                String SeqNoValue = getData(seq_no);
                if(Utils.validateString(SeqNoValue)){
                    mSeqNo = Integer.parseInt(SeqNoValue);
                }
            }

            if(mJSON.has(updated_at)){
                mUpdateAt = getData(updated_at);
            }

            if(mJSON.has(created_at)){
                mCreateAt = getData(created_at);
            }

            if(mJSON.has(bill_name)){
                mBillName = getData(bill_name);
            }

            if(mJSON.has(shipping_tax_amount)){
                String shippingTaxAmountValue = getData(shipping_tax_amount);
                if(Utils.validateString(shippingTaxAmountValue)){
                    try {
                        mShippingTaxAmount = Float.parseFloat(shippingTaxAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(base_shipping_tax_amount)){
                String baseShippingTaxValue = getData(base_shipping_tax_amount);
                if(Utils.validateString(baseShippingTaxValue)) {
                    try {
                        mBaseShippingTaxAmount = Float.parseFloat(baseShippingTaxValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(subtotal)){
                String subTotalValue = getData(subtotal);
                if(Utils.validateString(subTotalValue)) {
                    try {
                        mSubTotal = Float.parseFloat(subTotalValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(base_subtotal)){
                String baseSubTotalValue = getData(base_subtotal);
                if(Utils.validateString(baseSubTotalValue)){
                    try {
                        mBaseSubTotal = Float.parseFloat(baseSubTotalValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(grand_total)){
                String grandTotalValue = getData(grand_total);
                if(Utils.validateString(grandTotalValue)){
                    try {
                        mGrandTotal = Float.parseFloat(grandTotalValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(base_grand_total)){
                String baseGrandTotalValue = getData(base_grand_total);
                if(Utils.validateString(baseGrandTotalValue)){
                    try {
                        mBaseGrandTotal = Float.parseFloat(baseGrandTotalValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(cart_qty)) {
                String cartQtyValue = getData(cart_qty);
                if(Utils.validateString(cartQtyValue)){
                    try {
                        mCartQty = Integer.parseInt(cartQtyValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(payment_amount)){
                String paymentAmountValue = getData(payment_amount);
                if(Utils.validateString(paymentAmountValue)){
                    try {
                        mPaymentAmount = Float.parseFloat(paymentAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(items)){
                try {
                    JSONArray itemsArr = mJSON.getJSONArray(items);
                    if(itemsArr.length() > 0){
                        mProduct = new ArrayList<ProductEntity>();
                        for (int i = 0; i < itemsArr.length(); i++){
                            ProductEntity productEntity = new ProductEntity();
                            productEntity.setJSONObject(itemsArr.getJSONObject(i));
                            productEntity.parse();
                            mProduct.add(productEntity);
                        }
                    }
                } catch (JSONException e) {
                    mProduct = null;
                    Log.e("QuoteEntity", e.getMessage());
                }
            }

            if(mJSON.has(coupon)){
                try {
                    JSONArray couponArr = mJSON.getJSONArray(coupon);
                    if(couponArr.length() > 0){
                        mCouponCode = new ArrayList<CouponCodeEntity>();
                        for (int i = 0; i < couponArr.length(); i++){
                            CouponCodeEntity couponCodeEntity = new CouponCodeEntity();
                            couponCodeEntity.setJSONObject(couponArr.getJSONObject(i));
                            couponCodeEntity.parse();
                            mCouponCode.add(couponCodeEntity);
                        }
                    }
                } catch (JSONException e) {
                    mCouponCode = null;
                    Log.e("QuoteEntity", "CouponCode" + e.getMessage());
                }
            }

            if(mJSON.has(shipping)){
                String shippingValue = getData(shipping);
                if(Utils.validateString(shippingValue)){
                    mShippingMethod = new ShippingMethod();
                    try {
                        mShippingMethod.setJSONObject(new JSONObject(shippingValue));
                        mShippingMethod.parse();
                    } catch (JSONException e) {
                        mShippingMethod = null;
                        Log.e("QuoteEntity", "ShippingMethod: " + e.getMessage());
                    }
                }
            }

            if(mJSON.has(customer)) {
                mCustomer = new CustomerOrdersEntity();
                try {
                    JSONObject obj = mJSON.getJSONObject(customer);
                    mCustomer.setJSONObject(obj);
                    mCustomer.parse();
                } catch (JSONException e) {
                    mCustomer = null;
                    Log.e("QuoteEntity", "Customer: " + e.getMessage());
                }
            }
        }
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getWareHouseID() {
        return mWareHouseID;
    }

    public void setWareHouseID(String mWareHouseID) {
        this.mWareHouseID = mWareHouseID;
    }

    public String getQuoteCurrency() {
        return mQuoteCurrency;
    }

    public void setQuoteCurrency(String mQuoteCurrency) {
        this.mQuoteCurrency = mQuoteCurrency;
    }

    public String getBaseCurrency() {
        return mBaseCurrency;
    }

    public void setBaseCurrency(String mBaseCurrency) {
        this.mBaseCurrency = mBaseCurrency;
    }

    public String getStoreCurrency() {
        return mStoreCurrency;
    }

    public void setStoreCurrency(String mStoreCurrency) {
        this.mStoreCurrency = mStoreCurrency;
    }

    public String getCurrencyTemplate() {
        return mCurrencyTemplate;
    }

    public void setCurrencyTemplate(String mCurrencyTemplate) {
        this.mCurrencyTemplate = mCurrencyTemplate;
    }

    public String getOrigOrderID() {
        return mOrigOrderID;
    }

    public void setOrigOrderID(String mOrigOrderID) {
        this.mOrigOrderID = mOrigOrderID;
    }

    public Float getTaxPercent() {
        return mTaxPercent;
    }

    public void setTaxPercent(Float mTaxPercent) {
        this.mTaxPercent = mTaxPercent;
    }

    public Float getTaxAmount() {
        return mTaxAmount;
    }

    public void setTaxAmount(Float mTaxAmount) {
        this.mTaxAmount = mTaxAmount;
    }

    public Float getBaseTaxAmount() {
        return mBaseTaxAmount;
    }

    public void setBaseTaxAmount(Float mBaseTaxAmount) {
        this.mBaseTaxAmount = mBaseTaxAmount;
    }

    public Float getDiscountAmount() {
        return mDiscountAmount;
    }

    public void setDiscountAmount(Float mDiscountAmount) {
        this.mDiscountAmount = mDiscountAmount;
    }

    public Float getBaseDiscountAmount() {
        return mBaseDiscountAmount;
    }

    public void setBaseDiscountAmount(Float mBaseDiscountAmount) {
        this.mBaseDiscountAmount = mBaseDiscountAmount;
    }

    public Float getShppingAmount() {
        return mShppingAmount;
    }

    public void setShppingAmount(Float mShppingAmount) {
        this.mShppingAmount = mShppingAmount;
    }

    public Float getBaseShippingAmount() {
        return mBaseShippingAmount;
    }

    public void setBaseShippingAmount(Float mBaseShippingAmount) {
        this.mBaseShippingAmount = mBaseShippingAmount;
    }

    public Float getAdditionalFee() {
        return mAdditionalFee;
    }

    public void setAdditionalFee(Float mAdditionalFee) {
        this.mAdditionalFee = mAdditionalFee;
    }

    public Float getShippingTaxAmount() {
        return mShippingTaxAmount;
    }

    public void setShippingTaxAmount(Float mShippingTaxAmount) {
        this.mShippingTaxAmount = mShippingTaxAmount;
    }

    public Float getBaseShippingTaxAmount() {
        return mBaseShippingTaxAmount;
    }

    public void setBaseShippingTaxAmount(Float mBaseShippingTaxAmount) {
        this.mBaseShippingTaxAmount = mBaseShippingTaxAmount;
    }

    public Float getSubTotal() {
        return mSubTotal;
    }

    public void setSubTotal(Float mSubTotal) {
        this.mSubTotal = mSubTotal;
    }

    public Float getBaseSubTotal() {
        return mBaseSubTotal;
    }

    public void setBaseSubTotal(Float mBaseSubTotal) {
        this.mBaseSubTotal = mBaseSubTotal;
    }

    public Float getGrandTotal() {
        return mGrandTotal;
    }

    public void setGrandTotal(Float mGrandTotal) {
        this.mGrandTotal = mGrandTotal;
    }

    public Float getBaseGrandTotal() {
        return mBaseGrandTotal;
    }

    public void setBaseGrandTotal(Float mBaseGrandTotal) {
        this.mBaseGrandTotal = mBaseGrandTotal;
    }

    public String getClientID() {
        return mClientID;
    }

    public void setClientID(String mClientID) {
        this.mClientID = mClientID;
    }

    public int getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(int mSeqNo) {
        this.mSeqNo = mSeqNo;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }

    public void setUpdateAt(String mUpdateAt) {
        this.mUpdateAt = mUpdateAt;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String mCreateAt) {
        this.mCreateAt = mCreateAt;
    }

    public ArrayList<ProductEntity> getProduct() {
        return mProduct;
    }

    public void setProduct(ArrayList<ProductEntity> mProduct) {
        this.mProduct = mProduct;
    }

    public int getQty() {
        return mCartQty;
    }

    public void setQty(int mCartQty) {
        this.mCartQty = mCartQty;
    }

    public Float getPaymentAmount() {
        return mPaymentAmount;
    }

    public void setPaymentAmount(Float mPaymentAmount) {
        this.mPaymentAmount = mPaymentAmount;
    }

    public String getBillName() {
        return mBillName;
    }

    public void setBillName(String mBillName) {
        this.mBillName = mBillName;
    }

    public ArrayList<CouponCodeEntity> getCouponCode() {
        return mCouponCode;
    }

    public void setCouponCode(ArrayList<CouponCodeEntity> mCouponCode) {
        this.mCouponCode = mCouponCode;
    }

    public ShippingMethod getShippingMethod() {
        return mShippingMethod;
    }

    public void setShippingMethod(ShippingMethod mShippingMethod) {
        this.mShippingMethod = mShippingMethod;
    }

    public CustomerOrdersEntity getCustomer() {
        return mCustomer;
    }

    public void setCustomer(CustomerOrdersEntity mCustomer) {
        this.mCustomer = mCustomer;
    }
}
