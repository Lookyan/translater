package com.lookyan.alex.translater;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner langFromSpinner = (Spinner) findViewById(R.id.lang_from_spinner);
        langFromSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white_trian), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.langs_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        langFromSpinner.setAdapter(adapter);

        langFromSpinner.setOnItemSelectedListener(this);


        Spinner langToSpinner = (Spinner) findViewById(R.id.lang_to_spinner);
        langToSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white_trian), PorterDuff.Mode.SRC_ATOP);

        langToSpinner.setAdapter(adapter);


        if(savedInstanceState != null) {
            return;
        }

        TranslateArea translateArea = new TranslateArea();
        Language language = new Language();
        getFragmentManager().beginTransaction().add(R.id.frag_lang_lay, language).commit();
        getFragmentManager().beginTransaction().add(R.id.frag_tr_lay, translateArea).commit();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("tt", String.valueOf(id));
        //parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onCardClick(View v) {
        EditText textTranslate = (EditText)findViewById(R.id.textTranslate);
        textTranslate.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textTranslate, InputMethodManager.SHOW_IMPLICIT);
    }
}
