package com.example.android.unitedstatesquiz;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> questions = new ArrayList<> (0);
    ArrayList<String> choiceA = new ArrayList<> (0);
    ArrayList<String> choiceB = new ArrayList<> (0);
    ArrayList<String> choiceC = new ArrayList<> (0);
    ArrayList<String> choiceD = new ArrayList<> (0);
    ArrayList<questionTypeENUM> questionFormat = new ArrayList<> (0);

    int currentQuestionIndexNumber = 0;
    int numberOfQuestionsAnswered = 0;

    public enum questionTypeENUM {
        MULTIPLE_CHOICE, SINGLE_CHOICE, FREE_TEXT
    }

    public int numberOfQuestions() {
        return questions.size();
    }

    /** Initialize the randomNumberClass for our getRandom function */
    Random randomNumberClass = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Apply proper background based on display width/height
         */
        ImageView imageView = findViewById(R.id.quiz_background);
        if (Resources.getSystem().getDisplayMetrics().widthPixels > Resources.getSystem().getDisplayMetrics().heightPixels) {
            imageView.setImageResource(R.drawable.capital_landscape);
        } else {
            imageView.setImageResource(R.drawable.capital_portrait);
        }

        /**
         * Hide the quiz, prev and next buttons
         * Show the opening background and Begin button
         *
         **/
        addQuestion(questionTypeENUM.SINGLE_CHOICE,"What is the capital city of Wisconsin?",
                "Milwaukee", "*Madison", "Green Bay", "Edgar");
        addQuestion(questionTypeENUM.MULTIPLE_CHOICE,"Which states are in the Central Time Zone?",
                "California", "Georgia", "*Iowa", "*Mississippi");
        addQuestion(questionTypeENUM.SINGLE_CHOICE,"What is the capital city of Utah?",
                "Ogden", "St George", "Provo", "*Salt Lake City");
        addQuestion(questionTypeENUM.SINGLE_CHOICE, "What is the most common state bird?", "Robin", "Bald Eagle", "Western Meadowlark", "*Northern Cardinal");
    }

    /** Returns an integer from 0 to upperBound-1 */
    public int getRandom (int upperBound) {
        return randomNumberClass.nextInt(upperBound);
    }

    /**
     * This will add a new question to the quiz
     * @param questionType MULTIPLE_CHOICE, SINGLE_CHOICE
     * @param question Text to display for the question
     *
     *                precede the choice with a * for correct answers
     *                 ie: "*Correct answer"
     * @param choice1 Text for choice 1
     * @param choice2 Text for choice 2
     * @param choice3 Text for choice 3
     * @param choice4 Text for choice 4
     */
    public void addQuestion (questionTypeENUM questionType, String question, String choice1, String choice2, String choice3, String choice4) {
        questions.add(question + "");
        choiceA.add(choice1 + "");
        choiceB.add(choice2 + "");
        choiceC.add(choice3 + "");
        choiceD.add(choice4 + "");
        questionFormat.add(questionType);
    }

    /** parses the designated answer to remove the * symbol for correct answers for display purposes **/
    public String prepareAnswer (String answerText) {
        if (answerText.startsWith("*")) {
            return answerText.substring(1);
        } else {
            return answerText;
        }
    }

    /** Updates the quiz screen to display the proper fields for a given question **/
    public void showQuestion (int questionNumber) {

        /** TODO: Hide non-essential quiz type views **/
        View view = findViewById(R.id.quiz_multiple_choice);
        view.setVisibility(View.GONE);
        view = findViewById(R.id.quiz_single_choice);
        view.setVisibility(View.GONE);


        if (questionFormat.get(questionNumber - 1) == questionTypeENUM.SINGLE_CHOICE) {
            /** SINGLE_CHOICE **/

            /** Clear Radio Buttons **/
            RadioGroup radioGroup = findViewById(R.id.quiz_single_choice);
            radioGroup.clearCheck();

            TextView tv = findViewById(R.id.question_text);
            tv.setText(questions.get(questionNumber - 1));

            tv = findViewById(R.id.answer_1);
            if (choiceA.get(questionNumber - 1).length() > 0) {
                tv.setText(prepareAnswer(choiceA.get(questionNumber - 1)));
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }

            tv = findViewById(R.id.answer_2);
            if (choiceB.get(questionNumber - 1).length() > 0) {
                tv.setText(prepareAnswer(choiceB.get(questionNumber - 1)));
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }

            tv = findViewById(R.id.answer_3);
            if (choiceC.get(questionNumber - 1).length() > 0) {
                tv.setText(prepareAnswer(choiceC.get(questionNumber - 1)));
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }

            tv = findViewById(R.id.answer_4);
            if (choiceD.get(questionNumber - 1).length() > 0) {
                tv.setText(prepareAnswer(choiceD.get(questionNumber - 1)));
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }

            currentQuestionIndexNumber = questionNumber;
            View singleChoice = findViewById(R.id.quiz_single_choice);
            singleChoice.setVisibility(View.VISIBLE);
        } else {
            if (questionFormat.get(questionNumber - 1) == questionTypeENUM.MULTIPLE_CHOICE) {
                /** MULTIPLE_CHOICE **/

                TextView tv = findViewById(R.id.question_text);
                tv.setText(questions.get(questionNumber - 1));

                CheckBox cb = findViewById(R.id.checkbox_1);
                cb.setChecked(false);
                if (choiceA.get(questionNumber - 1).length() > 0) {
                    cb.setText(prepareAnswer(choiceA.get(questionNumber - 1)));
                    cb.setVisibility(View.VISIBLE);
                } else {
                    cb.setVisibility(View.GONE);
                }

                cb = findViewById(R.id.checkbox_2);
                cb.setChecked(false);
                if (choiceB.get(questionNumber - 1).length() > 0) {
                    cb.setText(prepareAnswer(choiceB.get(questionNumber - 1)));
                    cb.setVisibility(View.VISIBLE);
                } else {
                    cb.setVisibility(View.GONE);
                }

                cb = findViewById(R.id.checkbox_3);
                cb.setChecked(false);
                if (choiceC.get(questionNumber - 1).length() > 0) {
                    cb.setText(prepareAnswer(choiceC.get(questionNumber - 1)));
                    cb.setVisibility(View.VISIBLE);
                } else {
                    cb.setVisibility(View.GONE);
                }

                cb = findViewById(R.id.checkbox_4);
                cb.setChecked(false);
                if (choiceD.get(questionNumber - 1).length() > 0) {
                    cb.setText(prepareAnswer(choiceD.get(questionNumber - 1)));
                    cb.setVisibility(View.VISIBLE);
                } else {
                    cb.setVisibility(View.GONE);
                }

                currentQuestionIndexNumber = questionNumber;
                /** Show fields **/
                LinearLayout multi = findViewById(R.id.quiz_multiple_choice);
                multi.setVisibility(View.VISIBLE);
            }
        }
        /** Show quiz panel **/
        View quiz = findViewById(R.id.quiz);
        quiz.setVisibility(View.VISIBLE);
    }

    /**
     * BEGIN button clicked
     * Show the quiz, prev and next buttons
     * Hide the  Begin button
     * apply filter to background image
     */
    public void button_click (View view) {
        View filter = findViewById(R.id.background_filter);
        filter.setVisibility(View.VISIBLE);
        View scoreboard = findViewById(R.id.scoreboard);
        scoreboard.setVisibility(View.VISIBLE);
        Button button = findViewById(R.id.button);
        button.setVisibility(View.GONE);
        showQuestion (getRandom(numberOfQuestions()) + 1);
    }

    /**
     * Handles click of the submit_button
     * Used for Submit Answer
     * @param view
     */
    public void submit_button_click (View view) {
        Button button = findViewById(R.id.action_button);
        if (button.getTag().toString().equals("answer")) {
            if (answerCorrect()) {
                /** TODO: display score and progress **/
                button.setTag("next");
                button.setText("Next Question");
            }
        } else {
            if (button.getTag().toString().equals("next")) {
                /** TODO: Advance to next question or complete Quiz **/
                button.setTag("answer");
                button.setText("Submit Answer");
                showQuestion (getRandom(numberOfQuestions()) + 1);
            }
        }
    }

    public boolean answerCorrect() {
        /** TODO: Check answers **/
        return true;
    }
}