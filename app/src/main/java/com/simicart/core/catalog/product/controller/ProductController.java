package com.simicart.core.catalog.product.controller;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.delegate.OptionProductDelegate;
import com.simicart.core.catalog.product.delegate.ProductDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.model.AddToCartModel;
import com.simicart.core.catalog.product.model.CreateQuoteModel;
import com.simicart.core.catalog.product.model.ProductModel;
import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.model.UpdateQuoteModel;
import com.simicart.core.catalog.product.options.ManageOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupEntity;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.common.price.ProductDetailPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.splashscreen.entity.BaseCurrencyEntity;
import com.simicart.core.splashscreen.entity.ConfigEntity;
import com.simicart.core.splashscreen.entity.CurrencyEntity;
import com.simicart.core.splashscreen.entity.FormatConfigEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
public class ProductController extends SimiController implements OptionProductDelegate {
    protected ProductDelegate mDelegate;
    protected String mID;
    protected ArrayList<CustomOptionView> mOptionView;
    protected OnTouchListener mListenerAddToCart;
    protected VariantEntity mVariantEntity;
    protected ManageOptionView mManageOptionView;
    protected ProductDetailPriceView priceView;
    protected ProductEntity mProduct;


    public ProductController() {
        mOptionView = new ArrayList<CustomOptionView>();
    }

    public void setDelegate(ProductDelegate delegate) {
        mDelegate = delegate;
    }

    public void setProductId(String id) {
        mID = id;
    }

    public OnTouchListener getListenerAddToCart() {
        return mListenerAddToCart;
    }


    @Override
    public void onStart() {
        mDelegate.showLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        };
        mModel = new ProductModel();
        mModel.addDataBody("product_id", mID);
        mModel.setDelegate(delegate);
        mModel.request();

        mListenerAddToCart = new OnTouchListener() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        GradientDrawable gdDefault = new GradientDrawable();
                        gdDefault.setColor(Color.GRAY);
                        gdDefault.setCornerRadius(15);
                        v.setBackgroundDrawable(gdDefault);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        addtoCart();
                    }
                    case MotionEvent.ACTION_CANCEL: {

                        GradientDrawable gdDefault = new GradientDrawable();
                        gdDefault.setColor(Config.getInstance().getColorMain());
                        gdDefault.setCornerRadius(15);
                        v.setBackgroundDrawable(gdDefault);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        };

    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
        onUpdatePriceView();
        onUpdateOptionView();
    }

    protected void onUpdateOptionView() {
        View view = onShowOptionView();
        if (null != view) {
            mDelegate.onUpdateOptionView(view);
        }
    }

    protected void onUpdatePriceView() {
        ProductEntity product = getProductFromCollection();
        if (null != product) {
            View view = onShowPriceView(product);
            if (null != view) {
                mDelegate.onUpdatePriceView(view);
            }
        }
    }

    protected View onShowOptionView() {
        ProductEntity productEntity = getProductFromCollection();
        if (null != productEntity) {
            mManageOptionView = new ManageOptionView(productEntity);
            mManageOptionView.setDelegate(this);
            return mManageOptionView.createOptionView();
        }
        return null;
    }

    protected ProductEntity getProductFromCollection() {
        ProductEntity product = null;
        ArrayList<SimiEntity> entity = mModel.getCollection().getCollection();
        if (null != entity && entity.size() > 0) {
            product = (ProductEntity) entity.get(0);
        }
        return product;
    }

    protected View onShowPriceView(ProductEntity product) {
        priceView = new ProductDetailPriceView(product);
        return priceView.createView();
    }

    protected void addtoCart() {
        if (null != mManageOptionView && mManageOptionView.isComplete()) {
            mDelegate.showDialogLoading();
            JSONObject json = mManageOptionView.getDataForCheckout();
            if (DataLocal.isSignInComplete()) {
                String quoteSignIn = Config.getInstance().getQuoteCustomerSignIn();
                if (Utils.validateString(quoteSignIn)) {
                    addToCartWithQuote(quoteSignIn, json);
                } else {
                    createNewQuote(json);

                }
            } else {
                String quoteNotSignIn = DataLocal.getQuoteCustomerNotSigin();
                if (Utils.validateString(quoteNotSignIn)) {
                    addToCartWithQuote(quoteNotSignIn, json);
                } else {
                    createNewQuote(json);
                }
            }
        } else {
            if (null == mManageOptionView) {
                mManageOptionView = new ManageOptionView(mProduct);
            }
            //onShowOptionView();
            SimiManager.getIntance().showNotify("Please select all options");
        }
    }

    protected void createNewQuote(final JSONObject json) {
        CreateQuoteModel createQuoteModel = new CreateQuoteModel();
        createQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection.getCollection().size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                    String quote = quoteEntity.getID();
                    if (DataLocal.isSignInComplete()) {
                        Config.getInstance().setQuoteCustomerSignIn(quote);
                        DataLocal.saveQuoteCustomerNotSignIn("");
                        updateQuoteForCustomer(quote, json);
                    } else {
                        Config.getInstance().setQuoteCustomerSignIn("");
                        DataLocal.saveQuoteCustomerNotSignIn(quote);
                        addToCartWithQuote(quote, json);
                    }

                }
            }
        });

        Calendar today = Calendar.getInstance();
        String sToday = today.toString();
        String session_id = Utils.md5(sToday);

        String currency_template = getCurrencyTemplate();
        String symbol = Config.getInstance().getmCurrencySymbol();


        createQuoteModel.addDataBody("session_id", session_id);
        createQuoteModel.addDataBody("orig_order_id", "0");
        createQuoteModel.addDataBody("currency_template", currency_template);
        createQuoteModel.addDataBody("quote_currency_code", symbol);
        createQuoteModel.addDataBody("base_currency_code", symbol);
        createQuoteModel.addDataBody("store_currency_code", symbol);

        createQuoteModel.request();
    }

    protected void updateQuoteForCustomer(final String quote_id, final JSONObject json_add) {
        UpdateQuoteModel updateQuote = new UpdateQuoteModel();
        updateQuote.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

                mDelegate.dismissDialogLoading();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                addToCartWithQuote(quote_id, json_add);
            }
        });

        updateQuote.addDataExtendURL(quote_id);

        ProfileEntity customer = DataLocal.mCustomer;
        if (null != customer) {
            JSONObject json = customer.toParam();
            Log.e("Product Controller ", "DATA ---> " + json.toString());
            if (null != json) {
                updateQuote.setJSONBody(json);
            }
        }


        updateQuote.request();


    }

    protected String getCurrencyTemplate() {
        ConfigEntity configEntity = DataLocal.mConfig;
        FormatConfigEntity formatEntity = configEntity.getFormatOption();
        CurrencyEntity currency = formatEntity.getCurrency();
        String numberOfDecimal = currency.getNumberOfDecimals();
        int numberDecimal = Integer.parseInt(numberOfDecimal);

        String charSepDecimal = currency.getDecimalSeparator();
        String charSepThousand = currency.getThousandSeparator();

        String currencyPosition = currency.getCurrencyPosition();
        boolean isLeft = false;
        if (Utils.validateString(currencyPosition) && currencyPosition.toUpperCase().equals("left")) {
            isLeft = true;
        }

        BaseCurrencyEntity baseCurrency = currency.getBaseCurrency();
        String symbol = baseCurrency.getSymbol();

        String s_price = Utils.formatPrice((float) 1000.00, numberDecimal, charSepDecimal, charSepThousand);
        StringBuilder builder = new StringBuilder();
        if (isLeft) {
            builder.append(symbol);
            builder.append(s_price);
        } else {
            builder.append(s_price);
            builder.append(symbol);
        }
        return builder.toString();
    }

    protected void addToCartWithQuote(String quote, JSONObject data) {

        AddToCartModel addToCartModel = new AddToCartModel();
        addToCartModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showToast("Added to cart");

                ArrayList<SimiEntity> array = collection.getCollection();
                if (null != array && array.size() > 0) {
                    QuoteEntity quoteEntity = (QuoteEntity) array.get(0);
                    int cart_qty = quoteEntity.getQty();
                    SimiManager.getIntance().onUpdateCartQty(String.valueOf(cart_qty));
                }

            }
        });
        addToCartModel.addDataExtendURL(quote, "items");
        Log.e("ProductController", "addToCartWithQuote " + data.toString());
        addToCartModel.setJSONBody(data);

        addToCartModel.request();

    }

    @Override
    public void updatePriceWithVariant(VariantEntity variant, boolean isAdd) {

        View view = priceView.updatePriceWithVariant(variant, isAdd);
        if (null != view) {
            mDelegate.onUpdatePriceView(view);
        }
    }

    @Override
    public void updatePriceWithCustomOption(ValueCustomOptionEntity entity, boolean isAdd) {

        View view = priceView.updatePriceWithCustomOption(entity, isAdd);
        if (null != view) {
            mDelegate.onUpdatePriceView(view);
        }
    }

    @Override
    public void updatePriceWithItemGroup(ItemGroupEntity entity, boolean isAdd) {

        View view = priceView.updatePriceWithItemGroup(entity, isAdd);
        if (null != view) {
            Log.e("ProductController ", "updatePriceItemGroup");
            mDelegate.onUpdatePriceView(view);
        }
    }
}
