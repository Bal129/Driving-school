package JavaDir;
// black diamond composition // white aggregation

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.PriorityQueue;

public class Student extends Person implements User {
    public static final String FIRSTPHASE = "Computer test";
    public static final String SECONDPHASE = "Mock Test";
    public static final String THIRDPHASE = "JPJ";

    private int curPhase;   // current phase
    private Payment paymentLog;
    private Phase[] arrayOfPhase = {
        new Phase(getIc(), FIRSTPHASE), 
        new Phase(getIc(), SECONDPHASE), 
        new Phase(getIc(), THIRDPHASE)
    };
    private PriorityQueue<Schedule> schedules = new PriorityQueue<>();
    private PriorityQueue<Schedule> histories = new PriorityQueue<>();
    private PriorityQueue<Notification> notifications = new PriorityQueue<>();

    Student() {}
    Student(
        String name, String ic, String email, String tel, boolean isMale, 
        String password, boolean isActive, String nationality, String vehicle, 
        float paymentPrice
    ) {

        super(name, ic, email, tel, isMale, password, isActive, nationality, vehicle);
        paymentLog = new Payment(paymentPrice);
    }

    Student(
        String name, String ic, String email, String tel, boolean isMale, 
        String password, boolean isActive, String nationality, String vehicle, 
        Payment paymentLog, Phase[] arrPhase, int curPhase
    ) {

        super(name, ic, email, tel, isMale, password, isActive, nationality, vehicle);
        this.paymentLog = paymentLog;
        setCurPhase(curPhase);
    }

    // getter
    public int getCurPhase() {
        return curPhase;
    }
    public Payment getPaymentLog() {
        return paymentLog;
    }
    public Phase[] getArrayOfPhase() {
        return arrayOfPhase;
    }
    
    // setter
    public void setCurPhase(int curPhase) {
        this.curPhase = curPhase;
    }

    public boolean chooseAppointment(String tutorIc, LocalDate date, LocalTime time) {
        for (Schedule appointment : CheckSchedule.availableDate) {
            if (
                appointment.getTutorIc().equals(tutorIc) && 
                appointment.getDate().isEqual(date) && 
                appointment.getTime().compareTo(time) == 0
            ) {
                appointment.setStudentIc(getIc());
                schedules.add(appointment);
                CheckSchedule.updateBookedDate(tutorIc, date, time, getIc());
                return true;
            }
        }
        return false;
    }

    public void makePayment(float amountPaid) {
        paymentLog.updatePaid(amountPaid);
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

    public boolean finishStudy(LocalDate date, LocalTime time) {
        for (Schedule appointment : schedules) {
            if (
                appointment.getDate().equals(date) && 
                appointment.getTime().equals(time)
                ) {
                appointment.switchIsExecute();
                histories.add(appointment);
                schedules.remove(appointment);
                CheckSchedule.updatePassedDate(getIc(), date, time);
                return true;
            }
        }
        return false;
    } 

    @Override
    public String toString() {
        return  getName()                            + "," 
                + getIc()                            + "," 
                + getEmail()                         + ","
                + getTel()                           + ","
                + getPassword()                      + "," 
                + getNationality()                   + ","
                + getVehicle()                       + ","
                + getIsMale()                        + ","
                + getIsActive()                      + ","
                + getCurPhase()                      + ","
                + getPaymentLog().getTotalPrice()    + ","
                + getPaymentLog().getPaid()          + ","
                + getPaymentLog().getCompleted()     + ","
                + getArrayOfPhase()[0]               + ","
                + getArrayOfPhase()[1]               + ","
                + getArrayOfPhase()[2];
    }

    public String toDBString() {
        return "('" + getName()                          + "','" 
                   + getIc()                             + "','" 
                   + getEmail()                          + "','"
                   + getTel()                            + "','"
                   + getPassword()                       + "','" 
                   + getNationality()                    + "','"
                   + getVehicle()                        + "','"
                   + getIsMale()                         + "','"
                   + getIsActive()                       + "','"
                   + getCurPhase()                       + "','"
                   + getPaymentLog().getTotalPrice()     + "','"
                   + getPaymentLog().getPaid()           + "','"
                   + getPaymentLog().getCompleted()      + "','"
                   + getArrayOfPhase()[0].getCompleted() + "','"
                   + getArrayOfPhase()[1].getCompleted() + "','"
                   + getArrayOfPhase()[2].getCompleted()
        + "')";
    }
}