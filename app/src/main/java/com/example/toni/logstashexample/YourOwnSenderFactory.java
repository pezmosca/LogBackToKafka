package com.example.toni.logstashexample;

import android.content.Context;
import android.support.annotation.NonNull;

import org.acra.config.CoreConfiguration;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderFactory;

public class YourOwnSenderFactory implements ReportSenderFactory {
    @NonNull
    @Override
    public ReportSender create(@NonNull Context context, @NonNull CoreConfiguration config) {
        return new YourOwnSender();
    }

    @Override
    public boolean enabled(@NonNull CoreConfiguration config) {
        return true;
    }
}
