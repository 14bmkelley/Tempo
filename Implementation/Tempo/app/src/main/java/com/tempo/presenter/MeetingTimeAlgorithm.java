package com.tempo.presenter;

import com.tempo.model.CalendarEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by bkell on 3/15/2017.
 */

class MeetingTimeAlgorithm {

    public static List<MeetingTime> run(List<List<CalendarEvent>> events, long start, long end, long duration) {

        int numUsers = events.size();

        HashMap<Long, Integer> usersFree = initializeUserFreeMap(start, end, numUsers);

        calculateFreeTimes(events, start, usersFree);

        List<MeetingTime> availableTimes = calculateAvailableTimes(start, end, duration, numUsers, usersFree);

        Collections.sort(availableTimes);

        return availableTimes;

    }

    private static HashMap<Long, Integer> initializeUserFreeMap(long start, long end, int count) {

        HashMap<Long, Integer> usersFree = new HashMap<Long, Integer>();

        for (long t = start; t <= end; t += 1000) {
            usersFree.put(t, count);
        }

        return usersFree;

    }

    private static void calculateFreeTimes(List<List<CalendarEvent>> userCalendars,
                                           long start, HashMap<Long, Integer> usersFree) {

        for (List<CalendarEvent> userCalendar : userCalendars) {
            for (CalendarEvent userEvent : userCalendar) {
                HashSet<Long> uniqueTimeForUser = new HashSet<Long>();
                long offset = (userEvent.getStartTime() - start) % 1000;
                for (long t = userEvent.getStartTime() - offset; t <= userEvent.getEndTime(); t += 1000) {
                    if (!uniqueTimeForUser.contains(t) && usersFree.containsKey(t)) {
                        uniqueTimeForUser.add(t);
                        usersFree.put(t, usersFree.get(t) - 1);
                    }
                }
            }
        }

    }

    private static List<MeetingTime> calculateAvailableTimes(long start, long end, long duration, int numUsers, HashMap<Long, Integer> usersFree) {

        List<MeetingTime> availableTimes = new ArrayList<MeetingTime>();

        for (long t = start; t <= end; t += 1000) {
            int minAttendance = numUsers;
            long spanT = t;
            for ( ; spanT <= t + duration; spanT += 1000) {
                if (usersFree.containsKey(spanT)) {
                    int tempAttendance = usersFree.get(spanT);
                    if (tempAttendance < minAttendance) {
                        minAttendance = tempAttendance;
                    }
                }
            }
            availableTimes.add(new MeetingTime(minAttendance, spanT));
        }

        return availableTimes;

    }

    static class MeetingTime implements Comparable<MeetingTime> {

        private int attendance;
        private long time;

        public MeetingTime(int attendance, long time) {
            this.attendance = attendance;
            this.time = time;
        }

        public int compareTo(MeetingTime other) {
            if (this.attendance == other.attendance) {
                if (this.time < other.time) {
                    return -1;
                }
                return 1;
            }
            return other.attendance - this.attendance;
        }

        public int getAttendance() {
            return attendance;
        }

        public long getTime() {
            return time;
        }

    }

}
