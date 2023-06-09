package cafe.cafeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static cafe.cafeshop.data.*;

public class MainDashboardController implements Initializable {

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;



    // Display username on dashboard
    @FXML
    private Label label_username;

    private Date date;

    public void displayUsername() {

        String user = username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        label_username.setText(user);

    }

    // Switch dashboard when click
    @FXML
    private Button btn_dashboard;
    @FXML
    private Button btn_inventory;

    @FXML
    private Button btn_users;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private AnchorPane user_form;

    // Button switch on dashboard
    public void switchForm(ActionEvent event) {

        if (event.getSource() == btn_dashboard) {
            dashboard_form.setVisible(true);
//            inventory_form.setVisible(false);
//            menu_form.setVisible(false);
            user_form.setVisible(false);

            dashboardDisplayNC();
//            dashboardDisplayTI();
//            dashboardTotalI();
//            dashboardNSP();
//            dashboardIncomeChart();
//            dashboardCustomerChart();

        } else if (event.getSource() == btn_inventory) {
//            dashboard_form.setVisible(false);
//            inventory_form.setVisible(true);
//            menu_form.setVisible(false);
//            customers_form.setVisible(false);
//
//            inventoryTypeList();
//            inventoryStatusList();
//            inventoryShowData();
//        } else if (event.getSource() == menu_btn) {
//            dashboard_form.setVisible(false);
//            inventory_form.setVisible(false);
//            menu_form.setVisible(true);
//            customers_form.setVisible(false);
//
//            menuDisplayCard();
//            menuDisplayTotal();
//            menuShowOrderData();
        } else if (event.getSource() == btn_users) {
            dashboard_form.setVisible(false);
            //inventory_form.setVisible(false);
            //menu_form.setVisible(false);
            user_form.setVisible(true);

            userShowData();
        }

    }

    // Show user in table view

    public ObservableList<usersData> userDataList() {

        ObservableList<usersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            usersData cData;

            while (result.next()) {
                cData = new usersData(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("question"),
                        result.getString("answer"),
                        result.getDate("date"));


                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<usersData> userListData;

    @FXML
    private TableView<usersData> users_table_view;
    @FXML
    private TableColumn<usersData, String> customers_col_id;
    @FXML
    private TableColumn<usersData, String> customers_col_name;
    @FXML
    private TableColumn<usersData, String> customers_col_password;
    @FXML
    private TableColumn<usersData, String> customers_col_question;
    @FXML
    private TableColumn<usersData, String> customers_col_answer;
    @FXML
    private TableColumn<usersData, Date> customers_col_date;

    // This will show all user in table view
    public void userShowData() {

        userListData = userDataList();
        //ObservableList<usersData> list = this.userDataList();


        this.customers_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.customers_col_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.customers_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.customers_col_question.setCellValueFactory(new PropertyValueFactory<>("question"));
        this.customers_col_answer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        this.customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        this.users_table_view.setItems(userListData);
    }

    // End table view

    // Count user
    @FXML
    private Label count_users;
    public void dashboardDisplayNC() {

        String sql = "SELECT COUNT(id) FROM users";
        connect = database.connectDB();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            count_users.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Log out button
    @FXML
    private Button logout_btn;
    public void logout() {

        try {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT
                Parent root = FXMLLoader.load(getClass().getResource("login-form.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Cafe Shop Management System");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // End log out button

    // For select data to be edit
    @FXML
    private TextField sa_username;
    @FXML
    private TextField sa_password;
    @FXML
    private ComboBox sa_question;
    @FXML
    private TextField sa_answer;

    // Question list
    private String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "what is your birth date?"};
    // Loop question
    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        sa_question.setItems(listData);
    }

    public void usersSelectData() {

        usersData nuserData = users_table_view.getSelectionModel().getSelectedItem();
        int num = users_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        //userListData = userDataList();

        sa_username.setText(nuserData.getUsername());
        sa_password.setText(nuserData.getPassword());
        //sa_question.setItems(nuserData.getQuestion());
        sa_answer.setText(nuserData.getAnswer());

        // Catch date and put into database
        // Locate in usersData file or class and it should be static
        data.date = String.valueOf(usersData.getDate());

        id = nuserData.getId();
    }

    public void userAddBtn() {

        if (sa_username.getText().isEmpty()
                || sa_password.getText().isEmpty()
                || sa_question.getSelectionModel().getSelectedItem() == null
                || sa_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            // Check if the username is already have
            String checkUsername = "SELECT username FROM users WHERE username = '"
                    + sa_username.getText() + "'";

            connect = database.connectDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkUsername);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(sa_username.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO users "
                            + "(username, password, question, answer, date) "
                            + "VALUES(?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, sa_username.getText());
                    prepare.setString(2, sa_password.getText());
                    prepare.setString(3, (String) sa_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, sa_answer.getText());


                    // TO GET CURRENT DATE

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    userShowData();
                    userClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void userUpdateBtn() {



        if (sa_username.getText().isEmpty()
                || sa_password.getText().isEmpty()
                || sa_question.getSelectionModel().getSelectedItem() == null
                || sa_answer.getText().isEmpty() || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select one data on the table!");
            alert.showAndWait();

        } else {



            String query = "UPDATE users SET username='"+this.sa_username.getText()+"',password='"+this.sa_password.getText()+"',question='"+this.sa_question.getSelectionModel().getSelectedItem()+"',answer='"+this.sa_answer.getText()+"',date='"+ data.date +"' WHERE id="+data.id+"";

//            String updateData = "UPDATE users SET "
//                    + "', username = '" + sa_username.getText()
//                    + "', password = '" + sa_password.getText()
//                    + "', question = '" + sa_question.getSelectionModel().getSelectedItem()
//                    +  "', answer = '" + sa_answer.getText()
//                    + "', date = '"  + data.date + "' WHERE id = '" + data.id;


            connect = database.connectDB();

            try {

                //String query = "UPDATE users SET username='"+this.sa_username.getText()+"',password='"+this.sa_password.getText()+"',question='"+this.sa_question.getSelectionModel().getSelectedItem()+"',answer='"+this.sa_answer.getText()+"',date="+ data.date +" WHERE id="+data.id+"";


                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE User id: " + data.id + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(query);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    userShowData();
                    // TO CLEAR YOUR FIELDS
                    userClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//

    public void userDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select one data on the table!");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE User ID: " + data.id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM users WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    userShowData();
                    // TO CLEAR YOUR FIELDS
                    userClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void userClearBtn() {

        sa_username.setText("");
        sa_question.getSelectionModel().clearSelection();
        sa_password.setText("");
        sa_answer.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        regLquestionList();
        //usersSelectData();

        // Call
        dashboardDisplayNC();

        // Display username on dashboard
        displayUsername();

        // Show suer data when form load
        userShowData();

    }
}
