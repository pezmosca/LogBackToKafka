package com.example.toni.logstashexample;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;


@AcraCore(reportSenderFactoryClasses = YourOwnSenderFactory.class)
public class MyApplication extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        ACRA.init(this);
    }
}
