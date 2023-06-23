package cafe.cafeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

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
    private Button btn_profile;
    @FXML
    private Button btn_menu;

    @FXML
    private Button btn_costumers;
    @FXML
    private Button btn_inventory;

    @FXML
    private Button btn_users;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private AnchorPane user_form;

    @FXML
    private AnchorPane user_profile;

    @FXML
    private AnchorPane item_form;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private Label menu_total;

    @FXML
    private AnchorPane customer_form;

    // Button switch on dashboard
    public void switchForm(ActionEvent event) {

        if (event.getSource() == btn_dashboard) {
            dashboard_form.setVisible(true);
            item_form.setVisible(false);
            user_form.setVisible(false);
            item_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(false);




            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
//            dashboardIncomeChart();
//            dashboardCustomerChart();

        } else if (event.getSource() == btn_profile){
            user_profile.setVisible(true);
            dashboard_form.setVisible(false);
            user_form.setVisible(false);
            item_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(false);






        }else if (event.getSource() == btn_inventory) {
            item_form.setVisible(true);
            dashboard_form.setVisible(false);
            user_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(false);



            itemTypeList();
            itemStatusList();
            itemShowData();

        }else if (event.getSource() == btn_menu) {
            menu_form.setVisible(true);
            item_form.setVisible(false);
            dashboard_form.setVisible(false);
            user_form.setVisible(false);
            customer_form.setVisible(false);


//            menu_form.setVisible(false);menu_form
//            customers_form.setVisible(false);
//
            itemTypeList();
            itemStatusList();
            itemShowData();

//
            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == btn_costumers) {
            customer_form.setVisible(true);
            menu_form.setVisible(false);
            item_form.setVisible(false);
            dashboard_form.setVisible(false);
            user_form.setVisible(false);

            itemTypeList();
            itemStatusList();
            itemShowData();
            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == btn_users) {
            dashboard_form.setVisible(false);
            item_form.setVisible(false);
            user_profile.setVisible(false);

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

//    private ObservableList<customersData> customerListData;

    public void userID() {

        String sql = "SELECT MAX(userId) FROM customers";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(userId)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerID() {

        String sql = "SELECT MAX(userId) FROM customers";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(userId)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double totalP;


    public ObservableList<customersData> customersDataList() {

        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customersData cData;

            while (result.next()) {
                cData = new customersData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("emUsername"));

                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customersData> customersListData;
    @FXML
    private TableView<customersData> table_customView;
    @FXML
    private TableColumn<customersData, String> customer_colId;
    @FXML
    private TableColumn<customersData, String> custom_colTotal;
    @FXML
    private TableColumn<customersData, String> custom_colDate;

    @FXML
    private TableColumn<customersData, String> custome_colCashier;
    public void customersShowData() {
        customersListData = customersDataList();

        customer_colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        custom_colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        custom_colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        custome_colCashier.setCellValueFactory(new PropertyValueFactory<>("Usname"));

        table_customView.setItems(customersListData);
    }


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

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TotalI;

    @FXML
    private Label dashboard_NSP;
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

    public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";

        connect = database.connectDB();

        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";

        connect = database.connectDB();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_TotalI.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardNSP() {

        String sql = "SELECT COUNT(quantity) FROM customers";

        connect = database.connectDB();

        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(q));

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


    @FXML
    private TextField it_id;
    @FXML
    private TextField it_name;
    @FXML
    private TextField it_stock;

    @FXML
    private TextField it_price;
    @FXML
    private ComboBox it_type;

    @FXML
    private ComboBox it_status;

    @FXML
    private ImageView item_imageView;
    ;

    public void itemAddBtn() {

        if (it_name.getText().isEmpty()
                || it_type.getSelectionModel().getSelectedItem() == null
                || it_stock.getText().isEmpty()
                || it_price.getText().isEmpty()
                || it_status.getSelectionModel().getSelectedItem() == null
                || data.path == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            // CHECK PRODUCT ID
            String checkProdID = "SELECT item_name FROM items WHERE item_name = '"
                    + it_name.getText() + "'";

            connect = database.connectDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(it_name.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO items "
                            + "(item_id, item_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, it_id.getText());
                    prepare.setString(2, it_name.getText());
                    prepare.setString(3, (String) it_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, it_stock.getText());
                    prepare.setString(5, it_price.getText());
                    prepare.setString(6, (String) it_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);

                    // TO GET CURRENT DATE
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    itemShowData();
                    itemClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void itemUpdateBtn() {

        if (it_name.getText().isEmpty()
                || it_type.getSelectionModel().getSelectedItem() == null
                || it_stock.getText().isEmpty()
                || it_price.getText().isEmpty()
                || it_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            String query = "UPDATE items SET item_id='"+
                    it_id.getText()+"'," +
                    "item_name='"+ it_name.getText()+
                    "',type='"+ it_type.getSelectionModel().getSelectedItem()+
                    "',stock='"+ it_stock.getText()+
                    "',price='"+ it_price.getText()+
                    "',status='"+ it_status.getSelectionModel().getSelectedItem()+
                    "', image = '" + path +
                    "',date='"+ data.date +"' WHERE id="+data.id+"";

//            String updateData = "UPDATE items SET "+ "' item_id = " + it_id.getText()+ "', item_name = '" + it_name.getText() + "', type = '"
//                    + it_type.getSelectionModel().getSelectedItem() + "', stock = '"
//                    + it_stock.getText() + "', price = '"
//                    + it_price.getText() + "', status = '"
//                    + it_status.getSelectionModel().getSelectedItem() + "', image = '"
//                    + path + "', date = '"
//                    + data.date + "' WHERE id = " + data.id;

            connect = database.connectDB();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Item ID: " + it_name.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(query);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    itemShowData();
                    // TO CLEAR YOUR FIELDS
                    itemClearBtn();
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

    public void itemDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Item ID: " + data.id + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM items WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    itemShowData();
                    // TO CLEAR YOUR FIELDS
                    itemClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void itemClearBtn() {

        it_id.setText("");
        it_name.setText("");
        it_type.getSelectionModel().clearSelection();
        it_stock.setText("");
        it_price.setText("");
        it_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        item_imageView.setImage(null);

    }

    // LETS MAKE A BEHAVIOR FOR IMPORT BTN FIRST
    @FXML
    private AnchorPane main_form;
    private Image image;


    // TO SHOW DATA ON OUR TABLE
    @FXML
    private TableView<itemsData> item_tableView;

    @FXML
    private TableColumn<itemsData, String> item_col_id;

    @FXML
    private TableColumn<itemsData, String> item_col_idNo;

    @FXML
    private TableColumn<itemsData, String> item_col_name;

    @FXML
    private TableColumn<itemsData, String> item_col_type;

    @FXML
    private TableColumn<itemsData, String> item_col_stock;

    @FXML
    private TableColumn<itemsData, String> item_col_price;

    @FXML
    private TableColumn<itemsData, String> item_col_status;

    @FXML
    private TableColumn<itemsData, String> item_col_date;


    public void itemImportBtn() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            item_imageView.setImage(image);
        }
    }

    // MERGE ALL DATAS
    public ObservableList<itemsData> itemsDataList() {

        ObservableList<itemsData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM items";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            itemsData itemData;

            while (result.next()) {

                itemData = new itemsData(result.getInt("id"),
                        result.getString("item_id"),
                        result.getString("item_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(itemData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<itemsData> itemListData;



    public void itemShowData() {
        itemListData = itemsDataList();

        item_col_idNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        item_col_id.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        item_col_name.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        item_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        item_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        item_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        item_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        item_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        item_tableView.setItems(itemListData);

    }

    public void itemSelectData() {

        itemsData itemData = item_tableView.getSelectionModel().getSelectedItem();
        int num = item_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        it_id.setText(itemData.getItem_id());
        it_name.setText(itemData.getItem_name());
        it_stock.setText(String.valueOf(itemData.getStock()));
        it_price.setText(String.valueOf(itemData.getPrice()));

        data.path = itemData.getImage();

        String path = "File:" + itemData.getImage();
        data.date = String.valueOf(itemData.getDate());
        data.id = itemData.getId();

        image = new Image(path, 120, 127, false, true);
        item_imageView.setImage(image);
    }

    private String[] typeList = {"Meals", "Drinks"};

    public void itemTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        it_type.setItems(listData);
    }

    private String[] statusList = {"Available", "Unavailable"};

    public void itemStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        it_status.setItems(listData);

    }


    @FXML
    private GridPane menu_gridPane;

    private ObservableList<itemsData> cardListData = FXCollections.observableArrayList();

    public ObservableList<itemsData> menuGetData() {

        String sql = "SELECT * FROM items";

        ObservableList<itemsData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            itemsData itemD;

            while (result.next()) {
                itemD = new itemsData(result.getInt("id"),
                        result.getString("item_id"),
                        result.getString("item_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(itemD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }
    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("itemCard.fxml"));
                AnchorPane pane = load.load();
                itemCardController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 2) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private TableView<itemsData> menu_tableView;

    @FXML
    private TableColumn<itemsData, String> menu_colItemName;

    @FXML
    private TableColumn<itemsData, String> menu_colItemQulity;

    @FXML
    private TableColumn<itemsData, String> menu_colPrice;
    @FXML
    private Label menu_change;

    private int getid;
    @FXML
    private TextField menu_amount;
    private double amount;
    private double change;

    private int cID;

    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customers WHERE userId = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("$" + totalP);
    }

    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("$" + change);
            }
        }
    }

    public void menuPayBtn() {

        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, emUsername) "
                    + "VALUES(?,?,?,?)";

            connect = database.connectDB();

            try {

                if (amount == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {

                        customerID();
                        menuGetTotal();

                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);

                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();

                        menuShowOrderData();

                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void menuRemoveBtn() {

        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customers WHERE id = " + getid;
            connect = database.connectDB();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuReceiptBtn() {

        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\cafeShopManagementSystem\\src\\cafeshopmanagementsystem\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.0");
        menu_amount.setText("");
        menu_change.setText("$0.0");
    }

    public ObservableList<itemsData> menuGetOrder() {
        customerID();
        ObservableList<itemsData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customers WHERE userId = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            itemsData prod;

            while (result.next()) {
                prod = new itemsData(result.getInt("id"),
                        result.getString("item_id"),
                        result.getString("item_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<itemsData> menuOrderListData;

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_colItemName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        menu_colItemQulity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }

    public void menuSelectOrder() {
        itemsData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        regLquestionList();
        //usersSelectData();

        // Display list of type and status
        itemTypeList();
        itemStatusList();

        // Display menu total
        menuDisplayTotal();


        // Call
        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();

        // Show item card
        menuDisplayCard();

        // Show menu order
        menuShowOrderData();

        // Display username on dashboard
        displayUsername();

        // Show item data
        itemShowData();

        // Show suer data when form load
        userShowData();

        // Show customer
        customersShowData();

    }


}
