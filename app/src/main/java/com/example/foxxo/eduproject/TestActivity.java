package com.example.foxxo.eduproject;

import android.app.Activity;
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


public class TestActivity extends Activity {

    LinearLayout llTest;

    Button btnCheck;

    ArrayList<String> answerSelects = new ArrayList<String>();

    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<Integer> answers = new ArrayList<Integer>();
    ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        llTest = (LinearLayout) findViewById(R.id.llTest);

        String filename = getIntent().getStringExtra("file");
        String json_test = ConfigReader.loadJSONFromAsset(getBaseContext(),filename);
        questions = ConfigReader.getQuestionsList(json_test);
        answers   = ConfigReader.getAnswersList(json_test);
        options   = ConfigReader.getOptionsList(json_test);

        LinearLayout item;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);
        lParams.gravity = Gravity.LEFT;
        for (int i = 0; i < questions.size(); i++) {
            item = new LinearLayout(this);
            item.setBackgroundColor(Integer.parseInt("00E0E0", 16));
            item.setOrientation(LinearLayout.VERTICAL);
            TextView textView = new TextView(this);
            textView.setTag(i);
            textView.setText((i+1) +  ") " + questions.get(i));
            item.addView(textView, lParams);

            RadioGroup radioGroup = new RadioGroup(this);
            RadioButton button;
            ArrayList<String> opts = options.get(i);
            for(int j = 0; j < opts.size(); j++) {
                button = new RadioButton(this);
                button.setText(opts.get(j));
                radioGroup.addView(button);
            }
            item.addView(radioGroup, lParams);

            llTest.addView(item);
        }

        btnCheck = new Button(this);
        btnCheck.setText("Проверить");
        llTest.addView(btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View radioButton = radioButtonGroup.findViewById(radioButtonID);
            }
        });
    }

}