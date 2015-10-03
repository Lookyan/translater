package com.lookyan.alex.translater;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class TranslationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        String text = getIntent().getStringExtra("transl");
        if(text != null) {
            ((TextView) findViewById(R.id.translation)).setText(text);
        }
    }

}
