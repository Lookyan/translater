package com.lookyan.alex.translater;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Map;

import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Language extends Fragment {

    public Language() {
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

                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .setEndpoint(ITranslateApi.API_URL)
                            .build();

                    ITranslateApi translateApi = restAdapter.create(ITranslateApi.class);

                    String text = ((EditText) view.findViewById(R.id.textTranslate)).getText().toString();

                    //detect direction
                    Spinner lang_from = (Spinner) getActivity().findViewById(R.id.lang_from_spinner);
                    Spinner lang_to = (Spinner) getActivity().findViewById(R.id.lang_to_spinner);

                    String direction = getKeyFromValue(LangData.getLangs(), lang_from.getSelectedItem().toString())
                            + "-"
                            + getKeyFromValue(LangData.getLangs(), lang_to.getSelectedItem().toString());

                    translateApi.getTranslation(direction, text)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Translation>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Language.this.getActivity()).create();
                                    alertDialog.setTitle(getString(R.string.error_title));
                                    alertDialog.setMessage(e.getMessage());
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, Language.this.getString(R.string.ok_word),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }

                                @Override
                                public void onNext(Translation translation) {
                                    Intent intent = new Intent(getActivity(), TranslationActivity.class);
                                    intent.putExtra("transl", translation.text.get(0));
                                    startActivity(intent);
                                }
                            });

                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private static String getKeyFromValue(Map<String, String> hm, String value) {
        for (String o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

}
