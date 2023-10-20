package com.example.zad2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    private Button button_showAnswer;
    private TextView textView_answer;
    private boolean correctAnswer, answerChecked = false;
    public static final String KEY_ANSWER_CHECKED = "answerChecked test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra("hint test", true);

        textView_answer = findViewById(R.id.textView_answer);
        button_showAnswer = findViewById(R.id.button_showAnswer);

        button_showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                if (!answerChecked) {
                    int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                    textView_answer.setText(answer);
                    button_showAnswer.setText("Powrót do pytania");
                    answerChecked = true;
                    resultIntent.putExtra(KEY_ANSWER_CHECKED, answerChecked);
                } else {
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}