package com.kalher.meetingscheduler.helper;

import com.kalher.meetingscheduler.model.Example;
import com.kalher.meetingscheduler.utils.DateUtility;

import java.util.Comparator;

public class MeetingScheduleComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        boolean isBefore = DateUtility.timeToCalendar(((Example)o1).getStartTime()).
                before(DateUtility.timeToCalendar(((Example)o2).getStartTime()));
        return isBefore ? -1 : 1;
    }

}
