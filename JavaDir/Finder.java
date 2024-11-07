package JavaDir;

import java.sql.SQLException;
import java.util.ArrayList;

public class Finder {
    public static Database database;
    public static Admin admin;
    public static ArrayList<Tutor> tutors;
    public static ArrayList<Person> persons;
    public static ArrayList<Student> students;

    public static void loadDatabase() throws SQLException, ClassNotFoundException {
        database    = new Database();
        students    = database.loadstudents();
        tutors      = database.loadtutors();
        persons     = database.loadPersons();
        admin       = database.loadAdmin();
    }

    public static Student getStudent(String id) {
        for (Student student : students)
            if(student.getIc().equals(id))
                return student;
       return null;
    }

    public static Tutor getTutor(String id) {
        for (Tutor tutor : tutors)
            if(tutor.getIc().equals(id))
                return tutor;
       return null;
    }

    public static Person getPerson(String id) {
        for (Person person : persons)
            if(person.getIc().equals(id))
                return person;
       return null;
    }
}
