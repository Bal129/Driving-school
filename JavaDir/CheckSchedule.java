package JavaDir;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface CheckSchedule {
    public static final ArrayList<Schedule> bookedDate      = new ArrayList<>();
    public static final ArrayList<Schedule> passedDate      = new ArrayList<>();
    public static final ArrayList<Schedule> availableDate   = new ArrayList<>();

    public static boolean updateBookedDate(String tutorIc, LocalDate date, LocalTime time, String studentIc) {
        for (Schedule schedule : availableDate) {
            if (
                schedule.getTutorIc().equals(tutorIc) && 
                schedule.getDate().isEqual(date) && 
                schedule.getTime().compareTo(time) == 0
            ) {
                schedule.setStudentIc(studentIc);
                bookedDate.add(schedule);
                availableDate.remove(schedule);
                return true;
            }
        }
        return false;
    }

    public static boolean updatePassedDate(String tutorIc, LocalDate date, LocalTime time) {
        for (Schedule schedule : bookedDate) {
            if (
                schedule.getTutorIc().equals(tutorIc) && 
                schedule.getDate().isEqual(date) && 
                schedule.getTime().compareTo(time) == 0
            ) {
                if (!schedule.getIsExecute()) schedule.switchIsExecute();
                passedDate.add(schedule);
                bookedDate.remove(schedule);
                return true;
            }
        }
        return false;
    }

}

