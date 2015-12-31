package com.simicart.core.customer.entity;

import android.util.Log;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.common.Utils;

public class OrderHisDetail extends SimiEntity {
	private String mID;
	private String mWareHouseID;
	private BillingAddress mBillingAddress;
	private ShippingAddress mShippingAddress;
	private CustomerOrdersEntity mCustomer;
	private String mCurrencyTemplate;
	private float mSubTotal;
	private float mBaseSubTotal;
	private float mGrandTotal;
	private float mBaseGrandTotal;
	private String mCustomerID;
	private String mCustomerEmail;
	private ArrayList<OrderComment> mComment;
	private float mBaseTaxAmount;
	private float mTaxAmount;
	private JSONArray mTotals;
	private float mBaseDiscountAmount;
	private float mDiscountAmount;
	private float mTaxPercent;
	private String mBillName;
	private float mShippingAmount;
	private float mBaseShippingAmount;
	private int mTotalQtyOrdered;
	private ArrayList<ProductEntity> mItems;
	private String mQuoteID;
	private String mStatus;
	private String mOrderCurrencyCode;
	private String mBaseCurrencyCode;
	private String mStoreCurrencyCode;
	private int mSeqNum;
	private String mOrderNumber;
	private String mUpdateAt;
	private String mCreateAt;
	private float mPaidAmount;
	private String mDiscountPaid;
	private String mSubTotalPaid;
	private String mShippingPaid;
	private float mPaidTax;
	private String mShippedQty;
	private String mRefundedAmount;
	private float mShippingRefunded;
	private String mQuoteCurrencyCode;
//	private mAdditionalFeePaid;
	private String mCouponCode;
	private int mItemsCount;
	private ArrayList<String> mCoupon;
	private ShippingMethod mShipping;
	private float mShippingTaxAmount;
	private float mBaseShippingTaxAmount;
//	private mAdditionalFeePaid;
	private float mAdditionalFeeRefund;
	private PaymentMethod mPayment;

	@Override
	public void parse() {
		if(mJSON.has("_id")) {
			try {
				mID = mJSON.getString("_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("warehouse_id")) {
			try {
				mWareHouseID = mJSON.getString("warehouse_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("billing_address")) {
			mBillingAddress = new BillingAddress();
			try {
				JSONObject obj = mJSON.getJSONObject("billing_address");
				mBillingAddress.setJSONObject(obj);
				mBillingAddress.parse();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipping_address")) {
			mShippingAddress = new ShippingAddress();
			try {
				JSONObject obj = mJSON.getJSONObject("shipping_address");
				mShippingAddress.setJSONObject(obj);
				mShippingAddress.parse();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("payment")) {
			String paymentValue = getData("payment");
			if(Utils.validateString(paymentValue)){
				mPayment = new PaymentMethod();
				try {
					mPayment.setJSONObject(new JSONObject(paymentValue));
					mPayment.parse();
				} catch (JSONException e) {
					mPayment = null;
					Log.e("OrderHisDetail", "PaymentMethod: " + e.getMessage());
				}
			}
		}

		if(mJSON.has("customer")) {
			mCustomer = new CustomerOrdersEntity();
			try {
				JSONObject obj = mJSON.getJSONObject("customer");
				mCustomer.setJSONObject(obj);
				mCustomer.parse();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("currency_template")) {
			try {
				mCurrencyTemplate = mJSON.getString("currency_template");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("subtotal")) {
			try {
				mSubTotal = Float.parseFloat(mJSON.getString("subtotal"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_subtotal")) {
			try {
				mBaseSubTotal = Float.parseFloat(mJSON.getString("base_subtotal"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("grand_total")) {
			try {
				mGrandTotal = Float.parseFloat(mJSON.getString("grand_total"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_grand_total")) {
			try {
				mBaseGrandTotal = Float.parseFloat(mJSON.getString("base_grand_total"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("customer_id")) {
			try {
				mCustomerID = mJSON.getString("customer_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("customer_email")) {
			try {
				mCustomerEmail = mJSON.getString("customer_email");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("comments")) {
			mComment = new ArrayList<>();
			try {
				JSONArray arr = mJSON.getJSONArray("comments");
				for(int i=0;i<arr.length();i++) {
					OrderComment orderComment = new OrderComment();
					orderComment.setJSONObject(arr.getJSONObject(i));
					orderComment.parse();
					mComment.add(orderComment);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_tax_amount")) {
			try {
				mBaseTaxAmount = Float.parseFloat(mJSON.getString("base_tax_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("tax_amount")) {
			try {
				mTaxAmount = Float.parseFloat(mJSON.getString("tax_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("totals")) {
			try {
				mTotals = mJSON.getJSONArray("totals");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_discount_amount")) {
			try {
				mBaseDiscountAmount = Float.parseFloat(mJSON.getString("base_discount_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("discount_amount")) {
			try {
				mDiscountAmount = Float.parseFloat(mJSON.getString("discount_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("tax_percent")) {
			try {
				mTaxPercent = Float.parseFloat(mJSON.getString("tax_percent"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("bill_name")) {
			try {
				mBillName = mJSON.getString("bill_name");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipping_amount")) {
			try {
				mShippingAmount =Float.parseFloat(mJSON.getString("shipping_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_shipping_amount")) {
			try {
				mBaseShippingAmount = Float.parseFloat(mJSON.getString("base_shipping_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("total_qty_ordered")) {
			try {
				mTotalQtyOrdered = mJSON.getInt("total_qty_ordered");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("items")) {
			mItems = new ArrayList<>();
			try {
				JSONArray arr = mJSON.getJSONArray("items");
				for(int i=0;i<arr.length();i++) {
					ProductEntity item = new ProductEntity();
					item.setJSONObject(arr.getJSONObject(i));
					item.parse();
					mItems.add(item);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("quote_id")) {
			try {
				mQuoteID = mJSON.getString("quote_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("status")) {
			try {
				mStatus = mJSON.getString("status");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("order_currency_code")) {
			try {
				mOrderCurrencyCode = mJSON.getString("order_currency_code");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_currency_code")) {
			try {
				mBaseCurrencyCode = mJSON.getString("base_currency_code");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("store_currency_code")) {
			try {
				mStoreCurrencyCode = mJSON.getString("store_currency_code");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("seq_no")) {
			try {
				mSeqNum = mJSON.getInt("seq_no");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("order_number")) {
			try {
				mOrderNumber = mJSON.getString("order_number");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("created_at")) {
			try {
				mCreateAt = mJSON.getString("created_at");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("updated_at")) {
			try {
				mUpdateAt = mJSON.getString("updated_at");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("paid_amount")) {
			try {
				mPaidAmount = Float.parseFloat(mJSON.getString("paid_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("discount_paid")) {
			try {
				mDiscountPaid = mJSON.getString("discount_paid");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("subtotal_paid")) {
			try {
				mSubTotalPaid = mJSON.getString("subtotal_paid");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipping_paid")) {
			try {
				mShippingPaid = mJSON.getString("shipping_paid");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("paid_tax")) {
			try {
				mPaidTax = Float.parseFloat(mJSON.getString("paid_tax"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipped_qty")) {
			try {
				mShippedQty = mJSON.getString("shipped_qty");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("refunded_amount")) {
			try {
				mRefundedAmount = mJSON.getString("refunded_amount");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipping_refunded")) {
			try {
				mShippingRefunded = Float.parseFloat(mJSON.getString("shipping_refunded"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("quote_currency_code")) {
			try {
				mQuoteCurrencyCode = mJSON.getString("quote_currency_code");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("coupon_code")) {
			try {
				mCouponCode = mJSON.getString("coupon_code");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("items_count")) {
			try {
				mItemsCount = mJSON.getInt("items_count");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("coupon")) {
			try {
				mCoupon = getArrayData(mJSON.getString("coupon"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipping")) {
			mShipping = new ShippingMethod();
			try {
				JSONObject obj = mJSON.getJSONObject("shipping");
				if(obj != null) {
					mShipping.setJSONObject(obj);
					mShipping.parse();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("shipping_tax_amount")) {
			try {
				mShippingTaxAmount = Float.parseFloat(mJSON.getString("shipping_tax_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("base_shipping_tax_amount")) {
			try {
				mBaseShippingTaxAmount = Float.parseFloat(mJSON.getString("base_shipping_tax_amount"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("additional_fee_Refund")) {
			try {
				mAdditionalFeeRefund = Float.parseFloat(mJSON.getString("additional_fee_Refund"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	public float getmAdditionalFeeRefund() {
		return mAdditionalFeeRefund;
	}

	public void setmAdditionalFeeRefund(float mAdditionalFeeRefund) {
		this.mAdditionalFeeRefund = mAdditionalFeeRefund;
	}

	public String getmBaseCurrencyCode() {
		return mBaseCurrencyCode;
	}

	public void setmBaseCurrencyCode(String mBaseCurrencyCode) {
		this.mBaseCurrencyCode = mBaseCurrencyCode;
	}

	public float getmBaseDiscountAmount() {
		return mBaseDiscountAmount;
	}

	public void setmBaseDiscountAmount(float mBaseDiscountAmount) {
		this.mBaseDiscountAmount = mBaseDiscountAmount;
	}

	public float getmBaseGrandTotal() {
		return mBaseGrandTotal;
	}

	public void setmBaseGrandTotal(float mBaseGrandTotal) {
		this.mBaseGrandTotal = mBaseGrandTotal;
	}

	public float getmBaseShippingAmount() {
		return mBaseShippingAmount;
	}

	public void setmBaseShippingAmount(float mBaseShippingAmount) {
		this.mBaseShippingAmount = mBaseShippingAmount;
	}

	public float getmBaseShippingTaxAmount() {
		return mBaseShippingTaxAmount;
	}

	public void setmBaseShippingTaxAmount(float mBaseShippingTaxAmount) {
		this.mBaseShippingTaxAmount = mBaseShippingTaxAmount;
	}

	public float getmBaseSubTotal() {
		return mBaseSubTotal;
	}

	public void setmBaseSubTotal(float mBaseSubTotal) {
		this.mBaseSubTotal = mBaseSubTotal;
	}

	public float getmBaseTaxAmount() {
		return mBaseTaxAmount;
	}

	public void setmBaseTaxAmount(float mBaseTaxAmount) {
		this.mBaseTaxAmount = mBaseTaxAmount;
	}

	public BillingAddress getmBillingAddress() {
		return mBillingAddress;
	}

	public void setmBillingAddress(BillingAddress mBillingAddress) {
		this.mBillingAddress = mBillingAddress;
	}

	public String getmBillName() {
		return mBillName;
	}

	public void setmBillName(String mBillName) {
		this.mBillName = mBillName;
	}

	public ArrayList<OrderComment> getmComment() {
		return mComment;
	}

	public void setmComment(ArrayList<OrderComment> mComment) {
		this.mComment = mComment;
	}

	public ArrayList<String> getmCoupon() {
		return mCoupon;
	}

	public void setmCoupon(ArrayList<String> mCoupon) {
		this.mCoupon = mCoupon;
	}

	public String getmCouponCode() {
		return mCouponCode;
	}

	public void setmCouponCode(String mCouponCode) {
		this.mCouponCode = mCouponCode;
	}

	public String getmCreateAt() {
		return mCreateAt;
	}

	public void setmCreateAt(String mCreateAt) {
		this.mCreateAt = mCreateAt;
	}

	public String getmCurrencyTemplate() {
		return mCurrencyTemplate;
	}

	public void setmCurrencyTemplate(String mCurrencyTemplate) {
		this.mCurrencyTemplate = mCurrencyTemplate;
	}

	public CustomerOrdersEntity getmCustomer() {
		return mCustomer;
	}

	public void setmCustomer(CustomerOrdersEntity mCustomer) {
		this.mCustomer = mCustomer;
	}

	public String getmCustomerEmail() {
		return mCustomerEmail;
	}

	public void setmCustomerEmail(String mCustomerEmail) {
		this.mCustomerEmail = mCustomerEmail;
	}

	public String getmCustomerID() {
		return mCustomerID;
	}

	public void setmCustomerID(String mCustomerID) {
		this.mCustomerID = mCustomerID;
	}

	public float getmDiscountAmount() {
		return mDiscountAmount;
	}

	public void setmDiscountAmount(float mDiscountAmount) {
		this.mDiscountAmount = mDiscountAmount;
	}

	public String getmDiscountPaid() {
		return mDiscountPaid;
	}

	public void setmDiscountPaid(String mDiscountPaid) {
		this.mDiscountPaid = mDiscountPaid;
	}

	public float getmGrandTotal() {
		return mGrandTotal;
	}

	public void setmGrandTotal(float mGrandTotal) {
		this.mGrandTotal = mGrandTotal;
	}

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public ArrayList<ProductEntity> getmItems() {
		return mItems;
	}

	public void setmItems(ArrayList<ProductEntity> mItems) {
		this.mItems = mItems;
	}

	public int getmItemsCount() {
		return mItemsCount;
	}

	public void setmItemsCount(int mItemsCount) {
		this.mItemsCount = mItemsCount;
	}

	public String getmOrderCurrencyCode() {
		return mOrderCurrencyCode;
	}

	public void setmOrderCurrencyCode(String mOrderCurrencyCode) {
		this.mOrderCurrencyCode = mOrderCurrencyCode;
	}

	public String getmOrderNumber() {
		return mOrderNumber;
	}

	public void setmOrderNumber(String mOrderNumber) {
		this.mOrderNumber = mOrderNumber;
	}

	public float getmPaidAmount() {
		return mPaidAmount;
	}

	public void setmPaidAmount(float mPaidAmount) {
		this.mPaidAmount = mPaidAmount;
	}

	public float getmPaidTax() {
		return mPaidTax;
	}

	public void setmPaidTax(float mPaidTax) {
		this.mPaidTax = mPaidTax;
	}

	public PaymentMethod getPayment() {
		return mPayment;
	}

	public void setPayment(PaymentMethod mPayment) {
		this.mPayment = mPayment;
	}

	public String getmQuoteCurrencyCode() {
		return mQuoteCurrencyCode;
	}

	public void setmQuoteCurrencyCode(String mQuoteCurrencyCode) {
		this.mQuoteCurrencyCode = mQuoteCurrencyCode;
	}

	public String getmQuoteID() {
		return mQuoteID;
	}

	public void setmQuoteID(String mQuoteID) {
		this.mQuoteID = mQuoteID;
	}

	public String getmRefundedAmount() {
		return mRefundedAmount;
	}

	public void setmRefundedAmount(String mRefundedAmount) {
		this.mRefundedAmount = mRefundedAmount;
	}

	public int getmSeqNum() {
		return mSeqNum;
	}

	public void setmSeqNum(int mSeqNum) {
		this.mSeqNum = mSeqNum;
	}

	public String getmShippedQty() {
		return mShippedQty;
	}

	public void setmShippedQty(String mShippedQty) {
		this.mShippedQty = mShippedQty;
	}

	public ShippingMethod getmShipping() {
		return mShipping;
	}

	public void setmShipping(ShippingMethod mShipping) {
		this.mShipping = mShipping;
	}

	public ShippingAddress getmShippingAddress() {
		return mShippingAddress;
	}

	public void setmShippingAddress(ShippingAddress mShippingAddress) {
		this.mShippingAddress = mShippingAddress;
	}

	public float getmShippingAmount() {
		return mShippingAmount;
	}

	public void setmShippingAmount(float mShippingAmount) {
		this.mShippingAmount = mShippingAmount;
	}

	public String getmShippingPaid() {
		return mShippingPaid;
	}

	public void setmShippingPaid(String mShippingPaid) {
		this.mShippingPaid = mShippingPaid;
	}

	public float getmShippingRefunded() {
		return mShippingRefunded;
	}

	public void setmShippingRefunded(float mShippingRefunded) {
		this.mShippingRefunded = mShippingRefunded;
	}

	public float getmShippingTaxAmount() {
		return mShippingTaxAmount;
	}

	public void setmShippingTaxAmount(float mShippingTaxAmount) {
		this.mShippingTaxAmount = mShippingTaxAmount;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getmStoreCurrencyCode() {
		return mStoreCurrencyCode;
	}

	public void setmStoreCurrencyCode(String mStoreCurrencyCode) {
		this.mStoreCurrencyCode = mStoreCurrencyCode;
	}

	public float getmSubTotal() {
		return mSubTotal;
	}

	public void setmSubTotal(float mSubTotal) {
		this.mSubTotal = mSubTotal;
	}

	public String getmSubTotalPaid() {
		return mSubTotalPaid;
	}

	public void setmSubTotalPaid(String mSubTotalPaid) {
		this.mSubTotalPaid = mSubTotalPaid;
	}

	public float getmTaxAmount() {
		return mTaxAmount;
	}

	public void setmTaxAmount(float mTaxAmount) {
		this.mTaxAmount = mTaxAmount;
	}

	public float getmTaxPercent() {
		return mTaxPercent;
	}

	public void setmTaxPercent(float mTaxPercent) {
		this.mTaxPercent = mTaxPercent;
	}

	public int getmTotalQtyOrdered() {
		return mTotalQtyOrdered;
	}

	public void setmTotalQtyOrdered(int mTotalQtyOrdered) {
		this.mTotalQtyOrdered = mTotalQtyOrdered;
	}

	public JSONArray getmTotals() {
		return mTotals;
	}

	public void setmTotals(JSONArray mTotals) {
		this.mTotals = mTotals;
	}

	public String getmUpdateAt() {
		return mUpdateAt;
	}

	public void setmUpdateAt(String mUpdateAt) {
		this.mUpdateAt = mUpdateAt;
	}

	public String getmWareHouseID() {
		return mWareHouseID;
	}

	public void setmWareHouseID(String mWareHouseID) {
		this.mWareHouseID = mWareHouseID;
	}
}
