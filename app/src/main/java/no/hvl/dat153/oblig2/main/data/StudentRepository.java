package no.hvl.dat153.oblig2.main.data;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import android.app.Application;

import androidx.lifecycle.LiveData;


public class StudentRepository {

    StudentRoomDatabase db;
    private MutableLiveData<List<Student>> searchResults =
            new MutableLiveData<>();

    private LiveData<List<Student>> allStudents;
    private StudentDao StudentDao;

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public MutableLiveData<List<Student>> getSearchResults() {
        return searchResults;
    }

    public StudentRepository(Application application) {
        db = StudentRoomDatabase.getDatabase(application);
        StudentDao = db.studentDao();
        allStudents = StudentDao.getAllStudents();
    }

    public void insertStudent(Student newStudent) {
        InsertAsyncTask task = new InsertAsyncTask(StudentDao);
        task.execute(newStudent);
    }

    public void deleteStudent(String name) {
        DeleteAsyncTask task = new DeleteAsyncTask(StudentDao);
        task.execute(name);
    }

    public void deleteStudentId(int id) {
        DeleteIdAsyncTask task = new DeleteIdAsyncTask(StudentDao);
        task.execute(id);
    }

    public void deleteAllStudents() {
        DeleteAllAsyncTask task = new DeleteAllAsyncTask(StudentDao);
        task.execute();
    }


    public void findStudent(String name) {
        QueryAsyncTask task = new QueryAsyncTask(StudentDao);
        task.delegate = this;
        task.execute(name);
    }

    private void asyncFinished(List<Student> results) {
        searchResults.setValue(results);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<Student>> {

        private StudentDao asyncTaskDao;
        private StudentRepository delegate = null;

        QueryAsyncTask(StudentDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Student> doInBackground(final String... params) {
            return asyncTaskDao.findStudent(params[0]);
        }

        @Override
        protected void onPostExecute(List<Student> result) {
            delegate.asyncFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {

        private StudentDao asyncTaskDao;

        InsertAsyncTask(StudentDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Student... params) {
            asyncTaskDao.insertStudent(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {

        private StudentDao asyncTaskDao;

        DeleteAsyncTask(StudentDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteStudent(params[0]);
            return null;
        }
    }

    private static class DeleteIdAsyncTask extends AsyncTask<Integer, Void, Void> { //FUNKET IKKE !!!!!!! MED INT ???

        private StudentDao asyncTaskDao;

        DeleteIdAsyncTask(StudentDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            asyncTaskDao.deleteIdStudent(params[0]);
            return null;
        }
    }


    private static class DeleteAllAsyncTask extends AsyncTask<String, Void, Void> {

        private StudentDao asyncTaskDao;

        DeleteAllAsyncTask(StudentDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteAllStudents();
            return null;
        }
    }


}
