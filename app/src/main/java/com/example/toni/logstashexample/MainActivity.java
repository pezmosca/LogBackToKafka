package com.example.toni.logstashexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String message = null;
    Gson gson = null;
    Map<String, ArrayList<Map<String, String>>> logs = null;
    File file = null;
    Button button;
    EditText editText;
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger = LoggerFactory.getLogger(MainActivity.class);
        /*logger.debug("HELLO5");
        logger.debug("HELLO6");
        logger.debug("HELLO7");
        logger.debug("a ver");*/


        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.debug(editText.getText().toString());
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();


        String path = getApplicationContext().getFilesDir() + "/" + "log3.txt";
        file = new File(path);

        gson = new Gson();
        logs = new HashMap<>();
        ArrayList<Map<String, String>> values = new ArrayList<>();


        try {

            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                Map<String, String> value = new HashMap<>();
                value.put("value", line);
                values.add(value);

            }
            reader.close();

            logs.put("records", values);
            Log.d("POST", gson.toJson(logs));
            message = gson.toJson(logs);

        }catch(IOException e) {
            e.printStackTrace();
        }


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/vnd.kafka.json.v2+json");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(logs));
        final Request request = new Request.Builder()
                .url("http://example:8082/topics/test")
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
                file.delete();
            }
        });



    }

}
