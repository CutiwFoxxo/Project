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


public class MainActivity extends Activity {

    LinearLayout llMain;

    Button btnCheck;

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("test.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.llMain);

        ArrayList<String> questions = new ArrayList<String>();
        ArrayList<Integer> answers = new ArrayList<Integer>();
        ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
        try {
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset());

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String question = jo_inside.getString("question");
                int answer = jo_inside.getInt("answer");

                questions.add(question);
                answers.add(answer);

                JSONArray jOpts = jo_inside.getJSONArray("options");
                ArrayList<String> opts = new ArrayList<String>();
                for (int j = 0; j < jOpts.length(); j++) {
                    String jo_inside2 = jOpts.getString(j);
                    opts.add(jo_inside2);
                }
                options.add(opts);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int i;
        TextView textView;
        RadioGroup  radioGroup;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                wrapContent, wrapContent);
        lParams.gravity = Gravity.LEFT;
        for (i = 0; i < questions.toArray().length; i++) {
            textView = new TextView(this);
            textView.setTag("" + i);// setting tag with index i
            textView.setText(questions.get(i));
            llMain.addView(textView, lParams);
            radioGroup = new RadioGroup(this);
            RadioButton button;
            ArrayList<String> opts1 = options.get(i);
            for(int j = 0; j < opts1.size(); j++) {
                button = new RadioButton(this);
                button.setText(opts1.get(j));
                radioGroup.addView(button);
            }
            llMain.addView(radioGroup);
        }

        btnCheck = new Button(this);
        btnCheck.setText("Проверить");
        llMain.addView(btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ADROMARYN", "EEE!");
            }
        });
    }

}
