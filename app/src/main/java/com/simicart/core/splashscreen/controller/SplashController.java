package com.simicart.core.splashscreen.controller;

import android.content.Context;

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
        EventListener.setEvent("simi_developer");
    }

    private void getPlugin() {
        ReadXML readXml = new ReadXML(mContext);
        readXml.read();


    }

    private void getAvaiablePlugin()
    {
        // http://dev-api.jajahub.com/rest/site-plugins
        // lay ve danh sach id cua plugin enable
    }

    private void getPublicPlugin(String ids)
    {
        // http://dev-api.jajahub.com/rest/public_plugins
        // lay sku cua cac plugin enbale
    }



    private void getAppConfig() {
        AppConfigModel appConfigModel = new AppConfigModel();
        appConfigModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
                getSetting();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                getSetting();
                if (collection != null) {
                    ArrayList<SimiEntity> entities = collection.getCollection();
                    if (null != entities && entities.size() > 0) {
                        AppConfigEnitity appConfig = (AppConfigEnitity) entities.get(0);
                        Config.getInstance().parseAppConfig(appConfig);
                        getThemeAndPaypal();
                    }
                }
            }
        });

        appConfigModel.request();
    }


    private void getCMSPage() {
        CMSPagesModel model = new CMSPagesModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
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

    private void getSetting() {
        SettingModel model = new SettingModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                SimiEntity entity = collection.getCollection().get(0);
                ConfigEntity configEntity = (ConfigEntity) entity;
                Config.getInstance().parseConfigSetting(configEntity);
                mDelegate.creatMain();
            }
        });

        model.request();
    }


    private void getThemeAndPaypal() {

        String config_theme = Config.getInstance().getConfigTheme().toLowerCase();

        if (config_theme.equals(MATRIX_THEME)) {
            EventListener.setEvent("simi_themeone");
        } else if (config_theme.equals(ZARA_THEME)) {
            EventListener.setEvent("simi_ztheme");
        }
        EventListener.setEvent("simi_paypalmobile");
        EventListener.setEvent("simi_fblogin");
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
