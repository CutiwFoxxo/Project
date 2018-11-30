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

        TextView textView;
        RadioGroup  radioGroup;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);
        lParams.gravity = Gravity.LEFT;
        for (int i = 0; i < questions.size(); i++) {
            textView = new TextView(this);
            textView.setTag("" + i);// setting tag with index i
            textView.setText(questions.get(i));
            llTest.addView(textView, lParams);
            radioGroup = new RadioGroup(this);
            RadioButton button;
            ArrayList<String> opts1 = options.get(i);
            for(int j = 0; j < opts1.size(); j++) {
                button = new RadioButton(this);
                button.setText(opts1.get(j));
                radioGroup.addView(button);
            }
            llTest.addView(radioGroup);
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