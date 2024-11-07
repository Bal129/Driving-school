package JavaDir;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.PriorityQueue;

public class Tutor extends Person implements User {
    private boolean moduleReceived;
    private PriorityQueue<Report> reports = new PriorityQueue<>(); 
    private PriorityQueue<Schedule> schedules = new PriorityQueue<>();
    private PriorityQueue<Schedule> histories = new PriorityQueue<>();
    private PriorityQueue<Notification> notifications = new PriorityQueue<>();

    Tutor() {}
    Tutor(
        String name, String ic, String email, 
        String tel, Boolean isMale, String password, 
        Boolean isActive, String nationality, String vehicle
        ) {
        super(name, ic, email, tel, isMale, password, isActive, nationality, vehicle);

    }
    
    Tutor(
        String name, String ic, String email, String tel, 
        Boolean isMale, String password, Boolean isActive, 
        String nationality, String vehicle, Boolean moduleReceived
        ) {
        super(name, ic, email, tel, isMale, password, isActive, nationality, vehicle);
        this.moduleReceived = moduleReceived;
    }

    // getter
    public boolean getModuleReceived() {
        return moduleReceived;
    }

    public PriorityQueue<Report> getReports(){
        return reports;
    }

    // setter
    public void switchModuleReceived() {
        moduleReceived = !moduleReceived;
    }

    // methods
    public void setAvailability(LocalDate date, LocalTime time) {
        Schedule tempSchedule = new Schedule(getIc(), date, time);
        CheckSchedule.availableDate.add(tempSchedule);
        schedules.add(tempSchedule);
    }

    public void sendReport(int week) {
        reports.add(new Report(getIc(), week));
    }

    public PriorityQueue<Schedule> getSchedules() {
        return schedules;
    }

    public PriorityQueue<Schedule> getHistories() {
        return histories;
    }

    public PriorityQueue<Notification> getNotification() {
        return notifications;
    }

    @Override
    public String toString() {
        return  getName()               + "," 
                + getIc()               + "," 
                + getEmail()            + ","
                + getTel()              + ","
                + getPassword()         + "," 
                + getNationality()      + ","
                + getVehicle()          + ","
                + getIsMale()           + ","
                + getIsActive()         + ","
                + getModuleReceived();
    }

    public String toDBString() {
        return  "('" + getName()        + "','" 
                + getIc()               + "','" 
                + getEmail()            + "','"
                + getTel()              + "','"
                + getPassword()         + "','" 
                + getNationality()      + "','"
                + getVehicle()          + "','"
                + getIsMale()           + "','"
                + getIsActive()         + "','"
                + getModuleReceived()
                + "')";
    }
}
