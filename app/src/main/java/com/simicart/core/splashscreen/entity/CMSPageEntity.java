package com.simicart.core.splashscreen.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.cms.entity.Cms;
import com.simicart.core.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by James Crabby on 12/3/2015.
 */
public class CMSPageEntity extends SimiEntity {
    ArrayList<String> all_ids;
    ArrayList<Cms> page;
    int total;
    int page_size;
    int from;

    @Override
    public void parse() {
        if(mJSON.has("all_ids")) {
            all_ids = getArrayData("all_ids");
        }

        if(mJSON.has("pages")) {
            page = new ArrayList<>();
            String pageString = getData("pages");
            if(Utils.validateString(pageString)) {
                try {
                    JSONArray arr = new JSONArray(pageString);
                    for (int i = 0; i < arr.length(); i++) {
                        Cms cms = new Cms();
                        cms.setJSONObject(arr.getJSONObject(i));
                        cms.parse();
                        if(cms.isEnable()){
                            page.add(cms);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(mJSON.has("total")) {
            total = Integer.parseInt(getData("total"));
        }

        if(mJSON.has("page_size")) {
            page_size = Integer.parseInt(getData("page_size"));
        }

        if(mJSON.has("from")) {
            from = Integer.parseInt(getData("from"));
        }
    }

    public ArrayList<String> getAll_ids() {
        return all_ids;
    }

    public void setAll_ids(ArrayList<String> all_ids) {
        this.all_ids = all_ids;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public ArrayList<Cms> getPage() {
        return page;
    }

    public void setPage(ArrayList<Cms> page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
