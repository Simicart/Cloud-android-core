package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;

/**
 * Created by Sony on 12/3/2015.
 */
public class DateTimeEntity extends SimiEntity{
    protected String dateFormat;
    protected String timeFormat;

    private String date_format = "date_format";
    private String time_format = "time_format";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(date_format)){
                dateFormat = getData(date_format);
            }

            if(mJSON.has(time_format)){
                timeFormat = getData(time_format);
            }
        }
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
}
