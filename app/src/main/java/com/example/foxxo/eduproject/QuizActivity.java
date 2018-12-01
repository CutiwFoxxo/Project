package com.example.foxxo.eduproject;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class QuizActivity extends Activity {

    final static int COUNT_OF_TESTS = 5;

    static LinearLayout llQuiz;
    static ScrollView scrollView;
    static LinearLayout internalLayout;
    static TextView header;
    static Button btnCheck;
    static TextView infoText;

    final static int firstItemIbdex = 1;

    static ArrayList<String> questions = new ArrayList<String>();
    static ArrayList<Integer> answers = new ArrayList<Integer>();
    static ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        llQuiz = (LinearLayout) findViewById(R.id.llQuiz);

        scrollView = new ScrollView(this);
        internalLayout = new LinearLayout(this);
        internalLayout.setOrientation(LinearLayout.VERTICAL);

        String filename = getIntent().getStringExtra("file");
        String json_tests = ConfigReader.loadJSONFromAsset(getBaseContext(),filename);
        final ArrayList<String> tests_list = ConfigReader.getTestsList(json_tests);
        final ArrayList<Boolean> has_lesson_list = ConfigReader.getTestHasLessonList(json_tests);

        ArrayList<Integer> indexes = new ArrayList<Integer>();
        int counter = 0;
        for (int i = 0; i < tests_list.size(); i++) {
            String json_tests2 = ConfigReader.loadJSONFromAsset(getBaseContext(),tests_list.get(i));
            if (has_lesson_list.get(i)) {
                final HashMap<String,String> metaData = ConfigReader.getLesson(json_tests2);
                String testFileName = metaData.get("test");
                json_tests2 = ConfigReader.loadJSONFromAsset(getBaseContext(),testFileName);
            }
            ArrayList<String> questionsOne = ConfigReader.getQuestionsList(json_tests2);
            ArrayList<Integer> answersOne = ConfigReader.getAnswersList(json_tests2);
            ArrayList<ArrayList<String>> optionsOne = ConfigReader.getOptionsList(json_tests2);
            for (int j = 0; j < questionsOne.size(); j++) {
                questions.add(j, questionsOne.get(j));
                answers.add(j, answersOne.get(j));
                options.add(j, optionsOne.get(j));
                indexes.add(counter++);
            }
        }

        Collections.shuffle(indexes);

        final ArrayList<Integer> selectedIndexes = new ArrayList<Integer>(indexes.size() > COUNT_OF_TESTS ? indexes.subList(0,COUNT_OF_TESTS) : indexes);

        header = new TextView(this);
        header.setText(getIntent().getStringExtra("name"));
        header.setTextColor(0xff006400);
        header.setTypeface(header.getTypeface(), Typeface.BOLD_ITALIC);
        header.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        internalLayout.addView(header);

        LinearLayout item;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);
        lParams.gravity = Gravity.LEFT;
        for (int i = 0; i < selectedIndexes.size(); i++) {
            int index = selectedIndexes.get(i);
            item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            TextView textView = new TextView(this);
            textView.setTag(index);
            textView.setText((i+1) +  ") " + questions.get(index));
            item.addView(textView, lParams);

            RadioGroup radioGroup = new RadioGroup(this);
            RadioButton button;
            ArrayList<String> opts = options.get(index);
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
        llQuiz.addView(scrollView);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> wrongAnswers = new ArrayList<Integer>();
                int count = selectedIndexes.size();
                for (int i = 0; i < count; i++) {
                    LinearLayout item = (LinearLayout)internalLayout.getChildAt(i+firstItemIbdex);
                    RadioGroup group = (RadioGroup)item.getChildAt(1);
                    int id = group.getCheckedRadioButtonId();
                    View radioButton = group.findViewById(id);
                    int idx = group.indexOfChild(radioButton);
                    if (idx != answers.get(selectedIndexes.get(i))) {
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