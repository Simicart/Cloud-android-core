package com.simicart.core.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public class ReadXMLLanguage {

    private Context mContext;
    private String key = "";
    private String value = "";
    Map<String, String> languages = new HashMap<String, String>();

    public ReadXMLLanguage(Context context) {
        this.mContext = context;
    }

    public void parseXML(String filename) {
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(filename);
            VTDGen vg = new VTDGen();
            if (parseInputStream(vg, inputStream, true)) {
                VTDNav vn = vg.getNav();
                if (vn.matchElement("language")) {
                    if (vn.toElement(VTDNav.FC, "item")) {
                        do {
                            if (vn.toElement(VTDNav.FC, "key")) {
                                do {
                                    int val_key = vn.getText();
                                    if (val_key != 1) {
                                        key = vn.toNormalizedString(val_key).toLowerCase().trim();
//                                        Log.e("ReadXMLLanguage", "Key: " + key);
                                    }
                                } while (vn.toElement(VTDNav.NS, "key"));
                                vn.toElement(VTDNav.P);
                            } else {
                                Log.e("ReadXMLLanguage", "No child element named 'key'");
                            }

                            if (vn.toElement(VTDNav.FC, "value")) {
                                do {
                                    int val_value = vn.getText();
                                    if (val_value != 1) {
                                        value = vn.toNormalizedString(val_value).trim();
//                                        Log.e("ReadXMLLanguage", "Value: " + value);
                                    }
                                } while (vn.toElement(VTDNav.NS, "value"));
                                vn.toElement(VTDNav.P);
                            } else {
                                Log.e("ReadXMLLanguage", "No child element named 'value'");
                            }
                            languages.put(key, value);
                        } while (vn.toElement(VTDNav.NS, "item"));
                        vn.toElement(VTDNav.P);
                    } else {
                        Log.e("ReadXMLLanguage", "No child element named 'item'");
                    }
                } else {
                    Log.e("ReadXMLLanguage", "Root is not 'language'");
                }
            }
        } catch (NavException e) {
            Log.e("ReadXMLLanguage", "Exception during navigation: " + e.getMessage());
        } catch (IOException e){
            Log.e("ReadXMLLanguage", "IOException: " + e.getMessage());
        }
    }

    public boolean parseInputStream(VTDGen vg, InputStream in, boolean ns){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] ba = new byte[65536];
            int noRead;
            while ((noRead = in.read(ba)) != -1) {
                baos.write(ba, 0, noRead);
            }
            vg.setDoc(baos.toByteArray());
            vg.parse(ns);
            return true;
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (com.ximpleware.ParseException e){
            System.out.println("ParserException: "+e);
        }

        return false;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }
}
