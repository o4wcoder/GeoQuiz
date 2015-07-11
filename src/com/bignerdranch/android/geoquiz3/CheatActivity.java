package com.bignerdranch.android.geoquiz3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	//Key for the extra
	public static final String EXTRA_ANSWER_IS_TRUE =  "com.bignerdranch.android.geoquiz.anser_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

	//For loging
	private static final String TAG = "CheatActivity";
	//Variable to store the value of the extra from QuizActivity
	private boolean mAnswerIsTrue;
	
	//TextView to display retrieved value from QuizActivity
	private TextView mAnswerTextView;
	//Button to show retrieved value from QuizActivty
	private Button mShowAnswer;
	
	//Key to store the cheat answer after rotation of the device
	private static final String KEY_ANSWER = "answer";
	
	//method to send result back to QuizActivity
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		
		setResult(RESULT_OK,data);
	}
	
	//method to set the Answer to the TextView
	private void setAnswerTextView() {
		
		if(mAnswerIsTrue) {
			mAnswerTextView.setText(R.string.true_button);
		}
		else {
			mAnswerTextView.setText(R.string.false_button);
		}
		setAnswerShownResult(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		//Get value from extra
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		//Answer will not be shown until the user presses the button
		setAnswerShownResult(false);
		
		//Create TextView
		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
		
		//Create Button
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
				
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAnswerTextView();
			}
		});
		
        //Used to save data between instances of CheatActivity (After rotating device)
		Log.i(TAG,"Befoe seeing if savedInstanceState is null");
        if(savedInstanceState != null) {
        	mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER);
        	
			setAnswerTextView();
        }
	}
	
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
      
        savedInstanceState.putBoolean(KEY_ANSWER, mAnswerIsTrue);
    }

}
