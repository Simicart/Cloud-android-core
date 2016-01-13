package com.simicart.core.splashscreen.controller;

import android.content.Context;
import android.util.Log;

import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.ReadXMLLanguage;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.event.base.EventListener;
import com.simicart.core.event.base.ReadXML;
import com.simicart.core.splashscreen.delegate.SplashDelegate;
import com.simicart.core.splashscreen.entity.AppConfigEnitity;
import com.simicart.core.splashscreen.entity.BaseCurrencyEntity;
import com.simicart.core.splashscreen.entity.CMSPageEntity;
import com.simicart.core.splashscreen.entity.ConfigEntity;
import com.simicart.core.splashscreen.entity.CurrencyEntity;
import com.simicart.core.splashscreen.entity.FormatConfigEntity;
import com.simicart.core.splashscreen.entity.ThemeConfigEntity;
import com.simicart.core.splashscreen.model.AppConfigModel;
import com.simicart.core.splashscreen.model.CMSPagesModel;
import com.simicart.core.splashscreen.model.SettingModel;
import com.simicart.core.store.entity.Stores;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashController {

    protected SplashDelegate mDelegate;
    protected Context mContext;

    private String MATRIX_THEME = "matrix";
    private String ZARA_THEME = "zara";

    public SplashController(SplashDelegate delegate, Context context) {
        mDelegate = delegate;
        mContext = context;
    }

    public void create() {
        initCommon();
        getPlugin();
        getAppConfig();
        getCMSPage();
    }


    private void initCommon() {
        DataLocal.init(mContext);
//        Config.getInstance().setBaseUrl();
        EventListener.setEvent("simi_developer");
    }

    private void getAppConfig() {
        AppConfigModel appConfigModel = new AppConfigModel();
        appConfigModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
                getSetting();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                getSetting();
                if (collection != null) {
                    if (collection.getCollection().size() > 0) {
                        ArrayList<SimiEntity> entity = collection.getCollection();
                        if (null != entity && entity.size() > 0) {
                            ArrayList<AppConfigEnitity> appConfigArr = new ArrayList<AppConfigEnitity>();
                            for (SimiEntity simiEntity : entity) {
                                AppConfigEnitity appConfigEnitity = (AppConfigEnitity) simiEntity;
                                appConfigArr.add(appConfigEnitity);
                            }

                            if (appConfigArr.size() > 0) {
                                ThemeConfigEntity themeConfig = appConfigArr.get(0).getThemeConfig();
                                Log.e("SpashController", "Layout:"+appConfigArr.get(0).getLayout());
                                Config.getInstance().setConfigTheme(appConfigArr.get(0).getLayout());
                                Config.getInstance().setKey_color(themeConfig.getKeyColor());
                                Config.getInstance().setTop_menu_icon_color(themeConfig.getTopMenuIconColor());
                                Config.getInstance().setButton_background(themeConfig.getButtonBackground());
                                Config.getInstance().setButton_text_color(themeConfig.getButtonTextColor());
                                Config.getInstance().setMenu_background(themeConfig.getMenuBackground());
                                Config.getInstance().setMenu_text_color(themeConfig.getMenuTextColor());
                                Config.getInstance().setMenu_line_color(themeConfig.getMenuLineColor());
                                Config.getInstance().setMenu_icon_color(themeConfig.getMenuIconColor());
                                Config.getInstance().setApp_backrground(themeConfig.getAppBackground());
                                Config.getInstance().setContent_color(themeConfig.getContentColor());
                                Config.getInstance().setLine_color(themeConfig.getLineColor());
                                Config.getInstance().setImage_boder_color(themeConfig.getImageBorderColor());
                                Config.getInstance().setIcon_color(themeConfig.getIconColor());
                                Config.getInstance().setSection_color(themeConfig.getSectionColor());
                                Config.getInstance().setPrice_color(themeConfig.getPriceColor());
                                Config.getInstance().setSpecial_price_color(themeConfig.getSpecialPriceColor());
                                Config.getInstance().setSearch_box_background(themeConfig.getSearchBoxBackground());
                                Config.getInstance().setSearch_text_color(themeConfig.getSearchTextColor());
                                if (Utils.validateString(appConfigArr.get(0).getLocale())) {
                                    Config.getInstance().setLocale_identifier(appConfigArr.get(0).getLocale());
                                }
                                getThemeAndPaypal();
//                                Config.getInstance().setOut_stock_background(
//                                        js_theme_config.getString("out_stock_background"));
//                                Config.getInstance().setOut_stock_text(
//                                        js_theme_config.getString("out_stock_text"));
//                                Config.getInstance().setSearch_icon_color(
//                                        js_theme_config.getString("search_icon_color"));
                            }
                        }
                    }
                }
            }
        });

        appConfigModel.request();
    }

    private void getSetting() {
        SettingModel model = new SettingModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                SimiEntity entity = collection.getCollection().get(0);
                try {
                    DataLocal.mConfig = (ConfigEntity) entity;
                    setConfig(entity);
                    preProcess();
                    mDelegate.creatMain();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        model.request();
    }

    private void getCMSPage() {
        CMSPagesModel model = new CMSPagesModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                SimiEntity entity = collection.getCollection().get(0);

                DataLocal.listCms = ((CMSPageEntity) entity).getPage();
            }
        });

        model.request();
    }

    private void getPlugin() {
        ReadXML readXml = new ReadXML(mContext);
        readXml.read();
    }

    private void getThemeAndPaypal(){
        if(Config.getInstance().getConfigTheme().toLowerCase().equals(MATRIX_THEME)){
            EventListener.setEvent("simi_themeone");
        }
        if(Config.getInstance().getConfigTheme().toLowerCase().equals(ZARA_THEME)){
            EventListener.setEvent("simi_ztheme");
        }
        EventListener.setEvent("simi_paypalmobile");
        EventListener.setEvent("simi_fblogin");
    }

    private void setConfig(SimiEntity entity) throws JSONException {
        ConfigEntity configEntity = (ConfigEntity) entity;
        Config.getInstance().setCurrency_code(configEntity.getFormatOption().getCurrency().getBaseCurrency().getCode());
        Config.getInstance().setStore_name(configEntity.getGeneral().getStoreName());
        Config.getInstance().setCurrency_symbol(configEntity.getFormatOption().getCurrency().getBaseCurrency().getSymbol());
        Config.getInstance().setCurrencyPosition(configEntity.getFormatOption().getCurrency().getCurrencyPosition());
    }

    protected void preProcess() {
        ConfigEntity configEntity = DataLocal.mConfig;
        FormatConfigEntity formatEntity = configEntity.getFormatOption();
        CurrencyEntity currency = formatEntity.getCurrency();
        String numberOfDecimal = currency.getNumberOfDecimals();
        int numberDecimal = Integer.parseInt(numberOfDecimal);

        Config.getInstance().setNumberDecimal(numberDecimal);

        String charSepDecimal = currency.getDecimalSeparator();

        Config.getInstance().setCharSepDecimal(charSepDecimal);


        String charSepThousand = currency.getThousandSeparator();

        Config.getInstance().setCharSepThousand(charSepThousand);

        boolean isLeft = false;


        String currencyPosition = currency.getCurrencyPosition();
        if (Utils.validateString(currencyPosition) && currencyPosition.toUpperCase().equals("left")) {
            isLeft = true;
        }

        Config.getInstance().setisLeft(isLeft);

        BaseCurrencyEntity baseCurrency = currency.getBaseCurrency();
        String symbol = baseCurrency.getSymbol();
        Config.getInstance().setSymbol(symbol);

        Config.getInstance().setSenderId(configEntity.getmSenderID());
    }

    private void changeBaseUrl() {
        for (Stores store : DataLocal.listStores) {
            if (store.getStoreID().equals(
                    "" + Config.getInstance().getStore_id())) {
                int leg = Config.getInstance().getBaseUrl().split("/").length;
                String last = Config.getInstance().getBaseUrl().split("/")[leg - 1];
                Config.getInstance().setBase_url(
                        Config.getInstance().getBaseUrl()
                                .replace(last, store.getStoreCode()));
            }
        }
    }

    private void createLanguage() {
        try {
            ReadXMLLanguage readlanguage = new ReadXMLLanguage(mContext);
            readlanguage.parseXML(Config.getInstance().getLocale_identifier()
                    + "/localize.xml");
            Config.getInstance().setLanguages(readlanguage.getLanguages());
        } catch (Exception e) {
            Map<String, String> languages = new HashMap<String, String>();
            Config.getInstance().setLanguages(languages);
        }
    }

}
