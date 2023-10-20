package com.example.zad2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button_true;
    private Button button_false;
    private Button button_next;
    private Button button_hint;
    private TextView textView_question;
    private boolean answerChecked = false;
    private static final int REQUEST_CODE_PROMPT = 0;

    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, true),
            new Question(R.string.q_version, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_find_resources, false)
    };

    private int currentQuestion = 0, points = 0, answered = 0;

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questions[currentQuestion].getTrueAnswer();
        int resultMessageId = 0;

        if (answerChecked) {
            resultMessageId = R.string.answerChecked;
        }
        else {
            if (questions[currentQuestion].getAnswered())
                resultMessageId = R.string.already_answered;
            else if (userAnswer == correctAnswer) {
                resultMessageId = R.string.corrent_answer;
                questions[currentQuestion].setAnswered();
                points++;
                answered++;
            }
            else {
                resultMessageId = R.string.incorrent_answer;
                questions[currentQuestion].setAnswered();
                answered++;
            }
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        textView_question.setText(questions[currentQuestion].getQuestionId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("test", "onCreate method call");

        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt("SaveInstanceState test");
        }

        button_true = findViewById(R.id.button_true);
        button_false = findViewById(R.id.button_false);
        button_next = findViewById(R.id.button_next);
        button_hint = findViewById(R.id.button_hint);
        textView_question = findViewById(R.id.textView_question);

        button_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                checkAnswer(true);
            }
        });
        button_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                checkAnswer(false);
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                if (answered == 5) {
                    String cs = "Twoj wynik to : " + Integer.toString(points);
                    Toast.makeText(MainActivity.this, cs, Toast.LENGTH_SHORT).show();
                }
                else {
                    currentQuestion = ++currentQuestion%questions.length;
                    setNextQuestion();
                }
            }
        });
        button_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentQuestion].getTrueAnswer();
                intent.putExtra("hint test", correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

        setNextQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "onStart method call");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "onResume method call");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPause method call");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStop method call");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroy method call");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("test", "onSaveInstanceState method call");
        outState.putInt("SaveInstanceState test", currentQuestion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { return;}
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) { return; }
            answerChecked = data.getBooleanExtra(PromptActivity.KEY_ANSWER_CHECKED, false);
        }
    }
}