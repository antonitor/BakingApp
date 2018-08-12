package com.torres.toni.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.torres.toni.bakingapp.pojo.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListAdapterViewHolder> {

    private List<Step> mStepList;
    private final Context mContext;
    private final StepListClickHandler mClickHandler;
    private int mSelectedIndex = -1;

    public StepListAdapter(List<Step> stepsList, Context context, StepListClickHandler clickHandler) {
        this.mStepList = stepsList;
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    public interface StepListClickHandler{
        public void onStepItemClicked(int stepId);
    }

    @NonNull
    @Override
    public StepListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_list_item, parent, false);
        return new StepListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepListAdapterViewHolder holder, final int position) {
        final Step step = mStepList.get(position);
        holder.stepDescription.setText(step.getShortDescription());
        holder.stepNumber.setText(String.valueOf(step.getId()+1));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickHandler.onStepItemClicked(step.getId());
                mSelectedIndex = position;
                notifyDataSetChanged();
            }
        });
        if(mSelectedIndex==position) {
            holder.linearLayout.setBackgroundResource(R.drawable.round_corner_alt);
        } else {
            holder.linearLayout.setBackgroundResource(R.drawable.round_corner_filled);
        }

    }

    @Override
    public int getItemCount() {
        if (mStepList == null) return 0;
        return mStepList.size();
    }

    class StepListAdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.step_description) TextView stepDescription;
        @BindView(R.id.step_number) TextView stepNumber;
        @BindView(R.id.step_linear_layout) LinearLayout linearLayout;
        int stepId;

        public StepListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setSteps(List<Step> steps) {
        mStepList = steps;
        notifyDataSetChanged();
    }
}
