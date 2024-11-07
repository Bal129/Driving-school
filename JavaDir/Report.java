package JavaDir;

public class Report implements Comparable<Report> {
    private String tutorIc;
    private int week;

    Report() {}
    Report(String tutorIc, int week) {
        setTutorIc(tutorIc);
        setWeek(week);
    }
    
    // getter
    public String getTutorIc() {
        return tutorIc;
    }
    public int getWeek() {
        return week;
    }

    // setter
    public void setTutorIc(String tutorIc) {  
        this.tutorIc = tutorIc;
    }
    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public int compareTo(Report refReport) {
        return week - refReport.getWeek();      
    }

    @Override
    public String toString() {
        return getTutorIc()       + ","
               + getWeek();
    }

    public String toDBString() {
        return "('" + getTutorIc() + "','"
                   + getWeek()
                   + "')";
    }
}
