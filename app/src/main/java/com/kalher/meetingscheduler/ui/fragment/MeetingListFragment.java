package com.kalher.meetingscheduler.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalher.meetingscheduler.R;
import com.kalher.meetingscheduler.custom.wrapper.DataWrapper;
import com.kalher.meetingscheduler.custom.Status;
import com.kalher.meetingscheduler.databinding.FragmentMeetingListBinding;
import com.kalher.meetingscheduler.model.Example;
import com.kalher.meetingscheduler.ui.adapter.MeetingScheduleAdapter;
import com.kalher.meetingscheduler.ui.viewmodel.MeetingListFragmentViewmodel;
import com.kalher.meetingscheduler.utils.DateUtility;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class MeetingListFragment extends Fragment implements View.OnClickListener {

    private MeetingListFragment.OnFragmentInteractionListener mListener;
    private MeetingListFragmentViewmodel mViewModel;

    private FragmentMeetingListBinding binding;

    private MeetingScheduleAdapter mMeetingScheduleAdapter;

    public MeetingListFragment() {
    }

    public static MeetingListFragment newInstance() {
        MeetingListFragment fragment = new MeetingListFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MeetingListFragment.OnFragmentInteractionListener) {
            mListener = (MeetingListFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MeetingListFragmentViewmodel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.ivPrev.setOnClickListener(this);
        binding.tvMsgPrev.setOnClickListener(this);
        binding.ivNext.setOnClickListener(this);
        binding.tvMsgNext.setOnClickListener(this);
        binding.btnScheduleMeeting.setOnClickListener(this);
        if(DateUtility.isDateSame(Calendar.getInstance(), mViewModel.currentSelectedDate)){
            setPrevBtnEnable(false);
        }
        loadAndSetData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_prev:
            case R.id.tv_msg_prev:
                handleDateChange(false);
                break;

            case R.id.iv_next:
            case R.id.tv_msg_next:
                handleDateChange(true);
                break;

            case R.id.btn_schedule_meeting:
                mListener.onFragmentInteraction(R.id.btn_schedule_meeting);
                break;

        }
    }

    private void handleDateChange(boolean isNext) {
        if (!DateUtility.isDateSame(Calendar.getInstance(), mViewModel.currentSelectedDate) || isNext) {
            int diff = isNext ? 1 : -1;
            mViewModel.meetingScheduleList = Collections.EMPTY_LIST;
            mViewModel.currentSelectedDate.add(Calendar.DAY_OF_MONTH, diff);
            setPrevBtnEnable(true);
            loadAndSetData();
        }
        if(DateUtility.isDateSame(Calendar.getInstance(), mViewModel.currentSelectedDate)){
            setPrevBtnEnable(false);
        }
    }

    private void loadAndSetData(){
        final LiveData<DataWrapper> data = mViewModel
                .getMeetingSchedule(DateUtility.getDateDDMMYYYY(mViewModel.currentSelectedDate));

        if (data.getValue() != null) {
            inflateNewData(data.getValue());    // if data is available in cache
        } else {
            setFullScreenMessage(Status.FETCHING);
        }

        data.observe(getViewLifecycleOwner(), new Observer<DataWrapper>() {
            @Override
            public void onChanged(DataWrapper dataWrapper) {
                inflateNewData(dataWrapper);
            }
        });
    }

    private void inflateNewData(DataWrapper dataWrapper) {
        setFullScreenMessage(dataWrapper.getStatus());
        if (dataWrapper.getData() != null) {
            binding.tvTitle.setText(DateUtility.getDateDDMMYYYY(mViewModel.currentSelectedDate));
            mViewModel.meetingScheduleList = (List<Example>) dataWrapper.getData();
            setMeetingScheduleList();
            Timber.i(DateUtility.getDateDDMMYYYY(mViewModel.currentSelectedDate), mViewModel.meetingScheduleList);
        }
    }

    private void setMeetingScheduleList() {
        if (mMeetingScheduleAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            MeetingScheduleAdapter adapter = new MeetingScheduleAdapter(mViewModel.meetingScheduleList);
            binding.rvMeetingSchedule.setLayoutManager(linearLayoutManager);
            binding.rvMeetingSchedule.setAdapter(adapter);
        } else {
            mMeetingScheduleAdapter.notifyDataSetChanged();
        }
    }

    private void setFullScreenMessage(Status status) {
        switch (status) {
            case FAILED:
                binding.rlMeetinglistContainer.setVisibility(View.GONE);
                binding.layoutMessage.getRoot().setVisibility(View.VISIBLE);
                binding.layoutMessage.ivMessageImage.setImageResource(R.drawable.some_error);
                binding.layoutMessage.tvMessage.setText(status.getMessage());
                break;

            case NO_NETWORK_CONNECTION:
                binding.rlMeetinglistContainer.setVisibility(View.GONE);
                binding.layoutMessage.getRoot().setVisibility(View.VISIBLE);
                binding.layoutMessage.ivMessageImage.setImageResource(R.drawable.no_network);
                binding.layoutMessage.tvMessage.setText(status.getMessage());
                break;

            case FETCHING:
                binding.rlMeetinglistContainer.setVisibility(View.GONE);
                binding.layoutMessage.getRoot().setVisibility(View.VISIBLE);
                binding.layoutMessage.pbFetchData.setVisibility(View.VISIBLE);
                binding.layoutMessage.ivMessageImage.setVisibility(View.GONE);
                binding.layoutMessage.tvMessage.setText(status.getMessage());
                break;

            case SUCCESS:
                binding.rlMeetinglistContainer.setVisibility(View.VISIBLE);
                binding.layoutMessage.getRoot().setVisibility(View.GONE);
                break;
        }
    }

    private void setPrevBtnEnable(boolean isEnable) {
        binding.ivPrev.setVisibility(isEnable ? View.VISIBLE : View.INVISIBLE);
        binding.tvMsgPrev.setVisibility(isEnable ? View.VISIBLE : View.INVISIBLE);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int viewId);
    }

}
