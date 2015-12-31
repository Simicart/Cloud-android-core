package com.simicart.core.splashscreen.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MSI on 02/12/2015.
 */
public class ConfigEntity extends SimiEntity {
    protected GeneralConfigEntity mGeneral;
    protected SaleConfigEntity mSale;
    protected FormatConfigEntity mFormatOption;
    protected SaleChannelConfigEntity mSaleChannel;
    protected PaymentConfigEntity mPayment;
    protected ShippingConfigEntity mShipping;
    protected TaxConfigEntity mTax;
    protected String mSenderID;

    private String general = "general";
    private String sales = "sales";
    private String format_options = "format_options";
    private String sale_channels = "sale_channels";
    private String payment = "payment";
    private String shipping = "shipping";
    private String tax = "tax";
    private String androidSender = "androidSender";

    @Override
    public void parse() {
        if (mJSON != null) {
            if (mJSON.has(general)) {
                String generalValue = getData(general);
                if (Utils.validateString(generalValue)) {
                    mGeneral = new GeneralConfigEntity();
                    try {
                        mGeneral.setJSONObject(new JSONObject(generalValue));
                        mGeneral.parse();
                    } catch (JSONException e) {
                        mGeneral = null;
                        Log.e("ConfigEntity_General", e.getMessage());
                    }
                }
            }

            if (mJSON.has(sales)) {
                String salesValue = getData(sales);
                if (Utils.validateString(salesValue)) {
                    mSale = new SaleConfigEntity();
                    try {
                        mSale.setJSONObject(new JSONObject(salesValue));
                        mSale.parse();
                    } catch (JSONException e) {
                        mSale = null;
                        Log.e("ConfigEntity_Sale", e.getMessage());
                    }
                }
            }

            if (mJSON.has(format_options)) {
                String formatValue = getData(format_options);
                if (Utils.validateString(formatValue)) {
                    mFormatOption = new FormatConfigEntity();
                    try {
                        mFormatOption.setJSONObject(new JSONObject(formatValue));
                        mFormatOption.parse();
                    } catch (JSONException e) {
                        mFormatOption = null;
                        Log.e("ConfigEntity_Format", e.getMessage());
                    }
                }
            }

            if (mJSON.has(sale_channels)) {
                String salesChannelValue = getData(sale_channels);
                if (Utils.validateString(salesChannelValue)) {
                    mSaleChannel = new SaleChannelConfigEntity();
                    try {
                        mSaleChannel.setJSONObject(new JSONObject(salesChannelValue));
                        mSaleChannel.parse();
                    } catch (JSONException e) {
                        mSaleChannel = null;
                        Log.e("ConfigEntity_SalesCN", e.getMessage());
                    }
                }
            }

            if (mJSON.has(payment)) {
                String paymentValue = getData(payment);
                if (Utils.validateString(paymentValue)) {
                    mPayment = new PaymentConfigEntity();
                    try {
                        mPayment.setJSONObject(new JSONObject(paymentValue));
                    } catch (JSONException e) {
                        mPayment = null;
                        Log.e("ConfigEntity_Payment", e.getMessage());
                    }
                }
            }

            if (mJSON.has(shipping)) {
                String shippingValue = getData(shipping);
                if (Utils.validateString(shippingValue)) {
                    mShipping = new ShippingConfigEntity();
                    try {
                        mShipping.setJSONObject(new JSONObject(shippingValue));
                    } catch (JSONException e) {
                        mShipping = null;
                        Log.e("ConfigEntity_Shpping", e.getMessage());
                    }
                }
            }


            if (mJSON.has(tax)) {
                try {
                    JSONObject json = mJSON.getJSONObject(tax);
                    mTax = new TaxConfigEntity();
                    mTax.setJSONObject(json);
                    mTax.parse();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(mJSON.has(androidSender)) {
                mSenderID = getData(androidSender);
            }

        }
    }

    public GeneralConfigEntity getGeneral() {
        return mGeneral;
    }

    public void setGeneral(GeneralConfigEntity mGeneral) {
        this.mGeneral = mGeneral;
    }

    public SaleConfigEntity getSale() {
        return mSale;
    }

    public void setSale(SaleConfigEntity mSale) {
        this.mSale = mSale;
    }

    public FormatConfigEntity getFormatOption() {
        return mFormatOption;
    }

    public void setFormatOption(FormatConfigEntity mFormatOption) {
        this.mFormatOption = mFormatOption;
    }

    public SaleChannelConfigEntity getSaleChannel() {
        return mSaleChannel;
    }

    public void setSaleChannel(SaleChannelConfigEntity mSaleChannel) {
        this.mSaleChannel = mSaleChannel;
    }

    public PaymentConfigEntity getPayment() {
        return mPayment;
    }

    public void setPayment(PaymentConfigEntity mPayment) {
        this.mPayment = mPayment;
    }

    public ShippingConfigEntity getShipping() {
        return mShipping;
    }

    public void setShipping(ShippingConfigEntity mShipping) {
        this.mShipping = mShipping;
    }

    public TaxConfigEntity getTaxConfig() {
        return mTax;
    }

    public void setTaxConfig(TaxConfigEntity tax) {
        mTax = tax;
    }

    public String getmSenderID() {
        return mSenderID;
    }

    public void setmSenderID(String mSenderID) {
        this.mSenderID = mSenderID;
    }
}
