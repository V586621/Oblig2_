package no.hvl.dat153.oblig2.main.data;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.concurrent.atomic.AtomicInteger;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "studentId")
    private int id;

    @ColumnInfo(name="studentName")
    private String name;

    @ColumnInfo(name="studentImg")
    private Bitmap img;

    public Student(String name, Bitmap img){
        this.name = name;
        this.img = img;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }



}
