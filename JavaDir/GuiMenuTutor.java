package JavaDir;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * main menu for tutor
 * tutor will be able to 
 * 1. look at their profile
 * 2. set availability
 * 3. send report to admin
*/

public class GuiMenuTutor {
    private Tutor tutor;
    private GuiTemplate gui = new GuiTemplate();
    private GuiNotification guiNoti;
    private GridPane layoutProfile;
    private VBox layoutAvailability;
    private VBox layoutReport;
    private TableView<Report> tableReport = new TableView<>();
    private int notiCount;

    GuiMenuTutor() {}
    GuiMenuTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    
    /**
     * display the main window to tutor
     * @return
     */
    public void display() {
        // initially, setup, label top and bottom
        gui.setup();
        gui.getLabelMenuTop().setText("Tutor");
        gui.getLabelMenuDown().setText("You Are Signing As " + tutor.getName());

        // setup the notification
        for (Notification notification : tutor.getNotification()) {
            if (!notification.getRead())
                notiCount++;
        }
        
        // all notification set to read once click the notification button
        gui.getButtonNotification().setText("Unread (" + notiCount + ")");
        gui.getButtonNotification().setOnAction(e -> {
            try {
                for (Notification notification : tutor.getNotification()) {
                    Finder.database.updateNotification(DBI.NOTIFICATION_READ, "true", notification.getTutorIc(), notification.getStudentIc());
                }
            } catch (Exception ex) {
                GuiPopupWindow.display("Error", ex.getMessage());
            }
            guiNoti = new GuiNotification(tutor);
            guiNoti.display();
            gui.getButtonNotification().setText("Unread (0)");
        });

    
        // side button
        Button buttonProfile = new Button("Profile");
        Button buttonAvailability = new Button("Set Availability");
        Button buttonReport = new Button("Send Reports");

        gui.getSideMenu().getChildren().addAll(buttonProfile,
                buttonAvailability, buttonReport, gui.getButtonBack());

        // setting up layouts
        profile();
        availability();
        report();

        // embedding layout
        gui.getBorderPaneMenu().setLeft(gui.getSideMenu());
        gui.getBorderPaneMenu().setCenter(layoutProfile);
        BorderPane.setAlignment(gui.getSideMenu(), Pos.CENTER);
        BorderPane.setAlignment(layoutProfile, Pos.CENTER);
        BorderPane.setAlignment(layoutAvailability, Pos.CENTER);
        BorderPane.setAlignment(layoutReport, Pos.CENTER);

        // side button functionality
        buttonProfile.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutProfile));
        buttonAvailability.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutAvailability));
        buttonReport.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutReport));

        // applying scene
        Scene sceneMenu = new Scene(gui.getBorderPaneMenu());
        sceneMenu.getStylesheets().add(Keys.STYLING_FILE);

        // displaying stage
        gui.getStage().setScene(sceneMenu);
        gui.getStage().show();
    }

    /**
     * to show the profile for tutors
     * @return
     */
    private void profile() {
        String gender;
        String active;
        String module;

        if (tutor.getIsMale())
            gender = "Male";
        else
            gender = "Female";

        if (tutor.getIsActive())
            active = "Active";
        else
            active = "Not Active";

        if (tutor.getModuleReceived())
            module = "Received";
        else
            module = "Not Received";

        String[] listProfile = {
            "NAME:",
            "IC/PASSPORT:",
            "EMAIL:",
            "CONTACT:",
            "GENDER:",
            "PASSWORD:",
            "STATUS:",
            "NATIONALITY:",
            "VEHICLE CLASS:",
            "MODULE:"
        };

        String[] listValues = {
            tutor.getName(),
            tutor.getIc(),
            tutor.getEmail(),
            tutor.getTel(),
            gender,
            tutor.getPassword(),
            active,
            tutor.getNationality(),
            tutor.getVehicle(),
            module
        };
        
        StackPane layoutTop = new StackPane();
        layoutTop.setPadding(new Insets(20));
        layoutTop.setAlignment(Pos.CENTER);
        layoutTop.getChildren().add(new Label("YOUR PROFILE"));

        VBox layoutLeft = new VBox();
        layoutLeft.setPadding(new Insets(15));
        layoutLeft.setSpacing(10);
        layoutLeft.setAlignment(Pos.CENTER_RIGHT);
        for (int i=0;i<listProfile.length; i++) {
            layoutLeft.getChildren().add(new Label(listProfile[i]));
        }

        VBox layoutRight = new VBox();
        layoutRight.setPadding(new Insets(15));
        layoutRight.setSpacing(10);
        layoutRight.setAlignment(Pos.CENTER_LEFT);
        for (int i=0;i<listValues.length; i++) {
            layoutRight.getChildren().add(new Label(listValues[i]));
        }

        layoutProfile = new GridPane();
        GridPane.setConstraints(layoutTop, 0, 0);
        GridPane.setConstraints(layoutLeft, 0, 1);
        GridPane.setConstraints(layoutRight, 1, 1);
        GridPane.setColumnSpan(layoutTop, 2);
        layoutProfile.setPadding(new Insets(20));
        layoutProfile.setAlignment(Pos.CENTER);
        layoutProfile.setId("layoutDesign");
        layoutProfile.getChildren().addAll(layoutTop, layoutLeft, layoutRight);
    }

    /**
     * to set the availability and show it to student
     * @return
     */
    private void availability() {
        ChoiceBox<String> choiceTime = new ChoiceBox<>();
        choiceTime.getItems().addAll("08:00", "14:00");
        choiceTime.setValue("08:00");

        DatePicker datePickerAvail = new DatePicker();
        datePickerAvail.setValue(LocalDate.now());

        Button buttonSetDate = new Button("Set");
        buttonSetDate.setId("buttonInLayout");
        buttonSetDate.setAlignment(Pos.CENTER);

        Label labelShowStatus = new Label("Status");

        HBox layoutTopAvail = new HBox();
        layoutTopAvail.setAlignment(Pos.CENTER);
        layoutTopAvail.setPadding(new Insets(10));
        layoutTopAvail.getChildren().addAll(choiceTime, buttonSetDate);

        // set availablity
        buttonSetDate.setOnAction(e -> {
            try {
                if (datePickerAvail.getValue().isAfter(LocalDate.now())) {
                    if (tutor.getSchedules().isEmpty()) {
                        tutor.setAvailability(datePickerAvail.getValue(), LocalTime.parse(choiceTime.getValue()));
                        labelShowStatus.setText("Availability Set on " + datePickerAvail.getValue() + 
                                                ", time: " + choiceTime.getValue());
                    } else {
                        for (Schedule schedule : tutor.getSchedules()) {
                            if (schedule.getDate().equals(datePickerAvail.getValue()) &&
                                schedule.getTime().toString().equals(choiceTime.getValue())) {
                                GuiPopupWindow.display("Error", "Schedule Already Set On Current Time");
                            } else {
                                tutor.setAvailability(datePickerAvail.getValue(), LocalTime.parse(choiceTime.getValue()));
                                labelShowStatus.setText("Availability Set on " + datePickerAvail.getValue() + 
                                                        ", time: " + choiceTime.getValue());
                            }
                            break;
                        }    
                    }
                    Finder.database.saveTutor(tutor);
                    Finder.database.newSchedule(new Schedule(tutor.getIc(),  datePickerAvail.getValue(), LocalTime.parse(choiceTime.getValue())));
                } else {
                    GuiPopupWindow.display("Error", "Schedule Must Be At Least For Tomorrow");
                }
            } catch (SQLException ex) {
                GuiPopupWindow.display("Error", "Cannot Set Schedule");
            }
        });

        layoutAvailability = new VBox();
        layoutAvailability.setAlignment(Pos.CENTER);
        layoutAvailability.setId("layoutDesign");
        layoutAvailability.getChildren().addAll(layoutTopAvail, datePickerAvail, labelShowStatus);
    }

    /**
     * to send report to admin by week
     * @return
     */    
    private void report() {
        TableColumn<Report, Integer> columnWeek = new TableColumn<>("Week");
        columnWeek.setCellValueFactory(new PropertyValueFactory<>("week"));

        tableReport.getColumns().add(columnWeek);
        tableReport.getItems().addAll(tutor.getReports());
        tableReport.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableReport.setPlaceholder(new Label("No Data"));

        tableReport.setMaxWidth(200);
        tableReport.setPrefHeight(350);

        TextField fieldReport = new TextField();
        fieldReport.setPromptText("Enter Week");

        Button buttonSendReport = new Button("Submit");
        buttonSendReport.setId("buttonInLayout");

        // send the report entered in the text field
        // before sending report, a confirmation window will appear
        buttonSendReport.setOnAction(e -> {
            GuiConfirmWindow guiConfirm = new GuiConfirmWindow();
            guiConfirm.display("Are you Sure?", "Sending the report");
            if (guiConfirm.isTrue()) {
                try {
                    int weekValue = Integer.parseInt(fieldReport.getText());
                    tutor.sendReport(weekValue);
                    tableReport.getItems().clear();
                    tableReport.getItems().addAll(tutor.getReports());
                    Finder.database.newReport(new Report(tutor.getIc(), weekValue));
                } catch (SQLException ex) {
                    GuiPopupWindow.display("Error", Keys.CANNOTSAVE);
                } catch (NumberFormatException ex) {
                    GuiPopupWindow.display("Error", "Please Enter Integers only");
                }
            }
        });

        HBox layoutTop = new HBox();
        layoutTop.setAlignment(Pos.CENTER);
        layoutTop.getChildren().addAll(fieldReport, buttonSendReport);

        layoutReport = new VBox();
        layoutReport.setSpacing(15);
        layoutReport.setAlignment(Pos.CENTER);
        layoutReport.setId("layoutDesign");
        layoutReport.getChildren().addAll(layoutTop, tableReport);
    }
}