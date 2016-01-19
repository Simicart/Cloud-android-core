package com.simicart.core.splashscreen.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Sony on 12/10/2015.
 */
public class AppConfigEnitity extends SimiEntity{
    protected String mID;
    protected String mSplashScreenColor;
    protected String mLayout;
    protected int mSourceVersion;
    protected String mClientId;
    protected int mSeqNo;
    protected String mUpdatedAt;
    protected String mCreatedAt;
    protected String mName;
    protected String mIdentifyKey;
    protected String mSameData;
    protected ThemeConfigEntity mThemeConfig;
    protected LogoEntity mLogo;
    protected IconEntity mIcon;
    protected SplashScreenEntity mSpashScreen;
    protected String mLocale;

    private String _id = "_id";
    private String logo = "logo";
    private String icon = "icon";
    private String splash_screen = "splash_screen";
    private String splash_screen_color = "splash_screen_color";
    private String layout = "layout";
    private String theme = "theme";
    private String source_version = "source_version";
    private String client_id = "client_id";
    private String seq_no = "seq_no";
    private String updated_at = "updated_at";
    private String created_at = "created_at";
    private String name = "name";
    private String identify_key = "identify_key";
    private String language = "language";
    private String same_data = "same_data";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mID = getData(_id);
            }

            if(mJSON.has(logo)) {
                String logoValue = getData(logo);
                if(Utils.validateString(logoValue)){
                    mLogo = new LogoEntity();
                    try {
                        mLogo.setJSONObject(new JSONObject(logoValue));
                        mLogo.parse();
                    } catch (JSONException e) {
                        mLogo = null;
                    }
                }
            }

            if(mJSON.has(icon)){
                String iconValue = getData(icon);
                if(Utils.validateString(iconValue)){
                    mIcon = new IconEntity();
                    try {
                        mIcon.setJSONObject(new JSONObject(iconValue));
                        mIcon.parse();
                    } catch (JSONException e) {
                        mIcon = null;
                    }
                }
            }

            if(mJSON.has(splash_screen)){
                String splashScreenValue = getData(splash_screen);
                if(Utils.validateString(splashScreenValue)){
                    mSpashScreen = new SplashScreenEntity();
                    try {
                        mSpashScreen.setJSONObject(new JSONObject(splashScreenValue));
                        mSpashScreen.parse();
                    } catch (JSONException e) {
                        mSpashScreen = null;
                    }
                }
            }

            if(mJSON.has(splash_screen_color)){
                mSplashScreenColor = getData(splash_screen_color);
            }

            if(mJSON.has(layout)){
                mLayout = getData(layout);
            }

            if(mJSON.has(theme)){
                String themeValue = getData(theme);
                if(Utils.validateString(themeValue)){
                    mThemeConfig = new ThemeConfigEntity();
                    try {
                        mThemeConfig.setJSONObject(new JSONObject(themeValue));
                        mThemeConfig.parse();
                    } catch (JSONException e) {
                        mThemeConfig = null;
                    }
                }
            }

            if(mJSON.has(source_version)){
                String sourceVersionValue = getData(source_version);
                if(Utils.validateString(sourceVersionValue)){
                    try {
                        mSourceVersion = Integer.parseInt(sourceVersionValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(client_id)){
                mClientId = getData(client_id);
            }

            if(mJSON.has(seq_no)){
                String SeqNoValue = getData(seq_no);
                if(Utils.validateString(SeqNoValue)){
                    try {
                        mSeqNo = Integer.parseInt(SeqNoValue);
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

            if(mJSON.has(name)){
                mName = getData(name);
            }

            if(mJSON.has(identify_key)){
                mIdentifyKey = getData(identify_key);
            }

            if(mJSON.has(same_data)){
                mSameData = getData(same_data);
            }

            if(mJSON.has(language)){
                try {
                    JSONObject languageObj = mJSON.getJSONObject(language);
                    Iterator keys = languageObj.keys();
                    while(keys.hasNext()) {
                        mLocale = (String)keys.next();
                    }
                } catch (JSONException e) {
                    mLocale = null;
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

    public String getSplashScreenColor() {
        return mSplashScreenColor;
    }

    public void setSplashScreenColor(String mSplashScreenColor) {
        this.mSplashScreenColor = mSplashScreenColor;
    }

    public String getLayout() {
        return mLayout;
    }

    public void setLayout(String mLayout) {
        this.mLayout = mLayout;
    }

    public int getSourceVersion() {
        return mSourceVersion;
    }

    public void setSourceVersion(int mSourceVersion) {
        this.mSourceVersion = mSourceVersion;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String mClientId) {
        this.mClientId = mClientId;
    }

    public int getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(int mSeqNo) {
        this.mSeqNo = mSeqNo;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String mUpdatedAt) {
        this.mUpdatedAt = mUpdatedAt;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getIdentifyKey() {
        return mIdentifyKey;
    }

    public void setIdentifyKey(String mIdentifyKey) {
        this.mIdentifyKey = mIdentifyKey;
    }

    public String getSameData() {
        return mSameData;
    }

    public void setSameData(String mSameData) {
        this.mSameData = mSameData;
    }

    public ThemeConfigEntity getThemeConfig() {
        return mThemeConfig;
    }

    public void setThemeConfig(ThemeConfigEntity mThemeConfig) {
        this.mThemeConfig = mThemeConfig;
    }

    public LogoEntity getLogo() {
        return mLogo;
    }

    public void setLogo(LogoEntity mLogo) {
        this.mLogo = mLogo;
    }

    public IconEntity getIcon() {
        return mIcon;
    }

    public void setIcon(IconEntity mIcon) {
        this.mIcon = mIcon;
    }

    public SplashScreenEntity getSpashScreen() {
        return mSpashScreen;
    }

    public void setSpashScreen(SplashScreenEntity mSpashScreen) {
        this.mSpashScreen = mSpashScreen;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String mLocale) {
        this.mLocale = mLocale;
    }
}
