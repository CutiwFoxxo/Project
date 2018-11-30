package com.example.foxxo.eduproject;

import android.app.Activity;
import android.content.Intent;
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

import javax.security.auth.Subject;


public class MainActivity extends Activity {

    LinearLayout llMain;
    ScrollView scrollView;
    LinearLayout internalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        scrollView = new ScrollView(this);
        internalLayout = new LinearLayout(this);
        internalLayout.setOrientation(LinearLayout.VERTICAL);

        String json_subjects = ConfigReader.loadJSONFromAsset(getBaseContext(),"subjects.json");
        final ArrayList<String> subjects_list = ConfigReader.getTestsList(json_subjects);
        final ArrayList<String> subject_names_list = ConfigReader.getTestNamesList(json_subjects);

        Button btn;
        for (int i = 0; i < subjects_list.size(); i++) {
            btn = new Button(this);
            btn.setText(subject_names_list.get(i));
            btn.setTag(i);
            internalLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                    int index = Integer.parseInt(v.getTag().toString());
                    intent.putExtra("file", subjects_list.get(index));
                    intent.putExtra("name", subject_names_list.get(index));
                    startActivity(intent);
                }
            });
        }

        scrollView.addView(internalLayout);
        llMain.addView(scrollView);
    }

}
