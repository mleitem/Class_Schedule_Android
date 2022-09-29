package com.example.meganleitem_c196pa.termscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;

import java.util.List;

public class AssessmentReportsAdapter extends RecyclerView.Adapter<AssessmentReportsAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView =  itemView.findViewById(R.id.textView5);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, ViewAssessment.class);
                    intent.putExtra("id", current.getAssessmentId());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("title", current.getAssessmentTitle());
                    intent.putExtra("start", current.getStartDate());
                    intent.putExtra("end", current.getEndDate());
                    intent.putExtra("course id", current.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private LayoutInflater mInflater;

    public AssessmentReportsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //Which item we want to show
    @NonNull
    @Override
    public AssessmentReportsAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentReportsAdapter.AssessmentViewHolder(itemView);
    }

    //Put things on the text view
    @Override
    public void onBindViewHolder(@NonNull AssessmentReportsAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String title = current.getAssessmentTitle();
            holder.assessmentItemView.setText(title);
        }
        else{
            holder.assessmentItemView.setText("No assessment title");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }
        else return 0;
    }
}
