package com.simicart.core.event.base;

import android.util.Log;

import com.simicart.core.common.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ItemXMLHandler extends DefaultHandler {

    private Boolean currentElement = false;
    private String currentValue = "";
    private String tags = "event";
    private String currentSKU;
    private String currentFullName;
    ArrayList<String> mListSKU;

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ItemXMLHandler(ArrayList<String> listSKU) {
        this.mListSKU = listSKU;
    }


    // Called when tag starts
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        if (localName.equalsIgnoreCase("fullname")) {
            currentFullName = currentValue;
            currentValue = "";

        } else if (localName.equalsIgnoreCase("sku")) {
            currentSKU = currentValue;
            currentValue = "";
        } else if (localName.equalsIgnoreCase(this.tags)) {
            Log.e("ItemXMLHandler ---> ", "SKU " + currentSKU + " FULL NAME " + currentFullName);
            if (Utils.validateString(currentSKU) && checkSKU(currentSKU)) {
                try {
                    Class<?> change = Class.forName(currentFullName);
                    change.getConstructor().newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Called to get tag characters
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }

    }


    private boolean checkSKU(String sku) {
        if (null != mListSKU && mListSKU.size() > 0) {
            for (int i = 0; i < mListSKU.size(); i++) {
                String currentSKU = mListSKU.get(i);
                if (sku.equals(currentSKU)) {
                    return true;
                }
            }
        }
        return false;
    }

}