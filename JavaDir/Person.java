package JavaDir;
import java.util.PriorityQueue;

public class Person {
    private String name , ic , email , tel , password , nationality, vehicle; 
    private boolean isMale = false;
    private boolean isActive = true; 

    Person() {}
    Person(
        String name, String ic, String email, 
        String tel, boolean isMale, String password, 
        boolean isActive, String nationality, String vehicle
    ) {
        setName(name);
        setIc(ic);
        setEmail(email);
        setTel(tel);
        setPassword(password);
        setNationality(nationality);
        setVehicle(vehicle);
        this.isMale = isMale;
        this.isActive = isActive;
    }

    ///////getter///////
    public String getName() {
        return name;
    }
    public String getIc() { 
        return ic;
    }
    public String getEmail() {
        return email;
    }
    public String getTel() {
        return tel;
    }
    public String getPassword() {
        return password;
    }
    public String getNationality() {
        return nationality;
    }
    public boolean getIsMale() {
        return isMale;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public String getVehicle() {
        return vehicle;
    }

    ///////setter///////
    public void setName(String name) {
        this.name = name;
    }
    public void setIc(String ic) {
        this.ic = ic;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void switchMale() {
        isMale = !isMale;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void switchActive() {
        isActive = !isActive;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public PriorityQueue<Schedule> getSchedules() {
        return null;
    }
    public PriorityQueue<Schedule> getHistories() {
        return null;
    }
    public PriorityQueue<Notification> getNotification() {
        return null;
    }
}