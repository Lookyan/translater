package com.lookyan.alex.translater;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class Language extends Fragment {

    public ApiService apiService;

    public Language() {
        apiService = new ApiServiceImpl();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if(view != null) {
            view.findViewById(R.id.translButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Subscription subscription = Observable.create(new Observable.OnSubscribe<Translation>() {
//                        @Override
//                        public void call(Subscriber<? super Translation> subscriber) {
//
//                            RestAdapter restAdapter = new RestAdapter.Builder()
//                                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                                    .setEndpoint(ITranslateApi.API_URL)
//                                    .build();
//
//                            ITranslateApi translateApi = restAdapter.create(ITranslateApi.class);
//
//                            //сообщить сабскрайберу о том, что есть новые данные
//                            subscriber.onNext(translateApi.getTranslation("en-ru", "Follow me"));
//                            //А теперь сообщаем о том, что мы закончили и данных больше нет
//                            subscriber.onCompleted();
//                        }
//                    })
//                    .onErrorReturn(new Func1<Throwable, Translation>() {
//                        @Override
//                        public Translation call(Throwable throwable) {
//                            Translation tr = new Translation();
//                            tr.code = 500;
//                            tr.text.add(0, "Oshibka!");
//                            return tr;
//                        }
//                    })
//                    .timeout(5, TimeUnit.SECONDS)
//                    .subscribeOn(Schedulers.newThread()) //делаем запрос, преобразование, кэширование в отдельном потоке
//                    .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
//                    .subscribe(new Action1<Translation>() {
//                        @Override
//                        public void call(Translation s) {
//                            Toast.makeText(Language.this.getActivity(), s.text.get(0), Toast.LENGTH_LONG).show();
//                        }
//                    });

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .setEndpoint(ITranslateApi.API_URL)
                            .build();

                    ITranslateApi translateApi = restAdapter.create(ITranslateApi.class);

                    String text = ((EditText) view.findViewById(R.id.textTranslate)).getText().toString();

                    translateApi.getTranslation("en-ru", text)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Translation>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("ttt", e.getMessage());
                                }

                                @Override
                                public void onNext(Translation translation) {
                                    Intent intent = new Intent(getActivity(), TranslationActivity.class);
                                    intent.putExtra("transl",translation.text.get(0));
                                    startActivity(intent);
                                }
                            });

                }
            });
        }
    }

}
