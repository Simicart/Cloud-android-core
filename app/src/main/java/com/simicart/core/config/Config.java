package com.simicart.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.simicart.R;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.common.Utils;
import com.simicart.core.splashscreen.entity.AppConfigEnitity;
import com.simicart.core.splashscreen.entity.BaseCurrencyEntity;
import com.simicart.core.splashscreen.entity.ConfigEntity;
import com.simicart.core.splashscreen.entity.CurrencyEntity;
import com.simicart.core.splashscreen.entity.FormatConfigEntity;
import com.simicart.core.splashscreen.entity.GeneralConfigEntity;
import com.simicart.core.splashscreen.entity.LocaleConfigEntity;
import com.simicart.core.splashscreen.entity.TaxConfigEntity;
import com.simicart.core.splashscreen.entity.ThemeConfigEntity;

import org.json.JSONObject;

@SuppressLint("DefaultLocale")
public class Config {

//    private String mBaseUrl = "BASE_URL";
//    private String mSecretKey = "SECRET_KEY";
//    private String mDemoEnable = "DEMO_ENABLE";
//    private String mColorSplashScreen = "SCREEN_COLOR";


    private String mBaseUrl = "https://api.jajahub.com/rest/";
    // Android account
    private String mSecretKey = "6cb39477c46ed96c2671df7222aeaf3a28e2b861";

    private String mDemoEnable = "DEMO_ENABLE11";
    private String mColorSplashScreen = "#FFFFFF";
    // Zara account
//    private String mSecretKey = "5ed6a808a6142d936196cb4f69a781f7af02fb3a";
    // key site live : c843176d88b6e9a21f848482044503ea7ae82e85, api.jajahub.com/rest/
    // key zara: 5ed6a808a6142d936196cb4f69a781f7af02fb3a, dev-api.jajahub.com/rest/
    // key android: 096a00bc5958983da94aebebfb78e92fd5cbbaf1
    // key multi language: b80b768dbb68a2c039ea05b63d899ae344018301
    private String key_color = "#3498DB";
    private String top_menu_icon_color = "#FFFFFF";
    private String button_background = "#0277BD";
    private String button_text_color = "#FFFFFF";
    private String menu_background = "#F21b1b1b";
    private String menu_text_color = "#ECEFF1";
    private String menu_line_color = "#444444";
    private String menu_icon_color = "#ECEFF1";
    private String app_backrground = "#FFFFFF";
    private String content_color = "#000000";
    private String line_color = "#CACACA";
    private String image_boder_color = "#d3d3d3";
    private String icon_color = "#000000";
    private String section_color = "#E0E0E0";
    private String section_text_color = "#000000";
    private String price_color = "#BF360C";
    private String special_price_color = "#BF360C";
    private String search_box_background = "#E6E0E0E0";
    private String out_stock_background = "#FF9800";
    private String out_stock_text = "#FFFFFF";
    private String search_text_color = "#8b8b8b";
    private String search_icon_color = "#8b8b8b";
    private String config_theme = "default";
    private String mCurrencyCode;

    private String mSenderID = "";

    private String mFontCustom = "fonts/ProximaNovaLight.ttf";
    private String mDefaulList = "0";
    private String mStoreName;
    private String mLocaleIdentifier;
    private String mCookie = "";
    private int mGuestCheckout = 1;
    private int mEnableAgreements = 0;
    private boolean isShowZeroPrice = true;
    private boolean isShowLinkAllProduct = false;
    private boolean isReloadPaymentMethod = false;
    private String mCurrencyPosition = "before";
    private Map<String, String> mLanguages;
    private String quoteCustomerSignIn = "";
    private int numberDecimal;
    private String charSepDecimal;
    private String charSepThousand;
    private String symbol = "";
    private boolean isLeft;
    private boolean hasSpace;
    private int mTheme = 0; // 0 : default, 1 : matrix , 2 ztheme
    private String mDefaultCountryName;
    private String mDefaultCountryCode;
    private String mDefaultStateName;
    private String mDefaultStateCode;

    private static Config instance;


    private Config() {
        mLanguages = new HashMap<String, String>();
    }

    public static Config getInstance() {
        if (null == instance) {
            instance = new Config();
        }

        return instance;
    }

    public String getDefaultCountryName() {
        return mDefaultCountryName;
    }

    public void setDefaultCountryName(String mDefaultCountryName) {
        this.mDefaultCountryName = mDefaultCountryName;
    }

    public String getDefaultCountryCode() {
        return mDefaultCountryCode;
    }

    public void setDefaultCountryCode(String mDefaultCountryCode) {
        this.mDefaultCountryCode = mDefaultCountryCode;
    }

    public String getDefaultStateName() {
        return mDefaultStateName;
    }

    public void setDefaultStateName(String mDefaultStateName) {
        this.mDefaultStateName = mDefaultStateName;
    }

    public String getDefaultStateCode() {
        return mDefaultStateCode;
    }

    public void setDefaultStateCode(String mDefaultStateCode) {
        this.mDefaultStateCode = mDefaultStateCode;
    }

    public void setNumberDecimal(int number) {
        numberDecimal = number;
    }

    public int getNumberDecimal() {
        return numberDecimal;
    }

    public void setCharSepDecimal(String character) {
        charSepDecimal = character;
    }

    public String getCharSepDecimal() {
        return charSepDecimal;
    }

    public void setCharSepThousand(String character) {
        charSepThousand = character;
    }

    public String getCharSepThousand() {
        return charSepThousand;
    }

    public void setSymbol(String sbol) {
        symbol = sbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setisLeft(boolean left) {
        isLeft = left;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setHasSpace(boolean has_space) {
        hasSpace = has_space;
    }

    public boolean hasSpace() {
        return hasSpace;
    }

    public String getPrice(double price) {
        String s_price = Utils.formatPrice(price, numberDecimal, charSepDecimal, charSepThousand);
        StringBuilder builder = new StringBuilder();
        if (isLeft) {
            builder.append(symbol);
            if (hasSpace) {
                builder.append(" ");
            }
            builder.append(s_price);
        } else {
            builder.append(s_price);
            if (hasSpace) {
                builder.append(" ");
            }
            builder.append(symbol);
        }
        return builder.toString();
    }


    public int getTop_menu_icon_color() {
        return Color.parseColor(top_menu_icon_color);
    }

    public void setTop_menu_icon_color(String top_menu_icon_color) {
        this.top_menu_icon_color = top_menu_icon_color;
    }

    public int getSection_text_color() {
        return Color.parseColor(section_text_color);
    }

    public void setSection_text_color(String section_text_color) {
        this.section_text_color = section_text_color;
    }

    public void setConfigTheme(String config_theme) {
        this.config_theme = config_theme;
    }

    public String getConfigTheme() {
        return config_theme;
    }

    public int getKey_color() {
        return Color.parseColor(key_color);
    }

    public void setKey_color(String key_color) {
        this.key_color = key_color;
    }

    public int getButton_background() {
        return Color.parseColor(button_background);
    }

    public void setButton_background(String button_background) {
        this.button_background = button_background;
    }

    public int getButton_text_color() {
        return Color.parseColor(button_text_color);
    }

    public void setButton_text_color(String button_text_color) {
        this.button_text_color = button_text_color;
    }

    public int getMenu_background() {
        return Color.parseColor(menu_background);
    }

    public void setMenu_background(String menu_background) {
        this.menu_background = menu_background;
    }

    public int getMenu_text_color() {
        return Color.parseColor(menu_text_color);
    }

    public void setMenu_text_color(String menu_text_color) {
        this.menu_text_color = menu_text_color;
    }

    public int getMenu_line_color() {
        return Color.parseColor(menu_line_color);
    }

    public void setMenu_line_color(String menu_line_color) {
        this.menu_line_color = menu_line_color;
    }

    public int getMenu_icon_color() {
        return Color.parseColor(menu_icon_color);
    }

    public void setMenu_icon_color(String menu_icon_color) {
        this.menu_icon_color = menu_icon_color;
    }

    public int getApp_backrground() {
        return Color.parseColor(app_backrground);
    }

    public void setApp_backrground(String app_backrground) {
        this.app_backrground = app_backrground;
    }

    public int getContent_color() {
        return Color.parseColor(content_color);
    }

    public int getHintContent_color() {
        String color = content_color.replace("#", "#7D");
        return Color.parseColor(color);
    }

    public String getContent_color_string() {
        return content_color;
    }

    public void setContent_color(String content_color) {
        this.content_color = content_color;
    }

    public int getLine_color() {
        return Color.parseColor(line_color);
    }

    public void setLine_color(String line_color) {
        this.line_color = line_color;
    }

    public int getImage_boder_color() {
        return Color.parseColor(image_boder_color);
    }

    public void setImage_boder_color(String image_boder_color) {
        this.image_boder_color = image_boder_color;
    }

    public String getIcon_color() {
        return icon_color;
    }

    public void setIcon_color(String icon_color) {
        this.icon_color = icon_color;
    }

    public String getSection_color() {
        return section_color;
    }

    public void setSection_color(String section_color) {
        this.section_color = section_color;
    }

    public String getPrice_color() {
        return price_color;
    }

    public void setPrice_color(String price_color) {
        this.price_color = price_color;
    }

    public String getSpecial_price_color() {
        return special_price_color;
    }

    public void setSpecial_price_color(String special_price_color) {
        this.special_price_color = special_price_color;
    }

    public int getSearch_box_background() {
        return Color.parseColor(search_box_background);
    }

    public void setSearch_box_background(String search_box_background) {
        this.search_box_background = search_box_background;
    }

    public int getOut_stock_background() {
        return Color.parseColor(out_stock_background);
    }

    public void setOut_stock_background(String out_stock_background) {
        this.out_stock_background = out_stock_background;
    }

    public int getOut_stock_text() {
        return Color.parseColor(out_stock_text);
    }

    public void setOut_stock_text(String out_stock_text) {
        this.out_stock_text = out_stock_text;
    }

    public int getSearch_text_color() {
        return Color.parseColor(search_text_color);
    }

    public void setSearch_text_color(String search_text_color) {
        this.search_text_color = search_text_color;
    }

    public int getSearch_icon_color() {
        return Color.parseColor(search_text_color);
    }

    public void setSearch_icon_color(String search_icon_color) {
        this.search_icon_color = search_icon_color;
    }

    public String getDefaultList() {
        return mDefaulList;
    }

    public void setDefaultList(String mDefaulList) {
        this.mDefaulList = mDefaulList;
    }

    public String getDemoEnable() {
        return mDemoEnable;
    }

    public void setDemoEnable(String demo_enable) {
        mDemoEnable = demo_enable;
    }

    public int getTheme() {
        return mTheme;
    }

    public void setTheme(int mTheme) {
        this.mTheme = mTheme;
    }

    public boolean isShow_link_all_product() {
        return isShowLinkAllProduct;
    }

    public void setShow_link_all_product(boolean show_link_all_product) {
        isShowLinkAllProduct = show_link_all_product;
    }

    public boolean isReload_payment_method() {
        return isReloadPaymentMethod;
    }

    public void setReload_payment_method(boolean isReloadPaymentMethod) {
        this.isReloadPaymentMethod = isReloadPaymentMethod;
    }

    public boolean isShow_zero_price() {
        return isShowZeroPrice;
    }

    public void setIs_show_zero_price(boolean show_zero_price) {
        isShowZeroPrice = show_zero_price;
    }

    public String getSecretKey() {
        return mSecretKey;
    }

    public void setSecretKey(String secret_key) {
        mSecretKey = secret_key;
    }


    public void setLanguages(Map<String, String> languages) {
        try {
            mLanguages = new HashMap<String, String>();
            mLanguages = languages;
        } catch (Exception e) {

        }
    }

    public void clearLanguages() {
        if (mLanguages != null) {
            mLanguages.clear();
        }
    }

    public String getSenderId() {
        return mSenderID;
    }

    public void setSenderId(String sender_id) {
        mSenderID = sender_id;
    }

    public void setStore_name(String store_name) {
        mStoreName = store_name;
    }

    public String getLocale_identifier() {
        return mLocaleIdentifier;
    }

    public void setLocale_identifier(String locale_identifier) {
        mLocaleIdentifier = locale_identifier;
    }

    public int getGuest_checkout() {
        return mGuestCheckout;
    }

    public void setGuest_checkout(int guest_checkout) {
        mGuestCheckout = guest_checkout;
    }

    public int getEnable_agreements() {
        return mEnableAgreements;
    }

    public void setEnable_agreements(int enable_agreements) {
        mEnableAgreements = enable_agreements;
    }

    public String getCurrency_code() {
        return mCurrencyCode;
    }

    public void setCurrency_code(String currency_code) {
        mCurrencyCode = currency_code;
    }

    public String getConnectorUrl() {
        return mBaseUrl + "connector/";
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBase_url(String base_url) {
        mBaseUrl = base_url;
    }


    public int getColorSplash() {
        return Color.parseColor(mColorSplashScreen);
    }


    public String getFontCustom() {
        return mFontCustom;
    }

    public void setFontCustom(String mFontCustom) {
        this.mFontCustom = mFontCustom;
    }

    public String getText(String language) {
        if (language == null) {
            return null;
        }
        String translater = language;
        if (!mLanguages.isEmpty()) {
            if (mLanguages.get(language.toLowerCase().trim()) != null) {
                translater = mLanguages.get(language.toLowerCase().trim());
                return translater;
            }
        }
        return translater;
    }


    public String getCurrencyPosition() {
        return mCurrencyPosition;
    }

    public void setCurrencyPosition(String mCurrencyPosition) {
        this.mCurrencyPosition = mCurrencyPosition;
    }

    public String getCookie() {
        return mCookie;
    }

    public void setCookie(String _cookie) {
        this.mCookie = _cookie;
    }

    public String getQuoteCustomerSignIn() {
        return quoteCustomerSignIn;
    }

    public void setQuoteCustomerSignIn(String quoteCustomerSignIn) {
        this.quoteCustomerSignIn = quoteCustomerSignIn;
    }

    private boolean isTaxShop;
    private boolean isTaxCart;

    public void setTaxShop(boolean is_tax_shop) {
        isTaxShop = is_tax_shop;
    }

    public boolean isTaxShop() {
        return isTaxShop;
    }

    public void setTaxCart(boolean is_tax_cart) {
        isTaxCart = is_tax_cart;
    }

    public boolean isTaxCart() {
        return isTaxCart;
    }

    public boolean isRTLLanguage(String locale) {
        String[] rtl = SimiManager.getIntance().getCurrentContext().getResources().getStringArray(R.array.rtl);
        for (int i = 0; i < rtl.length; i++) {
            if (locale.equals(rtl[i]))
                return true;
        }
        return false;
    }

    public void parseConfigSetting(ConfigEntity configEntity) {
        FormatConfigEntity formatEntity = configEntity.getFormatOption();
        CurrencyEntity currency = formatEntity.getCurrency();


        instance.setCurrency_code(currency.getBaseCurrency().getCode());
        instance.setStore_name(configEntity.getGeneral().getStoreName());
        instance.setCurrencyPosition(currency.getCurrencyPosition());


        String numberOfDecimal = currency.getNumberOfDecimals();
        int numberDecimal = Integer.parseInt(numberOfDecimal);
        instance.setNumberDecimal(numberDecimal);

        String charSepDecimal = currency.getDecimalSeparator();
        instance.setCharSepDecimal(charSepDecimal);


        String charSepThousand = currency.getThousandSeparator();
        instance.setCharSepThousand(charSepThousand);

        boolean isLeft = false;
        boolean has_space = false;
        String currencyPosition = currency.getCurrencyPosition();
        if (Utils.validateString(currencyPosition)) {
            currencyPosition = currencyPosition.toLowerCase();
            if (currencyPosition.contains("left")) {
                isLeft = true;
            }

            if (currencyPosition.contains("space")) {
                has_space = true;
            }


        }
        instance.setisLeft(isLeft);
        instance.setHasSpace(has_space);

        BaseCurrencyEntity baseCurrency = currency.getBaseCurrency();
        String symbol = baseCurrency.getSymbol();
        instance.setSymbol(symbol);

        instance.setSenderId(configEntity.getmSenderID());

        TaxConfigEntity taxConfig = configEntity.getTaxConfig();
        if (null != taxConfig) {
            instance.setTaxShop(taxConfig.isTaxShop());
            instance.setTaxCart(taxConfig.isTaxCart());
        }

        GeneralConfigEntity general = configEntity.getGeneral();

        JSONObject js_general = general.getJSONObject();
        if (js_general.has("country")) {
            try {
                JSONObject js_country = js_general.getJSONObject("country");
                if (js_country.has("code")) {
                    mDefaultCountryCode = js_country.getString("code");
                }

                if (js_country.has("name")) {
                    mDefaultCountryName = js_country.getString("name");
                }
            } catch (Exception e) {

            }
        }

        if (js_general.has("state")) {
            try {
                JSONObject js_state = js_general.getJSONObject("state");
                if (js_state.has("code")) {
                    mDefaultStateCode = js_state.getString("code");
                }

                if (js_state.has("name")) {
                    mDefaultStateName = js_state.getString("name");
                }
            } catch (Exception e) {

            }
        }

        ArrayList<LocaleConfigEntity> listLocale = general.getLocaleApp();
        if (listLocale != null && listLocale.size() > 0) {
            DataLocal.listLocale = listLocale;
            String preLocale = DataLocal.getLocale();
            if (preLocale.equals("")) {
                String locale = listLocale.get(0).getCode();
                if (Utils.validateString(locale)) {
                    instance.setLocale_identifier(locale);
                    DataLocal.saveLocale(locale);
                    if (isRTLLanguage(locale) == true) {
                        DataLocal.isLanguageRTL = true;
                    }
                }
            } else {
                boolean checkLocale = false;
                for (int i = 0; i < listLocale.size(); i++) {
                    if (listLocale.get(i).getCode().equals(preLocale)) {
                        checkLocale = true;
                    }
                }
                if (checkLocale) {
                    instance.setLocale_identifier(preLocale);
                    if (isRTLLanguage(preLocale) == true) {
                        DataLocal.isLanguageRTL = true;
                    }
                } else {
                    String locale = listLocale.get(0).getCode();
                    if (Utils.validateString(locale)) {
                        instance.setLocale_identifier(locale);
                        if (isRTLLanguage(locale) == true) {
                            DataLocal.isLanguageRTL = true;
                        }
                    }
                }
            }
        }
    }

    public void parseAppConfig(AppConfigEnitity appConfig) {
        instance.setConfigTheme(appConfig.getLayout());
        ThemeConfigEntity themeConfig = appConfig.getThemeConfig();
        parseThemeConfig(themeConfig);
    }


    public void parseThemeConfig(ThemeConfigEntity themeConfig) {
        instance.setKey_color(themeConfig.getKeyColor());
        instance.setTop_menu_icon_color(themeConfig.getTopMenuIconColor());
        instance.setButton_background(themeConfig.getButtonBackground());
        instance.setButton_text_color(themeConfig.getButtonTextColor());
        instance.setMenu_background(themeConfig.getMenuBackground());
        instance.setMenu_text_color(themeConfig.getMenuTextColor());
        instance.setMenu_line_color(themeConfig.getMenuLineColor());
        instance.setMenu_icon_color(themeConfig.getMenuIconColor());
        instance.setApp_backrground(themeConfig.getAppBackground());
        instance.setContent_color(themeConfig.getContentColor());
        instance.setLine_color(themeConfig.getLineColor());
        instance.setImage_boder_color(themeConfig.getImageBorderColor());
        instance.setIcon_color(themeConfig.getIconColor());
        instance.setSection_color(themeConfig.getSectionColor());
        instance.setPrice_color(themeConfig.getPriceColor());
        instance.setSpecial_price_color(themeConfig.getSpecialPriceColor());
        instance.setSearch_box_background(themeConfig.getSearchBoxBackground());
        instance.setSearch_text_color(themeConfig.getSearchTextColor());
    }

    private void selectDefaultLanguage() {

    }

}
