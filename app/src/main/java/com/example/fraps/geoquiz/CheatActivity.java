package com.example.fraps.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by fraps on 22.09.2016.
 */

public class CheatActivity extends Activity {

    private static final String shownAnswerStatus = "ifAnswerShown";
    private static final String shownAnswerButtonStatus = "ifAnswerButtonPressed";

    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.fraps.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.fraps.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mPressedAnswerButton = false;

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        setAnswerShownResult(mPressedAnswerButton);

        //save answer visibilety after switching screen orientation
        //and save answer
        if(savedInstanceState != null) {
            mPressedAnswerButton = savedInstanceState.getBoolean(shownAnswerButtonStatus, false);
            mAnswerIsTrue = savedInstanceState.getBoolean(shownAnswerStatus, false);
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
            setAnswerShownResult(mPressedAnswerButton);
        }

        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mPressedAnswerButton = true;

                setAnswerShownResult(mPressedAnswerButton);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(shownAnswerStatus, mAnswerIsTrue);
        savedInstanceState.putBoolean(shownAnswerButtonStatus, mPressedAnswerButton);
    }
}
