package no.hvl.dat153.oblig2.main.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class Converters {

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bm){
        OutputStream outputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 30, outputStream);
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    @TypeConverter
    public Bitmap toBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
