package com.lookyan.alex.translater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Langs>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ploho", e.getMessage());
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
