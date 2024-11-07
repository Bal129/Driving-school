package JavaDir;

import java.time.LocalDate;

public class Notification implements Comparable<Notification> {
    private String studentIc;
    private String tutorIc;
    private String alertMessage;
    private LocalDate date;
    private boolean read;

    // getter
    public String getStudentIc() {
        return studentIc;
    }
    public String getTutorIc() {
        return tutorIc;
    }
    public String getAlertMessage() {
        return alertMessage;
    }
    public LocalDate getDate() {
        return date;
    }
    public boolean getRead() {
        return read;
    }
    public String getIc() {
        return studentIc != null? studentIc : tutorIc;
    }

    // setter
    public void setStudentIc(String studentIc) {
        this.studentIc = studentIc;
    }
    public void setTutorIc(String tutorIc) {
        this.tutorIc = tutorIc;
    }
    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setRead(boolean read) {
        this.read = read;
    }

    //constructor
    Notification(){}
    Notification(String studentIc,String tutorIc,String alertMessage,LocalDate date,boolean read){
        setStudentIc(studentIc);
        setTutorIc(tutorIc);
        setAlertMessage(alertMessage);
        setDate(date);
        setRead(read);
    }

    @Override
    public int compareTo(Notification refNotification) {
        if (date.isAfter(refNotification.getDate()))
            return 1;
        if (date.isBefore(refNotification.getDate()))
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return getStudentIc()       + ","
               + getTutorIc()       + ","
               + getAlertMessage()  + ","
               + getDate()          + ","
               + getRead();
    }

    public String toDBString() {
        return "('" 
                + getStudentIc()     + "','"
                + getTutorIc()       + "','"
                + getAlertMessage()  + "','"
                + getDate()          + "','"
                + getRead() 
                + "')";
    }
}
