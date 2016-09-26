package com.example.fraps.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String KEY_VALUE = "value";
    private static final String KEY_BUTTON = "button";
    private static  final String TAG = "MainActivity";
    Button mCheateButton;
    Button mTrueButton;
    Button mFalseButton;
    Button mNextButton;
    Button mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private static boolean[] cheatMass = new boolean[5];

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };

    private void setButtonVisibleTrue(Button button) {
        button.setClickable(true);
        button.setVisibility(View.VISIBLE);
    }

    private void setButtonVisibleFalse(Button button) {
        button.setClickable(false);
        button.setVisibility(View.INVISIBLE);
    }

    public void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;

        if(mIsCheater) {
            messageResId = R.string.judjment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_button;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        cheatMass[mCurrentIndex] = mIsCheater;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonVisibleTrue(mCheateButton);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = cheatMass[mCurrentIndex];
                updateQuestion();
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonVisibleTrue(mCheateButton);
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                mIsCheater = cheatMass[mCurrentIndex];
                updateQuestion();
            }
        });

        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(KEY_VALUE, false);
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mCheateButton = (Button) findViewById(R.id.cheat_button);
        mCheateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setButtonVisibleFalse(mCheateButton);
                Intent i = new Intent(MainActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_VALUE, mIsCheater);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
