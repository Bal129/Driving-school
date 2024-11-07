package JavaDir;
import java.time.format.DateTimeFormatter;

public interface Keys {
    public static final String PATH                      = System.getProperty("user.dir");
    public static final String STYLING_FILE              = "Styling.css";
    public static final String ICON                      = "icon.png";

    // FORMARTER
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter TIME_FORMATTER_READ = DateTimeFormatter.ofPattern("HHmm");

    // VEHICLE PRICE
    public static final double B    = 129.11;
    public static final double B2   = 129.11;
    public static final double D    = 129.11;
    public static final double DA   = 129.11;

    public static final String CANNOTSAVE = "Cannot Update Database";
    public static final String NOTFOUND = "Account Not Found";
}
