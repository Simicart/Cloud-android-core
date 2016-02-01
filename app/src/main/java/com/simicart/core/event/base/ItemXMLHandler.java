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

    Boolean currentElement = false;
    String currentValue = "";
    ItemMaster item = null;
    String tags = "";
    ArrayList<String> mListSKU;
    private ArrayList<ItemMaster> itemsList;

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ItemXMLHandler(String tags, ArrayList<ItemMaster> itemsList, ArrayList<String> listSKU) {
        this.tags = tags;
        this.itemsList = itemsList;
        this.mListSKU = listSKU;
    }


    // Called when tag starts
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";
        if (localName.equals(this.tags)) {
            item = new ItemMaster();
        }

    }

    // Called when tag closing
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;
        /** set value */
        if (localName.equalsIgnoreCase("name"))
            item.setName(currentValue);
        else if (localName.equalsIgnoreCase("package"))
            item.setPackageName(currentValue);
        else if (localName.equalsIgnoreCase("class"))
            item.setClassName(currentValue);
        else if (localName.equalsIgnoreCase("method"))
            item.setMethod(currentValue);
        else if (localName.equalsIgnoreCase("sku"))
            item.setSku(currentValue);
        else if (localName.equalsIgnoreCase("order")) {
            item.setOrder(currentValue);
        } else if (localName.equalsIgnoreCase(this.tags)) {
            String sku = item.getSku();
            if (Utils.validateString(sku) && checkSKU(sku)) {
                String packageName = item.getPackageName();
                String className = item.getClassName();
                String fullName = packageName + "." + className;
                try {
                    Class<?> change = Class.forName(fullName);
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
                //itemsList.add(item);
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