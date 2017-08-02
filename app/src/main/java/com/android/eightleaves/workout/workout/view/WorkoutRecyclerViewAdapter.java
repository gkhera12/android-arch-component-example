package com.android.eightleaves.workout.workout.view;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.eightleaves.workout.R;
import com.android.eightleaves.workout.model.Workout;

import java.util.List;
import java.util.Objects;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Workout} and makes a call to the
 * specified {@link WorkoutFragment}.
 */
public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.ViewHolder> {

    List<? extends Workout> mWorkoutList;

    @Nullable
    private WorkoutFragment mListener;

    public WorkoutRecyclerViewAdapter(@Nullable WorkoutFragment listener) {
        mListener = listener;
    }

    public void setWorkoutList(final List<? extends Workout> workoutList) {
        if (mWorkoutList == null) {
            mWorkoutList = workoutList;
            notifyItemRangeInserted(0, workoutList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mWorkoutList.size();
                }

                @Override
                public int getNewListSize() {
                    return workoutList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mWorkoutList.get(oldItemPosition).getId() ==
                            workoutList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Workout workout = workoutList.get(newItemPosition);
                    Workout old = workoutList.get(oldItemPosition);
                    return workout.getId() == old.getId()
                            && Objects.equals(workout.getName(), old.getName());
                }
            });
            mWorkoutList = workoutList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mWorkoutList.get(position);
        holder.nameView.setText(mWorkoutList.get(position).getName());
        holder.mWorkoutTypeView.setText(mWorkoutList.get(position).getWorkoutType());
        holder.mWorkoutTimeView.setText(mWorkoutList.get(position).getTotalTime());
        holder.mWorkoutLevelView.setText(mWorkoutList.get(position).getTrainingLevel());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWorkoutList == null ? 0 : mWorkoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameView;
        public final TextView mWorkoutTypeView;
        public final TextView mWorkoutTimeView;
        public final TextView mWorkoutLevelView;

        public Workout mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = (TextView) view.findViewById(R.id.workout_name);
            mWorkoutTypeView = (TextView) view.findViewById(R.id.workout_type);
            mWorkoutTimeView = (TextView) view.findViewById(R.id.workout_time);
            mWorkoutLevelView = (TextView) view.findViewById(R.id.workout_level);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mWorkoutTypeView.getText() + "'";
        }
    }
}
