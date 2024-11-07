package JavaDir;

public class Phase {
    private String studentIc;
    private String phase;
    private boolean completed;

    Phase() {}
    Phase(String studentIc, String phase) {
        setStudentIc(studentIc);
        setPhase(phase);
    }

    Phase(String studentIc, String phase, boolean completed) {
        setStudentIc(studentIc);
        setPhase(phase);
        this.completed = completed;
    }

    // getter
    public String getStudentIc() {
        return studentIc;
    }
    public String getPhase() {
        return phase;
    }
    public boolean getCompleted() {
        return completed;
    }

    // setter
    public void setStudentIc(String studentIc) {
        this.studentIc = studentIc;
    }
    public void setPhase(String phase) {
        this.phase = phase;
    }
    public void switchCompleted() {
        completed = !completed;
    }

}
