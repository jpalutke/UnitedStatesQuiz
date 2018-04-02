package com.example.android.unitedstatesquiz;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String[] stateNames = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
            "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
            "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
            "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
            "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
    private static final String[] capitalNames = {
            "Montgomery", "Juneau", "Phoenix", "Little Rock", "Sacramento", "Denver", "Hartford", "Dover", "Tallahassee",
            "Atlanta", "Honolulu", "Boise", "Springfield", "Indianapolis", "Des Moines", "Topeka", "Frankfort",
            "Baton Rouge", "Augusta", "Annapolis", "Boston", "Lansing", "St Paul", "Jackson", "Jefferson City", "Helena",
            "Lincoln", "Carson City", "Concord", "Trenton", "Santa Fe", "Albany", "Raleigh", "Bismark", "Columbus",
            "Oklahoma City", "Salem", "Harrisburg", "Providence", "Columbia", "Pierre", "Nashville", "Austin",
            "Salt Lake City", "Montpelier", "Richmond", "Olympia", "Charleston", "Madison", "Cheyenne"};

    private static final int numberOfQuestionsPerQuiz = 5;

    // Initialize the randomNumberClass for our getRandom function
    private final Random randomNumberClass = new Random();

    private questionTypeENUM questionType = questionTypeENUM.SINGLE_CHOICE;
    private int[] totalQuestionsAskedByType = {0, 0, 0};
    private int numberOfQuestionsAnswered = 0;
    private int numberOfQuestionsAnsweredCorrectly = 0;
    private int stateNumber = -1;
    private int[] questionAnswerIdx = {-1, -1, -1, -1};
    private String questionText = "";
    private String[] questionAnswerText = {"", "", "", ""};
    private String scoreText = "";
    private View multipleChoice_view;
    private RadioGroup singleChoice_view;
    private View freeText_view;
    private TextView question_text_view;
    private RadioButton radioButton1_view;
    private RadioButton radioButton2_view;
    private RadioButton radioButton3_view;
    private RadioButton radioButton4_view;
    private CheckBox checkbox1_view;
    private CheckBox checkbox2_view;
    private CheckBox checkbox3_view;
    private CheckBox checkbox4_view;
    private EditText editText_view;
    private View quiz_view;
    private View filter_view;
    private View scoreboard_view;
    private Button button_begin_view;
    private TextView scoreText_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Apply proper background based on display width/height
         */
        ImageView imageView = findViewById(R.id.quiz_background);
        if (Resources.getSystem().getDisplayMetrics().widthPixels > Resources.getSystem().getDisplayMetrics().heightPixels) {
            imageView.setImageResource(R.drawable.capital_landscape);
        } else {
            imageView.setImageResource(R.drawable.capital_portrait);
        }
        multipleChoice_view = findViewById(R.id.quiz_multiple_choice);
        singleChoice_view = findViewById(R.id.quiz_single_choice);
        freeText_view = findViewById(R.id.quiz_free_text);
        question_text_view = findViewById(R.id.question_text);
        radioButton1_view = findViewById(R.id.radio_button_1);
        radioButton2_view = findViewById(R.id.radio_button_2);
        radioButton3_view = findViewById(R.id.radio_button_3);
        radioButton4_view = findViewById(R.id.radio_button_4);
        checkbox1_view = findViewById(R.id.checkbox_1);
        checkbox2_view = findViewById(R.id.checkbox_2);
        checkbox3_view = findViewById(R.id.checkbox_3);
        checkbox4_view = findViewById(R.id.checkbox_4);
        editText_view = findViewById(R.id.answerText);
        quiz_view = findViewById(R.id.quiz_layout);
        filter_view = findViewById(R.id.background_filter);
        scoreboard_view = findViewById(R.id.status_bar);
        button_begin_view = findViewById(R.id.button_begin);
        scoreText_view = findViewById(R.id.score_text);
        scoreText_view.setText("");
    }

    /**
     * Returns an int from 0 to upperBound-1
     */
    private int getRandom(int upperBound) {
        return randomNumberClass.nextInt(upperBound);
    }

    /**
     * Updates the quiz screen to display the proper fields for a random question
     */
    private void showNextQuestion() {
        /* Hide all quiz types */
        multipleChoice_view.setVisibility(View.GONE);
        singleChoice_view.setVisibility(View.GONE);
        freeText_view.setVisibility(View.GONE);

        /* Pick a state number to question about (zero based) */
        stateNumber = getRandom(stateNames.length);

        /* Create the question */

        /* find least used type of question and select that type */
        int smallestValueIdx = 0;
        for (int i = 0; i < totalQuestionsAskedByType.length; i++) {
            if (totalQuestionsAskedByType[i] < totalQuestionsAskedByType[smallestValueIdx]) {
                smallestValueIdx = i;
            }
        }
        switch (smallestValueIdx) {
            case 0:
                questionType = questionTypeENUM.SINGLE_CHOICE;
                break;
            case 1:
                questionType = questionTypeENUM.MULTIPLE_CHOICE;
                break;
            case 2:
                questionType = questionTypeENUM.FREE_TEXT;
                break;
        }

        switch (questionType) {
            case SINGLE_CHOICE:
                questionAnswerIdx = getPossibleAnswers(stateNumber);
                questionText = "Which city is the capital of " + stateNames[stateNumber] + "?";
                for (int idx = 0; idx < 4; idx++) {
                    questionAnswerText[idx] = capitalNames[questionAnswerIdx[idx]];
                }

                /* Clear Radio Buttons */
                singleChoice_view.clearCheck();

                question_text_view.setText(questionText);
                if (questionAnswerIdx[0] != -1) {
                    radioButton1_view.setText(questionAnswerText[0]);
                    radioButton1_view.setVisibility(View.VISIBLE);
                } else {
                    radioButton1_view.setVisibility(View.GONE);
                }

                if (questionAnswerIdx[1] != -1) {
                    radioButton2_view.setText(questionAnswerText[1]);
                    radioButton2_view.setVisibility(View.VISIBLE);
                } else {
                    radioButton2_view.setVisibility(View.GONE);
                }

                if (questionAnswerIdx[2] != -1) {
                    radioButton3_view.setText(questionAnswerText[2]);
                    radioButton3_view.setVisibility(View.VISIBLE);
                } else {
                    radioButton3_view.setVisibility(View.GONE);
                }

                if (questionAnswerIdx[3] != -1) {
                    radioButton4_view.setText(questionAnswerText[3]);
                    radioButton4_view.setVisibility(View.VISIBLE);
                } else {
                    radioButton4_view.setVisibility(View.GONE);
                }

                totalQuestionsAskedByType[0]++;
                singleChoice_view.setVisibility(View.VISIBLE);
                break;
            case MULTIPLE_CHOICE:
                questionAnswerIdx = getPossibleAnswers(stateNumber);
                questionText = getString(R.string.cities_are_not) + stateNames[stateNumber] + "?";
                for (int idx = 0; idx < 4; idx++) {
                    questionAnswerText[idx] = capitalNames[questionAnswerIdx[idx]];
                }
                question_text_view.setText(questionText);


                checkbox1_view.setChecked(false);
                if (questionAnswerIdx[0] != -1) {
                    checkbox1_view.setText(questionAnswerText[0]);
                    checkbox1_view.setVisibility(View.VISIBLE);
                } else {
                    checkbox1_view.setVisibility(View.GONE);
                }

                checkbox2_view.setChecked(false);
                if (questionAnswerIdx[1] != -1) {
                    checkbox2_view.setText(questionAnswerText[1]);
                    checkbox2_view.setVisibility(View.VISIBLE);
                } else {
                    checkbox2_view.setVisibility(View.GONE);
                }

                checkbox3_view.setChecked(false);
                if (questionAnswerIdx[2] != -1) {
                    checkbox3_view.setText(questionAnswerText[2]);
                    checkbox3_view.setVisibility(View.VISIBLE);
                } else {
                    checkbox3_view.setVisibility(View.GONE);
                }

                checkbox4_view.setChecked(false);
                if (questionAnswerIdx[3] != -1) {
                    checkbox4_view.setText(questionAnswerText[3]);
                    checkbox4_view.setVisibility(View.VISIBLE);
                } else {
                    checkbox4_view.setVisibility(View.GONE);
                }

                totalQuestionsAskedByType[1]++;
                multipleChoice_view.setVisibility(View.VISIBLE);
                break;
            case FREE_TEXT:
                questionText = getString(R.string.capital_of_which) + capitalNames[stateNumber] + "?";
                question_text_view.setText(questionText);
                editText_view.setText("");
                freeText_view.setVisibility(View.VISIBLE);
                editText_view.requestFocus();
                totalQuestionsAskedByType[2]++;
                break;
        }

    /* Show quiz panel */
        quiz_view.setVisibility(View.VISIBLE);
    }

    /**
     * BEGIN button clicked
     * Hide the  Begin button
     * apply filter to background image
     */
    public void button_begin_onClick(View view) {
        filter_view.setVisibility(View.VISIBLE);
        scoreboard_view.setVisibility(View.VISIBLE);
        button_begin_view.setVisibility(View.GONE);
        updateScore();
        showNextQuestion();
    }

    /**
     * sets the score text (used for the toasts and the status bar)
     */
    private void updateScore() {
        scoreText = "Question " + (numberOfQuestionsAnswered + 1 + " of " + numberOfQuestionsPerQuiz);
        scoreText_view.setText(scoreText);
    }

    /**
     * Handles click of the submit_button
     * Used for Submit Answer
     */
    public void button_answer_onClick(View view) {
        hideSoftKeyboard();
        numberOfQuestionsAnswered++;
        if (answerCorrect()) {
            numberOfQuestionsAnsweredCorrectly++;
        }
        updateScore();
        if (numberOfQuestionsAnswered == numberOfQuestionsPerQuiz) {
            scoreText = "You answered " + numberOfQuestionsAnsweredCorrectly + " out of " + numberOfQuestionsAnswered + " correctly.";
            numberOfQuestionsAnsweredCorrectly = 0;
            numberOfQuestionsAnswered = 0;
            for (int idx = 0; idx < totalQuestionsAskedByType.length; idx++) {
                totalQuestionsAskedByType[idx] = 0;
            }
            filter_view.setVisibility(View.GONE);
            quiz_view.setVisibility(View.GONE);
            button_begin_view.setVisibility(View.VISIBLE);
            scoreboard_view.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), scoreText, Toast.LENGTH_LONG).show();
        } else {
            showNextQuestion();
        }
    }

    /**
     * method to check the users answers for the current question
     * @return boolean
     */
    private boolean answerCorrect() {
        boolean correct = false;
        switch (questionType) {
            case SINGLE_CHOICE: {
                if (radioButton1_view.isChecked() && questionAnswerIdx[0] == stateNumber) {
                    correct = true;
                }
                if (radioButton2_view.isChecked() && questionAnswerIdx[1] == stateNumber) {
                    correct = true;
                }
                if (radioButton3_view.isChecked() && questionAnswerIdx[2] == stateNumber) {
                    correct = true;
                }
                if (radioButton4_view.isChecked() && questionAnswerIdx[3] == stateNumber) {
                    correct = true;
                }
                break;
            }
            case MULTIPLE_CHOICE: {
                correct = (
                        (checkbox1_view.isChecked() != (questionAnswerIdx[0] == stateNumber)) &&
                                (checkbox2_view.isChecked() != (questionAnswerIdx[1] == stateNumber)) &&
                                (checkbox3_view.isChecked() != (questionAnswerIdx[2] == stateNumber)) &&
                                (checkbox4_view.isChecked() != (questionAnswerIdx[3] == stateNumber))
                );
                break;
            }
            case FREE_TEXT: {
                String userAnswer = editText_view.getText().toString().toLowerCase();
                correct = userAnswer.equals(stateNames[stateNumber].toLowerCase());
                break;
            }
        }
        if (correct) {
            Toast.makeText(getApplicationContext(), R.string.toast_correct, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), R.string.toast_incorrect, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Method to hide the soft keyboard
     */
    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(editText_view.getWindowToken(), 0);
            }
        }
    }

    /**
     *
     * @param correctAnswer
     *           The value to store in the random position in the results
     * @return int[3]
     *           Contains 4 possible answers(0 based state number)
     *           the correct answer will be placed into one of the 4
     *           answers at random.
     */
    private int[] getPossibleAnswers(int correctAnswer) {
        int[] answerNumber = {-1, -1, -1, -1};
        int trialNumber = 0;
        boolean answerAlreadyUsed;
        // place correct answer in one of the 4 positions randomly
        answerNumber[getRandom(3)] = correctAnswer;
        for (int i = 0; i < 4; i++) {
            // set each answer to a random answer if it is already not set (correct answer was set earlier)
            if (answerNumber[i] == -1) {

                // find an answer number that hasn't already been used in this set
                answerAlreadyUsed = true;
                while (answerAlreadyUsed) {
                    trialNumber = getRandom(stateNames.length);
                    answerAlreadyUsed = false;
                    for (int j = 0; j < 4; j++) {
                        if (answerNumber[j] == trialNumber) {
                            answerAlreadyUsed = true;
                        }
                    }
                }
                answerNumber[i] = trialNumber;
            }
        }
        return answerNumber;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("totalQuestionsAskedByType", totalQuestionsAskedByType);
        outState.putInt("numberOfQuestionsAnswered", numberOfQuestionsAnswered);
        outState.putInt("numberOfQuestionsAnsweredCorrectly", numberOfQuestionsAnsweredCorrectly);
        outState.putInt("stateNumber", stateNumber);
        outState.putIntArray("questionAnswerIdx", questionAnswerIdx);
        outState.putString("questionText", questionText);
        outState.putStringArray("questionAnswerText", questionAnswerText);
        outState.putString("scoreText", scoreText);
        outState.putInt("multipleChoice_view_vis", multipleChoice_view.getVisibility());
        outState.putInt("singleChoice_view_vis", singleChoice_view.getVisibility());
        outState.putInt("freeText_view_vis", freeText_view.getVisibility());
        outState.putInt("question_text_view_vis", question_text_view.getVisibility());
        outState.putInt("radioButton1_view_vis", radioButton1_view.getVisibility());
        outState.putInt("radioButton2_view_vis", radioButton2_view.getVisibility());
        outState.putInt("radioButton3_view_vis", radioButton3_view.getVisibility());
        outState.putInt("radioButton4_view_vis", radioButton4_view.getVisibility());
        outState.putInt("singleChoice_view_chk", singleChoice_view.getCheckedRadioButtonId());
        outState.putInt("checkbox1_view_vis", checkbox1_view.getVisibility());
        outState.putInt("checkbox2_view_vis", checkbox2_view.getVisibility());
        outState.putInt("checkbox3_view_vis", checkbox3_view.getVisibility());
        outState.putInt("checkbox4_view_vis", checkbox4_view.getVisibility());
        outState.putBoolean("checkbox1_view_chk", checkbox1_view.isChecked());
        outState.putBoolean("checkbox2_view_chk", checkbox2_view.isChecked());
        outState.putBoolean("checkbox3_view_chk", checkbox3_view.isChecked());
        outState.putBoolean("checkbox4_view_chk", checkbox4_view.isChecked());
        outState.putString("editText_view_txt", editText_view.getText().toString());
        outState.putInt("editText_view_SelectionStart", editText_view.getSelectionStart());
        outState.putInt("editText_view_SelectionEnd", editText_view.getSelectionEnd());
        outState.putInt("quiz_view_vis", quiz_view.getVisibility());
        outState.putInt("filter_view_vis", filter_view.getVisibility());
        outState.putInt("scoreboard_view_vis", scoreboard_view.getVisibility());
        outState.putInt("button_begin_view_vis", button_begin_view.getVisibility());
        if (scoreText_view.getText().length() > 0) {
            outState.putString("scoreText_view_txt", scoreText_view.getText().toString());
        } else {
            outState.putString("scoreText_view_txt", "");
        }

        switch (questionType) {
            case SINGLE_CHOICE:
                outState.putInt("questionType_int", 0);
                break;
            case MULTIPLE_CHOICE:
                outState.putInt("questionType_int", 1);
                break;
            case FREE_TEXT:
                outState.putInt("questionType_int", 2);
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // variableData = savedInstanceState.getInt("AStringKey");
        // variableData2 = savedInstanceState.getString("AStringKey2");
        totalQuestionsAskedByType = savedInstanceState.getIntArray("totalQuestionsAskedByType");
        numberOfQuestionsAnswered = savedInstanceState.getInt("numberOfQuestionsAnswered");
        numberOfQuestionsAnsweredCorrectly = savedInstanceState.getInt("numberOfQuestionsAnsweredCorrectly");
        stateNumber = savedInstanceState.getInt("stateNumber");
        questionAnswerIdx = savedInstanceState.getIntArray("questionAnswerIdx");
        questionText = savedInstanceState.getString("questionText");
        question_text_view.setText(questionText);

        questionAnswerText = savedInstanceState.getStringArray("questionAnswerText");
        assert questionAnswerText != null;
        if (questionAnswerText[0] != null) {
            checkbox1_view.setText(questionAnswerText[0]);
            radioButton1_view.setText(questionAnswerText[0]);
        }
        if (questionAnswerText[1] != null) {
            checkbox2_view.setText(questionAnswerText[1]);
            radioButton2_view.setText(questionAnswerText[1]);
        }
        if (questionAnswerText[2] != null) {
            checkbox3_view.setText(questionAnswerText[2]);
            radioButton3_view.setText(questionAnswerText[2]);
        }
        if (questionAnswerText[3] != null) {
            checkbox4_view.setText(questionAnswerText[3]);
            radioButton4_view.setText(questionAnswerText[3]);
        }

        scoreText = savedInstanceState.getString("scoreText");
        scoreText_view.setText(scoreText);
        multipleChoice_view.setVisibility(savedInstanceState.getInt("multipleChoice_view_vis"));
        singleChoice_view.setVisibility(savedInstanceState.getInt("singleChoice_view_vis"));
        freeText_view.setVisibility(savedInstanceState.getInt("freeText_view_vis"));
        question_text_view.setVisibility(savedInstanceState.getInt("question_text_view_vis"));

        radioButton1_view.setVisibility(savedInstanceState.getInt("radioButton1_view_vis"));
        radioButton2_view.setVisibility(savedInstanceState.getInt("radioButton2_view_vis"));
        radioButton3_view.setVisibility(savedInstanceState.getInt("radioButton3_view_vis"));
        radioButton4_view.setVisibility(savedInstanceState.getInt("radioButton4_view_vis"));

        singleChoice_view.check(savedInstanceState.getInt("singleChoice_view_chk"));

        checkbox1_view.setVisibility(savedInstanceState.getInt("checkbox1_view_vis"));
        checkbox2_view.setVisibility(savedInstanceState.getInt("checkbox2_view_vis"));
        checkbox3_view.setVisibility(savedInstanceState.getInt("checkbox3_view_vis"));
        checkbox4_view.setVisibility(savedInstanceState.getInt("checkbox4_view_vis"));

        checkbox1_view.setChecked(savedInstanceState.getBoolean("checkbox1_view_chk"));
        checkbox2_view.setChecked(savedInstanceState.getBoolean("checkbox2_view_chk"));
        checkbox3_view.setChecked(savedInstanceState.getBoolean("checkbox3_view_chk"));
        checkbox4_view.setChecked(savedInstanceState.getBoolean("checkbox4_view_chk"));

        editText_view.setText(savedInstanceState.getString("editText_view_txt"));
        editText_view.setSelection(savedInstanceState.getInt("editText_view_SelectionStart"), savedInstanceState.getInt("editText_view_SelectionEnd"));

        quiz_view.setVisibility(savedInstanceState.getInt("quiz_view_vis"));
        filter_view.setVisibility(savedInstanceState.getInt("filter_view_vis"));
        scoreboard_view.setVisibility(savedInstanceState.getInt("scoreboard_view_vis"));
        button_begin_view.setVisibility(savedInstanceState.getInt("button_begin_view_vis"));
        scoreText_view.setVisibility(savedInstanceState.getInt("scoreText_view_vis"));
        int i = savedInstanceState.getInt("questionType_int");
        switch (i) {
            case 0:
                questionType = questionTypeENUM.SINGLE_CHOICE;
                break;
            case 1:
                questionType = questionTypeENUM.MULTIPLE_CHOICE;
                break;
            case 2:
                questionType = questionTypeENUM.FREE_TEXT;
                break;
        }
    }

    enum questionTypeENUM {
        MULTIPLE_CHOICE, SINGLE_CHOICE, FREE_TEXT
    }
}