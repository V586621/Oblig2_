package no.hvl.dat153.oblig2.main.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import no.hvl.dat153.oblig2.R;
import no.hvl.dat153.oblig2.main.data.Student;
import no.hvl.dat153.oblig2.main.data.StudentDao;
import no.hvl.dat153.oblig2.main.data.StudentRoomDatabase;
import no.hvl.dat153.oblig2.main.ui.StudentListAdapter;
import no.hvl.dat153.oblig2.main.ui.StudentViewModel;

public class AddActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText imageText;
    private Button buttonBrowse;
    private Button buttonCamera;
    private Button buttonAdd;
    private StudentViewModel mViewModel;
    private Button buttonMenu;
    public ArrayList<Student> pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        buttonAdd = (Button) findViewById(R.id.addButton);
        buttonMenu = (Button) findViewById(R.id.buttonMenu2);
        imageView = (ImageView) findViewById(R.id.imagePerson); //Image for new person
        imageText = (EditText) findViewById(R.id.newPerson); //Name for new person
        buttonBrowse = (Button) findViewById(R.id.browseButton); //Button for browsing pictures
        mViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        observerSetup();

        buttonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageText.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this, "write name before selecting image", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 3);
                }
            }
        });


        buttonCamera = (Button) findViewById(R.id.cameraButton); //button for selecting picture
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageText.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this, "write name before selecting image", Toast.LENGTH_SHORT).show();
                } else {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //gallery
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && data != null && resultCode == RESULT_OK) { //
            final Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            addPicture(selectedImage);
        }
        // Camera
        else if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(selectedImage);
            addCamera(selectedImage);
        }
    }

    private void addPicture(final Uri valgtImage) {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = imageText.getText().toString();
                Bitmap personImage = null;
                try {
                    personImage = MediaStore.Images.Media.getBitmap(getContentResolver(), valgtImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Student s = new Student(name, personImage);
                mViewModel.insertStudent(s);
                Toast.makeText(AddActivity.this, "Person: " + s.getName() + " was added to the LIST! Click DATABASE button to see changes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observerSetup() {
        mViewModel.getAllStudents().observe(this,
                new Observer<List<Student>>() {
                    @Override
                    public void onChanged(@Nullable final List<Student> Students) {
                        pList = new ArrayList<Student>(Students);
                    }
                });
    }

    private void addCamera(Bitmap bm) {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = imageText.getText().toString();
                Student s = new Student(name, bm);

                mViewModel.insertStudent(s);
                Toast.makeText(AddActivity.this, "Person: " + s.getName() +
                        " was added to the LIST! Click DATABASE button to see changes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getDatabaseSize(){
        return pList.size();
    }

    public void goDatabase(View View) {
        Intent i = new Intent(this, DatabaseActivity.class);
        startActivity(i);
    }

    public void goMenu(View View) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}