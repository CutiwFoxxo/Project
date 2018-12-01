package com.example.foxxo.eduproject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigReader {

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
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

    public static ArrayList<String> getTestsList(String json) {
        ArrayList<String> tests = new ArrayList<String>();
        try {
            JSONArray m_jArry = new JSONArray(json);
            for (int i = 0; i < m_jArry.length(); i++) {
                tests.add(m_jArry.getJSONObject(i).getString("file"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public static ArrayList<String> getTestNamesList(String json) {
        ArrayList<String> tests = new ArrayList<String>();
        try {
            JSONArray m_jArry = new JSONArray(json);
            for (int i = 0; i < m_jArry.length(); i++) {
                tests.add(m_jArry.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public static ArrayList<Boolean> getTestHasLessonList(String json) {
        ArrayList<Boolean> tests = new ArrayList<Boolean>();
        try {
            JSONArray m_jArry = new JSONArray(json);
            for (int i = 0; i < m_jArry.length(); i++) {
                tests.add(m_jArry.getJSONObject(i).getBoolean("is_lesson"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public static HashMap<String, String> getLesson(String json) {
        HashMap<String, String> lesson = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            lesson.put("test", jsonObject.getString("test"));
            lesson.put("lesson", jsonObject.getString("lesson"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lesson;
    }



    public static ArrayList<String> getQuestionsList(String json) {
        ArrayList<String> questions = new ArrayList<String>();

        try {
            JSONArray m_jArry = new JSONArray(json);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String question = jo_inside.getString("question");
                questions.add(question);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static ArrayList<Integer> getAnswersList(String json) {
        ArrayList<Integer> answers = new ArrayList<Integer>();

        try {
            JSONArray m_jArry = new JSONArray(json);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int answer = jo_inside.getInt("answer");
                answers.add(answer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public static ArrayList<ArrayList<String>> getOptionsList(String json) {
        ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();

        try {
            JSONArray m_jArry = new JSONArray(json);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

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
        return options;
    }

}
