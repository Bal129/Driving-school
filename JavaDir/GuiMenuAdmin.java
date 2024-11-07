package JavaDir;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.PriorityQueue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * main menu for admin
 * admin will be able to 
 * 1. look at users' profile (tutors/students)
 * 2. distribute module to tutors
 * 3. check tutors/students' schedule
 * 4. check tutors/students' past schedule (history)
 * 5. view tutors' reports
 * 6. view students' payment
*/

public class GuiMenuAdmin {
    private GuiNotification guiNoti;
    private GuiTemplate gui = new GuiTemplate();
    private TableView<Schedule> tableSchedule;
    private TableView<Schedule> tableHistory;
    private TableView<Report> tableReport;
    private TableView<Payment> tablePayment;
    private VBox layoutProfile, layoutDistribute, layoutSchedule,
                 layoutHistory, layoutReport, layoutPayment;
    private Tutor tutor;
    private Student student;
    private PriorityQueue<Schedule> schedules;
    private boolean isTutor = true;
    private boolean update = true;

    /**
     * display the main window to admin
     * @return
     */
    public void display() {
        // initially, setup, label top and bottom
        gui.setup();
        gui.getLabelMenuTop().setText("Admin");
        gui.getLabelMenuDown().setText("You Are Signing As Admin");

        // all notification set to read once click the notification button
        gui.getButtonNotification().setText("Check Notifications");
        gui.getButtonNotification().setOnAction(e -> {
            try {
                guiNoti = new GuiNotification(Finder.database.loadPersons());
                guiNoti.display();
            } catch (Exception ex) {
                GuiPopupWindow.display("Error", Keys.NOTFOUND);
            }
        });

        // side button
        Button buttonProfile = new Button("Show Profile");
        Button buttonDistribute = new Button("Distribute Module");
        Button buttonSchedule = new Button("Show Schedule");
        Button buttonTutor = new Button("Show History");
        Button buttonReport = new Button("Reports");
        Button buttonPayment = new Button("Payments");

        gui.getSideMenu().getChildren().addAll(buttonProfile, buttonDistribute,
                                               buttonSchedule, buttonTutor,
                                               buttonReport, buttonPayment, 
                                               gui.getButtonBack());

        // setting up layouts
        profile();
        distribute();

        // true for schedule
        // false for history
        scheduleAndHistory(true);
        scheduleAndHistory(false);

        // true for report
        // false for payment
        reportAndPayment(true);
        reportAndPayment(false);

        // embedding layout
        gui.getBorderPaneMenu().setLeft(gui.getSideMenu());
        gui.getBorderPaneMenu().setCenter(layoutProfile);
        BorderPane.setAlignment(gui.getSideMenu(), Pos.CENTER);
        BorderPane.setAlignment(layoutProfile, Pos.CENTER);
        BorderPane.setAlignment(layoutDistribute, Pos.CENTER);
        BorderPane.setAlignment(layoutSchedule, Pos.CENTER);
        BorderPane.setAlignment(layoutHistory, Pos.CENTER);
        BorderPane.setAlignment(layoutReport, Pos.CENTER);
        BorderPane.setAlignment(layoutPayment, Pos.CENTER);

        // side button functionality
        buttonProfile.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutProfile));
        buttonDistribute.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutDistribute));
        buttonSchedule.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutSchedule));
        buttonTutor.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutHistory));
        buttonReport.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutReport));
        buttonPayment.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutPayment));

        // applying scene
        Scene sceneMenu = new Scene(gui.getBorderPaneMenu());
        sceneMenu.getStylesheets().add(Keys.STYLING_FILE);

        // displaying stage
        gui.getStage().setScene(sceneMenu);
        gui.getStage().show();
    }

    /**
     * to show students/tutors' profile
     * @return
     */
    private VBox profile() {
        isTutor = true;

        // setting up grid for profile
        Label labelName = new Label("Name");
        Label labelEmail = new Label("Email");
        Label labelGender = new Label("Gender");
        Label labelPassword = new Label("Password");
        Label labelNationality = new Label("Nationality");
        Label labelVehicle = new Label("Vehicle");

        TextField fieldName = new TextField("Name");
        TextField fieldEmail = new TextField("Email");
        RadioButton radioGenderM = new RadioButton("Male");
        RadioButton radioGenderF = new RadioButton("Female");
        ToggleGroup radioGender = new ToggleGroup();
        TextField fieldPassword = new TextField("password");
        TextField fieldNationality = new TextField("Nationality");
        TextField fieldVehicle = new TextField("Vehicle Class");
        fieldName.setPromptText("Enter Name");
        fieldEmail.setPromptText("Enter Email");
        fieldPassword.setPromptText("Enter Password");
        fieldNationality.setPromptText("Enter Nationality");
        fieldVehicle.setPromptText("Enter Vehicle Class");
        fieldName.setDisable(true);
        fieldEmail.setDisable(true);
        fieldPassword.setDisable(true);
        radioGenderM.setDisable(true);
        radioGenderF.setDisable(true);
        fieldNationality.setDisable(true);
        fieldVehicle.setDisable(true);
        fieldName.setPrefWidth(200);
        fieldEmail.setPrefWidth(200);
        fieldPassword.setPrefWidth(200);
        fieldNationality.setPrefWidth(200);
        fieldVehicle.setPrefWidth(200);
        radioGenderM.setToggleGroup(radioGender);
        radioGenderF.setToggleGroup(radioGender);
        radioGenderM.setSelected(true);

        // grid for labels
        GridPane.setConstraints(labelName, 0, 0);
        GridPane.setConstraints(labelEmail, 0, 1);
        GridPane.setConstraints(labelGender, 0, 2);
        GridPane.setConstraints(labelPassword, 0, 4);
        GridPane.setConstraints(labelNationality, 0, 5);
        GridPane.setConstraints(labelVehicle, 0, 6);
        GridPane.setRowSpan(labelGender, 2);

        // grid for fields
        GridPane.setConstraints(fieldName, 1, 0);
        GridPane.setConstraints(fieldEmail, 1, 1);
        GridPane.setConstraints(radioGenderM, 1, 2);
        GridPane.setConstraints(radioGenderF, 1, 3);
        GridPane.setConstraints(fieldPassword, 1, 4);
        GridPane.setConstraints(fieldNationality, 1, 5);
        GridPane.setConstraints(fieldVehicle, 1, 6);

        // layout for top layout profile
        Button buttonTutor = new Button("Tutor");
        Button buttonStudent = new Button("Student");
        buttonTutor.setAlignment(Pos.CENTER);
        buttonStudent.setAlignment(Pos.CENTER);
        buttonTutor.setId("buttonSwitch");
        buttonStudent.setId("buttonSwitch");
        buttonTutor.setDisable(true);

        TextField fieldSearchProfile = new TextField();
        fieldSearchProfile.setAlignment(Pos.CENTER);
        fieldSearchProfile.setPromptText("Tutor's IC/Passport");
        
        Button buttonSearchProfile = new Button("Find");
        buttonSearchProfile.setId("buttonInLayout");

        // node for bottom layout profile
        Button buttonUpdateProfile = new Button("Update");
        buttonUpdateProfile.setDisable(true);
        buttonUpdateProfile.setId("buttonInLayout");

        // bottom layout
        HBox layoutBottomProfile = new HBox();
        layoutBottomProfile.setPadding(new Insets(50, 50, 5, 5));
        layoutBottomProfile.setAlignment(Pos.BASELINE_RIGHT);
        layoutBottomProfile.getChildren().addAll(buttonUpdateProfile);

        // swap to tutor
        buttonTutor.setOnAction(e -> {
            isTutor = true;
            buttonTutor.setDisable(true);
            buttonStudent.setDisable(false);
            fieldSearchProfile.setPromptText("Tutor's IC/Passport");
        });

        // swap to student
        buttonStudent.setOnAction(e -> {
            isTutor = false;
            buttonTutor.setDisable(false);
            buttonStudent.setDisable(true);
            fieldSearchProfile.setPromptText("Student's IC/Passport");
        });

        // 2 cycle:
        // 1. update is true, admin in view only mode
        // 2. update is false, admin in editable mode, clicking button again will save the 
        //    data and enter the first cycle again
        buttonUpdateProfile.setOnAction(e -> {
            if (!update) {
                GuiConfirmWindow guiConfirm = new GuiConfirmWindow();
                guiConfirm.display("Are you Sure?", "Update the Profile");
                if (guiConfirm.isTrue()) {
                    try {
                        if (isTutor) {
                            tutor.setName(fieldName.getText());
                            tutor.setEmail(fieldEmail.getText());
                            if (radioGenderM.isSelected()) {
                                if (!tutor.getIsMale())
                                    tutor.switchMale();
                            } else if (radioGenderF.isSelected())
                                if (tutor.getIsMale())
                                    tutor.switchMale();
                            tutor.setPassword(fieldPassword.getText());
                            tutor.setNationality(fieldNationality.getText());
                            tutor.setVehicle(fieldVehicle.getText());
                            Finder.database.saveTutor(tutor);
                        } else {
                            student.setName(fieldName.getText());
                            student.setEmail(fieldEmail.getText());
                            if (radioGenderM.isSelected()) {
                                if (!student.getIsMale())
                                    student.switchMale();
                            } else if (radioGenderF.isSelected())
                                if (student.getIsMale())
                                    student.switchMale();
                            student.setPassword(fieldPassword.getText());
                            student.setNationality(fieldNationality.getText());
                            student.setVehicle(fieldVehicle.getText());
                            Finder.database.saveStudent(student);
                        }
                    } catch (SQLException ex) {
                        GuiPopupWindow.display("Error", ex + Keys.CANNOTSAVE);
                    }
                    buttonUpdateProfile.setText("Update");
                }
            }
            else
                buttonUpdateProfile.setText("OK");

            buttonSearchProfile.setDisable(update);
            fieldName.setDisable(!update);
            fieldEmail.setDisable(!update);
            radioGenderM.setDisable(!update);
            radioGenderF.setDisable(!update);
            fieldPassword.setDisable(!update);
            fieldNationality.setDisable(!update);
            fieldVehicle.setDisable(!update);
            update = !update;
        });

        // search for the profile and display them inside the fields
        buttonSearchProfile.setOnAction(e -> {
            try {
                if (isTutor) {
                    tutor = Finder.getTutor(fieldSearchProfile.getText());
                    buttonUpdateProfile.setDisable(false);
                    fieldName.setText(tutor.getName());
                    fieldEmail.setText(tutor.getEmail());
                    if (tutor.getIsMale())
                        radioGenderM.setSelected(true);
                    else
                        radioGenderF.setSelected(true);
                    fieldPassword.setText(tutor.getPassword());
                    fieldNationality.setText(tutor.getNationality());
                    fieldVehicle.setText(tutor.getVehicle());    
                } else {
                    student = Finder.getStudent(fieldSearchProfile.getText());
                    buttonUpdateProfile.setDisable(false);
                    fieldName.setText(student.getName());
                    fieldEmail.setText(student.getEmail());
                    if (student.getIsMale())
                        radioGenderM.setSelected(true);
                    else
                        radioGenderF.setSelected(true);
                    fieldPassword.setText(student.getPassword());
                    fieldNationality.setText(student.getNationality());
                    fieldVehicle.setText(student.getVehicle());    
                }
            } catch (NullPointerException ex) {
                buttonUpdateProfile.setDisable(true);
                GuiPopupWindow.display("Error", Keys.NOTFOUND);
            } catch (Exception ex) {
                buttonUpdateProfile.setDisable(true);
                GuiPopupWindow.display("Error", ex.getMessage());
            }
        });

        // internal layouts
        HBox layoutSwitch = new HBox();
        layoutSwitch.setPadding(new Insets(10));
        layoutSwitch.setAlignment(Pos.CENTER);
        layoutSwitch.getChildren().addAll(buttonTutor, buttonStudent);

        HBox layoutSearch = new HBox();
        layoutSearch.setAlignment(Pos.CENTER);
        layoutSearch.getChildren().addAll(fieldSearchProfile, buttonSearchProfile);

        VBox layoutTopProfile = new VBox();
        layoutTopProfile.setPadding(new Insets(30));
        layoutTopProfile.setAlignment(Pos.CENTER);
        layoutTopProfile.getChildren().addAll(layoutSwitch, layoutSearch);

        GridPane gridShow = new GridPane();
        gridShow.setVgap(10);
        gridShow.setHgap(10);
        gridShow.setAlignment(Pos.CENTER);
        gridShow.getChildren().addAll(labelName, labelEmail,
                                      labelGender, labelPassword,
                                      labelNationality, labelVehicle,
                                      fieldName, fieldEmail,
                                      radioGenderM, radioGenderF,
                                      fieldPassword, fieldNationality,
                                      fieldVehicle);

        // embedding layout
        layoutProfile = new VBox();
        layoutProfile.setId("layoutDesign");
        layoutProfile.setAlignment(Pos.CENTER);
        layoutProfile.getChildren().addAll(layoutTopProfile, gridShow, layoutBottomProfile);
        return layoutProfile;
    }

    /**
     * to distribute the module to tutors
     * @return
     */
    private void distribute() {
        // nodes for top layout distribute
        TextField fieldSearchDistribute = new TextField();
        fieldSearchDistribute.setPromptText("IC/Passport");
        fieldSearchDistribute.setAlignment(Pos.CENTER);

        Button buttonSearchDistribute = new Button("Find");
        buttonSearchDistribute.setId("buttonInLayout");
        buttonSearchDistribute.setAlignment(Pos.CENTER);

        // nodes for bottom layout distribute
        Label labelDistribute = new Label("Find a Tutor");
        labelDistribute.setAlignment(Pos.CENTER);

        Button buttonDistribute = new Button("Distribute");
        buttonDistribute.setDisable(true);
        buttonDistribute.setId("buttonInLayout");
        buttonDistribute.setAlignment(Pos.CENTER);

        // search for the profile then update the label
        buttonSearchDistribute.setOnAction(e -> {
            try {
                tutor = Finder.getTutor(fieldSearchDistribute.getText());
                buttonDistribute.setDisable(false);
                labelDistribute.setText("Found: " + tutor.getName());
            } catch (NullPointerException ex) {
                GuiPopupWindow.display("Error", Keys.NOTFOUND);
            } catch (Exception ex) {
                GuiPopupWindow.display("Error", ex.getMessage());
            }
        });

        // send module to tutor
        buttonDistribute.setOnAction(e -> {
            if(!tutor.getModuleReceived()) {
                Notification notification = new Notification("null", tutor.getIc(), 
                "Module Distributed", LocalDate.now(), false);
                tutor.switchModuleReceived();
                tutor.getNotification().add(notification);
                GuiPopupWindow.display("Success", "Module Distributed");
                try {
                    Finder.database.saveTutor(tutor);
                    Finder.database.newNotification(notification);
                } catch (SQLException ex) {
                    GuiPopupWindow.display("Error", ex + Keys.CANNOTSAVE);
                }
            } else {
                GuiPopupWindow.display("Error", "Tutor Already Received Module");
            }
            buttonDistribute.setDisable(true);
        });

        // internal layouts
        HBox layoutTopDistribute = new HBox();
        layoutTopDistribute.setAlignment(Pos.CENTER);
        layoutTopDistribute.getChildren().addAll(fieldSearchDistribute,
                                                 buttonSearchDistribute);

        VBox layoutBottomDistribute = new VBox();
        layoutBottomDistribute.setPadding(new Insets(20));
        layoutBottomDistribute.setAlignment(Pos.CENTER);
        layoutBottomDistribute.getChildren().addAll(labelDistribute, buttonDistribute);

        // embedding layout
        layoutDistribute = new VBox();
        layoutDistribute.setId("layoutDesign");
        layoutDistribute.setAlignment(Pos.CENTER);
        layoutDistribute.getChildren().addAll(layoutTopDistribute,
                                              layoutBottomDistribute);
    }

    /**
     * to show active schedule and past schedule (history)
     * @param isSchedule
     * @return
     */
    private void scheduleAndHistory(boolean isSchedule) {
        isTutor = true;
        
        // top part
        Button buttonTutor = new Button("Tutor");
        Button buttonStudent = new Button("Student");
        buttonTutor.setAlignment(Pos.CENTER);
        buttonStudent.setAlignment(Pos.CENTER);
        buttonTutor.setId("buttonSwitch");
        buttonStudent.setId("buttonSwitch");
        buttonTutor.setDisable(true);
        
        // middle part
        TextField fieldSearch = new TextField();
        fieldSearch.setAlignment(Pos.CENTER);
        fieldSearch.setPromptText("Tutor's IC/Passport");

        Button buttonSearch = new Button("Find");
        buttonSearch.setId("buttonInLayout");
        buttonSearch.setAlignment(Pos.CENTER);

        // bottom part for student
        ChoiceBox<Schedule> choiceSchedules = new ChoiceBox<>();
        Button buttonDone = new Button("Done");
        buttonDone.setDisable(true);
        buttonDone.setId("buttonInLayout");
        buttonDone.setAlignment(Pos.CENTER);

        // search the required user and update the tables
        buttonSearch.setOnAction(e -> {
            try {
                if (isSchedule) {
                    if (isTutor) {
                        tutor = Finder.getTutor(fieldSearch.getText());
                        schedules = checkSchedule(tutor);
                        tableSchedule.getItems().clear();
                        tableSchedule.getItems().addAll(schedules);
                    } else {
                        buttonDone.setDisable(false);
                        student = Finder.getStudent(fieldSearch.getText());
                        schedules = checkSchedule(student);
                        choiceSchedules.getItems().clear();
                        for (Schedule schedule : schedules) {
                            choiceSchedules.getItems().addAll(schedule);
                        }
                        choiceSchedules.setValue(schedules.peek());

                        buttonDone.setOnAction(ex -> {
                            try {
                                if (choiceSchedules.getValue() != null) {
                                    student.finishStudy(choiceSchedules.getValue().getDate(),
                                                        choiceSchedules.getValue().getTime());
                                    GuiPopupWindow.display("Success", "Student: " + 
                                                           choiceSchedules.getValue().getStudentIc() +
                                                           "\nDone the class");
                                    Finder.database.updateSchedule(DBI.SCHEDULE_ISEXECUTE, "true", choiceSchedules.getValue());
                                    schedules = checkSchedule(student);
                                    choiceSchedules.getItems().clear();
                                    choiceSchedules.getItems().addAll(schedules);
                                    tableSchedule.getItems().clear();
                                    tableSchedule.getItems().addAll(schedules);
                                }
                            } catch (SQLException exx) {
                                GuiPopupWindow.display("Error", Keys.CANNOTSAVE);
                            } catch (NullPointerException exx) {
                                GuiPopupWindow.display("Error", Keys.NOTFOUND);
                            }
                        });
                        
                        choiceSchedules.getItems().clear();
                        choiceSchedules.getItems().addAll(schedules);
                        tableSchedule.getItems().clear();
                        tableSchedule.getItems().addAll(schedules);
                    }
                } else {
                    if (isTutor) {
                        schedules = Finder.getTutor(fieldSearch.getText()).getSchedules();
                        tableHistory.getItems().clear();
                        tableHistory.getItems().addAll(schedules);
                    } else {
                        tableHistory.getItems().clear();
                        student = Finder.getStudent(fieldSearch.getText());
                        for (Schedule schedule : CheckSchedule.bookedDate) {
                            if (schedule.getStudentIc().equals(student.getIc()))
                                tableHistory.getItems().add(schedule);
                        }
                        for (Schedule schedule : CheckSchedule.passedDate) {
                            if (schedule.getStudentIc().equals(student.getIc()))
                                tableHistory.getItems().add(schedule);
                        }
                    }
                }
            } catch (NullPointerException ex) {
                GuiPopupWindow.display("Error", Keys.NOTFOUND);
            } catch (Exception ex) {
                GuiPopupWindow.display("Error", ex.getMessage());
            }
        });

        // set the search to tutors only
        buttonTutor.setOnAction(e -> {
            buttonDone.setDisable(true);
            isTutor = true;
            fieldSearch.setPromptText("Tutor's IC/Passport");
            buttonTutor.setDisable(true);
            buttonStudent.setDisable(false);
        });

        // set the search to students only
        buttonStudent.setOnAction(e -> {
            buttonDone.setDisable(true);
            isTutor = false;
            fieldSearch.setPromptText("Student's IC/Passport");
            buttonStudent.setDisable(true);
            buttonTutor.setDisable(false);
        });

        // internal layouts for history
        HBox layoutTop = new HBox();
        layoutTop.setPadding(new Insets(20));
        layoutTop.setAlignment(Pos.CENTER);
        layoutTop.getChildren().addAll(buttonTutor, buttonStudent);

        HBox layoutMiddle = new HBox();
        layoutMiddle.setPadding(new Insets(20));
        layoutMiddle.setAlignment(Pos.CENTER);
        layoutMiddle.getChildren().addAll(fieldSearch, buttonSearch);

        VBox layoutBottom = new VBox();
        layoutBottom.setPadding(new Insets(20));
        layoutBottom.setAlignment(Pos.CENTER_RIGHT);
        layoutBottom.getChildren().addAll(choiceSchedules, buttonDone);

        // embedding layout
        if (isSchedule) {
            showSchedule();
            layoutSchedule = new VBox();
            layoutSchedule.setId("layoutDesign");
            layoutSchedule.setAlignment(Pos.CENTER);
            layoutSchedule.getChildren().addAll(layoutTop, layoutMiddle, 
                                    new Label("Upcoming Class(es):") , tableSchedule, layoutBottom);
        } else {
            showHistory();
            layoutHistory = new VBox();
            layoutHistory.setId("layoutDesign");
            layoutHistory.setAlignment(Pos.CENTER);
            layoutHistory.getChildren().addAll(layoutTop, layoutMiddle, tableHistory);
        }
    }

    /**
     * to display the schedule table
     * @return
     */
    private void showSchedule() {
        tableSchedule = new TableView<>();
        TableColumn<Schedule, LocalDate> columnDate = new TableColumn<>("Date");
        TableColumn<Schedule, LocalTime> columnTime = new TableColumn<>("Time");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        
        tableSchedule.getColumns().add(columnDate);
        tableSchedule.getColumns().add(columnTime);
        tableSchedule.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableSchedule.setPlaceholder(new Label("No Data"));
        tableSchedule.setMaxWidth(400);
        tableSchedule.setPrefHeight(100);
    }

    /**
     * to check whether the schedule is active or not and return the active ones
     * @param <E>
     * @param student
     * @return
     */    
    private <E> PriorityQueue<Schedule> checkSchedule(Person person) {
        schedules = new PriorityQueue<>();
        for (Schedule schedule : person.getSchedules()) {
            if (!schedule.getIsExecute())
                schedules.add(schedule);
        }
        return schedules;
    }

    /**
     * to display history table
     * @return
     */
    private void showHistory() {
        tableHistory = new TableView<>();
        TableColumn<Schedule, LocalDate> columnDate = new TableColumn<>("Date");
        TableColumn<Schedule, LocalTime> columnTime = new TableColumn<>("Time");
        TableColumn<Schedule, Boolean> columnExecute = new TableColumn<>("Executed");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        columnExecute.setCellValueFactory(new PropertyValueFactory<>("isExecute"));

        tableHistory.getColumns().add(columnDate);
        tableHistory.getColumns().add(columnTime);
        tableHistory.getColumns().add(columnExecute);
        tableHistory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableHistory.setPlaceholder(new Label("No Data"));

        tableHistory.setMaxWidth(525);
        tableHistory.setPrefHeight(350);
    }

    /**
     * to show report(tutors) and payment(students)
     * @param isReport
     */
    private void reportAndPayment(boolean isReport) {
        TextField fieldSearch = new TextField();
        fieldSearch.setPromptText("IC/Passport");
        fieldSearch.setAlignment(Pos.CENTER);

        Button buttonSearch = new Button("Find");
        buttonSearch.setId("buttonInLayout");
        buttonSearch.setAlignment(Pos.CENTER);

        // search for the users
        buttonSearch.setOnAction(e -> {
            try {
                if (isReport) {
                    tutor = Finder.getTutor(fieldSearch.getText());
                    tableReport.getItems().clear();
                    tableReport.getItems().addAll(tutor.getReports());
                } else {
                    tablePayment.getItems().clear();
                    tablePayment.getItems().addAll(Finder.getStudent(fieldSearch.getText()).getPaymentLog());
                }    
            } catch (NullPointerException ex) {
                GuiPopupWindow.display("Error", Keys.NOTFOUND);
            }
        });

        // internal layout
        HBox layoutTop = new HBox();
        layoutTop.setPadding(new Insets(20));
        layoutTop.setAlignment(Pos.CENTER);
        layoutTop.getChildren().addAll(fieldSearch, buttonSearch);

        if (isReport) {
            showReport();
            layoutReport = new VBox();
            layoutReport.setId("layoutDesign");
            layoutReport.setAlignment(Pos.CENTER);
            layoutReport.getChildren().addAll(layoutTop, tableReport);
        } else {
            showPayment();
            layoutPayment = new VBox();
            layoutPayment.setId("layoutDesign");
            layoutPayment.setAlignment(Pos.CENTER);
            layoutPayment.getChildren().addAll(layoutTop, tablePayment);
        }
    }

    /**
     * to display the report table
     * @return
     */
    private void showReport() {
        tableReport = new TableView<>();
        TableColumn<Report, Integer> columnWeek = new TableColumn<>("Week");
        columnWeek.setCellValueFactory(new PropertyValueFactory<>("week"));

        tableReport.getColumns().add(columnWeek);
        tableReport.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableReport.setPlaceholder(new Label("No Data"));

        tableReport.setMaxWidth(300);
        tableReport.setPrefHeight(350);
    }

    /**
     * to display the payment table
     * @return
     */
    private void showPayment() {
        tablePayment = new TableView<>();
        TableColumn<Payment, Float> columnTotal = new TableColumn<>("Total Price");
        TableColumn<Payment, Float> columnPaid = new TableColumn<>("Paid Amount");
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        columnPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));

        tablePayment.setFixedCellSize(70);
        tablePayment.getColumns().add(columnTotal);
        tablePayment.getColumns().add(columnPaid);
        tablePayment.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablePayment.setPlaceholder(new Label("No Data"));

        tablePayment.setMaxWidth(400);
        tablePayment.setPrefHeight(100);
    }
}