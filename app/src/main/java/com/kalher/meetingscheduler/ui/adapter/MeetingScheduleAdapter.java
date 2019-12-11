package com.kalher.meetingscheduler.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.kalher.meetingscheduler.R;
import com.kalher.meetingscheduler.model.Example;
import com.kalher.meetingscheduler.ui.viewholder.MeetingScheduleViewHolder;

import java.util.List;

public class MeetingScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Example> mDataList;

    public MeetingScheduleAdapter(List<Example> dataList){
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding  = DataBindingUtil.inflate(inflater, R.layout.layout_meeting_schedule_item, parent, false);
        return new MeetingScheduleViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MeetingScheduleViewHolder) holder).bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

}
