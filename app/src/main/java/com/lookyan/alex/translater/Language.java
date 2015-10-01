package com.lookyan.alex.translater;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

        View view = getView();
        if(view != null) {
            view.findViewById(R.id.translButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable.create(new Observable.OnSubscribe<Translation>() {
                        @Override
                        public void call(Subscriber<? super Translation> subscriber) {
                            RestAdapter restAdapter = new RestAdapter.Builder()
                                .setLogLevel(RestAdapter.LogLevel.FULL)
                                .setEndpoint(ITranslateApi.API_URL)
                                 .build();

                            ITranslateApi translateApi = restAdapter.create(ITranslateApi.class);
                            //сообщить сабскрайберу о том, что есть новые данные
                            subscriber.onNext(translateApi.getTranslation("en-ru", "direction"));
                            //А теперь сообщаем о том, что мы закончили и данных больше нет
                            subscriber.onCompleted();
                        }
                    }).subscribeOn(Schedulers.newThread()) //делаем запрос, преобразование, кэширование в отдельном потоке
                            .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
                            .subscribe(new Action1<Translation>() {
                                @Override
                                public void call(Translation s) {
                                    Toast.makeText(Language.this.getActivity(), "yyyy", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
        }
    }

}
