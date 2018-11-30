package com.example.foxxo.eduproject;

import android.app.Activity;
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


public class TestActivity extends Activity {

    LinearLayout llTest;

    Button btnCheck;
    TextView infoText;


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

        infoText = new TextView(this);
        infoText.setText("");
        infoText.setVisibility(View.INVISIBLE);
        llTest.addView(infoText);

        btnCheck = new Button(this);
        btnCheck.setText("Проверить");
        llTest.addView(btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> wrongAnswers = new ArrayList<Integer>();
                int count = answers.size();
                for (int i = 0; i < count; i++) {
                    LinearLayout item = (LinearLayout)llTest.getChildAt(i);
                    RadioGroup group = (RadioGroup)item.getChildAt(1);
                    int id = group.getCheckedRadioButtonId();
                    View radioButton = group.findViewById(id);
                    int idx = group.indexOfChild(radioButton);
                    if (idx != answers.get(i)) {
                        wrongAnswers.add(i+1);
                    }
                }
                if (wrongAnswers.size() == 0) {
                    infoText.setText("Все ответы правильные!");
                    infoText.setTextColor(Color.GREEN);
                    infoText.setVisibility(View.VISIBLE);
                } else {
                    String errStr = TextUtils.join(" ", wrongAnswers);
                    infoText.setText("Неправильные ответы: " + errStr);
                    infoText.setTextColor(Color.RED);
                    infoText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}