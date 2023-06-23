package cafe.cafeshop;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    //    Switch form when click
    @FXML
//    This for register panel
    private AnchorPane si_registerBtn;

    @FXML
//    This for another panel
    private AnchorPane side_form;

    @FXML
//    This for have acc button
    private Button si_haveAccBtn;

    @FXML
//    This for new password panel
    private AnchorPane su_forgotPassForm;

    @FXML
    // This for login panel
    private AnchorPane su_loginForm;

    @FXML
    // This for signup panel
    private AnchorPane su_signupForm;

    @FXML
    // This for create new panel
    private Button si_createNew;

    @FXML
    private AnchorPane creat_new_pan;

    @FXML
    private AnchorPane register_pan;

    @FXML
    private Label new_have_text;

    // Question list
    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "what is your birth date?"};
    // Loop question
    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    // For switch form between login and signup
    public  void switchForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();

    //    Check if button register is click
        if(event.getSource() == si_createNew){
            slider.setNode(side_form);
            slider.setByX(300);
            slider.setDuration(Duration.seconds(0.5));

            //slider.setNode(su_signupForm);
            //slider.setByX(0);

            slider.setOnFinished((ActionEvent e) -> {
                // Show button have account
                si_haveAccBtn.setVisible(true);

                // Hide button create new or panel
                si_createNew.setVisible(false);
                creat_new_pan.setVisible(false);

                // Show register panel
                register_pan.setVisible(true);

                // Show create new text
                new_have_text.setText("Create New Account.");

                // Hide panel new password
                su_forgotPassForm.setVisible(false);

                // Show Login form
                su_loginForm.setVisible(false);

                //Hide signup form
                su_signupForm.setVisible(true);

                regLquestionList();

            });
            slider.play();
        }else if (event.getSource() == si_haveAccBtn){
            slider.setNode(side_form);
            slider.setByX(-300);
            slider.setDuration(Duration.seconds(0.5));

            slider.setOnFinished((ActionEvent e) -> {
                // Show button have account
                si_haveAccBtn.setVisible(false);

                // Show create new panel
                creat_new_pan.setVisible(true);
                si_createNew.setVisible(true);

                // Show create new text
                new_have_text.setText("Log In To Your Account.");

                // Hide button create new or register pan
                register_pan.setVisible(false);



                // Hide panel new password
                su_forgotPassForm.setVisible(false);

                // Show Login form
                su_loginForm.setVisible(true);

                //Hide signup form
                su_signupForm.setVisible(false);

                // Show side form
                side_form.setVisible(true);
            });
        }
        slider.play();

    }

    // Register action
    @FXML
    private TextField su_username;

    @FXML
    private TextField su_password;

    @FXML
    private TextField su_answer;

    @FXML
    // This for question combo
    private ComboBox su_question;

    // Declear variable
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;
    public void regBtn() {

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            String regData = "INSERT INTO users (username, password, question, answer, date) "
                    + "VALUES(?,?,?,?,?)";
            connect = database.connectDB();

            try {
                // CHECK IF THE USERNAME IS ALREADY RECORDED
                String checkUsername = "SELECT username FROM users WHERE username = '"
                        + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 3) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 3 characters are needed");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());

                    // Covert date
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    // Message
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    // After register then clear the filed
                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    // Form will move
                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));


                    slider.setOnFinished((ActionEvent e) -> {
                        si_haveAccBtn.setVisible(false);
                        si_createNew.setVisible(true);
                    });

                    // Show Login form
                    su_loginForm.setVisible(true);

                    slider.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // End register

    // Sign Up
    @FXML
    private AnchorPane main_login;

    @FXML
    private AnchorPane main_dashboard;

    @FXML
    private Button si_loginBtn;
    @FXML
    private TextField sus_username;

    @FXML
    private TextField sus_password;
    public void loginBtn() {

        if (sus_username.getText().isEmpty() || sus_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {

            String selctData = "SELECT username, password FROM users WHERE username = ? and password = ?";

            connect = database.connectDB();

            try {

                prepare = connect.prepareStatement(selctData);
                prepare.setString(1, sus_username.getText());
                prepare.setString(2, sus_password.getText());

                result = prepare.executeQuery();
                // IF SUCCESSFULLY LOGIN, THEN PROCEED TO ANOTHER FORM WHICH IS OUR MAIN FORM
                if (result.next()) {
                    // TO GET THE USERNAME THAT USER USED
                    data.username = sus_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    // Hide login form
                    main_login.getScene().getWindow().hide();

                    // If user is true than open the dashboard
                    Parent root = FXMLLoader.load(getClass().getResource("main_dashboard.fxml"));

                    //Parent root = FXMLLoader.load(getClass().getResource("main_dashboard.fxml"));



                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    // Set the application icon.
                    stage.getIcons().add(new Image(CafeShopManagement.class.getResourceAsStream("icons8-cafe-100.png")));
                    stage.setTitle("Cafe Shop Management System Dashboard");
                    stage.setMinWidth(1000);
                    stage.setMinHeight(600);

                    stage.setScene(scene);
                    stage.show();

                    main_dashboard.setVisible(true);




                } else { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // End sign up


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // End switch form
}
