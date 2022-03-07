package no.hvl.dat153.oblig2.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import no.hvl.dat153.oblig2.R;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goDatabase(View View) {
        Intent i = new Intent(this, DatabaseActivity.class);
        startActivity(i);
    }

    public void goQuiz(View View) {
        Intent i = new Intent(this, QuizActivity.class);
        startActivity(i);
    }

    public void goAdd(View View) {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
    }

    //This way when user is on menu the user can not go "back" to another activity without pressing button for activity
    @Override
    public void onBackPressed(){
    }

}