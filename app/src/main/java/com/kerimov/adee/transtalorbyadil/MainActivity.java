package com.kerimov.adee.transtalorbyadil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String KEY = "trnsl.1.1.20190212T111345Z.f1fdb70e3a66aba2.c2732bc7a4a2bdb812737805ef5d1cbea7c752da";
    private EditText mEditText;
    private ImageButton mButtonTranslate;
    private Spinner spinner;
    private Spinner startSpinner;
    private String language;
    private String startLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvResult = findViewById(R.id.tv_result);
        mEditText = findViewById(R.id.edit_text);
        mButtonTranslate = findViewById(R.id.b_translate);
        spinner = findViewById(R.id.spinner);
        startSpinner = findViewById(R.id.start_spinner);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.select_lang, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.select_lang2, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(this);
        startSpinner.setOnItemSelectedListener(this);

        mButtonTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentWord = mEditText.getText().toString();

                if (currentWord.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Text field is empty!",Toast.LENGTH_SHORT).show();
                } else {
                    final Call<Word> word = jsonPlaceHolderApi.getWord(startLanguage + "-" + language, currentWord, KEY);

                    word.enqueue(new Callback<Word>() {
                        @Override
                        public void onResponse(Call<Word> call, Response<Word> response) {
                            Word word1 = response.body();
                            String[] words = word1.getText();
                            tvResult.setText(words[0]);
                        }

                        @Override
                        public void onFailure(Call<Word> call, Throwable t) {
                            tvResult.setText(t.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.start_spinner) {
            switch (position) {
                case 0:
                    startLanguage = "en";
                    break;
                case 1:
                    startLanguage = "ru";
                    break;
                case 2:
                    startLanguage = "de";
                    break;
                case 3:
                    startLanguage = "ky";
                    break;
                case 4:
                    startLanguage = "ko";
                    break;
                case 5:
                    startLanguage = "zh";
                    break;
                case 6:
                    startLanguage = "es";
                    break;
                case 7:
                    startLanguage = "ja";
                    break;
            }
        } else if (parent.getId() == R.id.spinner) {
            switch (position) {
                case 0:
                    language = "ru";
                    break;
                case 1:
                    language = "en";
                    break;
                case 2:
                    language = "de";
                    break;
                case 3:
                    language = "ky";
                    break;
                case 4:
                    language = "ko";
                    break;
                case 5:
                    language = "zh";
                    break;
                case 6:
                    language = "es";
                    break;
                case 7:
                    language = "ja";
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}


