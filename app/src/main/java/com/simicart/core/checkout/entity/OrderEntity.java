package com.simicart.core.checkout.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.customer.entity.BillingAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.entity.ShippingAddress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 12/14/2015.
 */
public class OrderEntity extends SimiEntity{
    protected String mID;
    protected String mWareHouseID;
    protected PaymentMethod mPayment;
    protected String mBaseCurrencyCode;
    protected String mStoreCurrencyCode;
    protected String mOrderCurrencyCode;
    protected String mCurrencyTemplate;
    protected float mSubtotal;
    protected float mBaseSubTotal;
    protected float mGrandTotal;
    protected float mBaseGrandTotal;
    protected float mBaseTaxAmount;
    protected float mTaxAmount;
    protected float mBaseDiscountAmount;
    protected float mDiscountAmount;
    protected float mTaxPercent;
    protected float mShippingAmount;
    protected float mBaseShippingAmount;
    protected float mAdditionalFee;
    protected int mTotalQtyOrdered;
    protected String mQuoteID;
    protected String mStatus;
    protected int mSeqNo;
    protected String mCreatedAt;
    protected String mUpdatedAt;

    private String _id = "_id";
    private String warehouse_id = "warehouse_id";
    private String payment = "payment";
    private String base_currency_code = "base_currency_code";
    private String store_currency_code = "store_currency_code";
    private String order_currency_code = "order_currency_code";
    private String currency_template = "currency_template";
    private String subtotal = "subtotal";
    private String base_subtotal = "base_subtotal";
    private String grand_total = "grand_total";
    private String base_grand_total = "base_grand_total";
    private String base_tax_amount = "base_tax_amount";
    private String tax_amount = "tax_amount";
    private String base_discount_amount = "base_discount_amount";
    private String discount_amount = "discount_amount";
    private String tax_percent = "tax_percent";
    private String shipping_amount = "shipping_amount";
    private String base_shipping_amount = "base_shipping_amount";
    private String additional_fee = "additional_fee";
    private String total_qty_ordered = "total_qty_ordered";
    private String quote_id = "quote_id";
    private String status = "status";
    private String seq_no = "seq_no";
    private String updated_at = "updated_at";
    private String created_at = "created_at";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mID = getData(_id);
            }

            if(mJSON.has(warehouse_id)){
                mWareHouseID = getData(warehouse_id);
            }

            if(mJSON.has(payment)){
                String paymentValue = getData(payment);
                if(Utils.validateString(paymentValue)){
                    mPayment = new PaymentMethod();
                    try {
                        mPayment.setJSONObject(new JSONObject(paymentValue));
                        mPayment.parse();
                    } catch (JSONException e) {
                        mPayment = null;
                        Log.e("OrderEntity", "Payment: " + e.getMessage());
                    }
                }
            }

            if(mJSON.has(base_currency_code)){
               mBaseCurrencyCode = getData(base_currency_code);
            }

            if(mJSON.has(store_currency_code)){
                mStoreCurrencyCode = getData(store_currency_code);
            }

            if(mJSON.has(order_currency_code)){
                mOrderCurrencyCode = getData(order_currency_code);
            }

            if(mJSON.has(currency_template)){
                mCurrencyTemplate = getData(currency_template);
            }

            if(mJSON.has(subtotal)){
                String subTotalvalue = getData(subtotal);
                if(Utils.validateString(subTotalvalue)){
                    try {
                        mSubtotal = Float.parseFloat(subTotalvalue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(base_subtotal)){
                String baseSubTotal = getData(base_subtotal);
                if(Utils.validateString(baseSubTotal)){
                    try {
                        mBaseSubTotal = Float.parseFloat(baseSubTotal);
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

            if(mJSON.has(base_tax_amount)){
                String baseTaxAmountValue = getData(base_tax_amount);
                if(Utils.validateString(baseTaxAmountValue)){
                    try {
                        mBaseTaxAmount = Float.parseFloat(baseTaxAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(tax_amount)){
                String taxAmountValue = getData(tax_amount);
                if(Utils.validateString(taxAmountValue)){
                    try {
                        mTaxAmount = Float.parseFloat(taxAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(base_discount_amount)){
                String baseDiscountAmount = getData(base_discount_amount);
                if(Utils.validateString(baseDiscountAmount)){
                    try {
                        mBaseDiscountAmount = Float.parseFloat(baseDiscountAmount);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(discount_amount)){
                String discountAmountValue = getData(discount_amount);
                if(Utils.validateString(discountAmountValue)){
                    try {
                        mDiscountAmount = Float.parseFloat(discountAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(tax_percent)){
                String taxPercentValue = getData(tax_percent);
                if(Utils.validateString(taxPercentValue)){
                    try {
                        mTaxPercent = Float.parseFloat(taxPercentValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(shipping_amount)){
                String shippingAmountValue = getData(shipping_amount);
                if(Utils.validateString(shippingAmountValue)){
                    try {
                        mShippingAmount = Float.parseFloat(shippingAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(base_shipping_amount)){
                String baseShippingAmountValue = getData(base_shipping_amount);
                if(Utils.validateString(baseShippingAmountValue)){
                    try {
                        mBaseShippingAmount = Float.parseFloat(baseShippingAmountValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(additional_fee)){
                String additionalValue = getData(additional_fee);
                if(Utils.validateString(additionalValue)){
                    try {
                        mAdditionalFee = Float.parseFloat(additionalValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(total_qty_ordered)){
                String totalQtyOrderValue = getData(total_qty_ordered);
                if(Utils.validateString(totalQtyOrderValue)){
                    try {
                        mTotalQtyOrdered = Integer.parseInt(totalQtyOrderValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(quote_id)){
                mQuoteID = getData(quote_id);
            }

            if(mJSON.has(status)){
                mStatus = getData(status);
            }

            if(mJSON.has(seq_no)){
                String seqNoValue = getData(seq_no);
                if(Utils.validateString(seqNoValue)){
                    try {
                        mSeqNo = Integer.parseInt(seqNoValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(updated_at)){
                mUpdatedAt = getData(updated_at);
            }

            if(mJSON.has(created_at)){
                mCreatedAt = getData(created_at);
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

    public PaymentMethod getPayment() {
        return mPayment;
    }

    public void setPayment(PaymentMethod mPayment) {
        this.mPayment = mPayment;
    }

    public String getBaseCurrencyCode() {
        return mBaseCurrencyCode;
    }

    public void setBaseCurrencyCode(String mBaseCurrencyCode) {
        this.mBaseCurrencyCode = mBaseCurrencyCode;
    }

    public String getStoreCurrencyCode() {
        return mStoreCurrencyCode;
    }

    public void setStoreCurrencyCode(String mStoreCurrencyCode) {
        this.mStoreCurrencyCode = mStoreCurrencyCode;
    }

    public String getOrderCurrencyCode() {
        return mOrderCurrencyCode;
    }

    public void setOrderCurrencyCode(String mOrderCurrencyCode) {
        this.mOrderCurrencyCode = mOrderCurrencyCode;
    }

    public String getCurrencyTemplate() {
        return mCurrencyTemplate;
    }

    public void setCurrencyTemplate(String mCurrencyTemplate) {
        this.mCurrencyTemplate = mCurrencyTemplate;
    }

    public Float getSubtotal() {
        return mSubtotal;
    }

    public void setSubtotal(Float mSubtotal) {
        this.mSubtotal = mSubtotal;
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

    public Float getBaseTaxAmount() {
        return mBaseTaxAmount;
    }

    public void setBaseTaxAmount(Float mBaseTaxAmount) {
        this.mBaseTaxAmount = mBaseTaxAmount;
    }

    public Float getTaxAmount() {
        return mTaxAmount;
    }

    public void setmaxAmount(Float mTaxAmount) {
        this.mTaxAmount = mTaxAmount;
    }

    public Float getBaseDiscountAmount() {
        return mBaseDiscountAmount;
    }

    public void setBaseDiscountAmount(Float mBaseDiscountAmount) {
        this.mBaseDiscountAmount = mBaseDiscountAmount;
    }

    public Float getDiscountAmount() {
        return mDiscountAmount;
    }

    public void setDiscountAmount(Float mDiscountAmount) {
        this.mDiscountAmount = mDiscountAmount;
    }

    public Float getTaxPercent() {
        return mTaxPercent;
    }

    public void setTaxPercent(Float mTaxPercent) {
        this.mTaxPercent = mTaxPercent;
    }

    public Float getShippingAmount() {
        return mShippingAmount;
    }

    public void setShippingAmount(Float mShippingAmount) {
        this.mShippingAmount = mShippingAmount;
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

    public int getTotalQtyOrdered() {
        return mTotalQtyOrdered;
    }

    public void setTotalQtyOrdered(int mTotalQtyOrdered) {
        this.mTotalQtyOrdered = mTotalQtyOrdered;
    }

    public String getQuoteID() {
        return mQuoteID;
    }

    public void setQuoteID(String mQuoteID) {
        this.mQuoteID = mQuoteID;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public int getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(int mSeqNo) {
        this.mSeqNo = mSeqNo;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String mUpdatedAt) {
        this.mUpdatedAt = mUpdatedAt;
    }
}
