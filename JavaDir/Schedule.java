package JavaDir;

import java.time.LocalDate;
import java.time.LocalTime;
import java.lang.Comparable;

public class Schedule implements Comparable<Schedule> {
    private String studentIc;
    private String tutorIc;
    private boolean isExecute;
    private LocalDate date;
    private LocalTime time;

    Schedule() {}
    Schedule(String tutorIc, LocalDate date, LocalTime time) {
        setDate(date);
        setTime(time);
        setTutorIc(tutorIc);
    }
    
    Schedule(String studentIc, String tutorIc, boolean isExecute, LocalDate date, LocalTime time) {
        setDate(date);
        setTime(time);
        setStudentIc(studentIc);
        setTutorIc(tutorIc);
        this.isExecute = isExecute;
    }

    // getter
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public boolean getIsExecute() {
        return isExecute;
    }
    public String getStudentIc() {
        return studentIc;
    }
    public String getTutorIc() {
        return tutorIc;
    }

    // setter
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void switchIsExecute() {
        isExecute = !isExecute;
    }
    public void setStudentIc(String studentIc) {
        this.studentIc = studentIc;
    }
    public void setTutorIc(String tutorIc) {
        this.tutorIc = tutorIc;
    }

    @Override
    public int compareTo(Schedule refSchedule) {
        if (date.isAfter(refSchedule.getDate()))
            return 1;
        if (date.isBefore(refSchedule.getDate()))
            return -1;
        if (time.isAfter(refSchedule.getTime()))
            return 1;
        if (time.isBefore(refSchedule.getTime()))
            return -1;      
        return 0;      
    }
    
    @Override
    public String toString() {
        return "Date: " + date + ", Time: " + time;
    }

    public String toCSVString() {
        return getStudentIc()       + ","
               + getTutorIc()       + ","
               + getIsExecute()     + ","
               + getDate()          + ","
               + getTime();
    }

    public String toDBString() {
        return "('" + getStudentIc() + "','"
               + getTutorIc()       + "','"
               + getIsExecute()     + "','"
               + getDate()          + "','"
               + getTime()
               + "')";
    }
}
