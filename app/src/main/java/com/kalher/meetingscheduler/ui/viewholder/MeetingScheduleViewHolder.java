package com.kalher.meetingscheduler.ui.viewholder;

import android.content.res.Configuration;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.kalher.meetingscheduler.base.MeetingScheduler;
import com.kalher.meetingscheduler.databinding.LayoutMeetingScheduleItemBinding;
import com.kalher.meetingscheduler.model.Example;

public class MeetingScheduleViewHolder extends RecyclerView.ViewHolder {

    private LayoutMeetingScheduleItemBinding binding;

    public MeetingScheduleViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        if(viewDataBinding instanceof LayoutMeetingScheduleItemBinding){
            binding = (LayoutMeetingScheduleItemBinding) viewDataBinding;
        }
    }

    public void bind(Example data){
        if(binding != null){
            if(MeetingScheduler.getsCurrentBaseActivity().getResources().getConfiguration()
                    .orientation == Configuration.ORIENTATION_PORTRAIT){
                binding.tvTime.setText(data.getStartTime() + "-" + data.getEndTime());
                binding.tvDescription.setText(data.getDescription());
            }else {
                binding.tvStartTime.setText(data.getStartTime());
                binding.tvEndTime.setText(data.getEndTime());
                binding.tvDescription.setText(data.getDescription());
                binding.tvAttendee.setText(data.getParticipantsDisplayString());
            }
        }
    }

}
