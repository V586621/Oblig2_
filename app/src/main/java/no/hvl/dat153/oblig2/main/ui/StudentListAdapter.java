package no.hvl.dat153.oblig2.main.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import no.hvl.dat153.oblig2.R;
import no.hvl.dat153.oblig2.main.data.Student;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private int StudentItemLayout;
    private List<Student> StudentList;

    public StudentListAdapter(int layoutId) {
        StudentItemLayout = layoutId;
    }

    public void setStudentList(List<Student> Students) {
        StudentList = Students;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return StudentList == null ? 0 : StudentList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(StudentItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView itemText = holder.itemText;
        itemText.setText(StudentList.get(listPosition).getName());


        ImageView itemImg = holder.itemImg;
        itemImg.setImageBitmap(StudentList.get(listPosition).getImg()); //load her??

        TextView itemId = holder.itemId;
        itemId.setText("Id:" + String.valueOf(StudentList.get(listPosition).getId()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        ImageView itemImg;
        TextView itemId;

        ViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.student_row);
            itemImg = itemView.findViewById(R.id.student_img);
            itemId = itemView.findViewById(R.id.student_id);
        }
    }

}
