package JavaDir;

public interface DBI {
    // DBMS DETAILS
    public static final String DBMS_JDBC                        = "com.ibm.db2.jcc.DB2Driver";
    public static final String DBMS_NAME                        = "DBI";
    public static final String DBMS_URL                         = "jdbc:db2://localhost:50000/"+DBMS_NAME;
    public static final String DBMS_USER                        = "IQBAL";
    public static final String DBMS_PASS                        = "password";

    // TABLE NAME
    public static final String ADMIN_TABLE                      = "ADMIN";
    public static final String TUTOR_TABLE                      = "TUTOR";
    public static final String REPORT_TABLE                     = "REPORT";
    public static final String STUDENT_TABLE                    = "STUDENT";
    public static final String SCHEDULE_TABLE                   = "SCHEDULE";
    public static final String NOTIFICATION_TABLE               = "NOTIFICATION";

    // PERSON
    public static final String NAME                             = "NAME";
    public static final String IC                               = "IC";
    public static final String EMAIL                            = "EMAIL";
    public static final String TEL                              = "TEL";
    public static final String PASSWORD                         = "PASSWORD";
    public static final String NATIONALITY                      = "NATIONALITY";
    public static final String VEHICLE                          = "VEHICLE";
    public static final String IS_MALE                          = "IS_MALE";
    public static final String IS_ACTIVE                        = "IS_ACTIVE";

    // STUDENT + PAYMENT + PHASE
    public static final String STUDENT_CURPHASE                 = "STUDENT_CURPHASE";
    public static final String STUDENT_PAYMENT_TOTAL_PRICE      = "STUDENT_PAYMENT_TOTAL_PRICE";
    public static final String STUDENT_PAYMENT_PAID             = "STUDENT_PAYMENT_PAID";
    public static final String STUDENT_PAYMENT_COMPLETE         = "STUDENT_PAYMENT_COMPLETE";
    public static final String STUDENT_FIRST_PHASE_COMPLETE     = "STUDENT_FIRST_PHASE_COMPLETE";
    public static final String STUDENT_SECOND_PHASE_COMPLETE    = "STUDENT_SECOND_PHASE_COMPLETE";
    public static final String STUDENT_THIRD_PHASE_COMPLETE     = "STUDENT_THIRD_PHASE_COMPLETE";

    // TUTOR 
    public static final String TUTOR_MODULE_RECEIVED            = "TUTOR_MODULE_RECEIVED";

    // NOTI 
    public static final String NOTIFICATION_STUDENT_IC          = "STUDENT_IC";
    public static final String NOTIFICATION_TUTOR_IC            = "TUTOR_IC";
    public static final String NOTIFICATION_ALERT_MESSAGE       = "ALERT_MESSAGE";
    public static final String NOTIFICATION_DATE                = "DATE";
    public static final String NOTIFICATION_READ                = "READ";

    // SCHEDULE
    public static final String SCHEDULE_STUDENT_IC              = "STUDENT_IC";
    public static final String SCHEDULE_TUTOR_IC                = "TUTOR_IC";
    public static final String SCHEDULE_ISEXECUTE               = "ISEXECUTE";
    public static final String SCHEDULE_DATE                    = "DATE";
    public static final String SCHEDULE_TIME                    = "TIME";

    // REPORT  
    public static final String REPORT_TUTOR_IC                  = "TUTOR_IC";
    public static final String REPORT_WEEK                      = "WEEK";

    // COLUMNS
    public static final String STUDENT_COLUMNS                 = String.format(
        "(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", 
        NAME, IC, EMAIL, TEL, PASSWORD, NATIONALITY, VEHICLE, IS_MALE, IS_ACTIVE,
        STUDENT_CURPHASE, STUDENT_PAYMENT_TOTAL_PRICE, STUDENT_PAYMENT_PAID,
        STUDENT_PAYMENT_COMPLETE, STUDENT_FIRST_PHASE_COMPLETE, 
        STUDENT_SECOND_PHASE_COMPLETE, STUDENT_THIRD_PHASE_COMPLETE
    );

    public static final String TUTOR_COLUMNS                   = String.format(
        "(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", 
        NAME, IC, EMAIL, TEL, PASSWORD, NATIONALITY, VEHICLE, IS_MALE, IS_ACTIVE,
        TUTOR_MODULE_RECEIVED
    );

    public static final String REPORT_COLUMNS                  = String.format(
        "(%s, %s)", 
        REPORT_TUTOR_IC,
        REPORT_WEEK
    );

    public static final String SCHEDULE_COLUMNS                = String.format(
        "(%s, %s, %s, %s, %s)", 
        SCHEDULE_STUDENT_IC, SCHEDULE_TUTOR_IC, SCHEDULE_ISEXECUTE, SCHEDULE_DATE, 
        SCHEDULE_TIME 
    );

    public static final String NOTIFICATION_COLUMNS            = String.format(
        "(%s, %s, %s, %s, %s)", 
        NOTIFICATION_STUDENT_IC,
        NOTIFICATION_TUTOR_IC,
        NOTIFICATION_ALERT_MESSAGE,
        NOTIFICATION_DATE,
        NOTIFICATION_READ
    );
}
