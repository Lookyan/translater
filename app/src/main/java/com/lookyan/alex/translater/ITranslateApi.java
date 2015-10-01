package com.lookyan.alex.translater;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alex on 01.10.15.
 */

public interface ITranslateApi {
    static final String apiKey = "trnsl.1.1.20150927T104821Z.0a050ec5a56ab6fb.6ff6f9f4b75fbc975ac624dc42e665b9de2f5eb0";
    static final String API_URL = "https://translate.yandex.net/api";

    @GET("/v1.5/tr.json/translate?key=" + apiKey)
    Translation getTranslation(@Query("lang") String direction, @Query("text") String text);
}
