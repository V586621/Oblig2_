package no.hvl.dat153.oblig2.main.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import no.hvl.dat153.oblig2.R;

@Database(entities = {Student.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)

public abstract class StudentRoomDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

    private static StudentRoomDatabase INSTANCE;

    public static StudentRoomDatabase getDatabase(final Context context) {
        final boolean[] doImport = { false };
        if (INSTANCE == null) {
            synchronized (StudentRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentRoomDatabase.class, "student_database").
                            addCallback(new RoomDatabase.Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            db.beginTransaction();
                            ContentValues values = new ContentValues();

                            values.put("studentName", "White");
                            values.put("studentImg", Converters.fromBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.whitecat))));
                            db.insert("students", SQLiteDatabase.CONFLICT_ABORT, values);

                            values.put("studentName", "Black");
                            values.put("studentImg", Converters.fromBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.blackcat))));
                            db.insert("students", SQLiteDatabase.CONFLICT_ABORT, values);

                            values.put("studentName", "Ginger");
                            values.put("studentImg", Converters.fromBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.gingercat))));
                            db.insert("students", SQLiteDatabase.CONFLICT_ABORT, values);

                            db.setTransactionSuccessful();
                            db.endTransaction();
                        }
                    }).
                   fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
