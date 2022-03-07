package no.hvl.dat153.oblig2.main.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import no.hvl.dat153.oblig2.R;
import no.hvl.dat153.oblig2.main.data.Student;
import no.hvl.dat153.oblig2.main.ui.StudentViewModel;

public class QuizActivity extends AppCompatActivity {

    private TextView infoText;
    private TextView scoreText;
    private TextView counterText;
    private ImageView guessImage;
    private EditText guessText;
    private Button buttonGuess;

    private StudentViewModel mViewModel;
    public ArrayList<Student> pList;//Copy of the database so that we have an overview of which is black and not black
    private int index;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        observerSetup();
    }

    private void observerSetup() {
        mViewModel.getAllStudents().observe(this,
                new Observer<List<Student>>() {
                    @Override
                    public void onChanged(@Nullable final List<Student> Students) {
                        guessImage = (ImageView) findViewById(R.id.imageGuess);
                        buttonGuess = (Button) findViewById(R.id.guessButton);
                        guessText = (EditText) findViewById(R.id.guessText);
                        infoText = (TextView) findViewById(R.id.textView3);
                        scoreText = (TextView) findViewById(R.id.textViewScore);
                        counterText = (TextView) findViewById(R.id.textViewCounter);


                        pList = new ArrayList<Student>(Students);
                        Collections.shuffle(pList); //shuffles the list so that it is random
                        index = 0;
                        scoreText.setText("Score: " + score + "/" + pList.size());
                        counterText.setText("Persons left: " + (pList.size() - index));
                        nextPerson();

                        //Adds a listener to edit text so the user can press enter on the keyboard to guess
                        guessText.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                                    makeGuess();
                                    return true;
                                }
                                return false;
                            }
                        });
                        // can also press the button
                        buttonGuess.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makeGuess();
                            }
                        });
                    }
                });


    }

    public void checkCorrect() {
        Student p = pList.get(index);
        index++;
        if (p.getName().equals(guessText.getEditableText().toString())) {
            score++;
            Toast.makeText(QuizActivity.this, "You guessed correct! Name: " + p.getName() + " Your Score: " + score, Toast.LENGTH_SHORT).show();
            scoreText.setText("Score: " + score + "/" + pList.size());
            counterText.setText("Persons left: " + (pList.size() - index));
        } else if (p.getName().toLowerCase().equals(guessText.getEditableText().toString().toLowerCase())) {
            score++;
            Toast.makeText(QuizActivity.this, "You guessed correct! Name: " + p.getName() + " Your Score: " + score, Toast.LENGTH_SHORT).show();
            scoreText.setText("Score: " + score + "/" + pList.size());
            counterText.setText("Persons left: " + (pList.size() - index));
        } else if (p.getName().toUpperCase().equals(guessText.getEditableText().toString().toUpperCase())) {
            score++;
            Toast.makeText(QuizActivity.this, "You guessed correct! Name: " + p.getName() + " Your Score: " + score, Toast.LENGTH_SHORT).show();
            scoreText.setText("Score: " + score + "/" + pList.size());
            counterText.setText("Persons left: " + (pList.size() - index));
        } else {
            Toast.makeText(QuizActivity.this, "You guessed INCORRECT, Correct name was: " + p.getName(), Toast.LENGTH_SHORT).show();
            counterText.setText("Persons left: " + (pList.size() - index));
        }
        guessText.setText("");
        nextPerson();

    }

    public void nextPerson() {
        if (pList.size() > index) {
            Student pers = pList.get(index);
            guessImage.setImageBitmap(pers.getImg());
        } else {
            guessImage.setImageBitmap(null);
            buttonGuess.setEnabled(false);
            guessText.setOnKeyListener(null);
            infoText.setText("QUIZ OVER, PRESS RESET BUTTON TO START AGAIN");
        }
    }

    public void resetQuiz(View View) {
        recreate();
    }

    public void goMenu(View View) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public String getFirstPerson(){
        return pList.get(0).getName();
    }

    public int getDatabaseSize(){
        return pList.size();
    }

    public void makeGuess(){
        if (guessText.getText().toString().equals("")) {
            Toast.makeText(QuizActivity.this, "Enter your name before you guess!", Toast.LENGTH_SHORT).show();
        } else {
            checkCorrect();
            // keep the keyboard up and focus on guessText so that the user does not have to click several times
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(guessText, InputMethodManager.SHOW_IMPLICIT);
        }
    }


}