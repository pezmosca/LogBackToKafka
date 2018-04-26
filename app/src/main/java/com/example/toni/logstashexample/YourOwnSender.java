package com.example.toni.logstashexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.acra.data.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YourOwnSender implements ReportSender{
    @Override
    public void send(@NonNull Context context, @NonNull CrashReportData errorContent) throws ReportSenderException {

        Gson gson = new Gson();
        Map<String, ArrayList<Map<String, Object>>> logs = new HashMap<>();
        ArrayList<Map<String, Object>> values = new ArrayList<>();
        Map<String, Object> value = new HashMap<>();

        try {
            Object json = gson.fromJson(errorContent.toJSON(), Object.class);
            value.put("value", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        values.add(value);
        logs.put("records", values);

        Log.d("POST", gson.toJson(logs));

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/vnd.kafka.json.v2+json");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(logs));
        final Request request = new Request.Builder()
                .url(context.getString(R.string.url_kafka))
                .post(body)
                .addHeader("Content-Type", "application/vnd.kafka.json.v2+json")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("POST", response.toString());
            }
        });

    }
}
