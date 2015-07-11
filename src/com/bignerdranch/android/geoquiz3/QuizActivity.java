package com.bignerdranch.android.geoquiz3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    
    //private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER = "cheater";
    
    private TextView mQuestionTextView;
    private TextView mApiTextView;
    
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
    		new TrueFalse(R.string.question_oceans,true),
    		new TrueFalse(R.string.question_mideast,false),
    		new TrueFalse(R.string.question_africa,false),
    		new TrueFalse(R.string.question_americas,true),
    		new TrueFalse(R.string.question_asia,true),
    		};
    
    private int mCurrentIndex = 0;
    
    //Holds the value sent back from CheatActivity
    private boolean mIsCheater;
	
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
    	if(data == null) {
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
    
    private void updateQuestion() {
    	int question = mQuestionBank[mCurrentIndex].getQuestion();
    	mQuestionTextView.setText(question);
    }
    
    private void checkAnswer(boolean userPressedTrue) {
    	boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
    	
    	int messageResId = 0;
    	
    	if(mIsCheater) {
    		messageResId = R.string.judgment_toast;
    	}else {
    	    if(userPressedTrue == answerIsTrue) {
    		    messageResId = R.string.correct_toast;
    	    }
    	    else {
    		    messageResId = R.string.incorrect_toast;
    	    }
    	}
    	Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show();
    }
    			
    @TargetApi(11)
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           android.app.ActionBar actionBar = getActionBar();
           actionBar.setSubtitle("Bodies of Water");
        }
        
       mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        
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
        
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        
        mNextButton.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
				updateQuestion();
				
			}
		});
        
    
        mPreviousButton = (ImageButton)findViewById(R.id.previous_button);
        
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        		updateQuestion();
        		
        	}
        });
        
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
               // Start CheatActivity
        		Intent i = new Intent(QuizActivity.this,CheatActivity.class);
        		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        		i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        		startActivityForResult(i,0);
        	}
        });
        
        //Used to save data between instances of QuizActivity (After rotating device)
        if(savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        	mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER);
        }
        updateQuestion();
        
        mApiTextView = (TextView)findViewById(R.id.api_text_view);
        this.mApiTextView.setText(String.valueOf(Build.VERSION.SDK_INT));
        
    } //End of onCreate()
	
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
      
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    }	
    @Override
    public void onStop() {
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    
    }
    
}
