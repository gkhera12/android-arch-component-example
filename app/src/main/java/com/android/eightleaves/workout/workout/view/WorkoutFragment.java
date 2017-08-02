package com.android.eightleaves.workout.workout.view;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.eightleaves.workout.R;
import com.android.eightleaves.workout.databinding.FragmentWorkoutBinding;
import com.android.eightleaves.workout.db.entity.WorkoutEntity;
import com.android.eightleaves.workout.model.Workout;
import com.android.eightleaves.workout.workout.viewmodel.WorkoutViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class WorkoutFragment extends LifecycleFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FragmentWorkoutBinding mBinding;
    private WorkoutRecyclerViewAdapter mWorkoutAdapter;
    List<Float> dataList = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorkoutFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WorkoutFragment newInstance(int columnCount) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_workout, container, false);

        mWorkoutAdapter = new WorkoutRecyclerViewAdapter(this);
        mBinding.list.setAdapter(mWorkoutAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WorkoutViewModel.Factory factory = new WorkoutViewModel.Factory(getActivity().getApplication());
        final WorkoutViewModel viewModel =
                ViewModelProviders.of(this, factory).get(WorkoutViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(WorkoutViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getWorkouts().observe(this, new Observer<List<WorkoutEntity>>() {
            @Override
            public void onChanged(@Nullable List<WorkoutEntity> myWorkouts) {
                if (myWorkouts != null) {
                    mBinding.setIsLoading(false);
                    mWorkoutAdapter.setWorkoutList(myWorkouts);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }

    public void onListFragmentInteraction(Workout item){
    }
}
