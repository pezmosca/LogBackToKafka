package com.example.toni.logstashexample;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

public class MySampleLayout extends LayoutBase<ILoggingEvent> {



    @Override
    public String doLayout(ILoggingEvent event) {

        /*StringBuffer stringBuffer = new StringBuffer(128);
        stringBuffer.append(event.getTimeStamp() - event.getLoggerContextVO().getBirthTime());
        stringBuffer.append(" ");
        stringBuffer.append(event.getLevel());
        stringBuffer.append(" [");
        stringBuffer.append(event.getThreadName());
        stringBuffer.append("] ");
        stringBuffer.append(event.getLoggerName());
        stringBuffer.append(" - ");
        stringBuffer.append(event.getFormattedMessage());
        stringBuffer.append(CoreConstants.LINE_SEPARATOR);*/

        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("T", event.getTimeStamp() - event.getLoggerContextVO().getBirthTime());
            jsonObject.put("L", event.getLevel());
            jsonObject.put("TN", event.getThreadName());

            //String [] loggerName = event.getLoggerName().split(".");

            jsonObject.put("LN", event.getLoggerName());
            jsonObject.put("M", event.getFormattedMessage());

            //jsonFinal.put("value", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject.toString().concat(CoreConstants.LINE_SEPARATOR);
    }

}
