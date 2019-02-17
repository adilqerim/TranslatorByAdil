package com.kerimov.adee.transtalorbyadil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String KEY = "trnsl.1.1.20190212T111345Z.f1fdb70e3a66aba2.c2732bc7a4a2bdb812737805ef5d1cbea7c752da";
    private EditText mEditText;
    private ImageButton mButtonTranslate;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private String fromLanguage;
    private String toLanguage;
    private String currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvResult = findViewById(R.id.tv_result);
        mEditText = findViewById(R.id.edit_text);
        mButtonTranslate = findViewById(R.id.b_translate);
        toSpinner = findViewById(R.id.spinner);
        fromSpinner = findViewById(R.id.start_spinner);

        //устанавливаем спинерам слушателей, чтобы определять какой язык был нажат
        toSpinner.setOnItemSelectedListener(this);
        fromSpinner.setOnItemSelectedListener(this);

        //Кнопке стрелке, устанавливаем слушателя при котором изменяются параметры url-ссылки
        // и возрвращаются нужный метод api
        mButtonTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получаем текст
                currentWord = mEditText.getText().toString();

                //если поле ввода пустое, выводим уведомление
                if (currentWord.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Text field is empty!",Toast.LENGTH_SHORT).show();
                } else {
                    Call<Word> word = NetworkService.getInstance()
                            .getJSONApi()
                            .getWord(fromLanguage + "-" + toLanguage, currentWord, KEY);

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
    // в зависимости от спиннера назначаем значение переменным fromLanguage и toLanguage
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.start_spinner) {
            switch (position) {
                case 0:
                    fromLanguage = "ru";
                    break;
                case 1:
                    fromLanguage = "en";
                    break;
                case 2:
                    fromLanguage = "de";
                    break;
                case 3:
                    fromLanguage = "ky";
                    break;
                case 4:
                    fromLanguage = "ko";
                    break;
                case 5:
                    fromLanguage = "zh";
                    break;
                case 6:
                    fromLanguage = "es";
                    break;
                case 7:
                    fromLanguage = "ja";
                    break;
            }
        } else if (parent.getId() == R.id.spinner) {
            switch (position) {
                case 0:
                    toLanguage = "en";
                    break;
                case 1:
                    toLanguage = "ru";
                    break;
                case 2:
                    toLanguage = "de";
                    break;
                case 3:
                    toLanguage = "ky";
                    break;
                case 4:
                    toLanguage = "ko";
                    break;
                case 5:
                    toLanguage = "zh";
                    break;
                case 6:
                    toLanguage = "es";
                    break;
                case 7:
                    toLanguage = "ja";
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}


