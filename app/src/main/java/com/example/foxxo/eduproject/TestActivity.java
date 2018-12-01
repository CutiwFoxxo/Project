package com.example.foxxo.eduproject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.util.TypedValue;
import android.widget.*;
import android.view.*;


public class TestActivity extends Activity {

    static LinearLayout llTest;
    static ScrollView scrollView;
    static LinearLayout internalLayout;
    static TextView header;
    static Button btnCheck;
    static TextView infoText;

    final static int firstItemIbdex = 1;

    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<Integer> answers = new ArrayList<Integer>();
    ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        llTest = (LinearLayout) findViewById(R.id.llTest);

        scrollView = new ScrollView(this);
        internalLayout = new LinearLayout(this);
        internalLayout.setOrientation(LinearLayout.VERTICAL);

        String filename = getIntent().getStringExtra("file");
        String json_test = ConfigReader.loadJSONFromAsset(getBaseContext(),filename);
        String name = getIntent().getStringExtra("name");
        questions = ConfigReader.getQuestionsList(json_test);
        answers   = ConfigReader.getAnswersList(json_test);
        options   = ConfigReader.getOptionsList(json_test);

        header = new TextView(this);
        header.setText(name);
        header.setTextColor(0xff006400);
        header.setTypeface(header.getTypeface(), Typeface.BOLD_ITALIC);
        header.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        internalLayout.addView(header);

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

            internalLayout.addView(item);
        }

        infoText = new TextView(this);
        infoText.setText("");
        infoText.setVisibility(View.INVISIBLE);
        internalLayout.addView(infoText);

        btnCheck = new Button(this);
        btnCheck.setText("Проверить");
        btnCheck.setBackgroundColor(0xff006400);
        btnCheck.setTextColor(Color.WHITE);
        internalLayout.addView(btnCheck);

        scrollView.addView(internalLayout);
        llTest.addView(scrollView);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> wrongAnswers = new ArrayList<Integer>();
                int count = answers.size();
                for (int i = 0; i < count; i++) {
                    LinearLayout item = (LinearLayout)internalLayout.getChildAt(i+firstItemIbdex);
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