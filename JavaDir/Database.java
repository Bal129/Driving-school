package JavaDir;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class Database {
    private Admin admin;
    private ArrayList<Tutor>        tutors          = new ArrayList<>();
    private ArrayList<Person>       persons         = new ArrayList<>();
    private ArrayList<Report>       reports         = new ArrayList<>();
    private ArrayList<Student>      students        = new ArrayList<>();

    // constructor 
    Connection conn = null;

    Database() throws SQLException, ClassNotFoundException {
        connectSQL();
        readAdmin();
        readTutor();
        readReport();
        readStudent();
        readSchedule();
        readNotification();
    }

    private void connectSQL() throws SQLException, ClassNotFoundException {
        Class.forName(DBI.DBMS_JDBC);
        conn = DriverManager.getConnection(DBI.DBMS_URL, DBI.DBMS_USER, DBI.DBMS_PASS);
    }

    private ResultSet querySQL(String query) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.println(query); // for debugging purposes
        return statement.executeQuery();
    }

    private void insertSQL(String tableName, String columnName, String columnValue) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO "+tableName+" "+columnName+" VALUES "+columnValue);
        System.out.println("INSERT INTO "+tableName+" "+columnName+" VALUES "+columnValue); // for debugging purposes
        statement.executeUpdate();
    }

    private void updateSQL(String tableName, String columnName, String columnValue, String condition) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE "+tableName+" SET "+ columnName +"='"+columnValue+"'"+" WHERE "+condition);
        System.out.println("UPDATE "+tableName+" SET "+ columnName +"='"+columnValue+"'"+" WHERE "+condition); // for debugging purposes
        statement.executeUpdate();
    }

    private Student getStudent(String id) {
        for (Student student : students)
            if(student.getIc().equals(id))
                return student;
        return null;
    }

    private Tutor getTutor(String id) {
        for (Tutor tutor : tutors)
            if(tutor.getIc().equals(id))
                return tutor;
        return null;
    }

    // getter

    public ArrayList<Person> loadPersons() {
        return persons;
    }

    public ArrayList<Student> loadstudents() {
        return students;
    }

    public ArrayList<Tutor> loadtutors() {
        return tutors;
    }

    public Admin loadAdmin() {
        return admin;
    }

    private void readAdmin() throws SQLException {
        ResultSet rs = querySQL("SELECT * FROM "+DBI.ADMIN_TABLE);

        if (rs.next())
            admin = new Admin(
                rs.getString(DBI.NAME),
                rs.getString(DBI.IC),
                rs.getString(DBI.EMAIL),
                rs.getString(DBI.TEL),
                Boolean.parseBoolean(rs.getString(DBI.IS_MALE)),
                rs.getString(DBI.PASSWORD),
                Boolean.parseBoolean(rs.getString(DBI.IS_ACTIVE)),
                rs.getString(DBI.NATIONALITY), 
                rs.getString(DBI.VEHICLE)
            );
    };

    private void readTutor() throws SQLException {
        ResultSet rs = querySQL("SELECT * FROM "+DBI.TUTOR_TABLE);

        while (rs.next()) {
            Tutor tempTutor = new Tutor(
                rs.getString(DBI.NAME),
                rs.getString(DBI.IC),
                rs.getString(DBI.EMAIL),
                rs.getString(DBI.TEL),
                Boolean.parseBoolean(rs.getString(DBI.IS_MALE)),
                rs.getString(DBI.PASSWORD),
                Boolean.parseBoolean(rs.getString(DBI.IS_ACTIVE)),
                rs.getString(DBI.NATIONALITY), 
                rs.getString(DBI.VEHICLE), 
                Boolean.parseBoolean(rs.getString(DBI.TUTOR_MODULE_RECEIVED))
            );

            persons.add(tempTutor);
            tutors.add(tempTutor);
        }
    }

    private void readStudent() throws SQLException {
        ResultSet rs = querySQL("SELECT * FROM "+DBI.STUDENT_TABLE);
        while (rs.next()) {
            Phase[] arrayOfPhase = {
                new Phase(rs.getString(DBI.IC), Student.FIRSTPHASE,  Boolean.parseBoolean(rs.getString(DBI.STUDENT_FIRST_PHASE_COMPLETE))),
                new Phase(rs.getString(DBI.IC), Student.SECONDPHASE, Boolean.parseBoolean(rs.getString(DBI.STUDENT_SECOND_PHASE_COMPLETE))),
                new Phase(rs.getString(DBI.IC), Student.THIRDPHASE,  Boolean.parseBoolean(rs.getString(DBI.STUDENT_THIRD_PHASE_COMPLETE)))
            };

            Student tempStudent = new Student(
                rs.getString(DBI.NAME),
                rs.getString(DBI.IC),
                rs.getString(DBI.EMAIL),
                rs.getString(DBI.TEL),
                Boolean.parseBoolean(rs.getString(DBI.IS_MALE)),
                rs.getString(DBI.PASSWORD),
                Boolean.parseBoolean(rs.getString(DBI.IS_ACTIVE)),
                rs.getString(DBI.NATIONALITY), 
                rs.getString(DBI.VEHICLE), 
                new Payment(
                    rs.getFloat(DBI.STUDENT_PAYMENT_TOTAL_PRICE), 
                    rs.getFloat(DBI.STUDENT_PAYMENT_PAID), 
                    Boolean.parseBoolean(rs.getString(DBI.STUDENT_PAYMENT_COMPLETE))
                ),
                arrayOfPhase,
                rs.getInt(DBI.STUDENT_CURPHASE)
            );
            persons.add(tempStudent);
            students.add(tempStudent);
        }
    }

    private void readReport() throws SQLException {
        ResultSet rs = querySQL("SELECT * FROM "+DBI.REPORT_TABLE);
        while (rs.next()) {
            String tutorIc = rs.getString(DBI.REPORT_TUTOR_IC);
            Tutor tempTutor = getTutor(tutorIc);
            Report tempReport = new Report(
                tutorIc,
                rs.getInt(DBI.REPORT_WEEK)
            );
            reports.add(tempReport);
            tempTutor.getReports().add(tempReport);
        };
    }

    private void readSchedule() throws SQLException {
        ResultSet rs = querySQL("SELECT * FROM "+DBI.SCHEDULE_TABLE);
        while (rs.next()) {
            String  studentIc = rs.getString(DBI.SCHEDULE_STUDENT_IC),
                    tutorIc   = rs.getString(DBI.SCHEDULE_TUTOR_IC);
            studentIc   = (studentIc != null && studentIc.equals("null")) ? null : studentIc;
            tutorIc     = (tutorIc != null && tutorIc.equals("null"))   ? null : tutorIc;
            
            boolean isExecute = Boolean.parseBoolean(rs.getString(DBI.SCHEDULE_ISEXECUTE));

            Schedule tempSchedule = new Schedule(
                studentIc, 
                tutorIc,
                isExecute, 
                LocalDate.parse(rs.getString(DBI.SCHEDULE_DATE), Keys.DATE_FORMATTER),
                LocalTime.parse(rs.getString(DBI.SCHEDULE_TIME), Keys.TIME_FORMATTER) 
            );

            getTutor(tutorIc).getSchedules().add(tempSchedule);
            if (studentIc!=null) {
                getStudent(studentIc).getSchedules().add(tempSchedule);
                if (isExecute)  CheckSchedule.passedDate.add(tempSchedule); 
                else            CheckSchedule.bookedDate.add(tempSchedule);
            }
            else                CheckSchedule.availableDate.add(tempSchedule);
        }
    }

    private void readNotification() throws SQLException {
        ResultSet rs = querySQL("SELECT * FROM "+DBI.NOTIFICATION_TABLE);
        while (rs.next()) {
            String  studentIc = rs.getString(DBI.SCHEDULE_STUDENT_IC),
                    tutorIc   = rs.getString(DBI.SCHEDULE_TUTOR_IC);
            studentIc   = (studentIc != null && studentIc.equals("null")) ? null:studentIc;
            tutorIc     = (tutorIc != null && tutorIc.equals("null")) ? null:tutorIc;

            Notification tempNotification = new Notification(
                studentIc,
                tutorIc,
                rs.getString(DBI.NOTIFICATION_ALERT_MESSAGE),
                LocalDate.parse(rs.getString(DBI.NOTIFICATION_DATE), Keys.DATE_FORMATTER),
                Boolean.parseBoolean(rs.getString(DBI.NOTIFICATION_READ))
            );

            if (studentIc != null) {
                getStudent(studentIc).getNotification().add(tempNotification);
            }
            else if (tutorIc != null) {
                getTutor(tutorIc).getNotification().add(tempNotification);
            }
            else 
                admin.getNotification().add(tempNotification);
        }
    }

    public void saveAdmin() throws SQLException {
        updateSQL(DBI.ADMIN_TABLE, DBI.NAME,           admin.getName(),                         DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.EMAIL,          admin.getEmail(),                        DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.TEL,            admin.getTel(),                          DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.PASSWORD,       admin.getPassword(),                     DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.NATIONALITY,    admin.getNationality(),                  DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.VEHICLE,        admin.getVehicle(),                      DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.IS_MALE,        Boolean.toString(admin.getIsMale()),     DBI.IC+" = '"+admin.getIc()+"'");
        updateSQL(DBI.ADMIN_TABLE, DBI.IS_ACTIVE,      Boolean.toString(admin.getIsActive()),   DBI.IC+" = '"+admin.getIc()+"'");

    }

    public void saveStudent(Student student) throws SQLException {
        if (getStudent(student.getIc()) != null) {
            updateSQL(DBI.STUDENT_TABLE, DBI.NAME,        student.getName(),                         DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.EMAIL,       student.getEmail(),                        DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.TEL,         student.getTel(),                          DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.PASSWORD,    student.getPassword(),                     DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.NATIONALITY, student.getNationality(),                  DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.VEHICLE,     student.getVehicle(),                      DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.IS_MALE,     Boolean.toString(student.getIsMale()),     DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.IS_ACTIVE,   Boolean.toString(student.getIsActive()),   DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.STUDENT_PAYMENT_TOTAL_PRICE, String.valueOf(student.getPaymentLog().getTotalPrice()),   DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.STUDENT_PAYMENT_PAID, String.valueOf(student.getPaymentLog().getPaid()),   DBI.IC+" = '"+student.getIc()+"'");
            updateSQL(DBI.STUDENT_TABLE, DBI.STUDENT_PAYMENT_COMPLETE, Boolean.toString(student.getPaymentLog().getCompleted()),   DBI.IC+" = '"+student.getIc()+"'");

        }
        else
            insertSQL(DBI.STUDENT_TABLE, DBI.STUDENT_COLUMNS, student.toDBString());

    }

    public void saveTutor(Tutor tutor) throws SQLException {
        if (getTutor(tutor.getIc()) != null) {
            updateSQL(DBI.TUTOR_TABLE, DBI.NAME,           tutor.getName(),                       DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.EMAIL,          tutor.getEmail(),                      DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.TEL,            tutor.getTel(),                        DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.PASSWORD,       tutor.getPassword(),                   DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.NATIONALITY,    tutor.getNationality(),                DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.VEHICLE,        tutor.getVehicle(),                    DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.IS_MALE,        Boolean.toString(tutor.getIsMale()),   DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.IS_ACTIVE,      Boolean.toString(tutor.getIsActive()), DBI.IC+" = '"+tutor.getIc()+"'");
            updateSQL(DBI.TUTOR_TABLE, DBI.TUTOR_MODULE_RECEIVED,      Boolean.toString(tutor.getModuleReceived()), DBI.IC+" = '"+tutor.getIc()+"'");
        }
        else
            insertSQL(DBI.TUTOR_TABLE, DBI.TUTOR_COLUMNS, tutor.toDBString());
    }

    public void newSchedule(Schedule schedule) throws SQLException {
        insertSQL(DBI.SCHEDULE_TABLE, DBI.SCHEDULE_COLUMNS, schedule.toDBString());
    }

    public void updateSchedule(String column, String value, Schedule schedule) throws SQLException {
        updateSQL(DBI.SCHEDULE_TABLE, column, value, DBI.SCHEDULE_TUTOR_IC+" = '"+schedule.getTutorIc()
                  +"' AND "+DBI.SCHEDULE_DATE+" = '"+schedule.getDate()+"' AND "+DBI.SCHEDULE_TIME+" = '"+schedule.getTime()+"'");
    }


    public void newReport(Report report) throws SQLException {
        insertSQL(DBI.REPORT_TABLE, DBI.REPORT_COLUMNS, report.toDBString());
    }

    public void updateReport(String column, String value, String tutorIc) throws SQLException {
        updateSQL(DBI.REPORT_TABLE, column, value, DBI.SCHEDULE_TUTOR_IC+" = '"+tutorIc+"'");
    }

    public void newNotification(Notification notification) throws SQLException {
        insertSQL(DBI.NOTIFICATION_TABLE, DBI.NOTIFICATION_COLUMNS, notification.toDBString());
    }

    public void updateNotification(String column, String value, String tutorIc, String studentIc) throws SQLException {
        updateSQL(DBI.NOTIFICATION_TABLE, column, value, DBI.NOTIFICATION_TUTOR_IC+" = '"+tutorIc+"' AND "+DBI.NOTIFICATION_STUDENT_IC+" = '"+studentIc+"'");
    }

}
