package JavaDir;
import java.util.PriorityQueue;

public interface User {
    ///////method///////
    public PriorityQueue<Schedule> getSchedules();
    public PriorityQueue<Schedule> getHistories();
    public PriorityQueue<Notification> getNotification();
}
