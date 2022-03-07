package no.hvl.dat153.oblig2.main.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Query ("SELECT * FROM students WHERE studentName = :name")
    List<Student> findStudent(String name);

    @Query ("DELETE FROM students WHERE studentName= :name")
    void deleteStudent(String name);

    @Query ("DELETE FROM students WHERE studentId= :id")
    void deleteIdStudent(int id);

    @Query ("DELETE FROM students")
    void deleteAllStudents();

    @Query ("SELECT * FROM students")
    LiveData<List<Student>> getAllStudents();


}
