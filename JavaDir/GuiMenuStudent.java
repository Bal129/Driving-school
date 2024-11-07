package JavaDir;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * main menu for student
 * student will be able to
 * 1. look at their profile
 * 2. choose classes
 * 3. make payment
*/

public class GuiMenuStudent {
    private Student student;
    private GuiTemplate gui = new GuiTemplate();
    private GuiNotification guiNoti;
    private GridPane layoutProfile;
    private VBox layoutAvailability;
    private VBox layoutPayment;
    private Label labelPayment = new Label();
    private Label labelPhase = new Label();
    private int notiCount;
    private ArrayList<Schedule> availableSchedule = CheckSchedule.availableDate;

    GuiMenuStudent() {}
    GuiMenuStudent(Student student) {
        this.student = student;
    }

    /**
     * display the main window to students
     * @return
     */
    public void display() {
        // initially, setup, label top and bottom
        gui.setup();
        gui.getLabelMenuTop().setText("Student");
        gui.getLabelMenuDown().setText("You Are Signing As " + student.getName());

        // setup the notification
        for (Notification notification : student.getNotification()) {
            if (!notification.getRead())
                notiCount++;
        }
        
        // all notification set to read once click the notification button
        gui.getButtonNotification().setText("Unread (" + notiCount + ")");
        gui.getButtonNotification().setOnAction(e -> {
            try {
                for (Notification notification : student.getNotification()) {
                    notification.setRead(true);
                    Finder.database.updateNotification(DBI.NOTIFICATION_READ, "true", notification.getTutorIc(), notification.getStudentIc());
                    guiNoti = new GuiNotification(student);
                    guiNoti.display();
                    gui.getButtonNotification().setText("Unread (0)");
                }
            } catch (Exception ex) {
                GuiPopupWindow.display("Error", ex.getMessage());
            }
        });

        // side button
        Button buttonProfile = new Button("Profile");
        Button buttonAvailability = new Button("Set Availability");
        Button buttonPayment = new Button("Make Payment");

        gui.getSideMenu().getChildren().addAll(buttonProfile, buttonAvailability, 
                                               buttonPayment, gui.getButtonBack());

        // setting up layouts
        profile();
        availability();
        payment();

        // embedding layout
        gui.getBorderPaneMenu().setLeft(gui.getSideMenu());
        gui.getBorderPaneMenu().setCenter(layoutProfile);
        BorderPane.setAlignment(gui.getSideMenu(), Pos.CENTER);
        BorderPane.setAlignment(layoutProfile, Pos.CENTER);
        BorderPane.setAlignment(layoutAvailability, Pos.CENTER);
        BorderPane.setAlignment(layoutPayment, Pos.CENTER);

        // side button functionality
        buttonProfile.setOnAction(e -> {
            labelPayment.setText(""+(student.getPaymentLog().getTotalPrice() - 
            student.getPaymentLog().getPaid()));
            gui.getBorderPaneMenu().setCenter(layoutProfile);
        });
        buttonAvailability.setOnAction(e -> {
            gui.getBorderPaneMenu().setCenter(layoutAvailability);
        });
        buttonPayment.setOnAction(e -> gui.getBorderPaneMenu().setCenter(layoutPayment));

        // applying scene
        Scene sceneMenu = new Scene(gui.getBorderPaneMenu());
        sceneMenu.getStylesheets().add(Keys.STYLING_FILE);

        // displaying stage
        gui.getStage().setScene(sceneMenu);
        gui.getStage().show();
    }

    /**
     * to show the profile for students
     * @return
     */
    private void profile() {
        String gender;
        String active;
        labelPayment.setText(""+(student.getPaymentLog().getTotalPrice() - 
                             student.getPaymentLog().getPaid()));
        labelPhase.setText("Phase");

        if (student.getIsMale())
            gender = "Male";
        else
            gender = "Female";

        if (student.getIsActive())
            active = "Active";
        else
            active = "Not Active";

        Phase[] phases = student.getArrayOfPhase();

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
            "TOTAL PRICE:",
            "PAYMENT DUE:",
            "CURRENT PHASE:"
        };

        String[] listValues = {
            student.getName(), 
            student.getIc(),
            student.getEmail(), 
            student.getTel(),
            gender,
            student.getPassword(), 
            active,
            student.getNationality(), 
            student.getVehicle(),
            ""+student.getPaymentLog().getTotalPrice(),
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

        labelPhase = new Label(phases[student.getCurPhase()].getPhase());
        labelPhase.setAlignment(Pos.CENTER_RIGHT);

        VBox layoutRight = new VBox();
        layoutRight.setPadding(new Insets(15));
        layoutRight.setSpacing(10);
        layoutRight.setAlignment(Pos.CENTER_LEFT);
        for (int i=0;i<listValues.length; i++) {
            layoutRight.getChildren().add(new Label(listValues[i]));
        }
        layoutRight.getChildren().addAll(labelPayment, labelPhase);

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
     * to show the availability set by tutors 
     * and pick the date and time for student to attend the class
     * @return
     */
    private void availability() {
        // display the appointments available
        ChoiceBox<Schedule> datePickerAvail = new ChoiceBox<>();
        for (Schedule schedule : availableSchedule) {
            Tutor targetTutor = Finder.getTutor(schedule.getTutorIc());
            if (targetTutor.getVehicle().equals(student.getVehicle()))
                datePickerAvail.getItems().add(schedule);
        }
        
        Button buttonSetDate = new Button("Set");
        buttonSetDate.setId("buttonInLayout");
        buttonSetDate.setAlignment(Pos.CENTER);

        // status whether the appointment set success or not
        Label labelShowStatus = new Label("Status");

        HBox layoutTopAvail = new HBox();
        layoutTopAvail.setAlignment(Pos.CENTER);
        layoutTopAvail.setPadding(new Insets(10));
        layoutTopAvail.getChildren().addAll(datePickerAvail, buttonSetDate);

        // make the appointment and set the label to success/fail
        buttonSetDate.setOnAction(e -> {
            try {
                for (int i = 0; i < availableSchedule.size(); i++) {
                    if ((datePickerAvail.getValue() != null) && 
                         availableSchedule.get(i).equals(datePickerAvail.getValue())) {
                        labelShowStatus.setText("Successful\nAppointment Made On:\n" + 
                                            "\nDate: " + availableSchedule.get(i).getDate() +
                                            "\nTime: " + availableSchedule.get(i).getTime() +
                                            "\nTutor: " + Finder.getTutor(availableSchedule.get(i).getTutorIc()).getName());
                        Finder.database.updateSchedule(DBI.SCHEDULE_STUDENT_IC, student.getIc(), availableSchedule.get(i));
                        student.chooseAppointment(availableSchedule.get(i).getTutorIc(),
                                                  availableSchedule.get(i).getDate(), 
                                                  availableSchedule.get(i).getTime());
                        break;
                    }
                }
                
                datePickerAvail.getItems().clear();
                if (!availableSchedule.isEmpty())
                    datePickerAvail.getItems().addAll(availableSchedule);    
            } catch (SQLException ex) {
                GuiPopupWindow.display("Error", Keys.CANNOTSAVE);
            }
        });

        layoutAvailability = new VBox();
        layoutAvailability.setAlignment(Pos.CENTER);
        layoutAvailability.setId("layoutDesign");
        layoutAvailability.getChildren().addAll(layoutTopAvail, labelShowStatus);
    }
    
    /**
     * to show the payment amount due and pay
     * @return
     */
    private void payment() {
        float totalPrice = student.getPaymentLog().getTotalPrice();
        float totalPaid = student.getPaymentLog().getPaid();

        Label labelAmount = new Label("Amount: RM " + (totalPrice - totalPaid));
        labelAmount.setAlignment(Pos.CENTER);

        TextField fieldPayment = new TextField();
        fieldPayment.setAlignment(Pos.CENTER);
        fieldPayment.setMaxWidth(300);
        fieldPayment.setPromptText("Payment amount");

        Button buttonPay = new Button("Pay");

        // if the student already paid the full amount, the button will be disabled
        if (student.getPaymentLog().getCompleted())
            buttonPay.setDisable(true);
        else
            buttonPay.setDisable(false);

        buttonPay.setAlignment(Pos.CENTER);
        buttonPay.setId("buttonInLayout");

        // pay the amount entered in the text field
        // if the amount is less than 0  or more than required, 
        // the program will reject the payment
        // after done paying, the program will calculate the remaining amount
        // and decide whether to disable the button or not 
        buttonPay.setOnAction(e -> {
            try {
                int paymentAmount = Integer.parseInt(fieldPayment.getText());
                if (paymentAmount > 0) {
                    if (paymentAmount <= totalPrice) {
                        student.makePayment(paymentAmount);

                        // check the remaining amount again
                        float total = student.getPaymentLog().getTotalPrice();
                        float paid = student.getPaymentLog().getPaid();
                        GuiPopupWindow.display("Error", "Amount Paid: RM " + paymentAmount);
                        labelAmount.setText("Amount: RM " + (total - paid));

                        // disable button if full payment received
                        if (student.getPaymentLog().isFinishedPaying()) {
                            student.getPaymentLog().switchCompleted();
                            buttonPay.setDisable(true);
                        } else
                            buttonPay.setDisable(false);

                        Finder.database.saveStudent(student);
                    }
                    else
                        GuiPopupWindow.display("Error", "Amount Entered More Than Due");
                } else {
                    GuiPopupWindow.display("Error", "Please Enter A Positive Number");
                }
            } catch (SQLException ex) {
                GuiPopupWindow.display("Error", Keys.CANNOTSAVE);
            }
        });

        layoutPayment = new VBox();
        layoutPayment.setSpacing(15);
        layoutPayment.setAlignment(Pos.CENTER);
        layoutPayment.setId("layoutDesign");
        layoutPayment.getChildren().addAll(labelAmount, fieldPayment, buttonPay);
    }
}