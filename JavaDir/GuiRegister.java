package JavaDir;

import java.sql.SQLException;
import java.util.TreeMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

/**
 * to register tutor/student
*/

public class GuiRegister {
    private Stage stage;
    private char selectedMode;
    private boolean gender;
    private GuiMenuStudent guiStudent;
    private GuiMenuTutor guiTutor;

    private TreeMap<String, Float> mapVehicleClasses = new TreeMap<>();

    private Label labelName = new Label("Name");
    private Label labelIc = new Label("IC/Passport");
    private Label labelEmail = new Label("Email");
    private Label labelTel = new Label("Contact Number");
    private Label labelGender = new Label("Gender");
    private Label labelPassword = new Label("Password");
    private Label labelNationality = new Label("Nationality");
    private Label labelVehicle = new Label("Vehicle");

    private TextField fieldName = new TextField();
    private TextField fieldIc = new TextField();
    private TextField fieldEmail = new TextField();
    private TextField fieldTel = new TextField();
    private RadioButton radioGenderM = new RadioButton("Male");
    private RadioButton radioGenderF = new RadioButton("Female");
    private ToggleGroup radioGender = new ToggleGroup();
    private PasswordField fieldPassword = new PasswordField();
    private TextField fieldNationality = new TextField();
    private ChoiceBox<String> fieldVehicle = new ChoiceBox<>();

    GuiRegister() {}
    GuiRegister(char selectedMode) {
        this.selectedMode = selectedMode;
    }

    /**
     * display the window
     * @return
     */
    public void display(){
        String text = "Person";
        if      (selectedMode == 't') text = "Tutor";
        else if (selectedMode == 's') text = "Student";

        stage = new Stage();
        stage.getIcons().add(new Image(Keys.ICON));
        stage.setResizable(false);
        stage.setTitle("Register as " + text);
        stage.setOnCloseRequest(e-> {
            e.consume();
            GuiConfirmWindow guiConfirm = new GuiConfirmWindow();
            guiConfirm.display(GuiConfirmWindow.EXIT_TITLE, GuiConfirmWindow.EXIT_MESSAGE);
            if (guiConfirm.isTrue()) {
                stage.close();
            }
        });

        // setup map for vehicle classes
        mapVehicleClasses.put("B2", (float) 371);
        mapVehicleClasses.put("B", (float) 954);
        mapVehicleClasses.put("D", (float) 1219);
        mapVehicleClasses.put("DA", (float) 1325);
        mapVehicleClasses.put("D&B2", (float) 1590);
        mapVehicleClasses.put("DA&B2", (float) 1696);
        mapVehicleClasses.put("D&B", (float) 2173);
        mapVehicleClasses.put("DA&B", (float) 2279);
        mapVehicleClasses.put("GDL", (float) 265);
        mapVehicleClasses.put("PSV Bus", (float) 1060);
        mapVehicleClasses.put("PSV Taxi", (float) 477);
        mapVehicleClasses.put("PSV Van", (float) 265);
        mapVehicleClasses.put("Conductor", (float) 265);

        // labels for register scene
        labelName.setId("layoutFont");
        labelIc.setId("layoutFont");
        labelEmail.setId("layoutFont");
        labelTel.setId("layoutFont");
        labelGender.setId("layoutFont");
        labelPassword.setId("layoutFont");
        labelNationality.setId("layoutFont");
        labelVehicle.setId("layoutFont");
        radioGenderM.setId("layoutFont");
        radioGenderF.setId("layoutFont");

        // fields for register scene
        fieldName.setPromptText("Enter Name");
        fieldIc.setPromptText("Enter IC/Passport");
        fieldTel.setPromptText("Enter Contact Number");
        fieldEmail.setPromptText("Enter Email");
        fieldPassword.setPromptText("Enter Password");
        fieldNationality.setPromptText("Enter Nationality");
        radioGenderM.setToggleGroup(radioGender);
        radioGenderF.setToggleGroup(radioGender);
        radioGenderM.setSelected(true);

        fieldVehicle.getItems().addAll(mapVehicleClasses.keySet());
        fieldVehicle.setValue("B2");
        fieldVehicle.setPrefWidth(225);

        if (radioGenderM.isSelected())
            gender = true;
        else
            gender = false;

        // button for register scene
        Button buttonRegister = new Button("Register");
        buttonRegister.setDefaultButton(true); // pressing enter key will launch this button
        buttonRegister.setOnAction(e -> {
            register();
        });

        // button back to select mode
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            GuiSelect.display();
            stage.close();
        });
        
        // setting up grid for register scene
        GridPane gridRegister = new GridPane();

        GridPane.setConstraints(labelName, 0, 0);
        GridPane.setConstraints(labelIc, 0, 1);
        GridPane.setConstraints(labelEmail, 0, 2);
        GridPane.setConstraints(labelTel, 0, 3);
        GridPane.setConstraints(labelGender, 0, 4);
        GridPane.setConstraints(labelPassword, 0, 6);
        GridPane.setConstraints(labelNationality, 0, 7);
        GridPane.setConstraints(labelVehicle, 0, 8);
        GridPane.setRowSpan(labelGender, 2);

        GridPane.setConstraints(fieldName, 1, 0);
        GridPane.setConstraints(fieldIc, 1, 1);
        GridPane.setConstraints(fieldEmail, 1, 2);
        GridPane.setConstraints(fieldTel, 1, 3);
        GridPane.setConstraints(radioGenderM, 1, 4);
        GridPane.setConstraints(radioGenderF, 1, 5);
        GridPane.setConstraints(fieldPassword, 1, 6);
        GridPane.setConstraints(fieldNationality, 1, 7);
        GridPane.setConstraints(fieldVehicle, 1, 8);

        GridPane.setConstraints(buttonRegister, 1, 9);
        GridPane.setConstraints(buttonBack, 0, 9);

        // add all nodes inside the grid in register scene
        gridRegister.setAlignment(Pos.CENTER);
        gridRegister.setVgap(20);
        gridRegister.setHgap(10);
        gridRegister.getChildren().addAll(labelName, labelIc, labelEmail, labelTel,
                                          labelGender, labelPassword,
                                          labelNationality, labelVehicle,
                                          fieldName, fieldIc, fieldEmail, fieldTel,
                                          radioGenderM, radioGenderF,
                                          fieldPassword, fieldNationality,
                                          fieldVehicle, buttonRegister, buttonBack);

        Scene sceneRegister = new Scene(gridRegister, 425, 475);
        sceneRegister.getStylesheets().add(Keys.STYLING_FILE);

        stage.setScene(sceneRegister);
        stage.show();
    }

    private void register() {
        try {
            // checking whether the user insert non-empty textfield(s) or not
            if (fieldName.getText().trim().isEmpty() ||
                fieldIc.getText().trim().isEmpty() ||
                fieldEmail.getText().trim().isEmpty() ||
                fieldTel.getText().trim().isEmpty() ||
                fieldPassword.getText().trim().isEmpty() ||
                fieldNationality.getText().trim().isEmpty())
                GuiPopupWindow.display("Error", "Please enter the values");
            else{
                if (selectedMode == 't') {
                    if (Finder.getTutor(fieldIc.getText()) != null)
                        GuiPopupWindow.display("Error", "Account Already Exists");
                    Tutor tutor = new Tutor(fieldName.getText(), fieldIc.getText(), fieldEmail.getText(),
                                            fieldTel.getText(), gender, fieldPassword.getText(), true,
                                            fieldNationality.getText(), fieldVehicle.getValue());
                    Finder.database.saveTutor(tutor);
                    guiTutor = new GuiMenuTutor(tutor);
                    guiTutor.display();
                }
                else if (selectedMode == 's') {
                    if (Finder.getStudent(fieldIc.getText()) != null)
                        GuiPopupWindow.display("Error", "Account Already Exists");
                    Student student = new Student(fieldName.getText(), fieldIc.getText(), fieldEmail.getText(),
                                                  fieldTel.getText(), gender, fieldPassword.getText(), true,
                                                  fieldNationality.getText(), fieldVehicle.getValue(),
                                                  new Payment(mapVehicleClasses.get(fieldVehicle.getValue())),
                                                  new Phase[3], 0);
                    Finder.database.saveStudent(student);
                    guiStudent = new GuiMenuStudent(student);
                    guiStudent.display();
                }
                stage.close();
            }
        } catch(SQLException ex) {
            GuiPopupWindow.display("Error", ex.getMessage());
        } 
    }
}
