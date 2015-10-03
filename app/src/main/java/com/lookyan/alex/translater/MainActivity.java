package com.lookyan.alex.translater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        List<String> dirs = (ArrayList<String>) intent.getSerializableExtra("dirs");
        Map<String, String> langs = (HashMap<String, String>) intent.getSerializableExtra("langs");
        if(dirs != null || langs != null) {
            LangData.setDirs(dirs);
            LangData.setLangs(langs);
        }


        Spinner langFromSpinner = (Spinner) findViewById(R.id.lang_from_spinner);
        langFromSpinner.getBackground().setColorFilter(getResources().getColor(android.R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item,
                LangData.getLangs().values().toArray(new String[LangData.getLangs().size()]));

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        langFromSpinner.setAdapter(adapter);


        Spinner langToSpinner = (Spinner) findViewById(R.id.lang_to_spinner);
        langToSpinner.getBackground().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        langToSpinner.setAdapter(adapter);

        Language language = new Language();
        getFragmentManager().beginTransaction().add(R.id.frag_lang_lay, language).commit();


    }

    public void onCardClick(View v) {
        EditText textTranslate = (EditText)findViewById(R.id.textTranslate);
        textTranslate.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textTranslate, InputMethodManager.SHOW_IMPLICIT);
    }
}
