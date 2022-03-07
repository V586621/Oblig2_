package no.hvl.dat153.oblig2.main.ui;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import no.hvl.dat153.oblig2.main.data.Student;
import no.hvl.dat153.oblig2.main.data.StudentRepository;
import no.hvl.dat153.oblig2.main.data.StudentRoomDatabase;

public class StudentViewModel extends AndroidViewModel {

    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;
    private MutableLiveData<List<Student>> searchResults;

    public StudentViewModel (Application application) {
        super(application);
        repository = new StudentRepository(application);
        allStudents = repository.getAllStudents();
        searchResults = repository.getSearchResults();
    }

    MutableLiveData<List<Student>> getSearchResults() {
        return searchResults;
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insertStudent(Student Student) {
        repository.insertStudent(Student);
    }

    public void findStudent(String name) {
        repository.findStudent(name);
    }

    public void deleteStudent(String name) {
        repository.deleteStudent(name);
    }

    public void deleteStudentId(int id) {
        repository.deleteStudentId(id);
    }

    public void deleteAllStudents() {
        repository.deleteAllStudents();
    }

}