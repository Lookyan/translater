package com.lookyan.alex.translater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class Start extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ITranslateApi.API_URL)
                .build();
        ITranslateApi translateApi = restAdapter.create(ITranslateApi.class);

        translateApi.getLangs()
                .timeout(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Langs>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Start.this).create();
                        alertDialog.setTitle("Ошибка");
                        alertDialog.setMessage("Нет подключения к интернету");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Start.this.finishAffinity();
                                    }
                                });
                        alertDialog.show();
                    }

                    @Override
                    public void onNext(Langs langs) {
                        Intent intent = new Intent(Start.this, MainActivity.class);
                        intent.putExtra("dirs", langs.getDirs());
                        intent.putExtra("langs", langs.getLangs());
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
