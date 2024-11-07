import JavaDir.GuiSelect;
import javafx.application.Application;
import javafx.stage.Stage;
import JavaDir.Finder;
import java.sql.SQLException;

/**
 * The program starts here
 */

public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Finder.loadDatabase();
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR!\n" + e);
        }
        GuiSelect.display();
    }
}