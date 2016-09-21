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
import com.simicart.core.event.base.ReadXML;
import com.simicart.core.splashscreen.entity.AppConfigEnitity;
import com.simicart.core.splashscreen.entity.CMSPageEntity;
import com.simicart.core.splashscreen.entity.ConfigEntity;
import com.simicart.core.splashscreen.model.AppConfigModel;
import com.simicart.core.splashscreen.model.CMSPagesModel;
import com.simicart.core.splashscreen.model.GetIDPluginsModel;
import com.simicart.core.splashscreen.model.GetSKUPluginModel;
import com.simicart.core.splashscreen.model.SettingModel;
import com.simicart.theme.matrixtheme.MatrixTheme;
import com.simicart.theme.ztheme.ZTheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashController {

    protected Context mContext;

    private String MATRIX_THEME = "matrix";
    private String ZARA_THEME = "zara";

    public SplashController(Context context) {
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
        validateBaseURL();
    }

    private void getPlugin() {
        getAvaiablePlugin();
    }

    private void getAvaiablePlugin() {
        final GetIDPluginsModel idsModel = new GetIDPluginsModel();
        idsModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                ArrayList<String> listSKU = new ArrayList<String>();
                listSKU.add("simi_developer");
                readXMLPlugins(listSKU);
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                String ids = idsModel.getIDs();
                if (Utils.validateString(ids)) {
                    getSKUPlugin(ids);
                } else {
                    ArrayList<String> listSKU = new ArrayList<String>();
                    listSKU.add("simi_developer");
                    readXMLPlugins(listSKU);
                }
            }
        });

        idsModel.request();


    }

    private void getSKUPlugin(String ids) {

        final GetSKUPluginModel skuModel = new GetSKUPluginModel();
        skuModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                ArrayList<String> listSKU = skuModel.getListSKU();
                listSKU.add("simi_developer");
                readXMLPlugins(listSKU);
            }
        });

        skuModel.addDataParameter("ids", ids);
        skuModel.request();


    }

    private void readXMLPlugins(ArrayList<String> listSKU) {

        for(int i = 0; i < listSKU.size(); i++){
            Log.e("SplashController","=================> SKU " + listSKU.get(i));
        }

        ReadXML readXml = new ReadXML(mContext, listSKU);
        readXml.read();
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
                if (collection != null && collection.getCollection().size() > 0) {
                    SimiEntity entity = collection.getCollection().get(0);
                    DataLocal.listCms = ((CMSPageEntity) entity).getPage();
                }
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
                if (collection != null && collection.getCollection().size() > 0) {
                    SimiEntity entity = collection.getCollection().get(0);
                    ConfigEntity configEntity = (ConfigEntity) entity;
                    Config.getInstance().parseConfigSetting(configEntity);
                    createLanguage();
                    SimiManager.getIntance().toMainActivity();
                }
            }
        });

        model.request();
    }


    private void getThemeAndPaypal() {
        String config_theme = Config.getInstance().getConfigTheme().toLowerCase();
        if (Utils.validateString(DataLocal.THEME)) {
            config_theme = DataLocal.THEME;
        }
        DataLocal.THEME = config_theme;
        ArrayList<String> ids = new ArrayList<String>();

        Log.e("SplashController","======================> THEME " + config_theme);

        if (config_theme.equals(MATRIX_THEME)) {
            ids.add("simi_themeone");
        } else if (config_theme.equals(ZARA_THEME)) {
            ids.add("simi_ztheme");
        }
        else{
            new MatrixTheme();
            new ZTheme();
        }
        readXMLPlugins(ids);
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

    private void validateBaseURL() {
        String base_url = Config.getInstance().getBaseUrl();
        int last_index = base_url.length() - 1;
        char lastChar = base_url.charAt(last_index);
        if (lastChar == '/') {
            base_url = base_url.substring(0, last_index);
        }
        Config.getInstance().setBase_url(base_url);


    }

}
