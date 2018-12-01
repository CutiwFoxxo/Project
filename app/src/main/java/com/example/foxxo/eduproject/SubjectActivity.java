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
import android.util.Log;
import android.widget.*;
import android.view.*;


public class SubjectActivity extends Activity {

    LinearLayout llSubject;
    ScrollView scrollView;
    LinearLayout internalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject);

        llSubject = (LinearLayout) findViewById(R.id.llSubject);
        scrollView = new ScrollView(this);
        internalLayout = new LinearLayout(this);
        internalLayout.setOrientation(LinearLayout.VERTICAL);

        String filename = getIntent().getStringExtra("file");
        String json_tests = ConfigReader.loadJSONFromAsset(getBaseContext(),filename);
        final ArrayList<String> tests_list = ConfigReader.getTestsList(json_tests);
        final ArrayList<String> tests_name_list = ConfigReader.getTestNamesList(json_tests);
        final ArrayList<Boolean> has_lesson_list = ConfigReader.getTestHasLessonList(json_tests);

        Button btn;
        for (int i = 0; i < tests_list.size(); i++) {
            btn = new Button(this);
            btn.setText(tests_name_list.get(i));
            btn.setTag(i);
            btn.setBackgroundColor(0xff006400);
            btn.setTextColor(Color.WHITE);
            internalLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    int index = Integer.parseInt(v.getTag().toString());
                    if (has_lesson_list.get(index)) {
                        intent = new Intent(SubjectActivity.this, LessonActivity.class);
                    } else {
                        intent = new Intent(SubjectActivity.this, TestActivity.class);
                    }
                    intent.putExtra("file", tests_list.get(index));
                    intent.putExtra("name", tests_name_list.get(index));
                    startActivity(intent);
                }
            });
        }

        scrollView.addView(internalLayout);
        llSubject.addView(scrollView);
    }

}
