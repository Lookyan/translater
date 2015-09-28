package com.lookyan.alex.translater;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white_trian), PorterDuff.Mode.SRC_ATOP);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.langs_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);


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
}
