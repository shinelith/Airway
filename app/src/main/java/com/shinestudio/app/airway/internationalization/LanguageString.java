package com.shinestudio.app.airway.internationalization;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;

public class LanguageString {
    private Context context;
    private final static HashMap<String, String> strings = new HashMap<String, String>();
    private static LanguageString me;
    private String localLanguage;

    public static LanguageString getInstance(Context app) {
        if (me == null) {
            me = new LanguageString(app);
        }
        return me;
    }

    public String getString(String key) {
        if ("zh".endsWith(localLanguage))
            return strings.get(key);
        else
            return key;
    }

    private LanguageString(Context t) {
        context = t;
        Locale locale = context.getResources().getConfiguration().locale;
        localLanguage = locale.getLanguage();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(t.getAssets().open("add_language.csv")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] l = line.split(",");
                strings.put(l[0], l[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                reader.close();
            } catch (IOException e1) {
            }
        }

    }
}
