
package com.kalher.meetingscheduler.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("participants")
    @Expose
    private List<String> participants = null;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getParticipantsDisplayString(){
        if(participants == null){
            return "";
        }
        StringBuilder builder = new StringBuilder("");
        for(int i=0;i<participants.size();i++){
            builder.append(participants.get(i));
            if(i < participants.size()-1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
