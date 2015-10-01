package com.lookyan.alex.translater;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alex on 30.09.15.
 */
public class ApiServiceImpl implements ApiService {
    @Override
    public String detectLanguage(String text) {

        URL url = null;
        try {
            url = new URL("https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20150927T104821Z.0a050ec5a56ab6fb.6ff6f9f4b75fbc975ac624dc42e665b9de2f5eb0");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream in = null;
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return readStream(in, 10000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            urlConnection.disconnect();
        }
        return "no";
    }

    public String readStream(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

//    private void sendRequest(String uri) {
//        URL url = new URL(uri);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("X-Mashape-Key", "xvtJ6rRJAVmshbFasYHld3ERssImp1SWJWfjsnVUzLXgfE7U53");
//        connection.connect();
//        int code = connection.getResponseCode();
//        if (code == 200) {
//            InputStream in = connection.getInputStream();
//            handleInputStream(in);
//        }
//    }
}
