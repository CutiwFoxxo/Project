package com.example.foxxo.eduproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import android.view.*;

import android.os.Build;
import android.text.Html;


public class LessonActivity extends Activity {

    LinearLayout llLesson;
    ScrollView scrollView;
    LinearLayout internalLayout;
    TextView content;
    Button btnToTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        llLesson = (LinearLayout) findViewById(R.id.llLesson);
        scrollView = new ScrollView(this);
        internalLayout = new LinearLayout(this);
        internalLayout.setOrientation(LinearLayout.VERTICAL);

        String filename = getIntent().getStringExtra("file");
        String json_test = ConfigReader.loadJSONFromAsset(getBaseContext(),filename);
        final HashMap<String,String> metaData = ConfigReader.getLesson(json_test);

        content = new TextView(this);
        String html = ConfigReader.loadJSONFromAsset(getBaseContext(),metaData.get("lesson"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
        } else {
            content.setText(Html.fromHtml(html));
        }
        internalLayout.addView(content);

        btnToTest = new Button(this);
        btnToTest.setText("Пройти тест");
        internalLayout.addView(btnToTest);

        btnToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonActivity.this, TestActivity.class);
                intent.putExtra("file", metaData.get("test"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });

        scrollView.addView(internalLayout);
        llLesson.addView(scrollView);
    }

}
