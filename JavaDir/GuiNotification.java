package JavaDir;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * to display the notification
 */
public class GuiNotification {
    private Stage stage;
    private Person person;
    private ArrayList<Person> persons;
    private TableView<Notification> tableNoti;
    private boolean isAdmin = false;

    GuiNotification() {}
    GuiNotification(Person person) {
        this.person = person;
    }
    GuiNotification(ArrayList<Person> persons) {
        isAdmin = true;
        this.persons = persons;
    }

    /**
     * display the window
     * @return
     */
    public void display() {
        stage = new Stage();
        stage.setWidth(300);
        stage.getIcons().add(new Image(Keys.ICON));
        stage.setResizable(false);
        stage.setTitle("Notification");
        stage.initModality(Modality.APPLICATION_MODAL);

        tableNoti = new TableView<>();
        TableColumn<Notification, String> columnMessage = new TableColumn<>("Message");
        TableColumn<Notification, LocalDate> columnDate = new TableColumn<>("Date");
        columnMessage.setCellValueFactory(new PropertyValueFactory<>("alertMessage"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableNoti.getColumns().add(columnDate);
        tableNoti.getColumns().add(columnMessage);

        if (isAdmin) {
            TableColumn<Notification, String> columnTutor = new TableColumn<>("Tutor");
            TableColumn<Notification, String> columnStudent = new TableColumn<>("Student");
            columnTutor.setCellValueFactory(new PropertyValueFactory<>("tutorIc"));
            columnStudent.setCellValueFactory(new PropertyValueFactory<>("studentIc"));
            tableNoti.getColumns().add(columnTutor);
            tableNoti.getColumns().add(columnStudent);
            for (Person person : persons)
                tableNoti.getItems().addAll(person.getNotification());
        }
        else
            tableNoti.getItems().addAll(person.getNotification());    

        tableNoti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableNoti.setPlaceholder(new Label("No Data"));
        tableNoti.setMaxWidth(600);
        tableNoti.setMaxHeight(400);

        StackPane stackNoti = new StackPane();
        stackNoti.setId("layoutDesign");
        stackNoti.getChildren().addAll(tableNoti);

        Scene scene = new Scene(stackNoti);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }
}