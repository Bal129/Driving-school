package JavaDir;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Admin extends Person {
    private ArrayList<Person> persons = new ArrayList<>();
    private PriorityQueue<Notification> notifications = new PriorityQueue<>();

    Admin() {}
    Admin(
        String name, String ic, String email, 
        String tel, boolean isMale, String password, 
        boolean isActive, String nationality, String vehicle
    ) {
        super(name, ic, email, tel, isMale, password, isActive, nationality, vehicle);
    }

    Admin(
        String name, String ic, String email, 
        String tel, boolean isMale, String password, 
        boolean isActive, String nationality, String vehicle, 
        ArrayList<Person> persons
    ) {
        super(name, ic, email, tel, isMale, password, isActive, nationality, vehicle);
        this.persons = persons;
    }

    // getter
    public ArrayList<Person> getPersons() {
        return persons;
    }

    public Person getProfile(String ic){
        for (Person person : persons)
            if (person.getIc().equals(ic))
                return person;
        return null;
    }

    public PriorityQueue<Schedule> getSchedule(User user) {
        return user.getSchedules();
    }

    public PriorityQueue<Schedule> getHistories(User user) {
        return user.getHistories();
    }

    public PriorityQueue<Notification> getNotification(User user) { // users noti
        return user.getNotification();
    }

    public PriorityQueue<Notification> getNotification() {  // admin noti
        return notifications;
    }

    public void distributeModule(Tutor tutor) {
        tutor.switchModuleReceived();
    }

    public PriorityQueue<Report> getReports (Tutor tutor) {
        return tutor.getReports();
    }

    public Payment getPayment (Student student) {
        return student.getPaymentLog();
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
                + getIsActive();
    }
}