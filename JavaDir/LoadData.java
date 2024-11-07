package JavaDir;

import java.sql.*;

/** 
 * One Time Run to load data to db
 * @param args
 */

public class LoadData {
    Connection conn = null;

    LoadData() throws SQLException, ClassNotFoundException {
        connectSQL();
        createTableAdmin();
        createTableTutor();
        createTableReport();
        createTableStudent();
        createTableSchedule();
        createTableNotification();
        System.out.println("completed");
    }

    private void connectSQL() throws SQLException, ClassNotFoundException {
        Class.forName(DBI.DBMS_JDBC);
        conn = DriverManager.getConnection(DBI.DBMS_URL, DBI.DBMS_USER, DBI.DBMS_PASS);
    }

    private int updateSQL(String update) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(update);
        return statement.executeUpdate();
    }

    private void createTableAdmin() throws SQLException {
        updateSQL(
          "CREATE TABLE " + DBI.ADMIN_TABLE + "("
          + " "           + DBI.NAME        + " varchar(50)"
          + ","           + DBI.IC          + " varchar(20) PRIMARY KEY NOT NULL"
          + ","           + DBI.EMAIL       + " varchar(30)"
          + ","           + DBI.TEL         + " INT"
          + ","           + DBI.PASSWORD    + " varchar(10)"
          + ","           + DBI.NATIONALITY + " VARCHAR(30)"
          + ","           + DBI.VEHICLE     + " VARCHAR(9)"
          + ","           + DBI.IS_MALE     + " varchar(5)"
          + ","           + DBI.IS_ACTIVE   + " varchar(5)"
          +")"
        );

        updateSQL(
            "INSERT INTO ADMIN VALUES"
            + "('Admin','1','drivingSchoolAdmin@gmail.com',18002525,'123','DrivingSchool',null,null,null)"
        );
        
    }

    private void createTableTutor() throws SQLException {

        updateSQL(
            "CREATE TABLE " + DBI.TUTOR_TABLE               + "("
            + " "           + DBI.NAME                      + " varchar(50) NOT NULL"
            + ","           + DBI.IC                        + " varchar(20) PRIMARY KEY NOT NULL"
            + ","           + DBI.EMAIL                     + " varchar(30) NOT NULL"
            + ","           + DBI.TEL                       + " INT NOT NULL"
            + ","           + DBI.PASSWORD                  + " varchar(10) NOT NULL"
            + ","           + DBI.NATIONALITY               + " VARCHAR(30) NOT NULL"
            + ","           + DBI.VEHICLE                   + " VARCHAR(9)"
            + ","           + DBI.IS_MALE                   + " varchar(5)"
            + ","           + DBI.IS_ACTIVE                 + " varchar(5)"
            + ","           + DBI.TUTOR_MODULE_RECEIVED     + " varchar(5) NOT NULL"
            + ")"
        );

        updateSQL(
            "INSERT INTO " + DBI.TUTOR_TABLE + " VALUES"
            + "('Jong Soi Enn','760312-03-3265','Jong@gmail.com',18002525,'a','MY','D','TRUE','TRUE','FALSE')" 
            + ", " + "('Uthayakumar Sangalimuthu','J8369854','Uthayakumar@gmail.com',18002525,'a','IN','D','TRUE','TRUE','FALSE')"
            + ", " + "('Er Kua Teo','680103-01-5487','Kua_Teo@gmail.com',18002525,'a','MY','DA','FALSE','TRUE','FALSE')"
            + ", " + "('Nur Hajjah Insyira Wahidin','841024-01-5963','Insyira@gmail.com',18002525,'a','MY','DA','TRUE','TRUE','FALSE')"
            + ", " + "('Gheetha a/l Selvakkumar Sivanesan','850612-03-4587','Gheetha@gmail.com',18002525,'a','MY','D','TRUE','TRUE','FALSE')"
            + ", " + "('Raamiz bin Shaamil','780430-06-5328','Raamiz@gmail.com',18002525,'a','MY','B','TRUE','FALSE','FALSE')"
            + ", " + "('Nadeem bin Hilaal','951224-01-0251','Nadeem@gmail.com',18002525,'a','MY','B2','TRUE','FALSE','FALSE')"
        );
    }

    private void createTableReport() throws SQLException{
        updateSQL(
            "CREATE TABLE " + DBI.REPORT_TABLE      + "("
            + " "           + DBI.REPORT_TUTOR_IC   + " varchar(20) NOT NULL"
            + ","           + DBI.REPORT_WEEK       + " INT"
            + ")"
        );

        updateSQL(
            "INSERT INTO " + DBI.REPORT_TABLE + " VALUES"
            + "('760312-03-3265',0)"
        );
    }

    private void createTableStudent() throws SQLException {
        updateSQL(
            "CREATE TABLE " + DBI.STUDENT_TABLE                 + "("
            + " "           + DBI.NAME                          + " varchar(50) NOT NULL"
            + ","           + DBI.IC                            + " varchar(20) PRIMARY KEY NOT NULL"
            + ","           + DBI.EMAIL                         + " varchar(30) NOT NULL"
            + ","           + DBI.TEL                           + " INT NOT NULL"
            + ","           + DBI.PASSWORD                      + " varchar(10) NOT NULL"
            + ","           + DBI.NATIONALITY                   + " VARCHAR(30) NOT NULL"
            + ","           + DBI.VEHICLE                       + " VARCHAR(9)"
            + ","           + DBI.IS_MALE                       + " varchar(5)"
            + ","           + DBI.IS_ACTIVE                     + " varchar(5)"
            + ","           + DBI.STUDENT_PAYMENT_TOTAL_PRICE   + " INT"
            + ","           + DBI.STUDENT_PAYMENT_PAID          + " INT"
            + ","           + DBI.STUDENT_PAYMENT_COMPLETE      + " VARCHAR(5)"
            + ","           + DBI.STUDENT_FIRST_PHASE_COMPLETE  + " VARCHAR(5)"
            + ","           + DBI.STUDENT_SECOND_PHASE_COMPLETE + " VARCHAR(5)"
            + ","           + DBI.STUDENT_THIRD_PHASE_COMPLETE  + " VARCHAR(5)"
            + ","           + DBI.STUDENT_CURPHASE              + " INT"
            + ")"
        );

        updateSQL(
            "INSERT INTO " + DBI.STUDENT_TABLE + " VALUES"
            + "('Ghaalib bin Hasan','990519-01-2041','Ghaalib@gmail.com',18002525,'Gh@lib','MY','DA','TRUE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Abdul Kader bin Muneef','970321-06-2034','Abdul@gmail.com',18002525,'a','MY','D','TRUE','TRUE',1219,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Saahir bin Quraish','021203-12-0321','Saahir@gmail.com',18002525,'a','MY','D','TRUE','FALSE',1219,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Faadi bin Hakam','750228-03-0245','Faadi@gmail.com',18002525,'a','MY','D','TRUE','TRUE',1219,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Abdul Kader bin Mazeed','920302-02-0271','Abdul@gmail.com',18002525,'a','MY','DA','TRUE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Jusoh bin Merdeka','760321-02-7854','Jusoh@gmail.com',18002525,'a','MY','D','TRUE','TRUE',1219,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Yahaya bin Hitam','890401-03-0351','Yahaya@gmail.com',18002525,'a','MY','DA','TRUE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Yeop bin Rabu','020312-02-9821','Yeop@gmail.com',18002525,'a','MY','DA','TRUE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Baba bin Suria','030201-02-3203','Baba@gmail.com',18002525,'a','MY','DA','TRUE','FALSE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Jasra binti Noori','060212-02-1023','Jasra@gmail.com',18002525,'a','MY','B','FALSE','FALSE',954,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Hujja binti Naadir','7603-23-01-0465','Hujja@gmail.com',18002525,'a','MY','B','FALSE','TRUE',954,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Habeeba binti Ghaali','950419-02-0251','Habeeba@gmail.com',18002525,'a','MY','B','FALSE','TRUE',954,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Rabdaa binti Abdul Jabbaar','920304-01-5643','Rabdaa@gmail.com',18002525,'a','MY','B','FASLE','TRUE',954,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Thaabita binti Siraaj','920302-06-3021','Thaabita@gmail.com',18002525,'a','MY','DA','FALSE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Linda binti Putra','760403-09-0231','Linda@gmail.com',18002525,'a','MY','B','FALSE','TRUE',954,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Bulat binti Sagari','990203-01-0253','Bulat@gmail.com',18002525,'a','MY','D','FALSE','FALSE',1219,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Putih binti Ahad','670430-06-2345','Putih@gmail.com',18002525,'a','MY','DA','FALSE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Minah binti Labuh','5930201-03-4532','Minah@gmail.com',18002525,'a','MY','DA','FALSE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Junaid bin Miqdaam','970301-06-0234','Junaid@gmail.com',18002525,'a','MY','DA','TRUE','TRUE',1325,300,0,'FALSE','FALSE','FALSE',0)"
            + ", " + "('Ching Chong','651231-06-1234','Ching@gmail.com',18002525,'a','MY','B','TRUE','TRUE',954,300,0,'FALSE','FALSE','FALSE',0)"
        );
    }

    private void createTableSchedule() throws SQLException {
        updateSQL(
            "CREATE TABLE " + DBI.SCHEDULE_TABLE + "("
            + " "           + DBI.SCHEDULE_STUDENT_IC + " VARCHAR(20)"
            + ","           + DBI.SCHEDULE_TUTOR_IC + " VARCHAR(20)"
            + ","           + DBI.SCHEDULE_ISEXECUTE + " VARCHAR(5)"
            + ","           + DBI.SCHEDULE_DATE + " date"
            + ","           + DBI.SCHEDULE_TIME + " varchar(10)"
            + ")"
        );

        updateSQL(
            "INSERT INTO " + DBI.SCHEDULE_TABLE + " VALUES"
            + "('990519-01-2041','760312-03-3265','FALSE','2021-07-11','08:00')"
        );
    }

    private void createTableNotification() throws SQLException{
        updateSQL(
            "CREATE TABLE " + DBI.NOTIFICATION_TABLE         + "("
            + " "           + DBI.NOTIFICATION_STUDENT_IC    + " VARCHAR(20)"
            + ","           + DBI.NOTIFICATION_TUTOR_IC      + " VARCHAR(20)"
            + ","           + DBI.NOTIFICATION_ALERT_MESSAGE + " VARCHAR(100)"
            + ","           + DBI.NOTIFICATION_DATE          + " DATE"
            + ","           + DBI.NOTIFICATION_READ          + " VARCHAR(5)"
            + ")"
        );

        updateSQL(
            "INSERT INTO " + DBI.NOTIFICATION_TABLE + " VALUES"
            +"('990519-01-2041',NULL,'PLASE PAY YOUR FEES','2021-07-11','FALSE')"
            + ","  + "(NULL,'760312-03-3265','PLEASE SDUBMIT YOUR WEEKLY REPORT','2021-07-11','FALSE')"
        );
    }


    public static void main(String[] args) {
        try {
        new LoadData();
        }
        catch (Exception e) {
            System.out.println("exception" + e.getMessage());
        }
    }
}
