package no.hvl.dat153.oblig2.main.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import no.hvl.dat153.oblig2.R;
import no.hvl.dat153.oblig2.main.data.Student;
import no.hvl.dat153.oblig2.main.ui.StudentListAdapter;
import no.hvl.dat153.oblig2.main.ui.StudentViewModel;

public class DatabaseActivity extends AppCompatActivity {

    private Button buttonDelete;
    private EditText editTextDelete;
    private StudentViewModel mViewModel;
    private StudentListAdapter adapter;
    public ArrayList<Student> pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        buttonDelete = findViewById(R.id.button_delete);
        editTextDelete = findViewById(R.id.edittext_delete);
        observerSetup();
        recyclerSetup();
        deleteItemOnclick();
    }

    public void deleteItemOnclick() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextDelete.getText().toString().equals("")) {
                    Toast.makeText(DatabaseActivity.this, "Write in ID before deleting!", Toast.LENGTH_SHORT).show();
                } else {
                    mViewModel.deleteStudentId(Integer.parseInt(editTextDelete.getText().toString()));
                }
            }
        });
    }

    public void goAdd(View View) {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
    }

    public void goMenu(View View) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void resetView(View View) {
        Intent i = new Intent(this, DatabaseActivity.class);
        startActivity(i);
    }

    private void observerSetup() {
        mViewModel.getAllStudents().observe(this,
                new Observer<List<Student>>() {
                    @Override
                    public void onChanged(@Nullable final List<Student> Students) {
                        pList = new ArrayList<Student>(Students);
                        adapter.setStudentList(Students);
                    }
                });
    }

    public void deleteItem(int id) {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteStudentId(id);
            }
        });
    }

    private void recyclerSetup() {
        RecyclerView recyclerView;
        adapter = new StudentListAdapter(R.layout.student_list_item);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public int getFirstPersonId() {
        return pList.get(0).getId();
    }

    public int getDatabaseSize() {
        return pList.size();
    }

}